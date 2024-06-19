-- 비밀번호는 모두 동일하게 설정됨
SET @password = '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK';

-- 관리자 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed,marketing_agreed, email_verified, last_login, created_at)
VALUES ('관리자', 'admin@test.com', @password,'admin', '010-1234-5678', 0, 0, true, false, true, NOW(), NOW());

-- 테스트 유저 추가
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed,marketing_agreed, email_verified, last_login, created_at)
VALUES ('테스터', 'test@test.com', @password,'test', '010-9876-5432', 1, 0, true, false, true, NOW(), NOW());

-- ACTIVE 상태 유저 30명 생성
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login, created_at)
VALUES ('테스터1', 'test1_active@test.com', @password, 'test1', '010-1111-1111', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터2', 'test2_active@test.com', @password, 'test2', '010-1111-1112', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터3', 'test3_active@test.com', @password, 'test3', '010-1111-1113', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터4', 'test4_active@test.com', @password, 'test4', '010-1111-1114', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터5', 'test5_active@test.com', @password, 'test5', '010-1111-1115', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터6', 'test6_active@test.com', @password, 'test6', '010-1111-1116', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터7', 'test7_active@test.com', @password, 'test7', '010-1111-1117', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터8', 'test8_active@test.com', @password, 'test8', '010-1111-1118', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터9', 'test9_active@test.com', @password, 'test9', '010-1111-1119', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터10', 'test10_active@test.com', @password, 'test10', '010-1111-1120', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터11', 'test11_active@test.com', @password, 'test11', '010-1111-1121', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터12', 'test12_active@test.com', @password, 'test12', '010-1111-1122', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터13', 'test13_active@test.com', @password, 'test13', '010-1111-1123', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터14', 'test14_active@test.com', @password, 'test14', '010-1111-1124', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터15', 'test15_active@test.com', @password, 'test15', '010-1111-1125', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터16', 'test16_active@test.com', @password, 'test16', '010-1111-1126', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터17', 'test17_active@test.com', @password, 'test17', '010-1111-1127', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터18', 'test18_active@test.com', @password, 'test18', '010-1111-1128', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터19', 'test19_active@test.com', @password, 'test19', '010-1111-1129', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터20', 'test20_active@test.com', @password, 'test20', '010-1111-1130', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터21', 'test21_active@test.com', @password, 'test21', '010-1111-1131', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터22', 'test22_active@test.com', @password, 'test22', '010-1111-1132', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터23', 'test23_active@test.com', @password, 'test23', '010-1111-1133', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터24', 'test24_active@test.com', @password, 'test24', '010-1111-1134', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터25', 'test25_active@test.com', @password, 'test25', '010-1111-1135', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터26', 'test26_active@test.com', @password, 'test26', '010-1111-1136', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터27', 'test27_active@test.com', @password, 'test27', '010-1111-1137', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터28', 'test28_active@test.com', @password, 'test28', '010-1111-1138', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터29', 'test29_active@test.com', @password, 'test29', '010-1111-1139', 1, 0, true, false, true, NOW(), NOW()),
        ('테스터30', 'test30_active@test.com', @password, 'test30', '010-1111-1140', 1, 0, true, false, true, NOW(), NOW());

-- SUSPENDED 상태 유저 10명 생성
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login, created_at)
VALUES ('테스터31', 'test1_suspended@test.com', @password, 'test31', '010-2222-1111', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터32', 'test2_suspended@test.com', @password, 'test32', '010-2222-1112', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터33', 'test3_suspended@test.com', @password, 'test33', '010-2222-1113', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터34', 'test4_suspended@test.com', @password, 'test34', '010-2222-1114', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터35', 'test5_suspended@test.com', @password, 'test35', '010-2222-1115', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터36', 'test6_suspended@test.com', @password, 'test36', '010-2222-1116', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터37', 'test7_suspended@test.com', @password, 'test37', '010-2222-1117', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터38', 'test8_suspended@test.com', @password, 'test38', '010-2222-1118', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터39', 'test9_suspended@test.com', @password, 'test39', '010-2222-1119', 1, 1, true, false, true, NOW(), NOW()),
       ('테스터40', 'test10_suspended@test.com', @password, 'test40', '010-2222-1120', 1, 1, true, false, true, NOW(), NOW());

