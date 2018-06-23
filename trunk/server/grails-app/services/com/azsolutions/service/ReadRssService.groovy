package com.azsolutions.service

import com.azsolutions.bean.Rss
import com.azsolutions.domain.RssConfig
import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class ReadRssService {

    private Object getChildNodes(def node, String path) {

        path.split("\\.").each { node = node?."${it}"; };

        return node;
    }

    private Integer convertToInteger(def node) {

        if (node == null || !(node as String)) return null;

        return (node as String) as Integer;
    }

    private String getConvertType(String mappingPath) {

        switch (mappingPath) {

            case "root.image":
                return "object";

            case "root.items":
            case "root.items.thumbnails":
                return "list";

            case "root.items.thumbnails.height":
            case "root.items.thumbnails.width":
                return "integer";

            default:
                return "string";
        }
    }

    private Object initNewBean(String mappingPath) {

        switch (mappingPath) {

            case "root.image":
                return new Rss.Image();

            case "root.items":
                return new Rss.Item();

            case "root.items.thumbnails":
                return new Rss.Thumbnail();

            default:
                return new Rss();
        }
    }

    private Object parseXML(Map config, def node, String mappingPath) {

        def bean = initNewBean(mappingPath);

        config.keySet().each { String key ->

            Map propertyConfig = config[key];

            String path = propertyConfig["path"];

            def childNodes = getChildNodes(node, path);

            String convertType = getConvertType(mappingPath + "." + key);

            switch (convertType) {

                case "integer":
                    bean."${key}" = convertToInteger(childNodes);
                    break;

                case "object":
                    bean."${key}" = parseXML(propertyConfig["config"], childNodes, mappingPath + "." + key);
                    break;

                case "list":
                    bean."${key}" = childNodes.collect {
                        return parseXML(propertyConfig["config"], it, mappingPath + "." + key);
                    }
                    break;

                default:
                    bean."${key}" = childNodes;
                    break;
            }
        }

        return bean;
    }

    Rss readRss(RssConfig rssConfig) {

        String url = rssConfig.rssUrl;

        Map mappingConfig = JSON.parse(rssConfig.configJson);

        def channel = new XmlSlurper().parse(url).channel;

        return parseXML(mappingConfig, channel, "root");
    }
}
