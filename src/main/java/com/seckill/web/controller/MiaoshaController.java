package com.seckill.web.controller;

import com.seckill.common.rabbitMq.MQSender;
import com.seckill.common.rabbitMq.MiaoshaMessage;
import com.seckill.common.result.CodeMsg;
import com.seckill.common.result.Result;
import com.seckill.common.vo.GoodsVo;
import com.seckill.componet.dataobject.Goods;
import com.seckill.componet.dataobject.MiaoshaOrder;
import com.seckill.componet.dataobject.Order;
import com.seckill.componet.dataobject.User;
import com.seckill.componet.service.GoodsService;
import com.seckill.componet.service.OrderService;
import com.seckill.componet.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.management.Sensor;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

	@Autowired
	UserService userService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;

	@Autowired
	MQSender sender;

	//私有map，存储
	private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

	/**
	 *
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception{
		List<GoodsVo> goodsVoList = goodsService.selectGoodsList();
		if(goodsVoList == null){
			return;
		}
		for (GoodsVo goodsVo:goodsVoList){
			redisTemplate.opsForValue().set("goodsNum"+goodsVo.getGoodsId(),goodsVo.getStockCount());
			redisTemplate.expire("goodsNum"+goodsVo.getId(),60000,TimeUnit.MILLISECONDS);
			localOverMap.put(goodsVo.getId(),false);
		}
	}
	
    @RequestMapping("/do_miaosha")
	@ResponseBody
    public Result<Integer> miaoSha(Model model, User user,
								   @RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    		//return login;
    	}

    	Object stockObj = redisTemplate.opsForValue().get("goodsNum"+goodsId);
    	if(stockObj != null){
			Integer num = Integer.parseInt(stockObj.toString());
			if(num <= 0){
				return Result.error(CodeMsg.MIAO_SHA_OVER);
			}
		}else{
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}

		Object object = redisTemplate.opsForValue().get("miaoshaOder_"+user.getId()+"_"+goodsId);
		if(object != null) {
//			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//			return "miaosha_fail";
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//入队列
		MiaoshaMessage mm = new MiaoshaMessage();
		mm.setGoodsId(goodsId);
		mm.setUser(user);
		sender.sendMiaoshaMessage(mm);
		return Result.success(0);//排队中
//
    	//判断库存
//    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//    	int stock = goods.getStockCount();
//    	if(stock <= 0) {
//    		model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//    		return "miaosha_fail";
//    	}
//
//		//判断是否已经秒杀到了
//		MiaoshaOrder miaoshaOrder = orderService.getMsOrderByUserIdGoodsId(user.getId(),goods.getGoodsId());
//		if(miaoshaOrder != null) {
//			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//			return "miaosha_fail";
//		}
//
//		//减库存 下订单 写入秒杀订单
//		Order order = orderService.miaoshaOrder(user, goods);
//		MiaoshaOrder mo = orderService.getMsOrderByUserIdOrderId(order.getId());
//		if(mo == null){
//			model.addAttribute("errmsg", "秒杀订单异常，请稍后再试");
//			return "miaosha_fail";
//		}
//    	model.addAttribute("order", order);
//    	model.addAttribute("goods", goods);
//        return "order_detail";
    }
}
