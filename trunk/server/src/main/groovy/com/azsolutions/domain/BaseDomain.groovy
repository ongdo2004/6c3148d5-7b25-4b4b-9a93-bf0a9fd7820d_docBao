package com.azsolutions.domain

trait BaseDomain {

    String id;
    Date lastModifiedTime;
    String lastModifiedUser;
    Boolean isDeleted;
}
