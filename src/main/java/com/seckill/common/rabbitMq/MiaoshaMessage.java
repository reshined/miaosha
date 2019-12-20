package com.seckill.common.rabbitMq;


import com.seckill.componet.dataobject.User;

public class MiaoshaMessage {
	private User user;
	private long goodsId;

	public User getUser() {
		return user;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
}
