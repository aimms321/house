package com.project.house.web.controller;

import com.project.house.biz.service.CommentService;
import com.project.house.common.model.Comment;
import com.project.house.common.model.User;
import com.project.house.common.result.ResultMsg;
import com.project.house.web.Interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by user on 2018-05-27.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping("comment/leaveComment")
    public String leaveComment(Comment comment) {
        User user = UserContext.get();
        comment.setUserId(user.getId());
        comment.setHouseId(comment.getHouseId());
        comment.setType(1);
        int result = commentService.addHouseComment(comment);
        if (result > 0) {
            return "redirect:/house/detail?id="+comment.getHouseId()+"&"+ ResultMsg.successMsg("评论成功！").asUrlParams();
        }
        return "redirect:/house/detail?id="+comment.getHouseId()+"&"+ ResultMsg.errorMsg("评论失败").asUrlParams();
    }


}
