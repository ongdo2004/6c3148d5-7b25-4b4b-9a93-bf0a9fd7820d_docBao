package com.azsolutions.controller

import grails.converters.JSON

class ExampleController {

    def readRssService;

    def index() {

        render(readRssService.readRss() as JSON);
    }
}
