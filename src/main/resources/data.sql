-- `member` 테이블에 대한 더미 데이터
INSERT INTO member (id, `name`, member_type, phone, birthday, profile, email, simple_password, `state`, inactive_date, card_state, bank_member_id, created_at, updated_at)
VALUES
    (1, '강현우', 'PARENT',  '010-1234-5678', '1983-05-15', 'profile1.png', 'ghyunwoo@gmail.com', '1234', 'ACTIVE', NULL, 'CREATED', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
    (2, '구자빈', 'CHILD',  '010-5678-1234', '2013-08-20', 'profile2.png', 'kjabin@gmail.com', '1234', 'ACTIVE', NULL, 'CREATED', 2, '2024-01-02 11:30:00', '2024-01-02 11:30:00'),
    (3, '안찬웅', 'CHILD', '010-9876-5432', '2014-12-01', 'profile3.png', 'achanung@gmail.com', NULL, 'ACTIVE', NULL, 'NONE', NULL, '2024-01-03 09:00:00', '2024-01-03 09:00:00'),
    (4, '최윤정', 'PARENT', '010-4321-8765', '1985-07-25', 'profile4.png', 'choiyj@gmail.com', '4321', 'ACTIVE', NULL, 'CREATED', 3, '2024-01-04 14:20:00', '2024-01-04 14:20:00'),
    (5, '조예은', 'CHILD', '010-9876-1234', '2016-03-10', 'profile5.png', 'joyeun@gmail.com', '4321', 'ACTIVE', NULL, 'CREATED', 4, '2024-01-05 16:45:00', '2024-01-05 16:45:00'),
    (6, '이성희', 'CHILD', '010-8765-4321', '2017-11-18', 'profile6.png', 'leesh@gmail.com', NULL, 'ACTIVE', NULL, 'NONE', NULL, '2024-01-06 13:10:00', '2024-01-06 13:10:00');


-- `account` 테이블에 대한 더미 데이터
INSERT INTO account (id, account_number, balance, `state`, inactive_date, member_id, created_at, updated_at)
VALUES
    (1, '1002-913-023908', 0.00, 'ACTIVE', NULL, 1, '2024-11-18 15:57:03', '2024-12-01 20:00:00'), -- 강현우
    (2, '1002-913-023909', 1500.00, 'ACTIVE', NULL, 2, '2024-11-18 16:00:00', '2024-11-18 16:00:00'), -- 구자빈
    (3, '1002-913-023911', 2000.00, 'ACTIVE', NULL, 4, '2024-11-18 16:02:00', '2024-11-18 16:02:00'), -- 최윤정
    (4, '1002-913-023912', 1200.00, 'ACTIVE', NULL, 5, '2024-11-18 16:03:00', '2024-11-18 16:03:00'); -- 조예은


-- `account_transaction` 테이블에 대한 더미 데이터
INSERT INTO card (id, card_number, valid_thru, cvc, member_name, password, card_name, `state`, inactive_date, new_date, account_id, created_at, updated_at)
VALUES
    (1, '1234-5678-9012-3456', '2025-12-31', '123', '강현우', 'password1', '핑크 카드', 'ACTIVE', NULL, '2024-11-18 15:57:03', 1, '2024-11-18 15:57:03', '2024-11-18 15:57:03'),
    (2, '9876-5432-1098-7654', '2026-12-31', '456', '구자빈', 'password2', '블루 카드', 'ACTIVE', NULL, '2024-11-18 16:00:00', 2, '2024-11-18 16:00:00', '2024-11-18 16:00:00'),
    (3, '3210-1234-5678-9876', '2026-12-31', '012', '최윤정', 'password4', '초록 카드', 'ACTIVE', NULL, '2024-11-18 16:02:00', 3, '2024-11-18 16:02:00', '2024-11-18 16:02:00'),
    (4, '8765-4321-0987-6543', '2025-12-31', '345', '조예은', 'password5', '보라 카드', 'ACTIVE', NULL, '2024-11-18 16:03:00', 4, '2024-11-18 16:03:00', '2024-11-18 16:03:00');

INSERT INTO design (member_id, color, `character`, account_id, card_id)
VALUES
    (1, 'PINK1', 'HEARTSPRING', 1, 1), -- 강현우
    (2, 'BLUE', 'DADAPING', 2, 2),     -- 구자빈
    (4, 'GREEN', 'CHACHAPING', 3, 3),  -- 최윤정
    (5, 'PURPLE', 'LALAPING', 4, 4);   -- 조예은

-- `parent_child` 테이블에 대한 더미 데이터
INSERT INTO parent_child (`parent_id`, `child_id`, created_at)
VALUES
    (1, 2, '2024-01-02 11:30:00'),
    (1, 3, '2024-01-03 09:00:00'),
    (4, 5, '2024-01-05 16:45:00'),
    (4, 6, '2024-01-06 13:10:00');