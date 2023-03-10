package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.*;
import com.bluemsun.service.InformService;
import com.bluemsun.service.PostsService;
import com.bluemsun.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsServiceImpl implements PostsService {

    @Autowired PostsMapper postsMapper;
    @Autowired LikeMapper likeMapper;
    @Autowired UserMapper userMapper;
    @Autowired BlockMapper blockMapper;
    @Autowired FileMapper fileMapper;
    @Autowired FollowMapper followMapper;
    @Autowired CommentMapper commentMapper;
    @Autowired InformService informService;


    @Override
    public String addPosts(Posts posts,List<Integer> list) {
        blockMapper.addPostsNumber(posts.getBlockId());
        if(postsMapper.addPosts(posts) == 1) {
            informService.MasterConfirmInform(posts.getId());
            informService.confirmPostsForUser(posts.getUserId(),posts.getId());
            for(int i:list){
                fileMapper.setFilePosts(posts.getId(),i);
            }
            return "发帖成功";
        }
        return "发帖失败";
    }

    @Override
    public Map<String, Object> showPosts(int id,int userId) {
        Jedis jedis = RedisUtil.getJedis();
        Posts posts = postsMapper.showPosts(id);
        if(posts.getUserId() != userId){
            if(!jedis.hexists("posts_id_"+id,"scan_number1") ){
                jedis.hset("posts_id_"+id,"scan_number1",String.valueOf(postsMapper.getScanNumber(id)));
            }
            if(!jedis.hexists("posts_id_"+id,"scan_number2") ){
                jedis.hset("posts_id_"+id,"scan_number2",String.valueOf(postsMapper.getScanNumber(id)));
            }
            int scanNumber1 = Integer.parseInt(jedis.hget("posts_id_"+id,"scan_number1"));
            int scanNumber2 = Integer.parseInt(jedis.hget("posts_id_"+id,"scan_number2"));
            scanNumber2++;
            if((scanNumber2-scanNumber1)*100 >= scanNumber1 ){
                postsMapper.setScanNumber(scanNumber2,id);
                jedis.hset("posts_id_"+id,"scan_number1",String.valueOf(scanNumber2));
                jedis.hset("posts_id_"+id,"scan_number2",String.valueOf(scanNumber2));
            }else {
                jedis.hset("posts_id_"+id,"scan_number2",String.valueOf(scanNumber2));
            }
        }
//        posts.setLikeNumber(likeMapper.likeNumberPosts(id));
//        if(userId != posts.getUserId()) postsMapper.addScan(id);
        if(!jedis.hexists("posts_id_"+id,"like_number")){
            posts.setLikeNumber(0);
        }else {
            int likeNumber = Integer.parseInt(jedis.hget("posts_id_"+id,"like_number"));
            posts.setLikeNumber(likeNumber);
        }
        posts.setReplyNumber(postsMapper.getOneCommentNumber(id));
        if(jedis.hexists("user_id_"+userId,"posts_id_"+id) && "1".equals(jedis.hget("user_id_"+userId,"posts_id_"+id))){
            posts.setlikeStatus(1);
        }else posts.setlikeStatus(0);
        posts.setUser(userMapper.getUserById(posts.getUserId()));
        Map<String,Object> map = new HashMap<>();
        Follow follow = new Follow(userId,posts.getUserId());
        if(followMapper.checkFollowPeople(follow) == null){
            map.put("followPerson",0);
        }else map.put("followPerson",1);
        String blockName = blockMapper.getBlockName(posts.getBlockId()); //
//        int postsNumber = postsMapper.getOneCommentNumber(id);
//        commentPage.setPage(1,5,postsNumber);
//        commentPage.setList(postsMapper.getComments(id,commentPage.getStartIndex()));
        List<Comment> commentList = postsMapper.getComments(id);
        for(Comment c:commentList){
            c.setUser(userMapper.getUserById(c.getUserId()));
            c.setChildCommentNumber(commentMapper.getTwoCommentNumber(c.getId()));
            if(!jedis.hexists("posts_id_"+id,"like_number")) c.setLikeNumber(0);
            else {
                int likeNumber = Integer.parseInt(jedis.hget("posts_id_"+id,"like_number"));
                c.setLikeNumber(likeNumber);
            }
            if(jedis.hexists("user_id_"+userId,"one_id_"+c.getId()) && "1".equals(jedis.hget("user_id_"+userId,"one_id_"+c.getId()))){
                c.setLikeStatus(1);
            }else c.setLikeStatus(0);
        }
        List<File> fileList = fileMapper.getFile(id);
        for(File file:fileList){
            String name = file.getFileName();
            String hzName = name.substring(name.lastIndexOf(".")+1);
            file.setFileType(hzName);
        }
        map.put("posts",posts);
        map.put("blockName",blockName);
        map.put("commentList",commentList);
        map.put("fileList",fileList);
        RedisUtil.closeJedis(jedis);
        return map;
    }

    @Override
    public String updateLike(int postsId, int userId) {
//        Like like = likeMapper.confirmLikeByPosts(userId,postsId);
//        if(like ==null){
//            likeMapper.likePosts(userId,postsId);
//            postsMapper.addLike(postsId);
//            return "点赞成功";
//        }else {
//            likeMapper.deleteLikePosts(userId,postsId);
//            postsMapper.deleteLike(postsId);
//            return "取消成功";
//        }
        Jedis jedis = RedisUtil.getJedis();
        if(jedis!=null){
            if(!jedis.hexists("user_id_"+userId,"posts_id_"+postsId) || "0".equals(jedis.hget("user_id_"+userId,"posts_id_"+postsId))){
                jedis.hset("user_id_"+userId,"posts_id_"+postsId,"1");
                if(!jedis.hexists("posts_id_"+postsId,"like_number")){
                    jedis.hset("posts_id_"+postsId,"like_number","0");
                }
                int likeNumber = Integer.parseInt(jedis.hget("posts_id_"+postsId,"like_number"));
                likeNumber++;
                jedis.hset("posts_id_"+postsId,"like_number",String.valueOf(likeNumber));
                RedisUtil.closeJedis(jedis);
                return "点赞成功";
            }else {
                jedis.hset("user_id_"+userId,"posts_id_"+postsId,"0");
                int likeNumber = Integer.parseInt(jedis.hget("posts_id_"+postsId,"like_number"));
                likeNumber--;
                jedis.hset("posts_id_"+postsId,"like_number",String.valueOf(likeNumber));
                RedisUtil.closeJedis(jedis);
                return "取消成功";
            }
        }
        return "操作失败";
    }

    @Override
    public String updatePosts(Map<String, Object> map, int userId) {
        int id =(int) map.get("id");
        String head = (String) map.get("head");
        String body = (String) map.get("body");
        if(postsMapper.showPosts(id).getUserId()==userId){
            int i =  postsMapper.updatePosts(head,body,id);
            if(i != 0) return "更新成功";
            return "更新失败";
        }
        return "更新失败";
    }

    @Override
    public String deletePosts(int userId, int id) {
        if(postsMapper.showPosts(id).getUserId()==userId){
            int i = postsMapper.deletePosts(id);
            if(i != 0) return "删除成功";
            return "删除失败";
        }
        return "删除失败";
    }


    @Override
    public List<Posts> showDrafts(int userId) {
        return postsMapper.showDrafts(userId);
    }

    @Override
    public String addDraft(Posts posts) {
        int i = postsMapper.addDraft(posts);
        if(i != 0) return "已加入草稿箱";
        return "加入草稿箱失败";
    }

    @Override
    public String setPostsStatus(int postsId) {
        int i = postsMapper.setPostsStatus(postsId);
        if(i != 0) return "发帖成功";
        return "发帖失败";
    }

    @Override
    public String confirmPosts(int postsId,String msg,int userId) {
        if(blockMapper.checkMaster(postsMapper.showPosts(postsId).getBlockId(),userId) == null) return "无操作权限";
        if("accept".equals(msg)){
            int i = postsMapper.confirmPosts(postsId);
            int id = postsMapper.showPosts(postsId).getUserId();
            if(i == 1) {
                informService.confirmPostsInform(postsId,id,1);
                informService.addPostsInformByBlock(postsId,id);
                informService.addPostsInformByPerson(postsId,id);
                return "帖子已通过";
            }
            return "帖子通过失败";
        }else {
            int i = postsMapper.rejectPosts(postsId);
            if(i == 1) {
                informService.confirmPostsInform(postsId,postsMapper.showPosts(postsId).getUserId(),0);
                return "帖子已驳回";
            }
            return "帖子驳回失败";
        }
    }

    @Override
    public String setTopPosts(int postsId, int userId) {
        if(blockMapper.checkMaster(postsMapper.showPosts(postsId).getBlockId(),userId) == null) return "无操作权限";
        int i = postsMapper.setTop(postsId);
        if(i != 0) return "置顶成功";
        return "置顶失败";
    }

    @Override
    public String cancelTop(int postsId, int userId) {
        if(blockMapper.checkMaster(postsMapper.showPosts(postsId).getBlockId(),userId) == null) return "无操作权限";
        int i = postsMapper.deleteTop(postsId);
        if(i != 0) return "取消成功";
        return "取消失败";
    }

    @Override
    public List<Posts> getUnConfirmPosts(int blockId,int userId) {
        if(blockMapper.checkMaster(blockId,userId) == null) return null;
        return postsMapper.getUnConfirmPosts(blockId);
    }
}
