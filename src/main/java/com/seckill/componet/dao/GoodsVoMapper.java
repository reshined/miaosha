package com.seckill.componet.dao;

import com.seckill.common.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsVoMapper {

    /**
     * 查询商品及秒杀信息
     * @return List<GoodsVo>
     */
    @Select("SELECT g.id AS goodsId,g.goods_name AS goodsName,g.goods_img AS goodsImg,g.goods_price AS goodsPrice, " +
            "mg.id,mg.miaosha_price AS miaoshaPrice,mg.stock_count AS stockCount,mg.start_date As startDate,mg.end_date As endDate " +
            "FROM tb_miaoshaGoods mg " +
            "LEFT JOIN tb_goods g ON mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    /**
     * 查询单个商品及秒杀信息
     * @return GoodsVo
     */
    @Select("SELECT g.id AS goodsId,g.goods_name AS goodsName,g.goods_img AS goodsImg,g.goods_price AS goodsPrice," +
            "mg.id,mg.miaosha_price AS miaoshaPrice,mg.stock_count AS stockCount,mg.start_date As startDate,mg.end_date As endDate " +
            "FROM tb_miaoshaGoods mg " +
            "LEFT JOIN tb_goods g ON mg.goods_id = g.id WHERE  mg.goods_id = #{goodsId} ")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);


}
