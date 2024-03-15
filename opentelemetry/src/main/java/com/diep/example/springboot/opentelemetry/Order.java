package com.diep.example.springboot.opentelemetry;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class Order {
    Long id;
    Long customerId;
    ZonedDateTime orderDate;
    BigDecimal totalAmount;
}
