package com.celnet.interview.position.mgmt.resource;

import com.celnet.interview.position.mgmt.biz.PositionBiz;
import com.celnet.interview.position.mgmt.dto.TransactionLogDto;
import com.celnet.interview.position.mgmt.model.PositionStat;
import com.celnet.interview.position.mgmt.model.TransactionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class PositionResource {

    private static final Logger log = LoggerFactory.getLogger(PositionResource.class);

    @Autowired
    private PositionBiz positionBiz;

    @PostMapping("/syncTransLog")
    public void syncTransLog(@Valid TransactionLogDto transLogDto) {
        log.info("收到同步事务请求: " + transLogDto);
        TransactionLog transLog = new TransactionLog();
        BeanUtils.copyProperties(transLogDto, transLog);
        positionBiz.saveTransLog(transLog);
    }

    @GetMapping()
    public List<PositionStat> get() {
        return positionBiz.selectAll();
    }

}
