package com.cotton.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.cotton.dao.provider.UserProvider;
import com.cotton.model.user.User;

public interface UserMDAO {
    
    @SelectProvider(type = UserProvider.class, method = "page")
    public List<User> page(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @SelectProvider(type = UserProvider.class, method = "count")
    public Integer count();
    
    
}