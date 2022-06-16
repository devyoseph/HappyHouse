package com.ys.happyhouse.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseDto {
	//houseinfo
	private int no;
	private int aptCode;
	private String aptName;
	private String dongCode;
	private String dongName;
	private int buildYear;
	private String jibun;
	private String lat;
	private String lng;
	private String img;
	
	//added
	private String recentPrice;
	
	//for search
	private String sidoName;
	private String gugunName;

	//housedeal
	private int dealAmount;
	private int dealMonth;
	private int dealDay;
	private String area;
	private int floor;
	private int type;
	private String rentMoney;
}