-- BANNED 상태 유저 5명 생성
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login, created_at)
VALUES ('테스터41', 'test1_banned@test.com', @password, 'test41', '010-3333-1111', 1, 2, true, false, true, NOW(), NOW()),
       ('테스터42', 'test2_banned@test.com', @password, 'test42', '010-3333-1112', 1, 2, true, false, true, NOW(), NOW()),
       ('테스터43', 'test3_banned@test.com', @password, 'test43', '010-3333-1113', 1, 2, true, false, true, NOW(), NOW()),
       ('테스터44', 'test4_banned@test.com', @password, 'test44', '010-3333-1114', 1, 2, true, false, true, NOW(), NOW()),
       ('테스터45', 'test5_banned@test.com', @password, 'test45', '010-3333-1115', 1, 2, true, false, true, NOW(), NOW());
-- DORMANT 상태 유저 10명 생성
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login, created_at)
VALUES ('테스터46', 'test1_dormant@test.com', @password, 'test46', '010-4444-1111', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터47', 'test2_dormant@test.com', @password, 'test47', '010-4444-1112', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터48', 'test3_dormant@test.com', @password, 'test48', '010-4444-1113', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터49', 'test4_dormant@test.com', @password, 'test49', '010-4444-1114', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터50', 'test5_dormant@test.com', @password, 'test50', '010-4444-1115', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터51', 'test6_dormant@test.com', @password, 'test51', '010-4444-1116', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터52', 'test7_dormant@test.com', @password, 'test52', '010-4444-1117', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터53', 'test8_dormant@test.com', @password, 'test53', '010-4444-1118', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터54', 'test9_dormant@test.com', @password, 'test54', '010-4444-1119', 1, 3, true, false, true, NOW(), NOW()),
       ('테스터55', 'test10_dormant@test.com', @password, 'test55', '010-4444-1120', 1, 3, true, false, true, NOW(), NOW());

-- CANCELED 상태 유저 6명 생성
INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed, marketing_agreed, email_verified, last_login, created_at, deleted_at)
VALUES ('테스터56', 'test1_canceled@test.com', @password, 'test56', '010-5555-1111', 1, 4, true, false, true, NOW(), NOW(), NOW()),
       ('테스터57', 'test2_canceled@test.com', @password, 'test57', '010-5555-1112', 1, 4, true, false, true, NOW(), NOW(), NOW()),
       ('테스터58', 'test3_canceled@test.com', @password, 'test58', '010-5555-1113', 1, 4, true, false, true, NOW(), NOW(), NOW()),
       ('테스터59', 'test4_canceled@test.com', @password, 'test59', '010-5555-1114', 1, 4, true, false, true, NOW(), NOW(), NOW()),
       ('테스터60', 'test5_canceled@test.com', @password, 'test60', '010-5555-1115', 1, 4, true, false, true, NOW(), NOW(), NOW()),
       ('테스터61', 'test6_canceled@test.com', @password, 'test61', '010-5555-1116', 1, 4, true, false, true, NOW(), NOW(), NOW());

-- Product 테스트 값 추가
INSERT INTO products (title, author, isbn, content, fixed_price, publication_year, status,
                      stock_quantity, picture_url, discount)
