package com.celnet.interview.position.mgmt.biz;

import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.model.PositionStat;
import com.celnet.interview.position.mgmt.model.TransactionLog;
import com.celnet.interview.position.mgmt.service.PositionStatService;
import com.celnet.interview.position.mgmt.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class PositionBiz {

    @Autowired
    private TransactionLogService transLogService;

    @Autowired
    private PositionStatService positionStatService;

    /**
     * 期望的事务ID
     */
    private AtomicLong expectTransId = new AtomicLong(1);

    @Transactional
    public void saveTransLog(TransactionLog transLog) {
        // 如果遇到重复记录, isInsertSuccess 返回 false
        // 表示该记录已经被处理过了, 丢弃记录
        boolean isInsertSuccess = transLogService.insert(transLog);
        if (!isInsertSuccess) return;

        // 保证 PositionStat 中记录存在,
        // 如果不存在, 则插入一条 total = 0, last_trans_id = 0 的记录
        positionStatService.promiseSecurityCodeExists(transLog.getSecurityCode());
    }

    /**
     * 循环执行汇总
     * 如果获取到预期的交易记录则执行汇总
     * 否则结束
     */
    public void summaryPosition() {
        TransactionLog transLog;
        while ((transLog = transLogService.getById(expectTransId.get())) != null) {
            doSummary(transLog);
            expectTransId.incrementAndGet();
        }
    }

    @Transactional
    public void doSummary(TransactionLog transLog) {
        if (OperationType.INSERT == transLog.getOpType()) {
            positionStatService.merge(transLog.convertToPositionStat());
        } else if (OperationType.UPDATE == transLog.getOpType()) {
            positionStatService.update(transLog.convertToPositionStat());
        } else if (OperationType.CANCEL == transLog.getOpType()) {
            positionStatService.clean(transLog.convertToPositionStat());
        } else {
            throw new UnsupportedOperationException("不支持的操作. OperationType: " + transLog.getOpType());
        }
    }

    public List<PositionStat> selectAll() {
        return positionStatService.selectAll();
    }
}
