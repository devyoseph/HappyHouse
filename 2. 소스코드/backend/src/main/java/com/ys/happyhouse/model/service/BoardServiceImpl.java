package com.ys.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ys.happyhouse.model.BoardDto;
import com.ys.happyhouse.model.LikeDto;
import com.ys.happyhouse.model.ReplyDto;
import com.ys.happyhouse.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> list(int pageno) throws Exception {
		int start = pageno*7;
		int last = (pageno+1)*7;
	
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("last", last);
		return boardMapper.list(map);
	}
	
	@Override
	public List<BoardDto> searchByContent(BoardDto boardDto) throws Exception {
		int pageno = boardDto.getPageno();
		int start = pageno*7;
		int last = (pageno+1)*7;
	
		boardDto.setStart(start);
		boardDto.setLast(last);
		
		return boardMapper.searchByContent(boardDto);
	}
	
	@Override
	public int registerArticle(BoardDto boardDto) throws Exception {
		return boardMapper.registerArticle(boardDto);
	}

	@Override
	public BoardDto detailArticle(int articleno) throws Exception {
		return boardMapper.detailArticle(articleno);
	}

	@Override
	public int editArticle(BoardDto boardDto) throws Exception {
		return boardMapper.editArticle(boardDto);
	}

	@Override
	public int deleteArticle(int articleno) throws Exception {
		return boardMapper.deleteArticle(articleno);
	}

	@Override
	public List<ReplyDto> selectReply(int articleno) throws Exception {
		return boardMapper.selectReply(articleno);
	}

	@Override
	public int registerReply(ReplyDto replyDto) throws Exception {
		return boardMapper.registerReply(replyDto);
	}
	
	@Override
	public int deleteReply(int reply_id) throws Exception {
		return boardMapper.deleteReply(reply_id);
	}
	
	@Override
    public int getMyScore(LikeDto likedto) throws Exception {
        return boardMapper.getMyScore(likedto);
    }

	@Override
	public LikeDto getAllScores(int articleno) throws Exception {
		return boardMapper.getAllScores(articleno);
	}

	@Override
	public int deleteScore(LikeDto likedto) throws Exception {
		return boardMapper.deleteScore(likedto);
	}

	@Override
	public int registerScore(LikeDto likedto) throws Exception {
		return boardMapper.registerScore(likedto);
	}

}
