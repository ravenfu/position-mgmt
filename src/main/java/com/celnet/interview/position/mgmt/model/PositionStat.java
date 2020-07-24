package com.celnet.interview.position.mgmt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("position_stat")
public class PositionStat {

    @TableId
    private Long id;
    private String securityCode;
    private Long total;
    private Long lastTransId;
    private Date updateTime;
}
