-- -- `member` 테이블에 대한 더미 데이터
INSERT INTO member (id, `name`, member_type, phone, birthday, profile, email, simple_password, `state`, inactive_date, card_state, bank_member_id, created_at, updated_at, `role`, social)
VALUES
    (1, '강현우', 'PARENT', '010-1234-5678', '1985-01-05', null, 'tngkr7410@naver.com', '{bcrypt}$2a$10$H4hK3cIdFrHEk7dSiLF/KeBZcp99zsX2qCb6PlfupNcxZUPh3Ev7O', 'ACTIVE', null, 'NONE', 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 'ROLE_PARENT', 'naver'),
    (2, '구자빈', 'CHILD', '010-2345-6789', '2018-03-03', null, 'koreanjb1221@naver.com', '{bcrypt}$2a$10$A.yrgthceIagp5pCoPNbvug3sSpLBBBZWGZk/A8Wr4EpSO4JDvOr6', 'ACTIVE', null, 'NONE', 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 'ROLE_CHILD', 'naver'),
    (3, '최윤정', 'CHILD', '010-3456-6789', '2019-05-02', null, 'cbj0010@naver.com', '{bcrypt}$2a$10$oVd5oA3bSa4zh8JSiCLyeeVaRId17Lm.ay5ALzu9kGJD8wpNtlody', 'ACTIVE', null, 'NONE', 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 'ROLE_CHILD', 'naver');

INSERT INTO account (id, account_number, balance, password, `state`, inactive_date, member_id, created_at, updated_at)
VALUES
    (1, '1002-913-023908', 20800000, NULL, 'ACTIVE', NULL, 1, '2024-01-01 00:00:00', '2024-08-31 00:00:00'), -- 강현우
    (2, '1002-913-023909', 980000, '5678', 'ACTIVE', NULL, 2, '2024-01-01 00:00:00', '2024-07-02 00:00:00'), -- 구자빈
    (3, '1002-913-023910', 915000, '9012', 'ACTIVE', NULL, 3, '2024-01-01 00:00:00', '2024-09-03 00:00:00'); -- 최윤정

