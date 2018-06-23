package com.azsolutions.service

import grails.gorm.transactions.Transactional
import grails.web.databinding.DataBindingUtils

import java.text.SimpleDateFormat

@Transactional
class ApplicationUtilsService {

    def bindData(def target, def source) {

        DataBindingUtils.bindObjectToInstance(target, source, null, null, "");

        return target;
    }

    Date parseRssDate(String dateStr) {

        if (!dateStr) return null;

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

        format.parse(dateStr);
    }
}
