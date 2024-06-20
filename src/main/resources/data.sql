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
VALUES ('해커스 토익 Listening', 'David Cho', '9788965422781', '해커스 토익 Listening 교재는 최신 기출 경향을 반영한 실전 모의고사 문제와 해설, 그리고 Listening 전략을 포함하고 있습니다. 수많은 토익 수험생들이 선택한 이 교재는...', 14800, '2023-01-10', 'AVAILABLE', 100, '1.jpeg', false),
       ('해커스 토익 Reading', 'David Cho', '9788965422798', '해커스 토익 Reading 교재는 최신 기출 경향을 반영한 실전 모의고사 문제와 해설, 그리고 Reading 전략을 포함하고 있습니다. 이 교재를 통해 학생들은 기초부터 실전까지 ...', 14800, '2023-01-10', 'AVAILABLE', 100, '2.jpeg', false),
       ('미움받을 용기', '기시미 이치로', '9788960518786', '기시미 이치로의 대표작으로, 아들러 심리학을 기반으로 한 삶의 지혜를 전달하는 책입니다. 이 책은 독자에게 자신을 사랑하고, 타인의 시선을 의식하지 않으며 살아가는 방...', 15800, '2021-11-01', 'AVAILABLE', 50, '3.jpeg', false),
       ('나미야 잡화점의 기적', '히가시노 게이고', '9788972756194', '히가시노 게이고의 미스터리 소설로, 우연히 찾은 잡화점에서 벌어지는 기적 같은 이야기를 담고 있습니다. 이 책은 과거와 현재가 교차하며 펼쳐지는 스토리를 통해 독자에...', 13800, '2021-10-20', 'AVAILABLE', 70, '4.jpeg', true),
       ('데미안', '헤르만 헤세', '9788937460440', '헤르만 헤세의 성장 소설로, 청소년과 성인의 경계에서 방황하는 주인공의 내면적 성장을 그린 작품입니다. 이 책은 인간 존재의 본질과 자아 찾기의 여정을 담고 있으며, ...', 12000, '2020-05-15', 'AVAILABLE', 30, '5.jpeg', false),
       ('자바의 정석', '남궁성', '9788994492032', '남궁성의 자바 프로그래밍 입문서로, 기초부터 심화까지 다루는 종합적인 자바 교과서입니다. 이 책은 자바의 기본 개념부터 시작하여 객체 지향 프로그래밍, 예외 처리, 컬렉션...', 32000, '2019-11-15', 'AVAILABLE', 150, '6.jpeg', true),
       ('코어 자바', '케이 호르스트만', '9788980782958', '케이 호르스트만의 자바 프로그래밍 기본서로, 자바의 핵심 개념과 실습 예제를 제공하는 책입니다. 이 책은 자바 언어의 기본 문법, 객체 지향 프로그래밍, 스트림 처리 등...', 45000, '2018-08-05', 'AVAILABLE', 80, '7.jpeg', false),
       ('이것이 자바다', '신용권', '9788968481471', '신용권의 자바 프로그래밍 입문서로, 자바의 기본부터 고급 개념까지 다루는 종합적인 교과서입니다. 이 책은 초보자도 쉽게 따라할 수 있는 예제와 실습 문제를 통해 자바 프...', 38000, '2020-04-20', 'AVAILABLE', 120, '8.jpeg', false),
       ('모던 자바 인 액션', '라울-게이브리얼 우르마', '9788960778982', '라울-게이브리얼 우르마의 자바 프로그래밍 서적으로, 최신 자바 기능과 라이브러리를 다루는 책입니다. 이 책은 람다 표현식, 스트림 API, 디폴트 메소드 등 자바의 최신...', 42000, '2019-06-30', 'AVAILABLE', 60, '9.jpeg', true),
       ('자바 ORM 표준 JPA 프로그래밍', '김영한', '9788999415722', '김영한의 자바 ORM 표준 JPA 프로그래밍 책으로, JPA의 기본 개념부터 실전 예제까지 다루고 있습니다. 이 책은 엔티티 매핑, 연관 관계 매핑, JPQL 등 JPA ...', 45000, '2018-02-10', 'AVAILABLE', 90, '10.jpeg', false),
       ('스프링 부트 3 백엔드 개발자 되기: 자바 편', '신선영', '9788965402608', '백기선의 스프링 부트 2 프로그래밍 책으로, 스프링 프레임워크의 기본 개념부터 실전 예제까지 다룹니다. 이 책은 스프링 부트의 설정, 웹 애플리케이션 개발, 데이터베이스...', 38000, '2021-05-25', 'AVAILABLE', 70, '11.jpeg', true),
       ('실전 스프링 부트와 리액트 개발', '이동욱', '9788968481471', '이동욱의 실전 스프링 부트와 리액트 개발 책으로, 최신 웹 기술을 활용한 풀스택 개발을 다룹니다. 이 책은 스프링 부트와 리액트를 연동하여 애플리케이션을 개발하는 방법을...', 42000, '2020-03-30', 'AVAILABLE', 80, '12.jpeg', false),
       ('토비의 스프링 3.1', '이일민', '9788960773437', '이일민의 스프링 3.1 프로그래밍 책으로, 스프링 프레임워크의 심화 개념을 다룹니다. 이 책은 스프링의 핵심 개념과 디자인 패턴, 실전 예제 등을 포함하고 있어 스프링을...', 45000, '2017-11-20', 'AVAILABLE', 60, '13.jpeg', false),
       ('스프링 인 액션', '크레이그 월즈', '9788979146189', '크레이그 월즈의 스프링 인 액션 책으로, 스프링 프레임워크의 최신 기능과 실전 예제를 다룹니다. 이 책은 스프링의 핵심 개념, 웹 애플리케이션 개발, 데이터베이스 연동 등...', 48000, '2019-09-15', 'AVAILABLE', 90, '14.jpeg', true),
       ('리팩토링 2판', '마틴 파울러', '9788966260962', '마틴 파울러의 리팩토링 책으로, 코드의 품질을 개선하는 방법을 다룹니다. 이 책은 리팩토링의 기본 개념부터 실전 예제까지 포함하고 있어 개발자들에게 유용한 정보를 제공합...', 42000, '2018-12-20', 'AVAILABLE', 100, '15.jpeg', false),
       ('클린 코드', '로버트 C. 마틴', '9788966260955', '로버트 C. 마틴의 클린 코드 책으로, 깨끗하고 유지 보수하기 쉬운 코드를 작성하는 방법을 다룹니다. 이 책은 코드의 가독성, 리팩토링, 테스트 작성 등 다양한 주제를 ...', 38000, '2017-07-10', 'AVAILABLE', 120, '16.jpeg', false),
       ('프로그래밍 쪽빛노트', '장세훈', '9788966260740', '장세훈의 프로그래밍 책으로, 프로그래밍의 기본 개념과 실습 예제를 다룹니다. 이 책은 초보자도 쉽게 따라할 수 있는 예제와 문제를 통해 프로그래밍의 기초를 익힐 수 있습...', 25000, '2016-05-15', 'AVAILABLE', 50, '17.jpeg', true),
       ('파이썬 알고리즘 인터뷰', '박상길', '9788966260757', '박상길의 파이썬 알고리즘 책으로, 알고리즘 문제 해결 방법을 다룹니다. 이 책은 다양한 알고리즘 문제와 그 해결 방법을 제공하며, 파이썬을 사용하여 문제를 해결하는 방법...', 36000, '2020-07-10', 'AVAILABLE', 90, '18.jpeg', false),
       ('다시 태어나도 엄마 딸', '김지영', '9788965423207', '김지영 작가의 자전적 에세이로, 어머니와 딸 사이의 특별한 관계를 담담하게 풀어낸 책입니다. 어머니와의 추억과 사랑을 통해 가족의 소중함을 일깨워주는 감동적인 이야기...', 16800, '2022-09-15', 'AVAILABLE', 200, '19.jpeg', true),
       ('나는 나로 살기로 했다', '김수현', '9788965746353', '김수현 작가의 자존감 회복 에세이로, 자신을 사랑하고 당당하게 살아가는 방법을 제시합니다. 이 책은 일상 속 작은 변화들을 통해 행복을 찾고, 자존감을 높이는 방법을...', 13800, '2021-04-20', 'AVAILABLE', 150, '20.jpeg', true),
       ('하루 1시간 인생정리법', '고도원', '9788954645617', '고도원 작가의 자기계발서로, 하루 1시간을 투자해 인생을 정리하고 재정비하는 방법을 소개합니다. 이 책은 명상, 운동, 독서 등 다양한 활동을 통해 삶의 질을 높이는 방...', 16000, '2021-11-01', 'AVAILABLE', 120, '21.jpeg', false),
       ('아침형 인간', '야마시타 히데코', '9788935205609', '야마시타 히데코의 자기계발서로, 아침 일찍 일어나 생산적인 하루를 시작하는 방법을 소개합니다. 이 책은 아침 시간을 활용해 더 많은 일을 하고, 더 나은 삶을 사는 비...', 15000, '2020-03-15', 'AVAILABLE', 180, '22.jpeg', false),
       ('꿈꾸는 다락방', '이지성', '9788965746323', '이지성 작가의 자기계발서로, 꿈을 이루기 위한 구체적인 방법과 마인드셋을 제시합니다. 이 책은 목표 설정, 계획 수립, 자기 관리 등을 통해 꿈을 현실로 만드는 방법을...', 14000, '2019-07-25', 'AVAILABLE', 140, '23.jpeg', true),
       ('언어의 온도', '이기주', '9788954645617', '이기주 작가의 에세이로, 언어가 가진 힘과 그 온도에 대해 이야기합니다. 이 책은 말과 글이 사람의 마음을 어떻게 움직이는지, 그리고 그 안에 담긴 따뜻함을 전합니다...', 13500, '2018-06-20', 'AVAILABLE', 130, '24.jpeg', true),
       ('하버드 상위 1퍼센트의 비밀', '정주영', '9788954637162', '정주영 작가의 자기계발서로, 하버드 대학의 상위 1% 학생들이 실천하는 성공 비결을 소개합니다. 이 책은 공부 방법, 시간 관리, 목표 설정 등 다양한 주제를 다룹니다...', 17500, '2022-02-10', 'AVAILABLE', 170, '25.jpeg', false),
       ('아주 작은 습관의 힘', '제임스 클리어', '9788932473910', '제임스 클리어의 자기계발서로, 작은 습관의 변화가 인생에 미치는 큰 영향을 설명합니다. 이 책은 습관 형성의 원리와 이를 실천하는 구체적인 방법을 제시하여 독자가 원하...', 15800, '2020-10-05', 'AVAILABLE', 190, '26.jpeg', true),
       ('지적 대화를 위한 넓고 얕은 지식', '채사장', '9788994120997', '채사장 작가의 교양서로, 다양한 분야의 지식을 쉽게 설명합니다. 이 책은 역사, 철학, 경제, 정치 등 여러 주제를 다루며 독자가 지적인 대화를 나눌 수 있도록 도와줍...', 18000, '2019-04-15', 'AVAILABLE', 160, '27.jpeg', false),
       ('평생 돈운이 따르는 법', '유수진', '9788965706326', '유수진 작가의 재테크 책으로, 평생 돈운이 따르는 방법을 소개합니다. 이 책은 돈을 끌어들이는 마인드셋과 실천 방법을 제시하여 재테크에 관심 있는 독자에게 유익한 정보...', 15000, '2021-08-01', 'AVAILABLE', 100, '28.jpeg', true),
       ('마흔에 읽는 니체', '장은수', '9788932473910', '장은수 작가의 철학 에세이로, 니체의 사상을 마흔의 관점에서 풀어냅니다. 이 책은 중년의 독자들이 인생의 의미를 찾고, 보다 나은 삶을 살기 위한 철학적 통찰을 제공합니다...', 17800, '2020-05-10', 'AVAILABLE', 110, '29.jpeg', true),
       ('한 권으로 끝내는 디지털 마케팅', '황희철', '9788935703626', '황희철 작가의 자기계발서로, 디지털 마케팅의 기본 개념과 실전 전략을 소개합니다. 이 책은 소셜 미디어, 콘텐츠 마케팅, 데이터 분석 등 다양한 주제를 다루며, 마케터...', 20000, '2022-07-20', 'AVAILABLE', 100, '30.png', false),
       ('말 그릇', '김윤나', '9788954645617', '김윤나 작가의 자기계발서로, 말하는 방식이 인생에 미치는 영향을 설명합니다. 이 책은 말의 중요성과 효과적인 대화 방법을 통해 독자가 더 나은 인간 관계를 맺을 수 있...', 14500, '2018-11-25', 'AVAILABLE', 150, '31.jpeg', true),
       ('여행의 이유', '김영하', '9788936477162', '김영하 작가의 에세이로, 여행을 통해 얻는 깨달음과 경험을 담고 있습니다. 이 책은 여행이 우리에게 주는 의미와 그 속에서 발견하는 삶의 진리를 탐구합니다...', 16000, '2019-03-05', 'AVAILABLE', 120, '32.jpeg', false),
       ('이상한 정상 가족', '김희경', '9788936466263', '김희경 작가의 에세이로, 현대 사회의 가족 문제를 다룹니다. 이 책은 가족의 의미와 구성원 간의 관계를 재조명하며, 건강한 가족을 위한 제언을 담고 있습니다...', 17500, '2018-02-20', 'AVAILABLE', 100, '33.jpeg', false),
       ('서른의 심리학', '김민철', '9788954645617', '김민철 작가의 자기계발서로, 서른 살이 겪는 심리적 변화를 다룹니다. 이 책은 서른이 되면서 느끼는 고민과 불안을 극복하고, 새로운 시작을 준비하는 방법을 제시합니다...', 15000, '2020-04-15', 'AVAILABLE', 130, '34.jpeg', true),
       ('1cm 다이빙', '태영호', '9788994120706', '태영호 작가의 에세이로, 일상에서 발견하는 작은 행복을 이야기합니다. 이 책은 독자가 매일의 순간을 소중히 여기고, 작은 변화에서 큰 기쁨을 찾을 수 있도록 도와줍니다...', 13500, '2019-08-25', 'AVAILABLE', 140, '35.jpeg', true),
       ('오늘부터 차분하게 산다', '우에니시 아키라', '9788934947119', '우에니시 아키라의 자기계발서로, 현대인의 스트레스와 불안을 해소하는 방법을 소개합니다. 이 책은 마음의 평안을 찾기 위한 구체적인 방법과 일상에서 실천할 수 있는 팁을...', 17000, '2021-01-15', 'AVAILABLE', 160, '36.jpeg', false),
       ('나의 하루는 4시 30분에 시작된다', '김유진', '9788967976326', '김유진 작가의 자기계발서로, 이른 아침 시간을 활용하여 하루를 더 생산적으로 보내는 방법을 소개합니다. 이 책은 아침 루틴, 시간 관리, 목표 설정 등의 주제를 다루며...', 15500, '2020-06-05', 'AVAILABLE', 120, '37.jpeg', true),
       ('해리 포터와 마법사의 돌', 'J.K. 롤링', '9788965743832', '소년 해리 포터가 호그와트 마법 학교에 입학하면서 벌어지는 모험을 그린 이야기입니다.', 15000, '1997-06-26', 'AVAILABLE', 120, '38.jpeg', false),
       ('어린 왕자', '앙투안 드 생텍쥐페리', '9788932917245', '어린 왕자가 자신의 별을 떠나 여러 행성을 여행하며 만난 다양한 인물들과 겪는 이야기를 담은 철학 동화입니다.', 8000, '1943-04-06', 'AVAILABLE', 200, '39.jpeg', false),
       ('반지의 제왕: 반지 원정대', 'J.R.R. 톨킨', '9788982733014', '프로도 배긴스와 그의 친구들이 사우론의 반지를 파괴하기 위해 모험을 떠나는 이야기입니다.', 22000, '1954-07-29', 'AVAILABLE', 150, '40.jpeg', false),
       ('나미야 잡화점의 기적', '히가시노 게이고', '9788972756194', '폐업한 잡화점에서 벌어지는 의문의 사건들과 과거의 편지를 통해 연결되는 사람들의 이야기입니다.', 13000, '2012-03-25', 'AVAILABLE', 170, '41.jpeg', true),
       ('채식주의자', '한강', '9788954636765', '한 여성이 채식을 선언하며 벌어지는 가족 간의 갈등과 그로 인한 변화들을 그린 소설입니다.', 12000, '2007-10-30', 'AVAILABLE', 140, '42.jpeg', true),
       ('1984', '조지 오웰', '9788937431232', '전체주의적 감시 사회를 배경으로 한 디스토피아 소설로, 개인의 자유와 권리가 억압받는 세상을 그렸습니다.', 9000, '1949-06-08', 'AVAILABLE', 110, '43.jpeg', false),
       ('데미안', '헤르만 헤세', '9788932917245', '주인공 싱클레어가 자아를 찾아가는 과정에서 만난 데미안과의 교류를 통해 성장하는 이야기를 담고 있습니다.', 8000, '1919-11-12', 'AVAILABLE', 180, '44.jpeg', false),
       ('파친코', '이민진', '9788937463290', '20세기 초부터 전후 일본에 이르기까지, 한국 이민자 가족의 세대를 통해 드러나는 역사적 사건과 개인의 이야기를 그린 소설입니다.', 17000, '2017-04-25', 'AVAILABLE', 160, '45.jpeg', true),
       ('그리스인 조르바', '니코스 카잔차키스', '9788972756194', '그리스의 조르바라는 인물이 주인공에게 삶의 의미와 자유에 대해 가르치는 이야기를 담은 철학 소설입니다.', 12000, '1946-06-20', 'AVAILABLE', 140, '46.jpeg', true),
       ('연금술사', '파울로 코엘료', '9788983923086', '양치기 소년 산티아고가 자신의 꿈을 찾아 떠나는 여정을 통해 인생의 교훈을 배우는 이야기입니다.', 11000, '1988-11-01', 'AVAILABLE', 130, '47.jpeg', true),
       ('하울의 움직이는 성', '다이애나 윈 존스', '9788954622034', '마녀의 저주를 받은 소녀 소피가 마법사 하울과 함께 저주를 풀기 위한 모험을 떠나는 이야기입니다.', 13000, '1986-04-05', 'AVAILABLE', 120, '48.jpeg', true),
       ('달러구트 꿈 백화점', '이미예', '9788954443004', '꿈을 사고파는 백화점을 배경으로 펼쳐지는 다양한 꿈과 꿈꾸는 사람들의 이야기를 그린 소설입니다.', 14000, '2020-07-08', 'AVAILABLE', 180, '49.jpeg', true),
       ('해변의 카프카', '무라카미 하루키', '9788954635119', '소년 카프카가 가출 후 겪는 신비롭고 초현실적인 사건들을 통해 성장하는 이야기를 담은 소설입니다.', 15000, '2002-09-12', 'AVAILABLE', 150, '50.jpeg', true),
       ('달과 6펜스', '서머싯 몸', '9788972756194', '화가로서의 열정을 따라 평범한 삶을 버리고 예술을 향해 나아가는 한 남자의 이야기를 그린 소설입니다.', 11000, '1919-12-15', 'AVAILABLE', 170, '51.jpeg', true),
       ('대학의 영혼', 'C.S. 루이스', '9788932917245', '한 남자가 대학 생활에서 겪는 다양한 사건과 그를 통해 얻는 교훈을 담은 철학적 소설입니다.', 8000, '1945-04-06', 'AVAILABLE', 200, '52.jpeg', false),
       ('고양이의 보은', '히로시마 유미', '9788954443004', '한 소녀가 고양이 왕국에서 벌어지는 모험을 통해 자신을 발견하는 이야기를 그린 소설입니다.', 12000, '2002-07-08', 'AVAILABLE', 140, '53.jpeg', true),
       ('노르웨이의 숲', '무라카미 하루키', '9788954635119', '대학생 와타나베가 친구와의 관계 속에서 사랑과 상실을 경험하며 성장하는 이야기를 담은 소설입니다.', 15000, '1987-09-12', 'AVAILABLE', 150, '54.jpeg', true),
       ('도쿄 타워', '릴리 프랭키', '9788972756194', '도쿄 타워 주변에서 벌어지는 다양한 인간 군상을 통해 현대 일본 사회를 그린 소설입니다.', 11000, '2002-12-15', 'AVAILABLE', 170, '55.jpeg', true),
       ('모비 딕', '허먼 멜빌', '9788932917245', '고래 모비 딕을 추적하는 에이해브 선장과 그의 승무원들의 모험을 그린 소설입니다.', 8000, '1851-10-18', 'AVAILABLE', 200, '56.jpeg', false)
        ;

