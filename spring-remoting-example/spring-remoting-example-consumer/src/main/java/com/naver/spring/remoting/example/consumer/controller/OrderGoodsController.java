package com.naver.spring.remoting.example.consumer.controller;

import com.naver.spring.remoting.example.core.shoping.OrderException;
import com.naver.spring.remoting.example.core.shoping.OrderGoodsService;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RestController
public class OrderGoodsController {
	private final OrderGoodsService orderGoodsService;

	public OrderGoodsController(OrderGoodsService orderGoodsService) {
		this.orderGoodsService = orderGoodsService;
	}

	@GetMapping("/orderGoods")
	public int order(@RequestParam("goodsId") int goodsId) throws OrderException {

		return orderGoodsService.orderGoods(goodsId);
	}

	@ExceptionHandler(OrderException.class)
	public String handleOrderException(OrderException ex) {
		return "Error : " + ex.getMessage();
	}
}
