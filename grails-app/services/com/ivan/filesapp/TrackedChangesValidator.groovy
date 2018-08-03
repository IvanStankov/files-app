package com.ivan.filesapp

import com.aspose.words.Document
import com.aspose.words.UnsupportedFileFormatException
import org.apache.commons.logging.LogFactory

import org.springframework.stereotype.Component
import org.springframework.core.annotation.Order;

@Component
@Order(2)
class TrackedChangesValidator implements Validator {
    private static final logger = LogFactory.getLog(this)

    @Override
    def validate(file, response, Map responseBody) {
        try {
            Document document = new Document(file.getInputStream())
            if (!document.getTrackRevisions()) {
                return true
            }

            response.status = 400
            responseBody.revisionsNumber = document.getRevisions().getCount()
            return false
        } catch (UnsupportedFileFormatException e) {
            logger.error("Not an MS Word document", e)
            response.status = 415
            return false
        }
    }
}
