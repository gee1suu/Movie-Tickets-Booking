package com.mystudy.movieinfo;
import java.util.Date;

public class TimeTableVO {
	private int timetable_id;
	private Date start_time;
	private Date end_time;
	private String cinema_id;
	private int movie_id;
	private String movieprice_id;

	public TimeTableVO() {
	}

	public TimeTableVO(int timetable_id, Date start_time, Date end_time, String cinema_id, int movie_id,
			String movieprice_id) {
		this.timetable_id = timetable_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.cinema_id = cinema_id;
		this.movie_id = movie_id;
		this.movieprice_id = movieprice_id;
	}

	public int getTimetable_id() {
		return timetable_id;
	}

	public void setTimetable_id(int timetable_id) {
		this.timetable_id = timetable_id;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getCinema_id() {
		return cinema_id;
	}

	public void setCinema_id(String cinema_id) {
		this.cinema_id = cinema_id;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getMoviePrice_id() {
		return movieprice_id;
	}

	public void setMoviePrice_id(String movieprice_id) {
		this.movieprice_id = movieprice_id;
	}

	@Override
	public String toString() {
		return "TIMETABLE_ID = " + getTimetable_id() + "\nTIME = " + getStart_time() + " ~ " + getEnd_time()
				+ "\nCINEMA_ID = " + getCinema_id() + "\nMOVIE_ID = " + getMovie_id() + "\nTIME_ID = "
				+ getMoviePrice_id();
	}

}
