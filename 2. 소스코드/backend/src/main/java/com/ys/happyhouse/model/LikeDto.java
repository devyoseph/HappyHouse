package com.ys.happyhouse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeDto {
	private int id;
	private int articleno;
	private String userid;
	private String username;
	private int score;
	private String create_date;
	
	private int plus;
	private int minus;
}