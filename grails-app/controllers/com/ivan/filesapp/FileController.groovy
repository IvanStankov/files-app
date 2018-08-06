package com.ivan.filesapp

import static org.springframework.http.HttpStatus.*

import org.springframework.beans.factory.annotation.Autowired

class FileController {

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

        throw new RuntimeException("aaaaaaaaaaaaaaaaaa")

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
