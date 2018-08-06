package com.ivan.filesapp

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.codehaus.groovy.grails.commons.InstanceFactoryBean
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
@TestFor(FileController)
class FileControllerTest extends Specification {

    def validator1 = Mock(Validator)
    def validator2 = Mock(Validator)

    def doWithSpring = {
        validator(InstanceFactoryBean, validator1, Validator)
        validator(InstanceFactoryBean, validator2, Validator)
    }

    def "save when request is valid"() {
        given:
        def file = Mock(MultipartFile)
        file.getName() >> "targetFile"
        request.addFile(file)

        validator1.validate(*_) >> true
        validator2.validate(*_) >> true

        when:
        controller.save()

        then:
        assert response.status == 200
    }

    def "save when first validation fails should return error"() {
        given:
        def file = Mock(MultipartFile)
        file.getName() >> "targetFile"
        request.addFile(file)

        def fileUploadResult = [:]
        validator1.validate(*_) >> { args ->
            response.status = 500
            false
        }

        when:
        controller.save()

        then:
        0 * validator1.validate(*_)
        assert response.status == 500
        assert fileUploadResult.message == "Wrong"
    }

}
