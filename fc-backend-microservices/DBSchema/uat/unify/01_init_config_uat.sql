CREATE USER msconf WITH CREATEDB REPLICATION PASSWORD 'Bv@0C7f2g0';
ALTER USER msconf WITH superuser;

CREATE DATABASE "SSConfig"  WITH OWNER = msconf  ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;

create schema appconfigunify;

SET search_path to appconfigunify;

drop table if exists properties;

CREATE TABLE appconfigunify.properties (
                                      propertyId BIGSERIAL NOT NULL PRIMARY KEY,
                                      application character varying(50) COLLATE pg_catalog."default",
                                      profile character varying(50) COLLATE pg_catalog."default",
                                      label character varying(50) COLLATE pg_catalog."default",
                                      key character varying(256) COLLATE pg_catalog."default",
                                      value character varying(500) COLLATE pg_catalog."default"
);

INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('account-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('account-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('account-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('account-service','development','master','security.oauth2.client.clientId','order-service')
                                                                                     ,('account-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('account-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('account-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('account-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('account-service','development','master','server.port','18085')
                                                                                     ,('account-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('account-service','development','master','spring.datasource.password','123456')
                                                                                     ,('account-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('auth-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('auth-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('auth-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('auth-service','development','master','security.oauth2.mobile.clientId','bvMobileClient')
                                                                                     ,('auth-service','development','master','security.oauth2.mobile.clientSecret','Mo5b7i20le')
                                                                                     ,('auth-service','development','master','security.oauth2.web.clientId','bvWebClient')
                                                                                     ,('auth-service','development','master','security.oauth2.web.clientSecret','An5g7ul20ar')
                                                                                     ,('auth-service','development','master','server.port','18889')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('auth-service','development','master','server.servlet.context-path','/uaa')
                                                                                     ,('auth-service','development','master','spring.datasource.driverClassName','org.postgresql.Driver')
                                                                                     ,('auth-service','development','master','spring.datasource.hikari.maximum-pool-size','10')
                                                                                     ,('auth-service','development','master','spring.datasource.password','123456')
                                                                                     ,('auth-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('auth-service','development','master','spring.redis.password','')
                                                                                     ,('auth-service','development','master','spring.redis.port','6379')
                                                                                     ,('catalog-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('catalog-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('catalog-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('catalog-service','development','master','security.oauth2.client.clientId','catalog-service')
                                                                                     ,('catalog-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('catalog-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('catalog-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('catalog-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('catalog-service','development','master','server.port','18087')
                                                                                     ,('catalog-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('catalog-service','development','master','spring.datasource.password','123456')
                                                                                     ,('catalog-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('delivery-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('delivery-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('delivery-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('delivery-service','development','master','security.oauth2.client.clientId','delivery-service')
                                                                                     ,('delivery-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('delivery-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('delivery-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('delivery-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('delivery-service','development','master','server.port','18092')
                                                                                     ,('delivery-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('delivery-service','development','master','spring.datasource.password','123456')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('delivery-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('email-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('email-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('email-service','development','master','notify.send.cronjob','0 * * * * ?')
                                                                                     ,('auth-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('catalog-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('delivery-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('account-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('catalog-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('catalog-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('auth-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('delivery-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('account-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('auth-service','development','master','spring.redis.host','192.168.2.15')
                                                                                     ,('email-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('email-service','development','master','security.oauth2.client.clientId','email-service')
                                                                                     ,('email-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('email-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('email-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('email-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('email-service','development','master','server.port','18089')
                                                                                     ,('email-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('email-service','development','master','spring.datasource.password','123456')
                                                                                     ,('email-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('file-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('file-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('file-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('file-service','development','master','security.oauth2.client.clientId','file-service')
                                                                                     ,('file-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('file-service','development','master','security.oauth2.client.grant-type','client_credentials')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('file-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('file-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('file-service','development','master','server.port','18091')
                                                                                     ,('file-service','development','master','spring.servlet.multipart.max-file-size','20MB')
                                                                                     ,('file-service','development','master','spring.servlet.multipart.max-request-size','20MB')
                                                                                     ,('gateway-service','development','master','spring.servlet.multipart.max-file-size','20MB')
                                                                                     ,('gateway-service','development','master','spring.servlet.multipart.max-request-size','20MB')
                                                                                     ,('gateway-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('gateway-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('gateway-service','development','master','hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds','250000')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('gateway-service','development','master','ribbon.ConnectTimeout','60000')
                                                                                     ,('gateway-service','development','master','ribbon.ReadTimeout','60000')
                                                                                     ,('gateway-service','development','master','server.port','48888')
                                                                                     ,('gateway-service','development','master','zuul.host.connect-timeout-millis','60000')
                                                                                     ,('gateway-service','development','master','zuul.host.socket-timeout-millis','60000')
                                                                                     ,('gateway-service','development','master','zuul.routes.account-service.path','/account/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.account-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.account-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.account-service.url','account-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.auth-service.path','/uaa/**')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('gateway-service','development','master','zuul.routes.auth-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.auth-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.auth-service.url','auth-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.catalog-service.path','/catalog/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.catalog-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.catalog-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.catalog-service.url','catalog-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.delivery-service.path','/delivery/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.delivery-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.delivery-service.stripPrefix','false')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('gateway-service','development','master','zuul.routes.delivery-service.url','delivery-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.email-service.path','/email/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.email-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.email-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.email-service.url','email-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.file-service.path','/file/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.file-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.file-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.file-service.url','file-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.location-service.path','/location/**')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('gateway-service','development','master','zuul.routes.location-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.location-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.location-service.url','location-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.order-service.path','/order/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.order-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.order-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('file-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('email-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('file-service','development','master','file.location','~/fcg_images')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('gateway-service','development','master','zuul.routes.order-service.url','order-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.payment-service.path','/partner/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.payment-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.payment-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.payment-service.url','payment-service')
                                                                                     ,('gateway-service','development','master','zuul.routes.promotion-service.path','/promotion/**')
                                                                                     ,('gateway-service','development','master','zuul.routes.promotion-service.sensitiveHeaders','')
                                                                                     ,('gateway-service','development','master','zuul.routes.promotion-service.stripPrefix','false')
                                                                                     ,('gateway-service','development','master','zuul.routes.promotion-service.url','promotion-service')
                                                                                     ,('location-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('location-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('location-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('location-service','development','master','security.oauth2.client.clientId','location-service')
                                                                                     ,('location-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('location-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('location-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('location-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('location-service','development','master','server.port','18086')
                                                                                     ,('location-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('location-service','development','master','spring.datasource.password','123456')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('location-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('order-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('order-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('order-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('order-service','development','master','security.oauth2.client.clientId','order-service')
                                                                                     ,('order-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('order-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('order-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('order-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('order-service','development','master','server.port','18083')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('order-service','development','master','server.tomcat.accesslog.enabled','true')
                                                                                     ,('order-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('order-service','development','master','spring.datasource.password','123456')
                                                                                     ,('order-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('payment-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('payment-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('payment-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
                                                                                     ,('payment-service','development','master','security.oauth2.client.clientId','payment-service')
                                                                                     ,('payment-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('payment-service','development','master','security.oauth2.client.grant-type','client_credentials')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('payment-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('payment-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('payment-service','development','master','server.port','18090')
                                                                                     ,('payment-service','development','master','server.tomcat.accesslog.enabled','true')
                                                                                     ,('payment-service','development','master','spring.datasource.driver-class-name','org.postgresql.Driver')
                                                                                     ,('payment-service','development','master','spring.datasource.password','123456')
                                                                                     ,('payment-service','development','master','spring.datasource.username','smartshopper')
                                                                                     ,('promotion-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('promotion-service','development','master','eureka.instance.prefer-ip-address','false')
                                                                                     ,('promotion-service','development','master','security.oauth2.client.accessTokenUri','http://localhost:48888/uaa/oauth/token')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('promotion-service','development','master','security.oauth2.client.clientId','promotion-service')
                                                                                     ,('promotion-service','development','master','security.oauth2.client.clientSecret','Se5r7vi20ce')
                                                                                     ,('promotion-service','development','master','security.oauth2.client.grant-type','client_credentials')
                                                                                     ,('order-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('payment-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('order-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('location-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('order-service','development','master','url.image.server','https://b2bmyuat.banvien.com.vn/repository')
                                                                                     ,('promotion-service','development','master','promotion.image.server','https://buyer-b2bmyuat.banvien.com.vn/order/detail?id=')
                                                                                     ,('order-service','development','master','easy.order.detail.url','http://smartapp.banvien.com.vn:8150/repository')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('order-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('payment-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('location-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('promotion-service','development','master','security.oauth2.client.scope','server')
                                                                                     ,('promotion-service','development','master','security.oauth2.resource.user-info-uri','http://localhost:48888/uaa/user/current')
                                                                                     ,('promotion-service','development','master','server.port','18081')
                                                                                     ,('promotion-service','development','master','server.tomcat.accesslog.enabled','true')
                                                                                     ,('promotion-service','development','master','spring.datasource.driverClassName','org.postgresql.Driver')
                                                                                     ,('promotion-service','development','master','spring.datasource.password','123456')
                                                                                     ,('promotion-service','development','master','spring.datasource.username','smartshopper')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('registry-service','development','master','eureka.client.fetchRegistry','false')
                                                                                     ,('registry-service','development','master','eureka.client.registerWithEureka','false')
                                                                                     ,('registry-service','development','master','eureka.client.serviceUrl.defaultZone','http://localhost:18761/eureka/')
                                                                                     ,('registry-service','development','master','server.port','18761')
                                                                                     ,('payment-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('account-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('email-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('location-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('promotion-service','development','master','spring.datasource.hikari.schema','fcvsmartshopperunify')
                                                                                     ,('registry-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
;
INSERT INTO appconfigunify.properties (application,profile,"label","key",value) VALUES
('promotion-service','development','master','spring.datasource.hikari.maximum-pool-size','7')
                                                                                     ,('auth-service','development','master','security.platform.verifyTokenUri','https://b2bmyuat.banvien.com.vn/public/verifyToken.html')
                                                                                     ,('promotion-service','development','master','url.image.server','https://buyer-api-b2bmy.banvien.com.vn/file')
                                                                                     ,('promotion-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
                                                                                     ,('email-service','development','master','spring.datasource.url','jdbc:postgresql://192.168.2.15:5432/FCVSmartShopper')
;