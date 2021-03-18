package com.lcz.wdf.dao;

import com.lcz.wdf.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户mapper
 * @author li.chengzhen
 * @Entity com.lcz.wdf.entity.Users
 */
@Mapper
public interface UsersMapper {
    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    int insert(Users record);

    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    int insertSelective(Users record);

    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    Users selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    int updateByPrimaryKeySelective(Users record);

    /**
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    int updateByPrimaryKey(Users record);

    Users selectByAccount(@Param("account") String account);
}