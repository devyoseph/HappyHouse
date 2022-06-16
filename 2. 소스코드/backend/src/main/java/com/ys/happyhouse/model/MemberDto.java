package com.ys.happyhouse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	private String platform_id;
	private String platform;
	private String userid;
	private String username;
	private String email;
	private String refresh_token;
	private String refresh_token_date;
	private String joindate;
}
