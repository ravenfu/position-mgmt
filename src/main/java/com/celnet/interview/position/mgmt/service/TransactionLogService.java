package com.celnet.interview.position.mgmt.service;

import com.celnet.interview.position.mgmt.mapper.TransactionLogMapper;
import com.celnet.interview.position.mgmt.model.TransactionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransactionLogService {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogService.class);

    @Autowired
    private TransactionLogMapper transLogMapper;

    public boolean insert(TransactionLog transLog) {
        try {
            transLogMapper.insert(transLog);
            return true;
        } catch (DuplicateKeyException e) {
            log.warn("重复记录: " + transLog);
            return false;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw e;
        }
    }

    public TransactionLog getById(long transId) {
        return transLogMapper.selectById(transId);
    }
}
