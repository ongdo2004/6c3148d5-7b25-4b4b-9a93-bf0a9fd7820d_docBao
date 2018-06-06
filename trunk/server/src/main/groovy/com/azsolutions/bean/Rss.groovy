package com.azsolutions.bean

class Rss {

    static class Image {

        String url;
        String title;
        String link;
    }

    static class Thumbnail {

        String type;
        Integer width;
        Integer height;
        String url;
    }

    static class Item {

        String title;
        String link;
        String pubDate;
        String guid;
        String description;

        List<Thumbnail> thumbnails;
    }

    String title;
    String description;
    String link;

    Image image;
    List<Item> items;
}
