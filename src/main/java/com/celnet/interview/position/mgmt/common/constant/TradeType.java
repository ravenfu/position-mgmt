package com.celnet.interview.position.mgmt.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型
 */
@Getter
@AllArgsConstructor
public enum TradeType {

    BUY(1),
    SELL(2);

    @EnumValue
    private final Integer code;

    /**
     * 给数量添加正负号
     *
     * 买是正, 卖是负
     * @param quantity
     * @return
     */
    public Long sumQuantity(int quantity) {
        if (SELL.equals(this)) {
            return Long.valueOf(-1 * quantity);
        }
        return Long.valueOf(quantity);
    }
}
