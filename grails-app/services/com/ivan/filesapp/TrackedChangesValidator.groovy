package com.ivan.filesapp

import org.springframework.stereotype.Component

@Component
class TrackedChangesValidator implements Validator {

    @Override
    def isValid(file, response, Map responseBody) {
        return true
    }
}
