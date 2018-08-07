package com.ivan.filesapp

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.http.HttpStatus.*

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

    def handleException(Exception e) {
        StringWriter writer = new StringWriter()
        e.printStackTrace(new PrintWriter(writer))

        respond([stacktrace: writer.toString()], status: INTERNAL_SERVER_ERROR)
    }
}
