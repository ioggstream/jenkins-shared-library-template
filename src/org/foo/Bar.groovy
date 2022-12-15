package org.foo

import groovy.json.JsonSlurper

class Bar {

    static String loadName(String key) {
        def json = Bar.getResourceAsStream("bar.json")
        return json
    }

}
