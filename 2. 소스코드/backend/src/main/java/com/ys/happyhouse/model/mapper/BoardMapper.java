package com.ys.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ys.happyhouse.model.BoardDto;
import com.ys.happyhouse.model.LikeDto;
import com.ys.happyhouse.model.ReplyDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> list(Map<String, Integer> map) throws Exception;
	List<BoardDto> searchByContent(BoardDto boardDto) throws Exception;
	int registerArticle(BoardDto boardDto) throws Exception;
	BoardDto detailArticle(int articleno) throws Exception;
	int editArticle(BoardDto boardDto) throws Exception;
	int deleteArticle(int articleno) throws Exception;
	
	
	List<ReplyDto> selectReply(int articleno) throws Exception;
	int registerReply(ReplyDto replyDto) throws Exception;
	int deleteReply(int reply_id) throws Exception;
	
	int getMyScore(LikeDto likedto) throws Exception;
	LikeDto getAllScores(int articleno) throws Exception;
    int deleteScore(LikeDto likedto) throws Exception;
    int registerScore(LikeDto likedto) throws Exception;
}