VALUES ('스프링 부트 3 백엔드 개발자 되기 : 자바 편', '신선영', '9791191905717',
        '실력을 갖춘 개발자로 성장하려면 시작이 중요하다. 그래서 이 책은 무엇부터 익혀야 하는지 막막한 입문자에게 백엔드 개발의 필수 지식을 학습 로드맵 중심으로 설명한다. 이어서 스프링 부트 3 개발에 꼭 필요한 4대장인 JPA ORM, OAuth2 인증, AWS 배포, CI/CD를 최신 트렌드에 맞게 그리고 실무에 유용하게 알려준다. 모든 장 끝에는 연습문제가 수록되어 있어 배운 내용을 점검할 수 있다. 이번 2판에는 스프링 부트 프로젝트 진행을 위해 꼭 알아야 하는 SQL 기초와 실습, 스프링 시큐리티 업데이트, 블로그 댓글 기능 등을 추가하여 더욱 좋은 책으로 만들었다.',
        1200, '2024-04-05', 'AVAILABLE', 100,
        'https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791191905717.jpg', true),
       ('리액트훅 인 액션', '존 라슨 (지은이),오현석 (옮긴이)', '9791189909611',
        '여러 리액트 컴포넌트 사이에 기능 재사용과 손쉬운 공유를 위한 목적으로 만들어진 자바스크립트 함수인 리액트 훅(React Hook)을 사용하면 컴포넌트를 더 작은 함수로 나눌 수 있고, 상태와 부수 효과를 관리할 수 있으며, 클래스를 사용하지 않고도 리액트의 기능을 활용할 수 있다. 게다가 컴포넌트의 계층 구조를 재배열하지 않고도 이 모든 이점을 얻을 수 있다.',
        30000, '2024-03-29', 'AVAILABLE', 200, null, false),
       ('고급 파이썬 프로그래밍', 'Emily Johnson', '9781402894629',
        '전문적인 코딩을 위한 파이썬 프로그래밍의 고급 개념과 기술을 탐구합니다.', 45000, '2024-01-15', 'AVAILABLE', 150, null, true),
       ('Node.js 이해하기', 'Michael Roberts', '9781402894636',
        '기본부터 고급 주제까지, Node.js를 사용하여 확장성 있고 효율적인 웹 애플리케이션을 구축하는 방법을 배웁니다.', 38000, '2024-02-20',
        'AVAILABLE', 120, null, false),
       ('풀스택 Vue.js와 스프링 부트', 'Amit Patel', '9781402894643',
        'Vue.js의 프론트엔드 마법과 강력한 스프링 부트를 결합하여 강력한 풀스택 애플리케이션을 구축합니다.', 42000, '2023-12-12',
        'AVAILABLE', 90, null, true),
       ('앵귤러 초보자 가이드', 'Samantha Ming', '9781402894650',
        '웹 개발 세계로의 여정을 앵귤러와 함께 시작하세요. 기초부터 그 이상을 배울 수 있습니다.', 35000, '2024-03-05', 'AVAILABLE', 200,
        null, false),
       ('블록체인 마스터하기', 'Daniel Parker', '9781402894667',
        '다양한 산업에서 블록체인 기술을 이해하고 적용하는 방법에 대한 포괄적인 가이드.', 48000, '2024-05-14', 'AVAILABLE', 85, null, true),
       ('자바 데이터 구조', 'Laura Brown', '9781402894674',
        '자바에서 데이터 구조에 대해 배우고 실제 예제와 실제 시나리오를 통해 학습합니다.', 39000, '2024-04-23', 'AVAILABLE', 100,
        null, false),
       ('리액트 로드맵', 'Andrew Mead', '9781402894681', '리액트를 사용하여 확장 가능하고 유지 관리 가능한 애플리케이션을 구축하는 여정.',
        31000, '2024-01-10', 'AVAILABLE', 180, null, true),
       ('머신러닝 필수 요소', 'Sarah Lee', '9781402894698', '머신러닝의 기본을 탐구하는 필수 가이드로 모든 주요 측면을 다룹니다.', 43000,
        '2024-02-25', 'AVAILABLE', 70, null, false),
       ('Git과 Github 마스터하기', 'Tom Preston-Werner', '9781402894704',
        '모든 기술 수준을 위해 설계된 실용적인 가이드를 통해 Git과 GitHub를 마스터하세요.', 32000, '2024-06-30', 'AVAILABLE', 160,
        null, true),
       ('자바스크립트: 좋은 부분', 'Douglas Crockford', '9781402894711',
        '자바스크립트 프로그래밍의 모범 사례와 숨겨진 보석을 발견하세요.', 28000, '2024-07-12', 'AVAILABLE', 200, null, false),
       ('자바스크립트 비동기 프로그래밍', 'Kyle Simpson', '9781402894728', '비동기 프로그래밍 패턴과 자바스크립트에서의 활용법을 탐구합니다.',
        35000, '2024-08-01', 'AVAILABLE', 150, null, true),
       ('모던 C++ 프로그래밍', 'Bjarne Stroustrup', '9781402894735', 'C++의 최신 기능과 모범 사례를 학습합니다.', 45000,
        '2024-09-10', 'AVAILABLE', 120, null, false),
       ('Kotlin으로 안드로이드 개발하기', 'Jake Wharton', '9781402894742',
        'Kotlin을 사용하여 안드로이드 애플리케이션을 개발하는 방법을 배웁니다.', 42000, '2024-10-05', 'AVAILABLE', 100, null, true),
       ('데이터 과학 입문', 'Joel Grus', '9781402894759', '데이터 과학의 기본 개념과 실습 예제를 다룹니다.', 34000,
        '2024-11-20', 'AVAILABLE', 180, null, false),
       ('파이썬 머신러닝 쿡북', 'Chris Albon', '9781402894766', '파이썬을 이용한 머신러닝 실습을 위한 레시피 모음입니다.', 38000,
        '2024-12-15', 'AVAILABLE', 90, null, true),
       ('클라우드 네이티브 애플리케이션', 'Josh Long', '9781402894773', '클라우드 환경에서 애플리케이션을 개발하고 배포하는 방법을 배웁니다.',
        40000, '2025-01-08', 'AVAILABLE', 110, null, false),
       ('딥러닝과 자연어 처리', 'Yoav Goldberg', '9781402894780', '딥러닝을 활용한 자연어 처리 기술을 학습합니다.', 45000,
        '2025-02-14', 'AVAILABLE', 130, null, true),
       ('R로 데이터 분석하기', 'Hadley Wickham', '9781402894797', 'R 언어를 사용한 데이터 분석 기법을 다룹니다.', 32000,
        '2025-03-03', 'AVAILABLE', 140, null, false),
       ('스칼라 프로그래밍', 'Martin Odersky', '9781402894803', '스칼라 언어의 기본과 고급 기능을 학습합니다.', 37000,
        '2025-04-22', 'AVAILABLE', 100, null, true),
       ('애자일 소프트웨어 개발', 'Robert Martin', '9781402894810', '애자일 방법론을 사용한 소프트웨어 개발의 모범 사례를 다룹니다.',
        39000, '2025-05-17', 'AVAILABLE', 120, null, false),
       ('데이터베이스 설계와 구현', 'Thomas Connolly', '9781402894827', '효율적인 데이터베이스 설계와 구현 방법을 학습합니다.', 42000,
        '2025-06-21', 'AVAILABLE', 80, null, true),
       ('도커와 쿠버네티스', 'Nigel Poulton', '9781402894834', '컨테이너 기술을 활용한 애플리케이션 배포와 관리 방법을 배웁니다.',
        36000, '2025-07-13', 'AVAILABLE', 110, null, false),
       ('안드로이드 UI 디자인', 'Roman Nurik', '9781402894841', '안드로이드 애플리케이션의 사용자 인터페이스 디자인 기법을 학습합니다.',
        30000, '2025-08-04', 'AVAILABLE', 160, null, true),
       ('파워 BI로 데이터 시각화하기', 'Alberto Ferrari', '9781402894858', '파워 BI를 사용한 데이터 시각화와 분석 기법을 다룹니다.',
        35000, '2025-09-09', 'AVAILABLE', 140, null, false),
       ('클린 코드', 'Robert C. Martin', '9781402894865', '깨끗하고 유지 보수 가능한 코드 작성 방법을 학습합니다.', 38000,
        '2025-10-11', 'AVAILABLE', 150, null, true),
       ('TDD로 배우는 자바', 'Kent Beck', '9781402894872', '테스트 주도 개발을 통해 자바 프로그래밍을 학습합니다.', 40000,
        '2025-11-18', 'AVAILABLE', 130, null, false),
       ('스프링 마이크로서비스', 'John Carnell', '9781402894889', '스프링 프레임워크를 사용한 마이크로서비스 아키텍처 구현 방법을 배웁니다.',
        42000, '2025-12-25', 'AVAILABLE', 100, null, true),
       ('리액트 네이티브로 앱 개발하기', 'Nader Dabit', '9781402894896',
        '리액트 네이티브를 사용하여 모바일 애플리케이션을 개발하는 방법을 학습합니다.', 38000, '2026-01-06', 'AVAILABLE', 120, null, false),
       ('빅데이터 분석', 'Viktor Mayer-Schonberger', '9781402894902', '빅데이터 분석 기법과 실제 사례를 다룹니다.', 45000,
        '2026-02-17', 'AVAILABLE', 90, null, true),
       ('알고리즘 문제 해결 전략', 'Steven Skiena', '9781402894919', '효율적인 알고리즘 문제 해결 기법과 실습 예제를 제공합니다.',
        37000, '2026-03-28', 'AVAILABLE', 140, null, false);

