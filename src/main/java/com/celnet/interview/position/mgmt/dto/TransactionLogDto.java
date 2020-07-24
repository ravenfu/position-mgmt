package com.celnet.interview.position.mgmt.dto;

import com.celnet.interview.position.mgmt.common.constant.OperationType;
import com.celnet.interview.position.mgmt.common.constant.TradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLogDto {

    @NotNull
    private Long id;
    @NotNull
    private Long tradeId;
    @NotNull
    private Integer version;
    @NotEmpty
    private String securityCode;
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer quantity;
    @NotNull
    private OperationType opType;
    @NotNull
    private TradeType tradeType;
}
