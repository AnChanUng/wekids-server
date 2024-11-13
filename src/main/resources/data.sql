-- `member` 테이블에 대한 더미 데이터
INSERT INTO `member` (`id`, `name`, `phone`, `birthday`, `profile`, `email`, `created_at`, `updated_at`, `state`, `inactive_date`, `member_type`)
VALUES
    (1, '김철수', '010-1234-5678', '1985-05-15', 'profile1.jpg', 'chulsu.kim@example.com', NOW(), NOW(), 'ACTIVE', NULL, 'PARENT'),
    (2, '이영희', '010-9876-5432', '1990-08-25', 'profile2.jpg', 'younghee.lee@example.com', NOW(), NOW(), 'ACTIVE', NULL, 'CHILD'),
    (3, '박민수', '010-5555-1234', '2000-01-30', 'profile3.jpg', 'minsu.park@example.com', NOW(), NOW(), 'ACTIVE', '2023-01-01', 'CHILD');

-- `account` 테이블에 대한 더미 데이터
INSERT INTO `account` (`id`, `account_number`, `amount`, `member_id`, `state`, `design_type`, `created_at`, `updated_at`)
VALUES
    (1, '123-456-7890', 100000.00, 1, 'ACTIVE', 'WEBEE', NOW(), NOW()),
    (2, '987-654-3210', 250000.00, 2, 'ACTIVE', 'BOMBOM', NOW(), NOW()),
    (3, '555-123-4567', 50000.00, 3, 'INACTIVE', 'DALBO', NOW(), NOW());

-- `account_transaction` 테이블에 대한 더미 데이터
INSERT INTO `account_transaction` (`id`, `title`, `type`, `amount`, `balance`, `sender`, `receiver`, `created_at`, `memo`, `account_id`)
VALUES
    (1, '회사', 'DEPOSIT', 2000000.00, 2100000.00, '회사', '김철수', NOW(), '4월 급여', 1),
    (2, '식당', 'WITHDRAWAL', 12000.00, 2098800.00, '김철수', '식당', NOW(), '동료와 점심', 1),
    (3, '온라인 쇼핑몰', 'WITHDRAWAL', 55000.00, 244500.00, '이영희', '온라인 쇼핑몰', NOW(), '생일 선물 구매', 2),
    (4, '박민수', 'WITHDRAWAL', 30000.00, 47000.00, '박민수', '김철수', NOW(), '친구에게 송금', 3);

-- `parent_child` 테이블에 대한 더미 데이터
INSERT INTO `parent_child` (`parent_id`, `child_id`)
VALUES
    (1, 2),
    (1, 3)