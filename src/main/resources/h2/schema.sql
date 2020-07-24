--drop table if exists transaction_log;
--drop table if exists position_stat;

create table if not exists transaction_log
(
  id bigint(20) not null comment '事务id',
  trade_id bigint(20) not null comment '交易id',
  version int(5) not null comment '交易版本号',
  security_code varchar(20) not null comment '股权码',
  quantity int(9) not null comment '交易数量',
  op_type int(1) not null comment '操作类型, 1: insert, 2: update, 3: cancel',
  trade_type int(1) not null comment '交易类型, 1: buy, 2:sell',
  create_time datetime not null default current_timestamp,
  primary key (id)
);

create table if not exists position_stat
(
  id bigint(20) not null comment 'id',
  security_code varchar(20) not null unique comment '股权码',
  total bigint(20) not null comment '交易数量',
  last_trans_id bigint(20) not null comment '最近一次的事务id',
  update_time datetime not null default current_timestamp on update current_timestamp,
  primary key (id)
);
