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