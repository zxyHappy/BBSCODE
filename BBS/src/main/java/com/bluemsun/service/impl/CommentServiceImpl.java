package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.CommentMapper;
import com.bluemsun.dao.mapper.LikeMapper;
import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.OneComment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.TwoComment;
import com.bluemsun.entity.Like;
import com.bluemsun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired OneComment oneComment;
    @Autowired TwoComment twoComment;
    @Autowired CommentMapper commentMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired UserMapper userMapper;
    @Autowired LikeMapper likeMapper;
    @Autowired Page<OneComment> oneCommentPage;
    @Autowired Page<TwoComment> twoCommentPage;


    @Override
    public String addOneComment(int userId, int postsId, String body) {
        oneComment.setComment(userId,postsId,body);
        int i = commentMapper.addOneComment(oneComment);
        postsMapper.addComment(postsId);
        if(i != 0) return "评论成功";
        return "评论失败";
    }

    @Override
    public Page<OneComment> getOneComment(int postsId, int index) {
        int count = commentMapper.getOneCommentNumber(postsId);
        oneCommentPage.setPage(index,5,count);
        List<OneComment> list = commentMapper.getOneComment(postsId,oneCommentPage.getStartIndex());
        for(OneComment comment:list){
            comment.setUser(userMapper.getUserById(comment.getUserId()));
            comment.setZanNumber(likeMapper.likeNumberOne(comment.getId()));
        }
        oneCommentPage.setList(list);
        return oneCommentPage;
    }

    @Override
    public Page<TwoComment> getTwoComment(int oneId, int index) {
        int count = commentMapper.getTwoCommentNumber(oneId);
        twoCommentPage.setPage(index,5,count);
        List<TwoComment> list = commentMapper.getTwoComment(oneId,twoCommentPage.getStartIndex());
        for(TwoComment comment:list){
            comment.setUserSend(userMapper.getUserById(comment.getUseridSend()));
            comment.setUserReply(userMapper.getUserById(comment.getUseridReply()));
            comment.setZanNumber(likeMapper.likeNumberTwo(comment.getId()));
        }
        twoCommentPage.setList(list);
        return twoCommentPage;
    }

    @Override
    public String addTwoComment(int oneId, int useridSend, int useridReply, String body) {
        twoComment.setComment(oneId,useridSend,useridReply,body);
        int i = commentMapper.addTwoComment(twoComment);
        if(i != 0) return "评论成功";
        return "评论失败";
    }

    @Override
    public String updateLikeOne(int oneId, int userId) {
        Like like = likeMapper.confirmLikeByOne(userId,oneId);
        if(like != null){
            likeMapper.deleteLikeOne(userId,oneId);
            return "取消成功";
        }else {
            likeMapper.likeOne(userId,oneId);
            return "点赞成功";
        }
    }

    @Override
    public String updateLikeTwo(int twoId, int userId) {
        Like like = likeMapper.confirmLikeByTwo(userId,twoId);
        if(like != null){
            likeMapper.deleteLikeTwo(userId,twoId);
            return "取消成功";
        }else {
            likeMapper.likeTwo(userId,twoId);
            return "点赞成功";
        }
    }

    @Override
    public void setLikeStatusOne(int userId, Page<OneComment> commentPage) {
        for(OneComment comment:commentPage.getList()){
            if(likeMapper.confirmLikeByOne(userId,comment.getId()) == null) comment.setZanStatus(0);
            else comment.setZanStatus(1);
        }
    }

    @Override
    public void setLikeStatusTwo(int userId, Page<TwoComment> commentPage) {
        for(TwoComment comment:commentPage.getList()){
            if(likeMapper.confirmLikeByTwo(userId,comment.getId())==null) comment.setZanStatus(0);
            else comment.setZanStatus(1);
        }
    }

    @Override
    public String deleteOneComment(int userId, int id) {
        if(commentMapper.getOneCommentById(id).getUserId() == userId){
            int i = commentMapper.deleteOneComment(id);
            if(i != 0) return "删除成功";
            return "删除失败";
        }
        return "删除失败";
    }

    @Override
    public String deleteTwoComment(int userId, int id) {
        if(commentMapper.getTwoCommentById(id).getUseridSend() == userId || commentMapper.getTwoCommentById(id).getUseridReply() == userId){
            int i = commentMapper.deleteTwoComment(id);
            if(i != 0) return "删除成功";
            return "删除失败";
        }
        return "删除失败";
    }
}
