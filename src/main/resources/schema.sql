DROP TABLE IF EXISTS authenticate_user CASCADE;
DROP TABLE IF EXISTS oauth_access_token CASCADE;
DROP TABLE IF EXISTS oauth_refresh_token CASCADE;

CREATE TABLE authenticate_user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  role VARCHAR(50) NOT NULL,
  enabled INT DEFAULT NULL
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(255) PRIMARY KEY,
  token BLOB NOT NULL,
  authentication_id VARCHAR(255) NULL,
  user_name VARCHAR(255) NULL,
  client_id VARCHAR(255) NULL,
  authentication BLOB NULL,
  refresh_token BLOB NOT NULL
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(255) PRIMARY KEY,
  token BLOB NOT NULL,
  authentication BLOB NULL
);

CREATE TABLE oauth_client_details (
  client_id VARCHAR(48) PRIMARY KEY,
  resource_ids VARCHAR(256) NULL,
  client_secret VARCHAR(256) NULL,
  scope VARCHAR(256) NULL,
  authorized_grant_types VARCHAR(256) NULL,
  web_server_redirect_uri VARCHAR(256) NULL,
  authorities VARCHAR(256) NULL,
  access_token_validity INT(11) NULL,
  refresh_token_validity INT(11) NULL,
  additional_information VARCHAR(4096) NULL,
  autoapprove VARCHAR(256) NULL
);