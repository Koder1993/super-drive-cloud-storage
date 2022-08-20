package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @ResultMap("userResultMap")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    @Results(id = "userResultMap", value = {
            @Result(property = "userId", column = "userid"),
    })
    User getUser(String username);

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    void deleteUser(String username);
}
