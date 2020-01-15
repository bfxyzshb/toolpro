package com.test.test1

import com.weibo.test.ASNetworkType
import spock.lang.Specification

class GroovyTest extends Specification {
    def "ParseNetworkType"() {
        expect:
        ASNetworkType.parseNetworkType(originalStatus).getType() == expectRet
        where:
        originalStatus1 | expectRet
        "wifi"         | "NETWORK_TYPE_WIFI"
        "2g"           | "NETWORK_TYPE_MOBILE"
        "3g"           | "NETWORK_TYPE_EDGE"
        "4g"           | "NETWORK_TYPE_LTE"
        "cell"         | "NETWORK_TYPE_LTE"
        "unknown"      | "NETWORK_TYPE_UNKNOWN"
        "dsdsd"        | "NETWORK_TYPE_UNKNOWN"
        null           | "NETWORK_TYPE_UNKNOWN"
    }

    def setup(){
        printf "test"
    }

    def "geteunmType"(){
        expect:
        ASNetworkType.parseNetworkType(type).getType()==result;
        where:
        type|result
        "wifi"|"NETWORK_TYPE_WIFI1"
    }
}