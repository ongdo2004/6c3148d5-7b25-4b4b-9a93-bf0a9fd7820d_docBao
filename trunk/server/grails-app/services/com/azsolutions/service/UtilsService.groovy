package com.azsolutions.service

import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class UtilsService {

    Date parseRssDate(String dateStr) {

        if (!dateStr) return null;

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

        format.parse(dateStr);
    }
}
