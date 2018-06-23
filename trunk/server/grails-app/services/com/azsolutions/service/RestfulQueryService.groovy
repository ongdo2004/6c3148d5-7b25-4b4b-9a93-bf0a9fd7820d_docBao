package com.azsolutions.service

import com.azsolutions.domain.BaseDomain
import grails.gorm.transactions.Transactional

@Transactional
class RestfulQueryService {

    def applicationUtilsService;

    private bindData(def target, def source) {

        return applicationUtilsService.bindData(target, source);
    }

    Closure buildCommonRestClosure(Class domainClass, def params) {

        def example = domainClass.newInstance();

        bindData(example, params);

        return {

            (BaseDomain.isAssignableFrom(domainClass)) && (eq("isDeleted", false));

            example.properties.each { String key, def value ->

                if (value != null) eq(key, value);
            }
        }
    }
}
