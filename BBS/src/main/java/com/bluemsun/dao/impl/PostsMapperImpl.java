package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Posts;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class PostsMapperImpl implements PostsMapper {

    private SqlSessionTemplate sqlSession;
    private PostsMapper postsMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        postsMapper = sqlSession.getMapper(PostsMapper.class);
    }

    @Override
    public int addPosts(Posts posts) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.addPosts(posts);
    }

    @Override
    public Posts showPosts(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.showPosts(id);
    }

    @Override
    public List<Comment> getComments(int postsId) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getComments(postsId);
    }

    @Override
    public int getOneCommentNumber(int postsId) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getOneCommentNumber(postsId);
    }

    @Override
    public int addScan(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.addScan(id);
    }

    @Override
    public List<Posts> getTopPosts() {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getTopPosts();
    }

    @Override
    public List<Posts> getHotPosts(int blockId) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getHotPosts(blockId);
    }

    @Override
    public int getNumberByBlock(int blockId) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getNumberByBlock(blockId);
    }

    @Override
    public void addLike(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        postsMapper.addLike(id);
    }

    @Override
    public void deleteLike(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        postsMapper.deleteLike(id);
    }

    @Override
    public void addComment(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        postsMapper.addComment(id);
    }

    @Override
    public void deleteComment(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        postsMapper.deleteComment(id);
    }

    @Override
    public int setTop(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.setTop(id);
    }

    @Override
    public int deleteTop(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.deleteTop(id);
    }

    @Override
    public List<Posts> getTop(int blockId) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getTop(blockId);
    }

    @Override
    public List<Posts> getPosts(int blockId, int startIndex) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.getPosts(blockId,startIndex);
    }

    @Override
    public int updatePosts(String head, String body, int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.updatePosts(head,body,id);
    }

    @Override
    public int deletePosts(int id) {
//        PostsMapper postsMapper = sqlSession.getMapper(PostsMapper.class);
        return postsMapper.deletePosts(id);
    }

    @Override
    public int setScanNumber(int scanNumber,int postsId) {
        return postsMapper.setScanNumber(scanNumber,postsId);
    }

    @Override
    public int getScanNumber(int id) {
        return postsMapper.getScanNumber(id);
    }

    @Override
    public List<Posts> searchPosts(String value) {
        return postsMapper.searchPosts(value);
    }

    @Override
    public List<Posts> showDrafts(int userId) {
        return postsMapper.showDrafts(userId);
    }

    @Override
    public int addDraft(Posts posts) {
        int i = postsMapper.addDraft(posts);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public int setPostsStatus(int postsId) {
        int i = postsMapper.setPostsStatus(postsId);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public int confirmPosts(int postsId) {
        int i = postsMapper.confirmPosts(postsId);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public int rejectPosts(int postsId) {
        int i = postsMapper.rejectPosts(postsId);
        if(i != 0) return 1;
        return 0;
    }
}
