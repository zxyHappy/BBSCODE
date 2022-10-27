package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.CommentMapper;
import com.bluemsun.dao.mapper.LikeMapper;
import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.ChildComment;
import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Page;
import com.bluemsun.service.CommentService;
import com.bluemsun.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired
    Comment comment;
    @Autowired
    ChildComment childComment;
    @Autowired CommentMapper commentMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired UserMapper userMapper;
    @Autowired LikeMapper likeMapper;
    @Autowired Page<Comment> oneCommentPage;
    @Autowired Page<ChildComment> twoCommentPage;


    @Override
    public String addOneComment(int userId, int postsId, String body) {
        comment.setComment(userId,postsId,body);
        int i = commentMapper.addOneComment(comment);
        postsMapper.addComment(postsId);  //帖子量增加
        Jedis jedis = RedisUtil.getJedis();
        if(i != 0 && jedis != null) {
            jedis.set("one_id_"+ comment.getId(),"0");
            RedisUtil.closeJedis(jedis);
            return "评论成功";
        }
        return "评论失败";
    }

    @Override
    public List<Comment> getOneComment(int postsId,int userId) {
        List<Comment> list = commentMapper.getOneComment(postsId);
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null) {
            for (Comment comment : list) {
                comment.setUser(userMapper.getUserById(comment.getUserId()));
                if(jedis.exists("one_id_" + comment.getId())){
                    comment.setlikeNumber(Integer.parseInt(jedis.get("one_id_" + comment.getId())));
                }else comment.setlikeNumber(0);
//                if(jedis.hexists("user_id_"+userId,"one_id_"+comment.getId()) && "1".equals(jedis.hget("user_id_"+userId,"one_id_"+comment.getId()))){
//                    comment.setlikeStatus(1);
//                }else comment.setlikeStatus(0);
            }
        }
        return list;
    }

    @Override
    public List<ChildComment> getTwoComment(int oneId,int userId) {
        List<ChildComment> list = commentMapper.getTwoComment(oneId);
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null) {
            for (ChildComment comment : list) {
                comment.setUserSend(userMapper.getUserById(comment.getUseridSend()));
                comment.setUserReply(userMapper.getUserById(comment.getUseridReply()));
                //            comment.setlikeNumber(likeMapper.likeNumberTwo(comment.getId()));
                if(jedis.exists("two_id_"+comment.getId())) {
                    comment.setlikeNumber(Integer.parseInt(jedis.get("two_id_"+comment.getId())));
                }else comment.setlikeNumber(0);
//                if(jedis.hexists("user_id_"+userId,"two_id_"+comment.getId()) && "1".equals(jedis.hget("user_id_"+userId,"two_id_"+comment.getId()))){
//                    comment.setlikeStatus(1);
//                }else comment.setlikeStatus(0);
            }
        }
        return list;
    }

    @Override
    public String addTwoComment(ChildComment comment) {
        int i = commentMapper.addTwoComment(childComment);
        Jedis jedis = RedisUtil.getJedis();
        if(i != 0 && jedis != null) {
            jedis.set("two_id_"+comment.getId(),"0");
            RedisUtil.closeJedis(jedis);
            return "评论成功";
        }
        return "评论失败";
    }

    @Override
    public String updateLikeOne(int oneId, int userId) {
//        Like like = likeMapper.confirmLikeByOne(userId,oneId);
//        if(like != null){
//            likeMapper.deleteLikeOne(userId,oneId);
//            return "取消成功";
//        }else {
//            likeMapper.likeOne(userId,oneId);
//            return "点赞成功";
//        }
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null){
            if(jedis.hexists("userid_"+userId,"one_id_"+oneId) && "1".equals(jedis.hget("userid_"+userId,"one_id_"+oneId))){
                jedis.hset("userid_"+userId,"one_id_"+oneId,"0");
                jedis.decr("one_id_"+oneId);
                RedisUtil.closeJedis(jedis);
                return "取消成功";
            }else {
                jedis.hset("userid_"+userId,"one_id_"+oneId,"1");
                jedis.incr("one_id_"+oneId);
                RedisUtil.closeJedis(jedis);
                return "点赞成功";
            }
        }
        return "操作失败";

    }

    @Override
    public String updateLikeTwo(int twoId, int userId) {
//        Like like = likeMapper.confirmLikeByTwo(userId,twoId);
//        if(like != null){
//            likeMapper.deleteLikeTwo(userId,twoId);
//            return "取消成功";
//        }else {
//            likeMapper.likeTwo(userId,twoId);
//            return "点赞成功";
//        }
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null){
            if(jedis.hexists("userid_"+userId,"two_id_"+twoId) && "1".equals(jedis.hget("userid_"+userId,"two_id_"+twoId))){
                jedis.hset("userid_"+userId,"two_id_"+twoId,"0");
                jedis.decr("two_id_"+twoId);
                RedisUtil.closeJedis(jedis);
                return "取消成功";
            }else {
                jedis.hset("userid_"+userId,"two_id_"+twoId,"1");
                jedis.incr("two_id_"+twoId);
                RedisUtil.closeJedis(jedis);
                return "点赞成功";
            }
        }
        return "操作失败";
//        RedisUtil.closeJedis(jedis);
    }

    @Override
    public void setLikeStatusOne(int userId, List<Comment> commentList) {
//        for(Comment comment:commentPage.getList()){
//            if(likeMapper.confirmLikeByOne(userId,comment.getId()) == null) comment.setlikeStatus(0);
//            else comment.setlikeStatus(1);
//        }
        Jedis jedis = RedisUtil.getJedis();
        if (jedis != null){
            for (Comment comment : commentList) {
                if (jedis.hexists("userid_" + userId, "one_id_" + comment.getId()) && "1".equals(jedis.hget("userid_" + userId, "one_id_" + (comment.getId()))))
                    comment.setlikeStatus(1);
                else comment.setlikeStatus(0);
            }
        }
        RedisUtil.closeJedis(jedis);
    }

    @Override
    public void setLikeStatusTwo(int userId, List<ChildComment> commentList) {
//        for(ChildComment comment:commentPage.getList()){
//            if(likeMapper.confirmLikeByTwo(userId,comment.getId())==null) comment.setlikeStatus(0);
//            else comment.setlikeStatus(1);
//        }
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null) {
            for (ChildComment childComment : commentList) {
                if (jedis.hexists("userid_"+userId,"two_id_"+ childComment.getId()) && "1".equals(jedis.hget("userid_"+userId,"two_id_"+ childComment.getId()))) childComment.setlikeStatus(1);
                else childComment.setlikeStatus(0);
            }
        }
        RedisUtil.closeJedis(jedis);
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
