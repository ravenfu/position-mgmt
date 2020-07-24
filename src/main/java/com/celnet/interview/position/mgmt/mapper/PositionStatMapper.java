package com.celnet.interview.position.mgmt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celnet.interview.position.mgmt.model.PositionStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PositionStatMapper extends BaseMapper<PositionStat> {

    @Update("update position_stat set total = total + #{stat.total} , last_trans_id = #{stat.lastTransId} where security_code = #{stat.securityCode} and last_trans_id < #{stat.lastTransId}")
    int merge(@Param("stat") PositionStat positionStat);

    @Update("update position_stat set total = #{stat.total} , last_trans_id = #{stat.lastTransId} where security_code = #{stat.securityCode} and last_trans_id < #{stat.lastTransId}")
    int update(@Param("stat") PositionStat positionStat);

    @Update("update position_stat set total = 0 , last_trans_id = #{stat.lastTransId} where security_code = #{stat.securityCode} and last_trans_id < #{stat.lastTransId}")
    int clean(@Param("stat") PositionStat positionStat);
}
