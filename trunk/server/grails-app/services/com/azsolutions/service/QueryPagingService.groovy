package com.azsolutions.service

import com.azsolutions.bean.PaginationParams
import com.azsolutions.bean.TableQueryResponse
import grails.gorm.DetachedCriteria
import grails.gorm.transactions.Transactional

@Transactional
class QueryPagingService {

    TableQueryResponse query(Class domainClass, Closure filterClosure, PaginationParams paginationParams) {

        return query(domainClass.where(filterClosure) as DetachedCriteria, paginationParams);
    }

    TableQueryResponse query(DetachedCriteria criteria, PaginationParams paginationParams) {

        Long itemsLength = criteria.count();

        List items;

        Long pageIndex;

        if (itemsLength) {

            pageIndex = [paginationParams.pageIndex, itemsLength / paginationParams.maxPageSize].min();

            Long offset = pageIndex * paginationParams.maxPageSize;

            String order = paginationParams.order == "des" ? "desc" : paginationParams.order;

            def pagingParams = [offset: offset, max: paginationParams.maxPageSize, sort: paginationParams.sort, order: order];

            items = criteria.list(pagingParams);

        } else {

            pageIndex = 0;
        }

        return new TableQueryResponse(pageData: items, pageIndex: pageIndex, dataSize: itemsLength);
    }
}
