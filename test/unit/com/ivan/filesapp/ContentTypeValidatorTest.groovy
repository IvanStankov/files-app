package com.ivan.filesapp

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class ContentTypeValidatorTest extends Specification {

    private ContentTypeValidator validator = new ContentTypeValidator()

    def "validate with content type"() {
        given:
        def file = Mock(MultipartFile)
        file.getContentType() >> contentType

        def response = [:]
        def responseBody = [:]

        when:
        def result = validator.validate(file, response, responseBody)

        then:
        assert result == expected

        if (!expected) {
            assert response.status == 415
            assert responseBody.actualContentType == contentType
            assert responseBody.expectedContentType != null && responseBody.expectedContentType.size() > 0
        }

        where:
        contentType                                                               | expected
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document" | true
        "application/vnd.openxmlformats-officedocument.wordprocessingml.template" | true
        "application/vnd.ms-word.document.macroEnabled.12"                        | true
        "application/vnd.ms-word.template.macroEnabled.12"                        | true
        "application/msword"                                                      | false
        "image/png"                                                               | false
        "application/json"                                                        | false
    }

    def "validate where content type is application/octet-stream"() {
        given:
        def file = Mock(MultipartFile)
        file.getContentType() >> "application/octet-stream"
        file.getOriginalFilename() >> originalFileName

        def response = [:]
        def responseBody = [:]

        when:
        def result = validator.validate(file, response, responseBody)

        then:
        assert result == expected

        if (!expected) {
            assert response.status == 415
            assert responseBody.actualContentType == "application/octet-stream"
            assert responseBody.expectedContentType != null && responseBody.expectedContentType.size() > 0
        }


        where:
        originalFileName | expected
        "example.docx"   | true
        "example.docm"   | true
        "example.dotx"   | true
        "example.dotm"   | true
        "example.dot"    | false
        "example.doc"    | false
        "example.png"    | false
        "example.jpg"    | false
        "example.txt"    | false
        "example.exe"    | false
        "example.bat"    | false
        "example.zip"    | false
        "example.xlsx"   | false
        "example"        | false
    }

}
