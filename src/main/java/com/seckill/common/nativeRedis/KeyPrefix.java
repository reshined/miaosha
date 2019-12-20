package com.seckill.common.nativeRedis;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
