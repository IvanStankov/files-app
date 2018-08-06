package com.ivan.filesapp

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class TrackedChangesValidatorTest extends Specification {

    private TrackedChangesValidator validator = new TrackedChangesValidator()

    private InputStream inputStream;

    def cleanup() {
        inputStream?.close();
    }

    def "validate with different files"() {
        given:
        this.inputStream = this.getInputStream(fileName);
        def file = Mock(MultipartFile.class)
        file.getInputStream() >> this.inputStream

        def response = [:]
        def responseBody = [:]

        when:
        def result = validator.validate(file, response, responseBody)

        then:
        assert result == expected

        if (!expected) {
            assert response.status == status
            assert responseBody.revisionsNumber == revisionsNumber
        }

        where:
        fileName                   | expected | status | revisionsNumber
        "without_tracked.docx"     | true     | null   | null
        "without_tracked.docm"     | true     | null   | null
        "without_tracked.dotx"     | true     | null   | null
        "without_tracked.dotm"     | true     | null   | null
        "with_tracked.docx"        | false    | 400    | 2
    }

    private InputStream getInputStream(String name) {
        return this.getClass().getClassLoader().getResourceAsStream("resources/files/" + name)
    }
}
