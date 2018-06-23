package com.azsolutions.controller

import grails.converters.JSON

class ExampleController {

    def readRssService;
    def rssCrawlerService;

    def crawler() {

        rssCrawlerService.crawler();

        render([isSuccess: true] as JSON);
    }
}
