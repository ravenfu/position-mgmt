package com.celnet.interview.position.mgmt.test.unit.mapper;

import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.common.constant.TradeType;
import com.celnet.interview.position.mgmt.mapper.TransactionLogMapper;
import com.celnet.interview.position.mgmt.model.TransactionLog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@ActiveProfiles("dev")
@SpringBootTest
public class TransactionLogMapperTest {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogMapperTest.class);

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Test
    public void insert(){
        TransactionLog entity = new TransactionLog();
        entity.setId(5L);
        entity.setTradeId(6L);
        entity.setVersion(7);
        entity.setSecurityCode("ABCD");
        entity.setQuantity(100);
        entity.setOpType(OperationType.INSERT);
        entity.setTradeType(TradeType.BUY);

        transactionLogMapper.insert(entity);
    }

    @Test
    public void select(){
        TransactionLog transactionLog = transactionLogMapper.selectById(1L);
        log.info(transactionLog.toString());
    }
}
