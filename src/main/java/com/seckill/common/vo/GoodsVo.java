package com.seckill.common.vo;

import lombok.Data;
import java.util.Date;

/**
 * 秒杀商品VO
 */
@Data
public class GoodsVo{
	private Long id;
	private Long goodsId;
	private String goodsName;
	private String goodsImg;
	private String goodsPrice;
	private Double miaoShaPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

}
