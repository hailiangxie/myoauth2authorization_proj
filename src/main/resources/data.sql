INSERT INTO authenticate_user (username, password, role, enabled) VALUES ('user', '$2a$10$vunfSusqml5aUEY0jdB6k.KH/t.TiOrb0mx8bkmewH.KaP9qGjd8O', 'USER', 1);
INSERT INTO authenticate_user (username, password, role, enabled) VALUES ('admin', '$2a$10$vunfSusqml5aUEY0jdB6k.KH/t.TiOrb0mx8bkmewH.KaP9qGjd8O', 'ADMIN', 1);

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
 VALUES ('oauth2_client1', NULL, '$2a$10$vunfSusqml5aUEY0jdB6k.KH/t.TiOrb0mx8bkmewH.KaP9qGjd8O', 'read', 'authorization_code', 'http://localhost:9041/callback', NULL, NULL, NULL, NULL, NULL);
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
 VALUES ('oauth2_client2', NULL, '$2a$10$vunfSusqml5aUEY0jdB6k.KH/t.TiOrb0mx8bkmewH.KaP9qGjd8O', 'read', 'authorization_code', 'http://localhost:9041/callback', NULL, NULL, NULL, NULL, NULL);