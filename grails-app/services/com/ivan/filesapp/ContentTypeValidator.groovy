package com.ivan.filesapp

import com.ivan.filesapp.Validator
import org.springframework.stereotype.Component

@Component
class ContentTypeValidator implements Validator {

    private static def allowedTypes = [
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
            "application/vnd.ms-word.document.macroEnabled.12",
            "application/vnd.ms-word.template.macroEnabled.12"
    ]

    @Override
    def isValid(file, response, Map responseBody) {
        if (isValid(file.getContentType())) {
            return true
        }

        response.status = 415
        responseBody.actualContentType = file.getContentType()
        responseBody.expectedContentType = allowedTypes
        return false
    }

    static def isValid(String contentType) {
        allowedTypes.contains(contentType)
    }

    static def isNotValid(String contentType) {
        !isValid(contentType)
    }

    static def getAllowedTypes() {
        return allowedTypes
    }
}
