package com.service.vo;

public class DiscountVO {
	private String userType;
	private int start;
	private int end;
	private int discountPerc;

	public DiscountVO(String userType, int start, int end, int discountPerc) {
		super();
		this.userType = userType;
		this.start = start;
		this.end = end;
		this.discountPerc = discountPerc;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getDiscountPerc() {
		return discountPerc;
	}

	public void setDiscountPerc(int discountPerc) {
		this.discountPerc = discountPerc;
	}

	@Override
	public String toString() {
		return "DiscountVO [userType=" + userType + ", start=" + start + ", end=" + end + ", discountPerc="
				+ discountPerc + "]";
	}

}
