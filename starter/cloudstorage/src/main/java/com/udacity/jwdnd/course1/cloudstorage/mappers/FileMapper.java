package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @ResultMap("fileResultMap")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    @Results(id = "fileResultMap", value = {@Result(property = "userId", column = "userid"), @Result(property = "fileId", column = "fileid"), @Result(property = "fileName", column = "filename"), @Result(property = "contentType", column = "contenttype"), @Result(property = "fileSize", column = "filesize"), @Result(property = "fileData", column = "filedata")})
    List<File> getFilesForUser(int userId);

    @Delete("DELETE FROM FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    void deleteFile(int userId, int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    File getFileByName(Integer userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    File getFileById(Integer userId, Integer fileId);
}