-- 카테고리 테스트 값 추가
INSERT INTO category (name, parent_id)
VALUES
    -- 루트 카테고리
    ('국내도서', null),
    ('해외도서', null),
    ('전자책', null),
    ('만화', null),

    -- '국내도서' 아래 2단계 카테고리
    ('소설', 1),
    ('시/에세이', 1),
    ('인문학', 1),
    ('역사', 1),

    -- '해외도서' 아래 2단계 카테고리
    ('문학', 2),
    ('취미/실용/여행', 2),
    ('생활/요리', 2),
    ('과학/기술', 2),

    -- '전자책' 아래 2단계 카테고리
    ('만화', 3),
    ('어린이', 3),
    ('교육', 3),
    ('비즈니스', 3),

    -- '만화' 아래 2단계 카테고리
    ('판타지', 4),
    ('그래픽 노블', 4),
    ('웹툰', 4),
    ('코믹 스트립', 4),

    -- '소설' 아래 3단계 카테고리
    ('한국 소설', 5),
    ('영미 소설', 5),
    ('번역 소설', 5),
    ('판타지/과학 소설', 5),

    -- '시/에세이' 아래 3단계 카테고리
    ('현대 시', 6),
    ('고전 시', 6),
    ('개인 에세이', 6),
    ('문학 비평', 6),

    -- '인문학' 아래 3단계 카테고리
    ('철학', 7),
    ('사회학', 7),
    ('인류학', 7),
    ('언어학', 7),

    -- '역사' 아래 3단계 카테고리
    ('세계사', 8),
    ('한국사', 8),
    ('군사 역사', 8),
    ('전기', 8),

    -- '문학' 아래 3단계 카테고리
    ('고전 문학', 9),
    ('현대 문학', 9),
    ('시', 9),
    ('드라마', 9),

    -- '취미/실용/여행' 아래 3단계 카테고리
    ('공예/DIY', 10),
    ('원예', 10),
    ('여행 가이드', 10),
    ('사진', 10),

    -- '생활/요리' 아래 3단계 카테고리
    ('요리', 11),
    ('건강/피트니스', 11),
    ('육아', 11),
    ('홈 인테리어', 11),

    -- '과학/기술' 아래 3단계 카테고리
    ('물리학', 12),
    ('생물학', 12),
    ('컴퓨터 과학', 12),
    ('공학', 12),

    -- '만화' 아래 3단계 카테고리
    ('아동 만화', 13),
    ('성인 만화', 13),
    ('유머 만화', 13),
    ('모험 만화', 13),

    -- '어린이' 아래 3단계 카테고리
    ('그림책', 14),
    ('청소년', 14),
    ('교육용', 14),
    ('우화/동화', 14),

    -- '교육' 아래 3단계 카테고리
    ('교과서', 15),
    ('학습 가이드', 15),
    ('언어 학습', 15),
    ('참고서', 15),

    -- '비즈니스' 아래 3단계 카테고리
    ('경영', 16),
    ('창업', 16),
    ('재무/투자', 16),
    ('마케팅', 16),

    -- '판타지' 아래 3단계 카테고리
    ('로맨스 만화', 17),
    ('SF 만화', 17),
    ('청년 만화', 17),
    ('무협 만화', 17),

    -- '그래픽 노블' 아래 3단계 카테고리
    ('슈퍼히어로', 18),
    ('판타지', 18),
    ('공포', 18),
    ('논픽션', 18),

    -- '웹툰' 아래 3단계 카테고리
    ('로맨스', 19),
    ('액션', 19),
    ('코미디', 19),
    ('드라마', 19),

    -- '코믹 스트립' 아래 3단계 카테고리
    ('신문 만화', 20),
    ('웹 코믹', 20),
    ('역사 만화', 20),
    ('유머 만화', 20)
