package com.celnet.interview.position.mgmt.test.unit.service;

import com.celnet.interview.position.mgmt.model.PositionStat;
import com.celnet.interview.position.mgmt.service.PositionStatService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@ActiveProfiles("dev")
@SpringBootTest
public class PositionStatServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PositionStatServiceTest.class);

    @Autowired
    private PositionStatService positionStatService;

    @Test
    public void promiseSecurityCodeExists(){
        String code = "hhh";
        Integer count = positionStatService.countBySecurityCode(code);
        log.info(count.toString());

        positionStatService.promiseSecurityCodeExists(code);

        count = positionStatService.countBySecurityCode(code);
        log.info(count.toString());
        Assert.isTrue(count == 1, "promiseSecurityCodeExists 失败");
    }

    @Test
    public void summary(){
        String code = "xxx";
        positionStatService.promiseSecurityCodeExists(code);

        PositionStat stat = new PositionStat();
        stat.setSecurityCode(code);
        stat.setTotal(-100L);
        stat.setLastTransId(2L);

        positionStatService.update(stat);
        PositionStat s = positionStatService.selectBySecurityCode(code);
        Assert.isTrue(-100L == s.getTotal(), "update 失败");

        stat.setTotal(200L);
        stat.setLastTransId(3L);
        positionStatService.merge(stat);
        s = positionStatService.selectBySecurityCode(code);
        Assert.isTrue(100L == s.getTotal(), "merge 失败");

        stat.setLastTransId(4L);
        positionStatService.clean(stat);
        s = positionStatService.selectBySecurityCode(code);
        Assert.isTrue(0L == s.getTotal(), "clean 失败");
    }
}
