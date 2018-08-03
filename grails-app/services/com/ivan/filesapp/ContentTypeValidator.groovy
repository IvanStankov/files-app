package com.ivan.filesapp

import com.ivan.filesapp.Validator
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import org.springframework.core.annotation.Order;

@Component
@Order(1)
class ContentTypeValidator implements Validator {

    private static def allowedTypes = [
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
            "application/vnd.ms-word.document.macroEnabled.12",
            "application/vnd.ms-word.template.macroEnabled.12"
    ]

    // what about rtf extension?
    private static def allowedExtensions = ["doc", "docx", "docm", "dot", "dotx", "dotm"]

    @Override
    def validate(file, response, Map responseBody) {
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
