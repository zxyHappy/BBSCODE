package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.*;
import com.bluemsun.service.IndexService;
import com.bluemsun.service.InformService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InformServiceImpl implements InformService {

    @Autowired UserMapper userMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired InformMapper informMapper;
    @Autowired BlockMapper blockMapper;
    @Autowired FollowMapper followMapper;
    @Autowired Page<Inform> informPage;

    @Override
    public String addCommentInform(String value, int userIdSend, int userIdReply, int postsId) {
        String nickName = userMapper.getUserById(userIdSend).getNickName();
        String body = nickName+"：回复了您："+value;
        Inform inform = new Inform(userIdSend,postsId,body,"comment");
        informMapper.addInform(inform);
        informMapper.addInformIndex(inform.getId(),userIdReply);
        return "已通知";
    }

    @Override
    public String addFollowInform(int userId, int userIdFollowed) {
        String nickName = userMapper.getUserById(userId).getNickName();
        String body = nickName+"：关注了您";
        Inform inform = new Inform(userId,body,"follow");
        informMapper.addInform(inform);
        informMapper.addInformIndex(inform.getId(),userIdFollowed);
        return "已通知";
    }

    @Override
    public String addPostsInformByBlock(int postsId, int userId) {
        Posts posts = postsMapper.showPosts(postsId);
        Block block = blockMapper.showBlockMessage(posts.getBlockId());
        String body = "您关注的板块："+block.getBlockName()+"更新了帖子：《"+posts.getHead()+"》";
        Inform inform = new Inform(body,"system");
        inform.setPostsId(postsId);
        informMapper.addInform(inform);
        List<Integer> list = followMapper.getPersonByBlock(block.getId());
        for(int id:list){
            informMapper.addInformIndex(inform.getId(),id);
        }
        return "已通知";
    }

    @Override
    public String addPostsInformByPerson(int postsId, int userIdFollowed) {
        String postsName = postsMapper.showPosts(postsId).getHead();
        String userNickName = userMapper.getUserById(userIdFollowed).getNickName();
        String body = "您关注的用户："+userNickName+"更新了帖子：《"+postsName+"》";
        Inform inform = new Inform(userIdFollowed,postsId,body,"system");
        informMapper.addInform(inform);
        List<Integer> list = followMapper.getPersonByPerson(userIdFollowed);
        for(int userId:list){
            informMapper.addInformIndex(inform.getId(),userId);
        }
        return "已通知";
    }

    @Override
    public String confirmPostsInform(int postsId, int userId,int status) {
        Posts posts = postsMapper.showPosts(postsId);
        String body;
        if(status == 1) body = "您的帖子：《"+posts.getHead()+"》已通过审核";
        else body = "您的帖子："+posts.getHead()+"未通过审核";
        Inform inform = new Inform(body,"system");
        inform.setPostsId(postsId);
        informMapper.addInform(inform);
        informMapper.addInformIndex(inform.getId(),userId);
        return "已通知";
    }

    @Override
    public String MasterConfirmInform(int postsId) {
        Posts posts = postsMapper.showPosts(postsId);
        String body = "有一个帖子需要您的审核：《"+posts.getHead()+"》";
        Inform inform = new Inform(body,"system");
        inform.setPostsId(postsId);
        informMapper.addInform(inform);
        List<BlockMaster> list = blockMapper.getBlockMaster(posts.getBlockId());
        for(BlockMaster blockMaster:list){
            informMapper.addInformIndex(inform.getId(),blockMaster.getUserId());
        }
        return "已通知";
    }

    @Override
    public String deleteInform(int informId,int userId) {
        if(informMapper.checkInform(userId,informId) == null) return "删除失败";
        int i = informMapper.deleteInform(informId);
        if(i != 0) return "删除成功";
        return "删除失败";
    }

    @Override
    public Page<Inform> showInform(int userId,int index,String type) {
        informPage.setPage(index,6,informMapper.getInformNumber(userId,type));
        List<Inform> list = informMapper.getInform(userId,type,informPage.getStartIndex());
        for(Inform inform:list){
            if(inform.getConfirmStatus() == 0) informMapper.confirmInform(inform.getId());
        }
        informPage.setList(list);
        return informPage;
    }

    @Override
    public String confirmPostsForUser(int userId, int postsId) {
        Posts posts = postsMapper.showPosts(postsId);
        String body = "您的帖子《"+posts.getHead()+"》正在送审，请耐心等待";
        Inform inform = new Inform(body,"system");
        inform.setPostsId(postsId);
        informMapper.addInform(inform);
        informMapper.addInformIndex(inform.getId(),userId);
        return "已通知";
    }
}
