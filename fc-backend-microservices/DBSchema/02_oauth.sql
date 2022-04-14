SET search_path to fcvsmartshopper;

CREATE TABLE oauth_client_details (
	client_id varchar(255) NOT NULL PRIMARY KEY,
	resource_ids text NULL,
	client_secret text NULL,
	scope text NULL,
	authorized_grant_types text NULL,
	web_server_redirect_uri text NULL,
	authorities text NULL,
	access_token_validity int4 NULL,
	refresh_token_validity int4 NULL,
	additional_information text NULL,
	autoapprove varchar(255) NULL
);
-- The encrypted client_secret it `An5g7ul20ar`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('bvWebClient', '{bcrypt}$2a$10$TH.jO4Uce8OoSzw2Z.TNEeUwg5F5vt1HXTXGJheL1J1/HTv0vDmm2', 'ui', 'password,refresh_token,client_credentials', 86400);
-- The encrypted client_secret it `Mo5b7i20le`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('bvMobileClient', '{bcrypt}$2a$10$3pok/IJdIUhgNO3ow6c9WuLfp9w65NFTyRqAuQwj6yqXxnG6Hj/KW', 'ui', 'password,refresh_token,client_credentials', 86400);
-- The encrypted client_secret it `Se5r7vi20ce`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('promotion-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('order-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('auth-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('email-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('account-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('location-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('catalog-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('payment-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('file-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('delivery-service', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'client_credentials,refresh_token', 86400);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity)
  VALUES ('platform', '{bcrypt}$2a$10$nG3u9Ex.1DT2e7TF6HrekecmqotNO4b9zJQ.v21n.RrSusFOW7YG.', 'server', 'password,client_credentials,refresh_token', 86400);
