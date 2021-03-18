package com.lcz.wdf.dao;

import com.lcz.wdf.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @Entity com.lcz.wdf.entity.Role
 */
@Mapper
public interface RoleMapper {
    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    int insert(Role record);

    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    int insertSelective(Role record);

    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    Role selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     *
     * @mbg.generated 2021-03-16 10:57:53
     */
    int updateByPrimaryKey(Role record);

    Set<Role> selectByAccount(@Param("account") String account);
}