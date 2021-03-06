package com.seckill.componet.dao;

import com.seckill.componet.dataobject.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    int insert(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    int insertSelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    Goods selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    int updateByPrimaryKeySelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_goods
     *
     * @mbg.generated Thu Nov 21 16:53:38 CST 2019
     */
    int updateByPrimaryKey(Goods record);

    //商品库存减1
    void reduceGoodsStock(@Param("id") Long id);
}