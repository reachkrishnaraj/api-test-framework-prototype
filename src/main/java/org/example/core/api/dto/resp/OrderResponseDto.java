package org.example.core.api.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private String id;

    private String status;

    private Long createdEpochSeconds;

    private int amount;

    private String currency;

    private String productCode;

    private String productDescription;

    private int quantity;

}
