package com.celnet.interview.position.mgmt.test.unit.service;

import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.common.constant.TradeType;
import com.celnet.interview.position.mgmt.mapper.TransactionLogMapper;
import com.celnet.interview.position.mgmt.model.TransactionLog;
import com.celnet.interview.position.mgmt.service.TransactionLogService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@ActiveProfiles("dev")
@SpringBootTest
public class TransactionLogServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogServiceTest.class);

    @Autowired
    private TransactionLogService transactionLogService;

    @Test
    public void insert(){
        TransactionLog transLog = new TransactionLog();
        transLog.setId(5L);
        transLog.setTradeId(6L);
        transLog.setVersion(7);
        transLog.setSecurityCode("ABCD");
        transLog.setQuantity(100);
        transLog.setOpType(OperationType.INSERT);
        transLog.setTradeType(TradeType.BUY);

        transactionLogService.insert(transLog);

        transactionLogService.insert(transLog);
    }

    @Test
    public void getById(){
        TransactionLog transLog = transactionLogService.getById(5L);
        Assert.notNull(transLog, "insert 失败");
        log.info(transLog.toString());
    }
}
