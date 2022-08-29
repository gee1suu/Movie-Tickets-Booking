package com.mystudy.movieinfo;

public class GradeVO {
	private int grade_id;
	private int movie_id;
	private int customer_id;
	private double grade;

	public GradeVO(int grade_id, int movie_id, int customer_id, double grade) {
		this.grade_id = grade_id;
		this.movie_id = movie_id;
		this.customer_id = customer_id;
		this.grade = grade;
	}

	public int getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(int grade_id) {
		this.grade_id = grade_id;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

}
