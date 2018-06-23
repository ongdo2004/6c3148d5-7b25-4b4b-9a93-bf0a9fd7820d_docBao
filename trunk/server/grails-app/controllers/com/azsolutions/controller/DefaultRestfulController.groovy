package com.azsolutions.controller

import com.azsolutions.bean.PaginationParams
import com.azsolutions.bean.TableQueryResponse
import com.azsolutions.domain.BaseDomain
import grails.converters.JSON
import grails.rest.RestfulController

class DefaultRestfulController<T> extends RestfulController<T> {

    def restfulQueryService;
    def queryPagingService;

    DefaultRestfulController(Class<T> resource) {

        super(resource);
    }

    DefaultRestfulController(Class<T> resource, boolean readOnly) {

        super(resource, readOnly);
    }

    @Override
    def index(Integer max) {

        params.max = Math.min(max ?: 10, 100);

        render(_search() as JSON);
    }

    def paginate(PaginationParams paginationParams) {

        TableQueryResponse result = queryPagingService.query(
                this.resource, this.buildFilterClosure(), paginationParams
        );

        render(result as JSON);
    }

    protected void deleteResource(T domainInstance) {

        if (domainInstance instanceof BaseDomain) {

            (domainInstance as BaseDomain).with {

                isDeleted = true;
                lastModifiedTime = new Date();
                lastModifiedUser = "admin";

                save(flush: true);
            }

        } else {

            super.deleteResource(domainInstance);
        }
    }

    protected List<T> _search() {

        return resource.where(this.buildFilterClosure()).list(params);
    }

    protected Closure buildFilterClosure() {

        return restfulQueryService.buildCommonRestClosure(resource, params);
    }

    @Override
    protected T queryForResource(Serializable id) {

        T object = super.queryForResource(id);

        return ((object instanceof BaseDomain) && (object as BaseDomain)?.isDeleted) ? null : object;
    }

    @Override
    protected T createResource() {

        T obj = super.createResource();

        if (obj && obj instanceof BaseDomain) {

            autoBindBaseDomain(obj as BaseDomain);

            obj.isDeleted = false;
        }

        return obj;
    }

    @Override
    protected T updateResource(T resource) {

        T obj = super.updateResource(resource);

        if (obj && obj instanceof BaseDomain && obj.isDirty()) {

            autoBindBaseDomain(obj as BaseDomain);
        }

        return obj;
    }

    protected autoBindBaseDomain(BaseDomain obj) {

        obj.lastModifiedTime = new Date();
        obj.lastModifiedUser = "admin";
    }
}
