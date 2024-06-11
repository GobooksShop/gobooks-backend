-- 관리자 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login)
VALUES ('관리자', 'admin@test.com', '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK', 'admin', '010-1234-5678', 0, 0, true, false, true, NOW());

-- 테스트 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login)
VALUES ('테스터', 'test@test.com', '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK', 'test', '010-9876-5432', 1, 0, true, false, true, NOW());