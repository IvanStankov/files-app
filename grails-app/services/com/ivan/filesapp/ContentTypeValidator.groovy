package com.ivan.filesapp

import org.apache.commons.io.FilenameUtils
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class ContentTypeValidator implements Validator {

    private static def allowedTypes = [
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
            "application/vnd.ms-word.document.macroEnabled.12",
            "application/vnd.ms-word.template.macroEnabled.12"
    ]

    private static def allowedExtensions = ["docx", "docm", "dotx", "dotm"]

    @Override
    def validate(file, response, responseBody) {
        if (this.isValidFile(file)) {
            return true
        }

        response.status = 415
        responseBody.actualContentType = file.getContentType()
        responseBody.expectedContentType = allowedTypes
        return false
    }

    private boolean isValidFile(file) {
        if (allowedTypes.contains(file.getContentType())) {
            return true;
        }

        def extension = FilenameUtils.getExtension(file.getOriginalFilename())
        return allowedExtensions.contains(extension)
    }
}
