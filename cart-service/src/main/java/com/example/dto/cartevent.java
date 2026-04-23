
package com.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor   // 🔥 REQUIRED for Kafka
@AllArgsConstructor
public class cartevent {

    private Integer cartId;
    private Long userId;
    private Integer productId;
    private Integer quantity;

    public cartevent(Integer cartId, Integer productId, Integer quantity) {
    }
}
