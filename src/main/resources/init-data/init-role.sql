INSERT INTO roles (role_name)
SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'USER');
INSERT INTO roles (role_name)
SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ADMIN');
INSERT INTO roles (role_name)
SELECT 'MODERATOR' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'MODERATOR');

INSERT INTO platforms (platform_type)
SELECT 'PC' WHERE NOT EXISTS(SELECT 1 FROM platforms WHERE platform_type = 'PC');
INSERT INTO platforms (platform_type)
SELECT 'MOBILE' WHERE NOT EXISTS(SELECT 1 FROM platforms WHERE platform_type = 'MOBILE');
INSERT INTO platforms (platform_type)
SELECT 'XBOX' WHERE NOT EXISTS(SELECT 1 FROM platforms WHERE platform_type = 'XBOX');
INSERT INTO platforms (platform_type)
SELECT 'PLAYSTATION' WHERE NOT EXISTS(SELECT 1 FROM platforms WHERE platform_type = 'PLAYSTATION');
INSERT INTO platforms (platform_type)
SELECT 'SWITCH' WHERE NOT EXISTS(SELECT 1 FROM platforms WHERE platform_type = 'SWITCH');

INSERT INTO categories(category_type)
SELECT 'ANY_PERCENT' WHERE NOT EXISTS(SELECT 1 FROM categories WHERE category_type = 'ANY_PERCENT');
INSERT INTO categories(category_type)
SELECT 'ONE_HUNDRED_PERCENT' WHERE NOT EXISTS(SELECT 1 FROM categories WHERE category_type = 'ONE_HUNDRED_PERCENT');
