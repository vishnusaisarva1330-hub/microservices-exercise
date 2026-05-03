//package com.example.dto;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Data;
//@Data
//
//public class productrequest {
//    @NotBlank(message = "Product cannot be empty")
//    private String name;
//
//    @NotNull(message = "Price cannot be null")
//    @Min(value = 1, message = "Price must be greater than 0")
//    private Double price;
//
//    @NotNull(message = "Stock cannot be null")
//    @Min(value = 0, message = "Stock must be greater than or equal to 0")
//    private Integer stock;
//}
package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class productrequest {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock must be >= 0")
    private Integer stock;
}