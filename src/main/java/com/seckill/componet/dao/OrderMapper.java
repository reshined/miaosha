package com.seckill.componet.dao;

import com.seckill.componet.dataobject.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    Order selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbg.generated Thu Nov 21 16:52:16 CST 2019
     */
    int updateByPrimaryKey(Order record);
}