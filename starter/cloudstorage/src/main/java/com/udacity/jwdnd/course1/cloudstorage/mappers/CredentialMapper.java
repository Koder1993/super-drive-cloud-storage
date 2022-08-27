package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @ResultMap("credentialsResultMap")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    @ResultMap("credentialsResultMap")
    Credential getCredential(int credentialId);

    @Update("UPDATE CREDENTIALS SET username=#{username}, password=#{password}, url=#{url} WHERE credentialid=#{credentialId}")
    int updateCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    @Results(id = "credentialsResultMap", value = {
            @Result(property = "userId", column = "userid"),
            @Result(property = "credentialId", column = "credentialid"),
    })
    List<Credential> getCredentialsListForUser(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userId} AND credentialid = #{credentialId}")
    int deleteCredential(int userId, int credentialId);
}
