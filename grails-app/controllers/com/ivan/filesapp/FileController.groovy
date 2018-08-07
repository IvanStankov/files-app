package com.ivan.filesapp

import org.apache.commons.logging.LogFactory

import static org.springframework.http.HttpStatus.*

import org.springframework.beans.factory.annotation.Autowired

class FileController {
    private static final logger = LogFactory.getLog(this)

    static responseFormats = ['json']

    static constraints = {
        targetFile maxSize: 1024 * 1024 * 20 // 20 Mb max
    }

    @Autowired
    List<Validator> validators

    def save() {
        def file = request.getFile("targetFile")

        if (file.empty) {
            render status: BAD_REQUEST
        }

        def fileUploadResult = [:]

        for (Validator validator : validators) {
            if (!validator.validate(file, response, fileUploadResult)) {
                respond fileUploadResult
                return
            }
        }

        render status: OK
    }
}
