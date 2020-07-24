package com.celnet.interview.position.mgmt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.celnet.interview.position.mgmt.mapper.PositionStatMapper;
import com.celnet.interview.position.mgmt.model.PositionStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionStatService {

    private static final Logger log = LoggerFactory.getLogger(PositionStatService.class);

    @Autowired
    private PositionStatMapper positionStatMapper;

    /**
     * 保证 PositionStat 中记录存在,
     * 避免检查记录是否存在
     * <p>
     * 查询是否有 securityCode 的记录,
     * 如果没有则插入一条 total = 0, last_trans_id = 0 的记录
     *
     * @param securityCode
     */
    public void promiseSecurityCodeExists(String securityCode) {
        Integer count = countBySecurityCode(securityCode);
        if (count == null || count == 0) {
            insertEmpty(securityCode);
        }
    }

    public Integer countBySecurityCode(String securityCode) {
        QueryWrapper<PositionStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("security_code", securityCode);
        return positionStatMapper.selectCount(queryWrapper);
    }

    public void insertEmpty(String securityCode) {
        PositionStat positionStat = new PositionStat();
        positionStat.setSecurityCode(securityCode);
        positionStat.setLastTransId(0L);
        positionStat.setTotal(0L);

        try {
            positionStatMapper.insert(positionStat);
        } catch (DuplicateKeyException e) {
            log.warn("重复记录: " + positionStat);
        } catch (Exception e) {
            log.warn("SecurityCode: " + securityCode + " 的空记录已存在", e);
            throw e;
        }
    }

    public List<PositionStat> selectAll() {
        return positionStatMapper.selectList(null);
    }

    public PositionStat selectBySecurityCode(String securityCode) {
        QueryWrapper<PositionStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("security_code", securityCode);
        return positionStatMapper.selectOne(queryWrapper);
    }

    /**
     * 加减 交易数量
     */
    public void merge(PositionStat positionStat) {
        positionStatMapper.merge(positionStat);
    }

    /**
     * 直接替换 交易数量
     */
    public void update(PositionStat positionStat) {
        positionStatMapper.update(positionStat);
    }

    /**
     * 交易数量清零
     */
    public void clean(PositionStat positionStat) {
        positionStatMapper.clean(positionStat);
    }
}
