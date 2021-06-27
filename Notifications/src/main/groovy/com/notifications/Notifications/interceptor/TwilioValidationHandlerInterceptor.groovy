package com.notifications.Notifications.interceptor

import com.notifications.Notifications.annotation.ValidateTwilioSignature
import com.notifications.Notifications.config.SMSConfig
import com.twilio.security.RequestValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.UriComponentsBuilder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets

@Component
class TwilioValidationHandlerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(TwilioValidationHandlerInterceptor.class)

    private final String webhookURLOverride
    private final RequestValidator twilioValidator

    @Autowired
    TwilioValidationHandlerInterceptor(SMSConfig smsConfig) {
        this.webhookURLOverride = smsConfig.webhookUrlOverride
        this.twilioValidator = new RequestValidator(smsConfig.authToken)
    }

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object requestHandler) throws Exception {
        if(requestHandler instanceof HandlerMethod) {
            if(((HandlerMethod) requestHandler).getMethodAnnotation(ValidateTwilioSignature.class) == null) {
                return true
            }
        } else {
            return true
        }

        var signatureHeader = request.getHeader("X-Twilio-Signature")
        var validationURL = normalizedRequestUrl(request)

        switch (request.getMethod().toUpperCase()) {
            case "GET":
            case "POST":

                var validationParameters = extractOnlyBodyParams(request, validationURL)

                if(twilioValidator.validate(validationURL, validationParameters, signatureHeader)) {
                    return true
                } else {
                    logger.warn("Validation failed for {} request to {}", request.getMethod(), validationURL)
                    return validationFailedResponse(response)
                }

            default:
                return validationFailedResponse(response)
        }
    }

    // This method exists to do a couple of things:
    //  1. Take the first value for each param. x-www-form-urlencoded requests can theoretically specify
    //     multiple values for the same parameter. Twilio doesn't do this, and the RequestValidator expects
    //     a `Map<String,String>` so we just take the first value of any parameter found.
    //  2. Work around a quirk in HttpServletRequest. For validation we only need to pass the parameters
    //     from the request *body*, but HSR.getParameterMap() *also* includes any parameters found in the
    //     query String, which will cause validation to fail if present. For POST requests, Twilio will not
    //     add any queryString params, but it's perfectly possible for a user to do that by including them
    //     in the webhook URL.
    private HashMap<String, String> extractOnlyBodyParams(HttpServletRequest request, String validationUrl) {

        var allRequestParameters = request.getParameterMap()
        var queryStringParams = UriComponentsBuilder.fromUriString(validationUrl).build().getQueryParams()

        var validationParams = new HashMap<String, String>()

        // loop through _all_ parameters, only keeping ones which _don't_ appear in the query string.
        // request.getParameterMap() decodes query param values, but UriComponents _doesn't_ so we need to
        // cater for that in our comparison
        allRequestParameters.forEach((name, values) -> {
            for(String value: values) {
                if(!(queryStringParams.containsKey(name) &&
                     queryStringParams.get(name).contains(URLEncoder.encode(value, StandardCharsets.US_ASCII)))) {
                    validationParams.put(name, value)
                }
            }
        })

        return validationParams
    }


    private String normalizedRequestUrl(HttpServletRequest request) {
        String queryStringPart = ""
        if (request.getQueryString() != null) {
            queryStringPart = "?" + request.getQueryString()
        }

        if (webhookURLOverride == null || webhookURLOverride.isBlank()) {
            return request.getRequestURL().toString() + queryStringPart
        }

        return webhookURLOverride + queryStringPart
    }

    private boolean validationFailedResponse(HttpServletResponse response) throws IOException{
        response.setStatus(401)
        response.getWriter().print("Unauthorized")
        return false
    }
}
