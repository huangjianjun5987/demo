CREATE TABLE msg_retry_queue
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    unique_id VARCHAR(100) NOT NULL COMMENT '消息唯一主键，如订单ID',
    msg_body TEXT,
    retry_count INT DEFAULT 0 COMMENT '已尝试次数',
    msg_type VARCHAR(40) NOT NULL COMMENT '消息类型',
    `order` INT DEFAULT 0 NOT NULL COMMENT '同类型消息的消息顺序',
    state VARCHAR(10) DEFAULT 'WZX' NOT NULL COMMENT '状态：WZX 未执行成功，ZXCG 执行成功，QX 取消执行',
    create_time DATETIME NOT NULL,
    last_exec_time DATETIME COMMENT '最近一次执行时间'
);
CREATE INDEX msg_retry_queue_unique_id_index ON msg_retry_queue (unique_id);