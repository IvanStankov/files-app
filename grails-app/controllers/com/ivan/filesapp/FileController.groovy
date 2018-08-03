package com.ivan.filesapp

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.springframework.beans.factory.annotation.Autowired

class FileController {

    static responseFormats = ['json']

    static constraints = {
        targetFile maxSize: 1024 * 1024 * 20 // 20 Mb max
    }

    @Autowired
    List<Validator> validators

    def show() {
        def id = params.id as int

        if (id < 20) {
            render(status: 415)
            return
        }

        def item = [id: id, name: "John", type: "Simple"]

        respond item
    }

    def save() {

        def file = request.getFile("targetFile")

        if (file.empty) {
            response.status = 400
            return
        }

        def fileUploadResult = [:]

        for (Validator validator : validators) {
            if (!validator.isValid(file, response, fileUploadResult)) {
                respond fileUploadResult
                return
            }
        }

//        def contentType = file.getContentType()
//        if (ContentTypeValidator.isNotValid(contentType)) {
//            response.status = 415
//            def fileUploadResult = [
//                    actualContentType: contentType,
//                    expectedContentType: ContentTypeValidator.getAllowedTypes()
//            ]
//            respond fileUploadResult
//        }

    }
}
