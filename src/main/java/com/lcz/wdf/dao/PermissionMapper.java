package com.lcz.wdf.dao;

import com.lcz.wdf.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @Entity com.lcz.wdf.entity.Permission
 */
@Mapper
public interface PermissionMapper {
    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    int insert(Permission record);

    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    int insertSelective(Permission record);

    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    Permission selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     *
     * @mbg.generated 2021-03-16 14:18:21
     */
    int updateByPrimaryKey(Permission record);

    Set<Permission> selectByAccount(@Param("account") String account);
}