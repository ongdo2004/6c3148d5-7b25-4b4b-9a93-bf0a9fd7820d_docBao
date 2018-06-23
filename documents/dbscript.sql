CREATE TABLE aznews.category
(
  id                 varchar(50) PRIMARY KEY NOT NULL,
  version            bigint(20)              NOT NULL,
  is_deleted         bit(1),
  last_modified_user varchar(255),
  last_modified_time datetime,

  rss_source_id      varchar(50),
  title              varchar(1000)

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE aznews.image
(
  id                 varchar(50) PRIMARY KEY NOT NULL,
  version            bigint(20)              NOT NULL,
  is_deleted         bit(1),
  last_modified_user varchar(255),
  last_modified_time datetime,

  type               varchar(100),
  width              integer,
  height             integer,
  url                varchar(255),
  reference_id       varchar(50),
  reference_type     varchar(20)

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE aznews.news
(
  id                 varchar(50) PRIMARY KEY NOT NULL,
  version            bigint(20)              NOT NULL,
  is_deleted         bit(1),
  last_modified_user varchar(255),
  last_modified_time datetime,

  title              varchar(1000),
  link               varchar(1000),
  pub_date           datetime,
  guid               varchar(1000),
  description        text,
  rss_source_id      varchar(50)

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE aznews.rss_source
(
  id                 varchar(50) PRIMARY KEY NOT NULL,
  version            bigint(20)              NOT NULL,
  is_deleted         bit(1),
  last_modified_user varchar(255),
  last_modified_time datetime,

  rssUrl             varchar(1000),
  code               varchar(100)

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE aznews.rss_config
(
  id                 varchar(50) PRIMARY KEY NOT NULL,
  version            bigint(20)              NOT NULL,
  is_deleted         bit(1),
  last_modified_user varchar(255),
  last_modified_time datetime,

  rssUrl             varchar(1000),
  config_json        text,
  rss_source_id      varchar(50)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

alter table aznews.news
  add index news_guid(guid);

alter table aznews.image
  add index image_reference_id(reference_id);