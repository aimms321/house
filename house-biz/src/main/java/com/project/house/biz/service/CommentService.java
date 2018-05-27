package com.project.house.biz.service;

import com.project.house.biz.mapper.CommentMapper;
import com.project.house.biz.mapper.UserMapper;
import com.project.house.common.model.Comment;
import com.project.house.common.model.User;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public int addHouseComment(Comment comment) {
        BeanHelper.onInsert(comment);
        return commentMapper.insert(comment);
    }

    public List<Comment> getHouseComment(long id,int size) {
        List<Comment> comments = commentMapper.selectComments(id, size);
        comments.forEach(k->{
            User query = new User();
            query.setId(k.getUserId());
            List<User> userList = userService.selectUsersByQuery(query);
            User user = null;
            if (!userList.isEmpty()) {
                user = userList.get(0);
            }
            k.setUserName(user.getName());
            k.setAvatar(user.getAvatar());

        });
        return comments;
    }
}
