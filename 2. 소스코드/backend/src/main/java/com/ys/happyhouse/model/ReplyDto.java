package com.ys.happyhouse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDto {
	private int id;
	private int articleno;
	private String userid;
	private String username;
	private String content;
	private String create_date;
}
