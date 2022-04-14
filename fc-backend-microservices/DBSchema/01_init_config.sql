CREATE USER msconf WITH CREATEDB REPLICATION PASSWORD 'Bv@0C7f2g0';
CREATE DATABASE "SSConfig"  WITH OWNER = msconf  ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;

create schema appconfig;

SET search_path to appconfig;

drop table if exists properties;

CREATE TABLE appconfig.properties
(
    application character varying(50) COLLATE pg_catalog."default",
    profile character varying(50) COLLATE pg_catalog."default",
    label character varying(50) COLLATE pg_catalog."default",
    key character varying(256) COLLATE pg_catalog."default",
    value character varying(500) COLLATE pg_catalog."default"
);

insert into properties (application, profile, label, key, value) values ('gateway-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('account-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('email-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('catalog-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('promotion-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('location-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('payment-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('registry-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('auth-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('order-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');
insert into properties (application, profile, label, key, value) values ('file-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '2');


INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'ribbon.ReadTimeout', '20000');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'ribbon.ConnectTimeout', '20000');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.host.connect-timeout-millis', '20000');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.host.socket-timeout-millis', '20000');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'server.port', '8080');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds', '60000');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'spring.servlet.multipart.max-file-size', '20MB');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'spring.servlet.multipart.max-request-size', '20MB');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.path', '/promotion/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.url', 'promotion-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.path', '/order/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.url', 'order-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.path', '/uaa/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.url', 'auth-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.path', '/email/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.url', 'email-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.path', '/account/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.url', 'account-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.path', '/location/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.url', 'location-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.path', '/catalog/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.url', 'catalog-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.path', '/file/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.url', 'file-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.path', '/partner/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.url', 'payment-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.path', '/delivery/**');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.url', 'delivery-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.stripPrefix', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.sensitiveHeaders', '');

INSERT INTO properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'server.port', '8761');
INSERT INTO properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'true');
INSERT INTO properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.client.registerWithEureka', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.client.fetchRegistry', 'false');


INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.driverClassName', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'server.port', '8081');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.clientId', 'promotion-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'url.image.server', 'http://smartapp.banvien.com.vn:8150/repository');
INSERT INTO properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'promotion.image.server', 'https://customer-api-smartapp.banvien.com.vn/file');


INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.driverClassName', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'server.servlet.context-path', '/uaa');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'server.port', '8889');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.host', 'localhost');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.database', '0');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.port', '6379');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.password', '');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.web.clientId', 'bvWebClient');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.web.clientSecret', 'An5g7ul20ar');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.mobile.clientId', 'bvMobileClient');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.mobile.clientSecret', 'Mo5b7i20le');
INSERT INTO properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.platform.verifyTokenUri', 'http://localhost:18080/public/verifyToken.html');


INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'server.port', '8083');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.clientId', 'order-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'url.image.server', 'http://smartapp.banvien.com.vn:8150/repository');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'salesflow.host', 'https://integration.salesflo.com');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'salesflow.AccessKey', 'MTYwNTgxNjgzN19SWkR5T05pNmJwaXlqRmtvWDJraGVRPT0=');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'salesflow.SecretKey', 'UlpEeU9OaTZicGl5akZrb1gya2hlUT09');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'salesflow.X-API-KEY', 'vOHr2X7EDe6QM3HYj6nj42zwhw1jTpFE1Tc9aGKr');
INSERT INTO properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'salesflow.CompanyCode', 'efoods4');


INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.clientId', 'email-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'server.port', '8089');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'notify.send.cronjob', '0 * * * * ?');

INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'server.port', '8085');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.clientId', 'account-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');

INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'server.port', '8086');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.clientId', 'location-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');


INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'server.port', '8087');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.clientId', 'catalog-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'salesflow.host', 'https://integration.salesflo.com');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'salesflow.AccessKey', 'MTYwNTgxNjgzN19SWkR5T05pNmJwaXlqRmtvWDJraGVRPT0=');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'salesflow.SecretKey', 'UlpEeU9OaTZicGl5akZrb1gya2hlUT09');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'salesflow.X-API-KEY', 'vOHr2X7EDe6QM3HYj6nj42zwhw1jTpFE1Tc9aGKr');
INSERT INTO properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'salesflow.CompanyCode', 'efoods4');


INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'server.port', '8090');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.clientId', 'payment-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.url', 'https://partner-api.stg-myteksi.com');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.relative.url', '/grabpay/partner/v2/charge/init');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.redirect.url', 'https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.token.url', 'https://api.stg-myteksi.com/grabid/v1/oauth2/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.redirect.status.url', 'https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?status=');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.complete.url', 'https://partner-api.stg-myteksi.com/grabpay/partner/v2/charge/complete');
INSERT INTO properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.refund.url', 'https://api.stg-myteksi.com/grabpay/partner/v2/refund');

INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'server.port', '8091');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.clientId', 'file-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'file.location', 'D:/fcvFile');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'spring.servlet.multipart.max-file-size', '20MB');
INSERT INTO properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'spring.servlet.multipart.max-request-size', '20MB');

INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'server.port', '8092');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:8761/eureka/');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8080/uaa/oauth/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.clientId', 'delivery-service');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8080/uaa/user/current');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.token.url', 'https://api.stg-myteksi.com/grabid/v1/oauth2/token');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.quotes.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries/quotes');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.delivery.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries');
INSERT INTO properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.cancel.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries/');
