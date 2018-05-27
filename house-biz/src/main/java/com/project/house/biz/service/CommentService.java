package com.project.house.biz.service;

import com.project.house.biz.mapper.CommentMapper;
import com.project.house.common.model.Comment;
import com.project.house.common.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018-05-27.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public int addHouseComment(Comment comment) {
        BeanHelper.onInsert(comment);
        return commentMapper.insert(comment);
    }

    public List<Comment> getHouseComment(long id,int size) {
        return commentMapper.selectComments(id, size);
    }
}
