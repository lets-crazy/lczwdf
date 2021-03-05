package com.lcz.wdf.dao;

import com.lcz.wdf.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/5 16:14
 */
@Mapper
public interface UserMapper {

  /**
   * 查询所有用户
   *
   * @return com.lcz.wdf.entity.Users
   * @author li.chengzhen
   * @since 2021/3/5 16:33
   */
  Users selectAll();
}
