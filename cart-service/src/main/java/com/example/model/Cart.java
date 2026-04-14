package com.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    public List<CartItem> getItems() {

        return null;
    }
}