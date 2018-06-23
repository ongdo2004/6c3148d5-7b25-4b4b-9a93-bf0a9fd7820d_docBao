package com.azsolutions.interceptor

class RequestInterceptor {

    Long start;

    RequestInterceptor() {

        matchAll();
    }

    boolean before() {

        start = new Date().time;

        println("${controllerName}.${actionName}: start");

        return true;
    }

    boolean after() {

        return true;
    }

    void afterView() {

        Long end = new Date().time;

        println("${controllerName}.${actionName}: end| duration=${end - start}");
    }
}
