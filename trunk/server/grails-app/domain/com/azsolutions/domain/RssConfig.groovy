package com.azsolutions.domain

class RssConfig implements BaseDomain {

    String rssUrl;
    String configJson;
    String rssSourceId;

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
