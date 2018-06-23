package com.azsolutions.bean;

import grails.validation.Validateable;

public class PaginationParams implements Validateable {

    String sort;
    String order;
    Integer pageIndex;
    Integer maxPageSize;
}