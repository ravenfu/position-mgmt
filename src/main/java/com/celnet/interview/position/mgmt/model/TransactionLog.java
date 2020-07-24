package com.celnet.interview.position.mgmt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.common.constant.TradeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("transaction_log")
public class TransactionLog{

    @TableId
    @JsonProperty("transId")
    private Long id;
    private Long tradeId;
    private Integer version;
    private String securityCode;
    private Integer quantity;
    @TableField("op_type")
    private OperationType opType;
    private TradeType tradeType;
    private Date createTime;

    public PositionStat convertToPositionStat() {
        PositionStat positionStat = new PositionStat();
        positionStat.setSecurityCode(this.securityCode);
        positionStat.setLastTransId(this.id);
        positionStat.setTotal(this.tradeType.sumQuantity(this.quantity));
        return positionStat;
    }

}
