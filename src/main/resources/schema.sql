DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS account_transaction;
DROP TABLE IF EXISTS parent_child;

CREATE TABLE `member` (
    `id`	BIGINT	NOT NULL,
    `name`	VARCHAR(20)	NOT NULL,
    `phone`	VARCHAR(20)	NOT NULL,
    `birthday`	DATE	NOT NULL,
    `profile`   VARCHAR(255),
    `email`	VARCHAR(255)    NOT NULL,
    `created_at`	TIMESTAMP   NOT NULL,
    `updated_at`	TIMESTAMP	NOT NULL,
    `state`	VARCHAR(20)	NOT NULL DEFAULT 'ACTIVE',
    `inactive_date` DATE,
    `member_type` VARCHAR(20)   NOT NULL,
    CONSTRAINT `PK_MEMBER` PRIMARY KEY (`id`)
);

CREATE TABLE `account` (
    `id`	BIGINT	NOT NULL,
    `account_number`	VARCHAR(50)	NOT NULL,
    `amount`	DECIMAL(20, 2)	NOT NULL,
    `member_id`	BIGINT	NOT NULL,
    `state`	VARCHAR(20)	NOT NULL,
    `design_type`	VARCHAR(255)    NOT NULL,
    `created_at`	TIMESTAMP   NOT NULL,
    `updated_at`	TIMESTAMP	NOT NULL,
    CONSTRAINT `PK_ACCOUNT` PRIMARY KEY (`id`)
);

CREATE TABLE `account_transaction` (
  `id`	BIGINT	NOT NULL,
  `title`	VARCHAR(255)	NOT NULL,
  `type`	VARCHAR(20)	NOT NULL,
  `amount`	DECIMAL(20, 2)	NOT NULL,
  `balance`	DECIMAL(20, 2)	NOT NULL,
  `sender`	VARCHAR(255)	NOT NULL,
  `receiver`	VARCHAR(255)	NOT NULL,
  `created_at`	TIMESTAMP	NOT NULL,
  `memo`	VARCHAR(255),
  `account_id`	BIGINT	NOT NULL,
  CONSTRAINT `PK_ACCOUNT_TRANSACTION` PRIMARY KEY (`id`)
);

CREATE TABLE `parent_child` (
    `parent_id`	BIGINT	NOT NULL,
    `child_id`	BIGINT	NOT NULL,
    CONSTRAINT `PK_PARENT_CHILD` PRIMARY KEY (`parent_id`, `child_id`)
);

