package com.celnet.interview.position.mgmt.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 */
@Getter
@AllArgsConstructor
public enum OperationType  {

    INSERT(1),
    UPDATE(2),
    CANCEL(3);

    @EnumValue
    private final Integer code;

}
