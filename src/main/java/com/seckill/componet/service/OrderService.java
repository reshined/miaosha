package com.seckill.componet.service;

import com.seckill.common.vo.GoodsVo;
import com.seckill.componet.dao.GoodsMapper;
import com.seckill.componet.dao.MiaoshaGoodsMapper;
import com.seckill.componet.dao.MiaoshaOrderMapper;
import com.seckill.componet.dao.OrderMapper;
import com.seckill.componet.dataobject.MiaoshaOrder;
import com.seckill.componet.dataobject.Order;
import com.seckill.componet.dataobject.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private MiaoshaGoodsMapper miaoshaGoodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据订单id，查询秒杀订单
     * @param orderId
     * @return
     */
    @Transactional
    public MiaoshaOrder getMsOrderByUserIdOrderId(long orderId){
       return miaoshaOrderMapper.getMsOrderByUserIdOrderId(orderId);
    }


    /**
     * 根据用户id和商品id，查询秒杀订单
     * @param userId
     * @param goodId
     * @return
     */
    @Transactional
    public MiaoshaOrder getMsOrderByUserIdGoodsId(long userId, long goodId) {

        Object object = redisTemplate.opsForValue().get("miaoshaOder_"+userId+"_"+goodId);
        //判断缓存中是否有数据
        if(object == null){
            return miaoshaOrderMapper.getMsOrderByUserIdGoodsId(userId,goodId);
        }

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        BeanUtils.copyProperties(object,miaoshaOrder);
        return  miaoshaOrder;
    }

    /**
     * 下订单，减库存
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public Order miaoshaOrder(User user, GoodsVo goods) {
        //商品减库存1
        goodsMapper.reduceGoodsStock(goods.getGoodsId());
        //秒杀商品库存减1
        miaoshaGoodsMapper.reduceMiaoshaGoodsStock(goods.getId());
        //下订单 写入秒杀订单
        return createOrder(user, goods);
    }

    /**
     *  创建普通订单，及对应的秒杀订单
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public Order createOrder(User user, GoodsVo goods) {
        //普通订单
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setAddressId(1L);
        order.setGoodsCount(1);
        order.setGoodsId(goods.getGoodsId());
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsPrice(goods.getMiaoShaPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setUserId(user.getId());
        orderMapper.insert(order);
        //秒杀订单
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getGoodsId());
        miaoshaOrder.setOrderId(order.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrderMapper.insert(miaoshaOrder);

        redisTemplate.opsForValue().set("miaoshaOder_"+user.getId()+"_"+goods.getId(),miaoshaOrder);
        return order;
    }
}
