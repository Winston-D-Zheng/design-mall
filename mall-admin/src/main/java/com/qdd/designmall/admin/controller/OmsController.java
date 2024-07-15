package com.qdd.designmall.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Transactional
public class OmsController {



}