INSERT INTO card (id, card_number, valid_thru, cvc, member_name, password, card_name, `state`, inactive_date, new_date, account_id, created_at, updated_at)
VALUES
    (1, '5159-5432-1098-7654', '2026-12-31', '456', '구자빈', '5678', '자빈핑', 'ACTIVE', NULL, '2024-01-01 00:00:00', 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00'),
    (2, '5159-5434-5678-9876', '2026-12-31', '012', '최윤정', '9012', '윤정핑', 'ACTIVE', NULL, '2024-01-01 00:00:00', 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00');

INSERT INTO design (member_id, color, `character`, account_id, card_id)
VALUES
    (1, 'GREEN', 'CHACHAPING', 1, NULL),  -- 강현우
    (2, 'BLUE', 'DADAPING', 2, 1),     -- 구자빈
    (3, 'PINK1', 'HEARTSPRING', 3, 2);  -- 최윤정

INSERT INTO parent_child (`parent_id`, `child_id`, created_at, updated_at)
VALUES
    (1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00'),
    (1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00');

-- INSERT INTO member (id, `name`, member_type, phone, birthday, profile, email, simple_password, `state`, inactive_date, card_state, bank_member_id, created_at, updated_at, role, social)
-- VALUES
--     (1, '강현우', 'PARENT',  '010-1234-5678', '1983-05-15', 'profile1.png', 'ghyunwoo@gmail.com', '1234', 'ACTIVE', NULL, 'CREATED', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00', 'ROLE_PARENT', 'naver'),
--     (2, '구자빈', 'CHILD',  '010-5678-1234', '2013-08-20', 'profile2.png', 'kjabin@gmail.com', '1234', 'ACTIVE', NULL, 'CREATED', 2, '2024-01-02 11:30:00', '2024-01-02 11:30:00', 'ROLE_CHILD', 'naver'),
--     (3, '안찬웅', 'CHILD', '010-9876-5432', '2014-12-01', 'profile3.png', 'achanung@gmail.com', NULL, 'ACTIVE', NULL, 'NONE', NULL, '2024-01-03 09:00:00', '2024-01-03 09:00:00', 'ROLE_CHILD', 'naver'),
--     (4, '최윤정', 'PARENT', '010-4321-8765', '1985-07-25', 'profile4.png', 'choiyj@gmail.com', '4321', 'ACTIVE', NULL, 'CREATED', 3, '2024-01-04 14:20:00', '2024-01-04 14:20:00', 'ROLE_PARENT', 'naver'),
--     (5, '조예은', 'CHILD', '010-9876-1234', '2016-03-10', 'profile5.png', 'joyeun@gmail.com', '4321', 'ACTIVE', NULL, 'CREATED', 4, '2024-01-05 16:45:00', '2024-01-05 16:45:00', 'ROLE_CHILD', 'naver'),
--     (6, '이성희', 'CHILD', '010-8765-4321', '2017-11-18', 'profile6.png', 'leesh@gmail.com', NULL, 'ACTIVE', NULL, 'NONE', NULL, '2024-01-06 13:10:00', '2024-01-06 13:10:00', 'ROLE_CHILD', 'naver');
--
--
-- -- `account` 테이블에 대한 더미 데이터
-- INSERT INTO account (id, account_number, balance, `state`, inactive_date, member_id, created_at, updated_at)
-- VALUES
--     (1, '1002-913-023908', 1000000.00, 'ACTIVE', NULL, 1, '2024-11-18 15:57:03', '2024-11-18 15:57:03'), -- 강현우
--     (2, '1002-913-023909', 17000.00, 'ACTIVE', NULL, 2, '2024-11-18 16:00:00', '2024-11-25 10:00:00'), -- 구자빈
--     (3, '1002-913-023911', 80000.00, 'ACTIVE', NULL, 4, '2024-11-18 16:02:00', '2024-11-25 17:00:00'), -- 최윤정
--     (4, '1002-913-023912', 1000.00, 'ACTIVE', NULL, 5, '2024-11-18 16:03:00', '2024-11-18 16:05:00'); -- 조예은
--
--
-- -- `account_transaction` 테이블에 대한 더미 데이터
-- INSERT INTO card (id, card_number, valid_thru, cvc, member_name, password, card_name, `state`, inactive_date, new_date, account_id, created_at, updated_at)
-- VALUES
--     (1, '1234-5678-9012-3456', '2025-12-31', '123', '강현우', 'password1', '핑크 카드', 'ACTIVE', NULL, '2024-11-18 15:57:03', 1, '2024-11-18 15:57:03', '2024-11-18 15:57:03'),
--     (2, '9876-5432-1098-7654', '2026-12-31', '456', '구자빈', 'password2', '블루 카드', 'ACTIVE', NULL, '2024-11-18 16:00:00', 2, '2024-11-18 16:00:00', '2024-11-18 16:00:00'),
--     (3, '3210-1234-5678-9876', '2026-12-31', '012', '최윤정', 'password4', '초록 카드', 'ACTIVE', NULL, '2024-11-18 16:02:00', 3, '2024-11-18 16:02:00', '2024-11-18 16:02:00'),
--     (4, '8765-4321-0987-6543', '2025-12-31', '345', '조예은', 'password5', '보라 카드', 'ACTIVE', NULL, '2024-11-18 16:03:00', 4, '2024-11-18 16:03:00', '2024-11-18 16:03:00');
--
-- INSERT INTO design (member_id, color, `character`, account_id, card_id)
-- VALUES
--     (1, 'PINK1', 'HEARTSPRING', 1, 1), -- 강현우
--     (2, 'BLUE', 'DADAPING', 2, 2),     -- 구자빈
--     (4, 'GREEN', 'CHACHAPING', 3, 3),  -- 최윤정
--     (5, 'PURPLE', 'LALAPING', 4, 4);   -- 조예은
--
-- -- `parent_child` 테이블에 대한 더미 데이터
-- INSERT INTO parent_child (`parent_id`, `child_id`, created_at, updated_at)
-- VALUES
--     (1, 2, '2024-01-02 11:30:00', '2024-01-02 11:30:00'),
--     (1, 3, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
--     (4, 5, '2024-01-05 16:45:00', '2024-01-05 16:45:00'),
--     (4, 6, '2024-01-06 13:10:00', '2024-01-06 13:10:00');