CREATE USER msconf WITH CREATEDB REPLICATION PASSWORD 'Bv@0C7f2g0';
ALTER USER msconf WITH superuser;

CREATE DATABASE "SSConfig"  WITH OWNER = msconf  ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;

create schema appconfig;

SET search_path to appconfig;

drop table if exists properties;

CREATE TABLE appconfig.properties (
    propertyId BIGSERIAL NOT NULL PRIMARY KEY,
    application character varying(50) COLLATE pg_catalog."default",
    profile character varying(50) COLLATE pg_catalog."default",
    label character varying(50) COLLATE pg_catalog."default",
    key character varying(256) COLLATE pg_catalog."default",
    value character varying(500) COLLATE pg_catalog."default"
);

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.clientId', 'order-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'server.port', '18085');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('account-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.mobile.clientId', 'bvMobileClient');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.mobile.clientSecret', 'Mo5b7i20le');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.web.clientId', 'bvWebClient');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.oauth2.web.clientSecret', 'An5g7ul20ar');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'security.platform.verifyTokenUri', 'https://b2bmy.banvien.com.vn/public/verifyToken.html');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'server.port', '18889');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'server.servlet.context-path', '/uaa');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.driverClassName', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '10');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.host', 'localhost');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.password', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('auth-service', 'development', 'master', 'spring.redis.port', '6379');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.clientId', 'catalog-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'server.port', '18087');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('catalog-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.clientId', 'delivery-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'server.port', '18092');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.token.url', 'https://api.stg-myteksi.com/grabid/v1/oauth2/token')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.quotes.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries/quotes')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.delivery.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('delivery-service', 'development', 'master', 'grab.cancel.url', 'https://partner-api.stg-myteksi.com/grab-express-sandbox/v1/deliveries/')

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'notify.send.cronjob', '0 * * * * ?');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.clientId', 'email-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'server.port', '18089');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('email-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'file.location', '/home/ec2-user/b2b/fcg_images');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.clientId', 'file-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'server.port', '18091');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'spring.servlet.multipart.max-file-size', '20MB');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('file-service', 'development', 'master', 'spring.servlet.multipart.max-request-size', '20MB');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'spring.servlet.multipart.max-file-size', '20MB');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'spring.servlet.multipart.max-request-size', '20MB');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds', '250000');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'ribbon.ConnectTimeout', '60000');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'ribbon.ReadTimeout', '60000');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'server.port', '8093');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.host.connect-timeout-millis', '60000');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.host.socket-timeout-millis', '60000');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.path', '/account/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.account-service.url', 'account-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.path', '/uaa/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.auth-service.url', 'auth-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.path', '/catalog/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.catalog-service.url', 'catalog-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.path', '/delivery/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.delivery-service.url', 'delivery-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.path', '/email/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.email-service.url', 'email-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.path', '/file/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.file-service.url', 'file-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.path', '/location/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.location-service.url', 'location-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.path', '/order/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.order-service.url', 'order-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.path', '/partner/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.payment-service.url', 'payment-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.path', '/promotion/**');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.sensitiveHeaders', '');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.stripPrefix', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('gateway-service', 'development', 'master', 'zuul.routes.promotion-service.url', 'promotion-service');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.clientId', 'location-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'server.port', '18086');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('location-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'easy.order.detail.url', 'https://buyer-b2bmy.banvien.com.vn/order/detail?id=');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.clientId', 'order-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'server.port', '18083');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'server.tomcat.accesslog.enabled', 'true');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('order-service', 'development', 'master', 'url.image.server', 'https://b2bmy.banvien.com.vn/repository');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.clientId', 'payment-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'server.port', '18090');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'server.tomcat.accesslog.enabled', 'true');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.driver-class-name', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.url', 'https://partner-api.stg-myteksi.com')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.relative.url', '/grabpay/partner/v2/charge/init')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.redirect.url', 'https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.token.url', 'https://api.stg-myteksi.com/grabid/v1/oauth2/token')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.redirect.status.url', 'https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?status=')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.complete.url', 'https://partner-api.stg-myteksi.com/grabpay/partner/v2/charge/complete')
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('payment-service', 'development', 'master', 'grab.refund.url', 'https://api.stg-myteksi.com/grabpay/partner/v2/refund')

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'eureka.instance.prefer-ip-address', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'promotion.image.server', 'https://buyer-api-b2bmy.banvien.com.vn/file');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.accessTokenUri', 'http://localhost:8093/uaa/oauth/token');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.clientId', 'promotion-service');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.clientSecret', 'Se5r7vi20ce');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.grant-type', 'client_credentials');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.client.scope', 'server');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'security.oauth2.resource.user-info-uri', 'http://localhost:8093/uaa/user/current');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'server.port', '18081');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.enabled', 'true');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.driverClassName', 'org.postgresql.Driver');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.hikari.schema', 'fcvsmartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.password', '123456');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.url', 'jdbc:postgresql://localhost:5432/FCVSmartShopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'spring.datasource.username', 'smartshopper');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('promotion-service', 'development', 'master', 'url.image.server', 'https://b2bmy.banvien.com.vn/repository');

INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.client.fetchRegistry', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.client.registerWithEureka', 'false');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://localhost:18761/eureka/');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'server.port', '18761');
INSERT INTO appconfig.properties (application, profile, label, key, value) VALUES ('registry-service', 'development', 'master', 'spring.datasource.hikari.maximum-pool-size', '3');
