package com.ivan.filesapp

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.runtime.FreshRuntime
import org.codehaus.groovy.grails.commons.InstanceFactoryBean
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

@FreshRuntime
@TestMixin(GrailsUnitTestMixin)
@TestFor(FileController)
class FileControllerTest extends Specification {

    def validator1 = Mock(Validator)
    def validator2 = Mock(Validator)

    def doWithSpring = {
        validator1(InstanceFactoryBean, validator1, Validator)
        validator2(InstanceFactoryBean, validator2, Validator)
    }

    def "save when request is valid"() {
        given:
        def file = Mock(MultipartFile)
        file.getName() >> "targetFile"
        file.empty >> false
        request.addFile(file)

        1 * validator1.validate(*_) >> true
        1 * validator2.validate(*_) >> true

        when:
        controller.save()

        then:
        assert response.status == 200
    }

    def "save when first validation fails should return error"() {
        given:
        def file = Mock(MultipartFile)
        file.getName() >> "targetFile"
        file.empty >> false
        request.addFile(file)

        1 * this.validator2.validate(*_) >> { args ->
            args[1].status = 500
            args[2].message = "Wrong"
            return false
        }

        when:
        controller.save()

        then:
        0 * validator1.validate(*_) // first validator should not be called
        assert response.status == 500
        assert response.json.message == "Wrong"
    }

}
