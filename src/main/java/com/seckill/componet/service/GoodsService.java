package com.seckill.componet.service;

import com.seckill.common.vo.GoodsVo;
import com.seckill.componet.dao.GoodsMapper;
import com.seckill.componet.dao.GoodsVoMapper;
import com.seckill.componet.dao.MiaoshaGoodsMapper;
import com.seckill.componet.dataobject.MiaoshaGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  yangtao
 *
 * @time 2019-11-19
 *
 * @method 商品业务层
 */

@Service
public class GoodsService {

    @Autowired
    private GoodsVoMapper goodsVoMapper;

    //查询所有商品及秒杀列表
    @Transactional
    public List<GoodsVo> selectGoodsList(){
        return goodsVoMapper.listGoodsVo();
    }

    //查询单个商品
    @Transactional
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsVoMapper.getGoodsVoByGoodsId(goodsId);
    }

}
