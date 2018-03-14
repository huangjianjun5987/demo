alter table scp_order add index `idx_scp_order_profile_id` (`profile_id`) USING BTREE;
alter table scp_order add index `idx_scp_order_creation_time` (`creation_time` desc) USING BTREE;
alter table scp_order_items add index `idx_scp_order_items_o_id` (`order_id`) USING BTREE;

alter table scp_order  ADD COLUMN `cancel_status` varchar(40) COMMENT '取消状态';

INSERT INTO `order_dictionary` (`property_name`,`property_value`)VALUES ('totalItem','50');
