# 背景

上游系统实时推送股权交易记录, 需要在本系统进行汇总

# 需求分析

1. 交易事务需要根据 transactionId 顺序实时计算
    transactionId 是一个不会重复, 连续自增的需要
    同一 tradeId 会有多条事务记录, 并且 version 在同一 tradeId 下连续自增
2. 在同一个 tradeId 的事务中, 会出现 INSERT/UPDATE/CANCEL 3 种操作类型
    - INSERT: 需要根据交易类型`加减`交易数量
    - UPDATE: 需要根据交易类型`直接替换`数量
    - CANCEL: 直接将数量置为0
3. 在同一个 tradeId 的事务中 INSERT 会出现在第一条, CANCEL 会出现在最后一条
    看给出的需求文档 UPDATE & CANCEL 不一定会出现
4. UPDATE 事务可能改变 securityCode or Quantity or Buy/Sell; CANCEL 事务中 刚刚提到的 3个字段的改变都要忽略
    这个我理解在同一个 tradeId 的事务 securityCode 是不能变的, Quantity or Buy/Sell 是可以变的
5. 事务会乱序推送
    上游系统无法保证推送的顺序性, 本系统需要自己保证处理顺序
    
# 思考

由于无法保证接收到的事务记录的顺序性, 如果不按顺序计算, 不同操作可能会导致计算不正确.
通过 tradeId 和 version 也无法判断交易是否结束.
所以只能根据 transactionId 的顺序进行计算.

是否可以沟通对于相同 securityCode 保证连续性的事务id增长 ? 否则无法并发处理

# 解决方案

1. 新建数据库表表结构见 resource/h2/sechema.sql
     - transaction_log: 事务记录表
     - position_stat: 股权汇总表
2. POST http://ip:port/position-mgmt/syncTransLog 接口保存事务记录
    在接收到事务记录后存入 transaction_log, 防止数据丢失;
    如果 position_stat 中没有对应 securityCode 的记录, 则创建一条 total = 0, last_trans_id = 0 的记录
3. 定时任务汇总
    内存中保存期望事务ID (expectTransId), 根据事务ID区查 transaction_log
    如果找到则根据规则汇总到 position_stat;
    如果找不到间隔 1 秒后重复执行该步骤.
4. GET http://ip:port/position-mgmt/ 接口查询汇总结果


## 防止重复执行

1. transaction_log 表将事务ID作为主键, 防止重复记录
2. 期望事务ID (expectTransId) 是原子类
3. position_stat 中记录 最近一次的事务id(last_trans_id), 在执行汇总时更新语句过滤条件比较, 不能小于等于该事务id

# 项目结构

按调用层次从上往下:

- resource 接口层
    - dto 数据传输对象
- biz 业务层, 循环任务也在这里
- service 简单逻辑层, 对象简单处理
- model 数据库对象
aaaaaa