;
INSERT INTO book_category (product_id, category_id)
VALUES (1, 5),
       (2, 6),
       (3, 7),
       (4, 8),
       (5, 5),
       (6, 9),
       (7, 10),
       (8, 11),
       (9, 12),
       (10, 13),
       (11, 14),
       (12, 15),
       (13, 16),
       (14, 17),
       (15, 18),
       (16, 19),
       (17, 20),
       (18, 21),
       (19, 22),
       (20, 23),
       (21, 24),
       (22, 25),
       (23, 26),
       (24, 27),
       (25, 28),
       (26, 29),
       (27, 30),
       (28, 31),
       (29, 32),
       (30, 7),
       (31, 8),
       (32, 9);



INSERT INTO users (name, email, password, nickname, phone, role, status, terms_agreed,
                   marketing_agreed, email_verified, last_login)
VALUES ('Tester1', 'tester1@test.com', '$2b$12$ExampleHash', 'tester1', '010-0000-0001', 1, 0, true,
        false, true, NOW()),
       ('Tester2', 'tester2@test.com', '$2b$12$ExampleHash', 'tester2', '010-0000-0002', 1, 0, true,
        false, true, NOW()),
       ('Tester3', 'tester3@test.com', '$2b$12$ExampleHash', 'tester3', '010-0000-0003', 1, 0, true,
        false, true, NOW()),
       ('Tester4', 'tester4@test.com', '$2b$12$ExampleHash', 'tester4', '010-0000-0004', 1, 0, true,
        false, true, NOW()),
       ('Tester5', 'tester5@test.com', '$2b$12$ExampleHash', 'tester5', '010-0000-0005', 1, 0, true,
        false, true, NOW()),
       ('Tester6', 'tester6@test.com', '$2b$12$ExampleHash', 'tester6', '010-0000-0006', 1, 0, true,
        false, true, NOW()),
       ('Tester7', 'tester7@test.com', '$2b$12$ExampleHash', 'tester7', '010-0000-0007', 1, 0, true,
        false, true, NOW()),
       ('Tester8', 'tester8@test.com', '$2b$12$ExampleHash', 'tester8', '010-0000-0008', 1, 0, true,
        false, true, NOW()),
       ('Tester9', 'tester9@test.com', '$2b$12$ExampleHash', 'tester9', '010-0000-0009', 1, 0, true,
        false, true, NOW()),
       ('Tester10', 'tester10@test.com', '$2b$12$ExampleHash', 'tester10', '010-0000-0010', 1, 0,
        true, false, true, NOW());

