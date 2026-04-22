package com.example.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class cartevent {
    private Integer cartId;
    private Long userId;
    private Integer productId;
    private Integer quantity;

    public cartevent(Integer cartId, Integer productId, Integer quantity) {
//        	this.cartId = cartId.longValue();
        this.cartId = cartId;
        	            this.productId = productId;
        	this.quantity = quantity;
//            this.userId = null; // userId can be set later if needed
    }
}
