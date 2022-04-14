SET search_path to appconfig;

-- ALTER SEQUENCE properties_propertyid_seq RESTART WITH 500;

INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.enabled', 'true');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.suffix', '.log');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.prefix', 'access_log');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.file-date-format', '.yyyy-MM-dd');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.basedir', 'tomcat_promotion');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.directory', 'logs');
INSERT INTO properties (application, profile, label, key, value)
  VALUES ('promotion-service', 'development', 'master', 'server.tomcat.accesslog.pattern', '%t %a "%r" %s (%D ms)');