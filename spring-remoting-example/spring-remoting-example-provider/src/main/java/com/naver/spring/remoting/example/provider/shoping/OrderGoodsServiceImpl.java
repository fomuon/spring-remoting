package com.naver.spring.remoting.example.provider.shoping;


import com.naver.spring.remoting.annotation.RemotingService;
import com.naver.spring.remoting.example.core.shoping.OrderException;
import com.naver.spring.remoting.example.core.shoping.OrderGoodsService;

import static java.lang.Math.random;

@RemotingService("orderGoodsService")
public class OrderGoodsServiceImpl implements OrderGoodsService {

	@Override
	public int orderGoods(int goodsId) throws OrderException {

		if (random() < 0.3) {
			throw new OrderException("no inventory error");
		}

		return goodsId * 10000;
	}
}