INSERT INTO delivery (delivery_id, delivery_status, delivery_start, tracking_number)
VALUES (1, 'READY', '2023-06-01', 1234567891),
       (2, 'STARTED', '2023-06-01', 1234567892),
       (3, 'COMPLETED', '2023-06-01', 1234567893),
       (4, 'CANCELED', '2023-06-01', 1234567894),
       (5, 'READY', '2023-06-01', 1234567895),
       (6, 'READY', '2023-06-01', 1234567896),
       (7, 'READY', '2023-06-01', 1234567897),
       (8, 'READY', '2023-06-01', 1234567898),
       (9, 'READY', '2023-06-01', 1234567899),
       (10, 'READY', '2023-06-01', 1234567890);

INSERT INTO address (address_id, user_id, label, is_primary, zipcode, address1,
                     address2, recipient_name, recipient_phone)
VALUES (1, 1, 'Home 1', TRUE, '10001', '123 Example St, Apt 1', 'City, State, 1', 'Recipient 1',
        '010-0000-0001'),
       (2, 2, 'Home 2', FALSE, '10002', '123 Example St, Apt 2', 'City, State, 2', 'Recipient 2',
        '010-0000-0002'),
       (3, 3, 'Home 3', FALSE, '10003', '123 Example St, Apt 3', 'City, State, 3', 'Recipient 3',
        '010-0000-0003'),
       (4, 4, 'Home 4', FALSE, '10004', '123 Example St, Apt 4', 'City, State, 4', 'Recipient 4',
        '010-0000-0004'),
       (5, 5, 'Home 5', FALSE, '10005', '123 Example St, Apt 5', 'City, State, 5', 'Recipient 5',
        '010-0000-0005'),
       (6, 6, 'Home 6', FALSE, '10006', '123 Example St, Apt 6', 'City, State, 6', 'Recipient 6',
        '010-0000-0006'),
       (7, 7, 'Home 7', FALSE, '10007', '123 Example St, Apt 7', 'City, State, 7', 'Recipient 7',
        '010-0000-0007'),
       (8, 8, 'Home 8', FALSE, '10008', '123 Example St, Apt 8', 'City, State, 8', 'Recipient 8',
        '010-0000-0008'),
       (9, 9, 'Home 9', FALSE, '10009', '123 Example St, Apt 9', 'City, State, 9', 'Recipient 9',
        '010-0000-0009'),
       (10, 10, 'Home 10', FALSE, '10010', '123 Example St, Apt 10', 'City, State, 10',
        'Recipient 10', '010-0000-0010');
