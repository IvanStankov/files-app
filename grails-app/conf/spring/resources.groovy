// Place your Spring DSL code here
import grails.spring.BeanBuilder

def bb = new BeanBuilder()
bb.beans {
    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "com.ivan.fileapp")
}

beans = {
}
