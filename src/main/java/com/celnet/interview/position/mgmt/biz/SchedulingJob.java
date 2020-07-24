package com.celnet.interview.position.mgmt.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulingJob {

    @Autowired
    private PositionBiz positionBiz;

    /**
     * 循环执行汇总, 如果获取不到期望的事务则休息 1 秒
     */
    @Scheduled(fixedDelay=1000)
    public void summaryPositionTask(){
        positionBiz.summaryPosition();
    }
}