--
INSERT INTO orders (merchant_Uid, ORDER_DATE_TIME, ORDER_STATUS, user_id, delivery_id,
                    ORDER_TOTAL_PRICE, ORDER_TOTAL_AMOUNT)
VALUES ('MID1001', '2023-06-01T14:30:00', 'PAYED', 1, 1, 5000, 3),
       ('MID1002', '2023-06-02T15:00:00', 'DELIVERED', 2, 2, 6000, 2),
       ('MID1003', '2023-06-03T16:30:00', 'CANCELED', 3, 3, 7000, 5),
       ('MID1004', '2023-06-04T17:00:00', 'ACCEPTED', 4, 4, 8000, 1),
       ('MID1005', '2023-06-05T18:30:00', 'PAYED', 5, 5, 3000, 4),
       ('MID1006', '2023-06-06T19:00:00', 'PAYED', 6, 6, 4000, 3),
       ('MID1007', '2023-06-07T20:30:00', 'PAYED', 7, 7, 2000, 2),
       ('MID1008', '2023-06-08T21:00:00', 'PAYED', 8, 8, 5000, 5),
       ('MID1009', '2023-06-09T22:30:00', 'PAYED', 9, 9, 4500, 6),
       ('MID1010', '2023-06-10T23:00:00', 'PAYED', 10, 10, 3500, 1);
