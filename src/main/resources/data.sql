-- 관리자 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed,
                   marketing_agreed, email_verified, last_login)
VALUES ('관리자', 'admin@test.com', '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK',
        'admin', '010-1234-5678', 0, 0, true, false, true, NOW());

-- 테스트 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed,
                   marketing_agreed, email_verified, last_login)
VALUES ('테스터', 'test@test.com', '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK',
        'test', '010-9876-5432', 1, 0, true, false, true, NOW());

-- Product 테스트 값 추가
INSERT INTO products (title, author, isbn, content, fixed_price, publication_year, status,
                      stock_quantity, picture_url)
VALUES ('스프링 부트 3 백엔드 개발자 되기 : 자바 편', '신선영', '9791191905717',
        '실력을 갖춘 개발자로 성장하려면 시작이 중요하다. 그래서 이 책은 무엇부터 익혀야 하는지 막막한 입문자에게 백엔드 개발의 필수 지식을 학습 로드맵 중심으로 설명한다. 이어서 스프링 부트 3 개발에 꼭 필요한 4대장인 JPA ORM, OAuth2 인증, AWS 배포, CI/CD를 최신 트렌드에 맞게 그리고 실무에 유용하게 알려준다. 모든 장 끝에는 연습문제가 수록되어 있어 배운 내용을 점검할 수 있다. 이번 2판에는 스프링 부트 프로젝트 진행을 위해 꼭 알아야 하는 SQL 기초와 실습, 스프링 시큐리티 업데이트, 블로그 댓글 기능 등을 추가하여 더욱 좋은 책으로 만들었다.',
        32000, '2024-04-05', 'AVAILABLE', 100, null),
       ('리액트훅 인 액션', '존 라슨 (지은이),오현석 (옮긴이)', '9791189909611',
        '여러 리액트 컴포넌트 사이에 기능 재사용과 손쉬운 공유를 위한 목적으로 만들어진 자바스크립트 함수인 리액트 훅(React Hook)을 사용하면 컴포넌트를 더 작은 함수로 나눌 수 있고, 상태와 부수 효과를 관리할 수 있으며, 클래스를 사용하지 않고도 리액트의 기능을 활용할 수 있다. 게다가 컴포넌트의 계층 구조를 재배열하지 않고도 이 모든 이점을 얻을 수 있다.',
        30000, '2024-03-29', 'AVAILABLE', 200, null);

-- 카테고리 테스트 값 추가
INSERT INTO category (name, parent_id)
VALUES ('국내도서', null),
       ('해외도서', null),
       ('eBook', null),
       ('소설', 1),
       ('시/에세이', 1),
       ('인문', 1),
       ('문학', 2),
       ('취미/실용/여행', 2),
       ('생활/요리', 2),
       ('만화', 3),
       ('한국소설', 4),
       ('영미소설', 4)
;