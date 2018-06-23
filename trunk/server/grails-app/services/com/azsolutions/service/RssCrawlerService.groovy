package com.azsolutions.service

import com.azsolutions.bean.Rss
import com.azsolutions.domain.Image
import com.azsolutions.domain.News
import com.azsolutions.domain.RssConfig
import grails.gorm.transactions.Transactional

@Transactional
class RssCrawlerService {

    def readRssService;
    def utilsService;

    def crawler() {

        Date now = new Date();

        List<RssConfig> rssConfigs = RssConfig.findAllByIsDeleted(false);

        rssConfigs.each { RssConfig rssConfig ->

            Rss rss = readRssService.readRss(rssConfig);

            List<String> guidList = rss?.items?.guid;

            List<News> existNewsList =
                    guidList ? News.findAllByIsDeletedAndGuidInList(false, guidList) : null;

            rss?.items.each { Rss.Item item ->

                //neu news da duoc dong bo thi khong dong bo nua;
                if (existNewsList.find { it.guid == item.guid }) return;

                News news = new News(
                        title: item.title, link: item.link,
                        pubDate: utilsService.parseRssDate(item.pubDate),
                        guid: item.guid, description: item.description, rssSourceId: rssConfig.rssSourceId,
                        isDeleted: false, lastModifiedUser: "system", lastModifiedTime: now
                );

                news.save();

                item?.thumbnails.each { Rss.Thumbnail thumbnail ->

                    Image image = new Image(
                            type: thumbnail.type, width: thumbnail.width, height: thumbnail.height,
                            url: thumbnail.url, referenceId: news.id, referenceType: "NEWS",
                            isDeleted: false, lastModifiedUser: "system", lastModifiedTime: now
                    );

                    image.save();
                }
            }
        }
    }
}
