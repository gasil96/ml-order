package com.ml.order.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Management", description = "Access to manipulaton Orders BUY or SELL")
@Slf4j
@RestController
@RequestMapping
public class OrderController {

}