INSERT INTO product_img_detail (product_id, detail_page_url, created_at)
VALUES (1, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/772/i9788965424772.jpg', NOW()),
       (2, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/765/i9788965424765.jpg', NOW()),
       (3, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/787/i9791168340787.jpg', NOW()),
       (4, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/188/i9791161571188.jpg', NOW()),
       (5, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/129/i9791186560129.jpg', NOW()),
       (6, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/951/i9791168341951.jpg', NOW()),
       (7, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/720/i9791158391720.jpg', NOW()),
       (8, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/940/i9791170401940.jpg', NOW()),
       (9, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/025/i9791162242025.jpg', NOW()),
       (10, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/718/i4801191905718.jpg', NOW()),
       (11, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/097/i9791158394097.jpg', NOW()),
       (12, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/354/i9791192987354.jpg', NOW()),
       (13, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/675/i9791192987675.jpg', NOW()),
       (14, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/636/i9788968484636.jpg', NOW()),
       (15, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/427/i9791169212427.jpg', NOW()),
       (16, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/199/i9791169212199.jpg', NOW()),
       (17, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/953/i9788998955953.jpg', NOW()),
       (18, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/953/i9788998955953.jpg', NOW()),
       (19, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/838/i9791130621838.jpg', NOW()),
       (20, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/150/i9791197377150.jpg', NOW()),
       (21, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/325/i9791190312325.jpg', NOW()),
       (22, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/384/i9791160077384.jpg', NOW()),
       (23, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/956/i9791185035956.jpg', NOW()),
       (24, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/171/i4801195522171.jpg', NOW()),
       (25, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/397/i9791164457397.jpg', NOW()),
       (26, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/640/i9791162540640.jpg', NOW()),
       (27, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/186/i9791190313186.jpg', NOW()),
       (28, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/036/i9788927812036.jpg', NOW()),
       (29, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/245/i9791192300245.jpg', NOW()),
       (30, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/292/i9791172100292.jpg', NOW()),
       (31, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/838/i9791168271838.jpg', NOW()),
       (32, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/842/i9791167620842.jpg', NOW()),
       (33, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/175/i9788962624175.jpg', NOW()),
       (34, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/455/i9791193506455.jpg', NOW()),
       (35, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/060/i9791190299060.jpg', NOW()),
       (36, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/647/i9788925564647.jpg', NOW()),
       (37, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/673/i9791171711673.jpg', NOW()),
       (38, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/175/i9788962624175.jpg', NOW()),
       (39, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/313/i9791190669313.jpg', NOW()),
       (40, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/460/i9788950992460.jpg', NOW()),
       (41, 'https://contents.kyobobook.co.kr/sih/fit-in/754x0/dtl/illustrate/864/i9791167372864.jpg', NOW()),
       (42, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/595/i9788936434595.jpg', NOW()),
       (43, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/595/i9788936434595.jpg', NOW()),
       (44, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/768/i9791161571768.jpg', NOW()),
       (45, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/510/i9791168340510.jpg', NOW()),
       (46, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/148/i9791157404148.jpg', NOW()),
       (47, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/812/i9791198524812.jpg', NOW()),
       (48, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/210/i9791133477210.jpg', NOW()),
       (49, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/909/i9791165341909.jpg', NOW()),
       (50, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/184/i9791193262184.jpg', NOW()),
       (51, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/233/i9791191922233.jpg', NOW()),
       (52, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/098/i9791165045098.jpg', NOW()),
       (53, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/385/i9791133494385.jpg', NOW()),
       (54, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/488/i9788937434488.jpg', NOW()),
       (55, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/382/i9788925566382.jpg', NOW()),
       (56, 'https://contents.kyobobook.co.kr/sih/fit-in/814x0/dtl/illustrate/137/i9791139707137.jpg', NOW())
        ;


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
    ('번역 소설', 5),

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




INSERT INTO orders (order_id, merchant_Uid, ORDER_DATE_TIME, ORDER_STATUS, user_id,
                    ORDER_TOTAL_PRICE, ORDER_TOTAL_AMOUNT)
VALUES (1,'MID1001', '2023-06-01T14:30:00', 'PAYED', 1, 5000, 3),
    (2,'MID1002', '2023-06-02T15:00:00', 'DELIVERED', 2, 6000, 2),
    (3,'MID1003', '2023-06-03T16:30:00', 'CANCELED', 3, 7000, 5),
    (4,'MID1004', '2023-06-04T17:00:00', 'ACCEPTED', 4, 8000, 1),
    (5,'MID1005', '2023-06-05T18:30:00', 'PAYED', 5, 3000, 4),
    (6,'MID1006', '2023-06-06T19:00:00', 'PAYED', 6, 4000, 3),
    (7,'MID1007', '2023-06-07T20:30:00', 'PAYED', 7, 2000, 2),
    (8,'MID1008', '2023-06-08T21:00:00', 'PAYED', 8, 5000, 5),
    (9,'MID1009', '2023-06-09T22:30:00', 'PAYED', 9, 4500, 6),
    (10,'MID1010', '2023-06-10T23:00:00', 'PAYED', 10, 3500, 1);


INSERT INTO delivery (delivery_id, delivery_status, delivery_start, tracking_number, order_id)
VALUES (1, 'READY', '2023-06-01', 1234567891, 1),
       (2, 'STARTED', '2023-06-01', 1234567892, 1),
       (3, 'COMPLETED', '2023-06-01', 1234567893, 1),
       (4, 'CANCELED', '2023-06-01', 1234567894, 1),
       (5, 'READY', '2023-06-01', 1234567895, 1),
       (6, 'READY', '2023-06-01', 1234567896, 1),
       (7, 'READY', '2023-06-01', 1234567897, 1),
       (8, 'READY', '2023-06-01', 1234567898, 1),
       (9, 'READY', '2023-06-01', 1234567899, 1),
       (10, 'READY', '2023-06-01', 1234567890, 1);

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


