package com.ys.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.happyhouse.model.BoardDto;
import com.ys.happyhouse.model.LikeDto;
import com.ys.happyhouse.model.ReplyDto;
import com.ys.happyhouse.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/board")
@RequiredArgsConstructor
@Api("게시판 컨트롤러")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final BoardService boardService;
	
	@ApiOperation(value = "페이지 번호", notes = "게시판의 <big>지정 페이지<big>목록을 리턴.", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "게시판 글 목록 정상")
	})
	@GetMapping("/{pageno}")
	public ResponseEntity<?> list(@PathVariable("pageno") int pageno) throws Exception{
		List<BoardDto> list = boardService.list(pageno);
		if(list == null) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<BoardDto>> (list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색어", notes = "게시판 내용을 검색해 목록을 리턴.", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "게시판 글 목록 정상")
	})
	@PostMapping("/search")
	public ResponseEntity<?> searchByContent(@RequestBody BoardDto boardDto) throws Exception{
		System.out.println(boardDto);
		List<BoardDto> list = boardService.searchByContent(boardDto);
		if(list == null) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<BoardDto>> (list, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "BoardDto: 게시판 정보", notes = "게시판의 내용을 <big>등록 후<big> 목록을 리턴.", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "게시판 글 등록 성공")
	})
	@PostMapping("/")
	public ResponseEntity<?> registerArticle(@RequestBody BoardDto boardDto) throws Exception {
		int res = boardService.registerArticle(boardDto);
		
		if(res == 0) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<BoardDto>> (boardService.list(0), HttpStatus.OK);
	}
	
	@ApiOperation(value = "BoardDto: 게시판 정보", notes = "특정 게시글을 <big>수정 후<big> 조회", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "글 수정 성공")
	})
	@PutMapping("/")
	public ResponseEntity<?> editArticle(@RequestBody BoardDto boardDto) throws Exception {
		int res = boardService.editArticle(boardDto);
		
		if(res == 0) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<BoardDto>> (boardService.list(0), HttpStatus.OK);
	}
	
	@ApiOperation(value = "articleno", notes = "특정 게시글을 <big>삭제 후<big> 조회", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "글 삭제 성공")
	})
	@DeleteMapping("/{articleno}")
	public ResponseEntity<?> deleteArticle(@PathVariable("articleno") int articleno) throws Exception {
		int res = boardService.deleteArticle(articleno);
		
		if(res == 0) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<BoardDto>> (boardService.list(0), HttpStatus.OK);
	}
	
	@ApiOperation(value = "articleno", notes = "특정 게시글의 <big>세부사항<big> 조회", response = BoardDto.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "상세 글 조회 성공")
	})
	@GetMapping("detail/{articleno}")
	public ResponseEntity<?> detailArticle(@PathVariable("articleno") int articleno) throws Exception {
		BoardDto boardDto =  boardService.detailArticle(articleno);
		
		if(boardDto == null) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BoardDto> (boardDto, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "articleno", notes = "특정 게시글의 <big>댓글<big> 조회", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "댓글 조회 성공")
	})
	@GetMapping("reply/{articleno}")
	public ResponseEntity<?> selectReply(@PathVariable("articleno") int articleno) throws Exception {
		System.out.println("댓글 조회: "+ articleno);
		List<ReplyDto> replyDto =  boardService.selectReply(articleno);
		
		return new ResponseEntity<List<ReplyDto>> (replyDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "ReplyDto", notes = "특정 게시글의 <big>댓글<big> 등록")
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "댓글 등록 성공")
	})
	@PostMapping("reply/register")
	public ResponseEntity<?> registerReply(@RequestBody ReplyDto replyDto) throws Exception {
		System.out.println(replyDto);
		int res = boardService.registerReply(replyDto);
		
		if(res == 0) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Void> (HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "articleno", notes = "특정 게시글의 <big>댓글<big> 삭제", response = List.class)
	@ApiResponses({
		@ApiResponse(code=404, message = "주소 오류!!"),
		@ApiResponse(code=500, message = "서버 에러!!"),
		@ApiResponse(code=200, message = "댓글 삭제 성공")
	})
	@DeleteMapping("reply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable("id") String id) throws Exception {
		int reply_id = Integer.parseInt(id);
		System.out.println(reply_id+" 가 삭제 전");
		int res = boardService.deleteReply(reply_id);
		
		if(res == 0) {
			return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
		}
		System.out.println(id+" 삭제 성공!!");
		return new ResponseEntity<List<ReplyDto>> (boardService.selectReply(reply_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "likedto", notes = "해당 글에 대한 나의 좋아요 값 리턴", response = Integer.class)
	@GetMapping("like/my")
	public ResponseEntity<?> getMyScore(@RequestBody LikeDto likedto) throws Exception {
		System.out.println("해당 글 번호와 회원정보 "+ likedto);
	    return new ResponseEntity<Integer> (boardService.getMyScore(likedto), HttpStatus.NOT_FOUND); 
	    
	}

	@ApiOperation(value = "articleno", notes = "해당 글에 대한 전체 좋아요 싫어요 개수 리턴", response = List.class)
	@GetMapping("like/{articleno}")
	public ResponseEntity<?> getAllScores(@PathVariable int articleno) throws Exception {
		System.out.println("해당 글에 대한 좋아요 싫어요 개수 조사 : "+ articleno);
	    return new ResponseEntity<LikeDto> (boardService.getAllScores(articleno), HttpStatus.OK); 
	    
	}
	
	@ApiOperation(value = "likedto", notes = "이미 해당글에 대한 좋아요가 있는지 확인하고 없으면 생성, 있으면 변경, 같으면 삭제", response = Integer.class)
	@PostMapping("like/my")
	public ResponseEntity<?> addScore(@RequestBody LikeDto likedto) throws Exception {
		System.out.println("좋아요 클릭: "+ likedto);
		int score = likedto.getScore();
		int DB_score = boardService.getMyScore(likedto);
		int del_res = 0;
		int res = 0; //최종결과값
		logger.info("요청: {}, DB 값: {}", score, DB_score);
		if(DB_score == score) { //같은 번호를 클릭했을 때: 그 값을 삭제
			 del_res = boardService.deleteScore(likedto);
		}else if(score != 0) { //score에 넣는 값이 0이 아니고 DB에도 없는 값일 때 추가한다.
			 del_res = boardService.deleteScore(likedto);
			 boardService.registerScore(likedto);
			 res = score;
		}
	    return new ResponseEntity<Integer>(res, HttpStatus.OK);
	}
}
