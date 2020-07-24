package com.celnet.interview.position.mgmt.test.unit.resource;

import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.common.constant.TradeType;
import com.celnet.interview.position.mgmt.dto.TransactionLogDto;
import com.celnet.interview.position.mgmt.resource.PositionResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest
public class PositionResourceTest {

    private static final Logger log = LoggerFactory.getLogger(PositionResourceTest.class);

    @Autowired
    private PositionResource positionResource;

    private static List<TransactionLogDto> list = new ArrayList<>();

    @Test
    public void test() throws InterruptedException {
        for (TransactionLogDto logDto : list) {
            positionResource.syncTransLog(logDto);
        }
        Thread.sleep(1000);

        positionResource.get().stream().forEach( it -> log.info(it.toString()));
    }

    @BeforeAll
    public static void before(){
        TransactionLogDto dto1 = new TransactionLogDto(1L,1L,1,"REC", 50, OperationType.INSERT, TradeType.BUY);
        TransactionLogDto dto2 = new TransactionLogDto(2L,2L,1,"ITC", 40, OperationType.INSERT, TradeType.SELL);
        TransactionLogDto dto3 = new TransactionLogDto(3L,3L,1,"INF", 70, OperationType.INSERT, TradeType.BUY);
        TransactionLogDto dto4 = new TransactionLogDto(4L,1L,2,"REC", 60, OperationType.UPDATE, TradeType.BUY);
        TransactionLogDto dto5 = new TransactionLogDto(5L,2L,2,"ITC", 30, OperationType.CANCEL, TradeType.BUY);
        TransactionLogDto dto6 = new TransactionLogDto(6L,4L,1,"INF", 20, OperationType.INSERT, TradeType.SELL);

//        list = List.of(dto1, dto2);
        list = List.of(dto1, dto2, dto3, dto4, dto5, dto6);
    }

}
