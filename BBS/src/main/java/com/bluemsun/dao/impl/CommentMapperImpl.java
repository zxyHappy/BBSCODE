package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.CommentMapper;
import com.bluemsun.entity.OneComment;
import com.bluemsun.entity.TwoComment;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class CommentMapperImpl implements CommentMapper {

    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int addOneComment(OneComment oneComment) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.addOneComment(oneComment);
    }

    @Override
    public List<OneComment> getOneComment(int postsId, int startIndex) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneComment(postsId,startIndex);
    }

    @Override
    public List<TwoComment> getTwoComment(int oneId, int startIndex) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoComment(oneId,startIndex);
    }

    @Override
    public int addTwoComment(TwoComment twoComment) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.addTwoComment(twoComment);
    }

    @Override
    public int getOneCommentNumber(int postsId) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneCommentNumber(postsId);
    }

    @Override
    public int getTwoCommentNumber(int oneId) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoCommentNumber(oneId);
    }

    @Override
    public int deleteOneComment(int id) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.deleteOneComment(id);
    }

    @Override
    public int deleteTwoComment(int id) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.deleteTwoComment(id);
    }

    @Override
    public OneComment getOneCommentById(int id) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getOneCommentById(id);
    }

    @Override
    public TwoComment getTwoCommentById(int id) {
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        return commentMapper.getTwoCommentById(id);
    }
}
