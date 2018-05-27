package com.project.house.biz.mapper;

import com.project.house.common.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by user on 2018-05-27.
 */
public interface CommentMapper {

    int insert(Comment comment);

    List<Comment> selectComments(@Param("id") long id, @Param("size") int size);
}
