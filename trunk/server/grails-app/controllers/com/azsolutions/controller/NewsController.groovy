package com.azsolutions.controller

import com.azsolutions.domain.News

class NewsController extends DefaultRestfulController<News> {

    NewsController() {
        super(News)
    }

    NewsController(Class<Category> resource) {
        super(resource)
    }

    NewsController(Class<Category> resource, boolean readOnly) {
        super(resource, readOnly)
    }
}
