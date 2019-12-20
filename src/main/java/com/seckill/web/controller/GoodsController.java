package com.seckill.web.controller;

import com.github.pagehelper.StringUtil;
import com.seckill.common.vo.GoodsVo;
import com.seckill.componet.dataobject.User;
import com.seckill.componet.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	//thymleaf模版引擎视图转换器
	private ThymeleafViewResolver thymeleafViewResolver;

	@Autowired
	//容器
	private ApplicationContext applicationContext;


    @RequestMapping(value = "/to_list",produces = "text/html")
	@ResponseBody
    public String list(HttpServletRequest request,HttpServletResponse response, Model model, User user) {
    	if(user == null){
    		return "login";
		}
    	model.addAttribute("user", user);
		//取缓存中商品列表
    	String html = (String) redisTemplate.opsForValue().get("goods_list");
    	if(!StringUtils.isEmpty(html)){
			return html;
		}

		List<GoodsVo> goodsVoList = goodsService.selectGoodsList();
		model.addAttribute("goodsList",goodsVoList);

		//创建thymleaf模版引擎容器
		SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),
				response.getLocale(),model.asMap(),applicationContext);
		//手动渲染，并解析模版
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
		//判断不为空，则放入缓存
		if(!StringUtils.isEmpty(html)){
			redisTemplate.opsForValue().set("goods_list",html);
			//设置过期时间 60秒
			redisTemplate.expire("goods_list",60000, TimeUnit.MILLISECONDS);
		}
      	return html;
    }

	@RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
	@ResponseBody
	public String detail(HttpServletRequest request,HttpServletResponse response,Model model,User user,
						 @PathVariable("goodsId")long goodsId) {

		if(user == null){
			return "login";
		}
		model.addAttribute("user", user);

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now < startAt ) {//秒杀还没开始，倒计时
			miaoshaStatus = 0;
			remainSeconds = (int)((startAt - now )/1000);
		}else  if(now > endAt){//秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);

		SpringWebContext ctx = new SpringWebContext(
				request,response,request.getServletContext(),request.getLocale(),model.asMap(),applicationContext);

		String html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
		return html;
	}
    
}
