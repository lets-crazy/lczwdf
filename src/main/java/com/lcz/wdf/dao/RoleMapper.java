package com.lcz.wdf.dao;

import com.lcz.wdf.entity.Role;

/**
 * @Entity com.lcz.wdf.entity.Role
 */
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
}