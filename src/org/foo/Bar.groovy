package org.foo

import groovy.json.JsonSlurper

class Bar {

    static String loadName(String key) {
        def json = libraryResource "org/foo/bar.json"
        return json
    }

}
