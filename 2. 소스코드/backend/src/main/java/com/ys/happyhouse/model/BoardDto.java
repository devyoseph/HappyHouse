package com.ys.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "Board (게시글정보)", description = "글번호, 작성자아이디, 작성자 닉네임, 제목, 내용, 작성일, 조회수를 가진 Domain Class")
public class BoardDto {
	@ApiModelProperty(value = "글번호")
	private int articleno;
	@ApiModelProperty(value = "작성자 아이디")
	private String userid;
	@ApiModelProperty(value = "작성자 이름")
	private String username;
	@ApiModelProperty(value = "글 제목")
	private String subject;
	@ApiModelProperty(value = "글 내용")
	private String content;
	@ApiModelProperty(value = "작성일")
	private String create_date;
	@ApiModelProperty(value = "조회수")
	private String visit_count;
	@ApiModelProperty(value = "검색을 위한 타입 설정")
	private String type;
	@ApiModelProperty(value = "페이징을 위한 start, last")
	private int start;
	@ApiModelProperty(value = "페이징을 위한 start, last")
	private int last;
	@ApiModelProperty(value = "페이징을 위한 pageno")
	private int pageno;
}
