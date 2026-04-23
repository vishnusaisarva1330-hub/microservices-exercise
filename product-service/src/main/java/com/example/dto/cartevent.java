
package com.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class cartevent {

    private Integer cartId;
    private Long userId;
    private Integer productId;
    private Integer quantity;
}

