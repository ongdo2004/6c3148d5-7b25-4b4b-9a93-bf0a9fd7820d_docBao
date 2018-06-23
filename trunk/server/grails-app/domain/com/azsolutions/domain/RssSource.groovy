package com.azsolutions.domain

import com.azsolutions.domain.BaseDomain

class RssSource implements BaseDomain {

    String rssUrl;
    String code;

    static constraints = {

        lastModifiedTime nullable: true
        lastModifiedUser nullable: true
        isDeleted nullable: true
    }

    static mapping = {

        id generator: 'uuid'
        isDeleted defaultValue: false
    }
}
