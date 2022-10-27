package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.CommentMapper;
import com.bluemsun.entity.ChildComment;
import com.bluemsun.entity.Comment;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class CommentMapperImpl implements CommentMapper {

    private SqlSessionTemplate sqlSession;
    private CommentMapper commentMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        commentMapper = sqlSession.getMapper(CommentMapper.class);
    }

    @Override
    public int addOneComment(Comment comment) {
        return commentMapper.addOneComment(comment);
    }

    @Override
    public List<Comment> getOneComment(int postsId) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneComment(postsId);
    }

    @Override
    public List<ChildComment> getTwoComment(int oneId) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoComment(oneId);
    }

    @Override
    public int addTwoComment(ChildComment childComment) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.addTwoComment(childComment);
    }

    @Override
    public int getOneCommentNumber(int postsId) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneCommentNumber(postsId);
    }

    @Override
    public int getTwoCommentNumber(int oneId) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoCommentNumber(oneId);
    }

    @Override
    public int deleteOneComment(int id) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.deleteOneComment(id);
    }

    @Override
    public int deleteTwoComment(int id) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.deleteTwoComment(id);
    }

    @Override
    public Comment getOneCommentById(int id) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneCommentById(id);
    }

    @Override
    public ChildComment getTwoCommentById(int id) {
//        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoCommentById(id);
    }
}
