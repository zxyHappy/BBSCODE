package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.*;
import com.bluemsun.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsServiceImpl implements PostsService {

    @Autowired PostsMapper postsMapper;
    @Autowired ZanMapper zanMapper;
    @Autowired UserMapper userMapper;
    @Autowired BlockMapper blockMapper;
    @Autowired Page<OneComment> commentPage;
    @Autowired FileMapper fileMapper;

    @Override
    public String addPosts(Posts posts) {
        blockMapper.addPostsNumber(posts.getBlockId());
        if(postsMapper.addPosts(posts) == 1) return "发帖成功";
        return "发帖失败";
    }

    @Override
    public Map<String, Object> showPosts(int id,int userId) {
        Posts posts = postsMapper.showPosts(id);
        posts.setLikeNumber(zanMapper.zanNumberPosts(id));
//        postsMapper.addScan(id);
        if(userId != posts.getUserId()) postsMapper.addScan(id);
        posts.setReplyNumber(postsMapper.getOneCommentNumber(id));
        if(zanMapper.confirmZanByPosts(posts.getUserId(),id) != null){
            posts.setZanStatus(1);
        }else posts.setZanStatus(0);
        posts.setUser(userMapper.getUserById(posts.getUserId()));
        String blockName = blockMapper.getBlockName(posts.getBlockId()); //
        int postsNumber = postsMapper.getOneCommentNumber(id);
        commentPage.setPage(1,5,postsNumber);
        commentPage.setList(postsMapper.getComments(id,commentPage.getStartIndex()));
        for(OneComment c:commentPage.getList()){
            c.setUser(userMapper.getUserById(c.getUserId()));
        }
        List<File> fileList = fileMapper.getFile(id);
        for(File file:fileList){
            String name = file.getFileName();
            String hzName = name.substring(name.lastIndexOf(".")+1);
            file.setFileType(hzName);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("posts",posts);
        map.put("blockName",blockName);
        map.put("commentPage",commentPage);
        map.put("fileList",fileList);
        return map;
    }

    @Override
    public String updateZan(int postsId, int userId) {
        Zan zan = zanMapper.confirmZanByPosts(userId,postsId);
        if(zan==null){
            zanMapper.zanPosts(userId,postsId);
            postsMapper.addLike(postsId);
            return "点赞成功";
        }else {
            zanMapper.deleteZanPosts(userId,postsId);
            postsMapper.deleteLike(postsId);
            return "取消成功";
        }
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
}
