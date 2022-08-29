package com.mystudy.movieinfo;


public class CinemaVO {

	String cinema_name;
	int total_numofseat;
	String cinema_id;
	String region_id;

	public CinemaVO(String cinema_name, int total_numofseat, String cinema_id, String region_id) {
		super();
		this.cinema_name = cinema_name;
		this.total_numofseat = total_numofseat;
		this.cinema_id = cinema_id;
		this.region_id = region_id;
	}

	public CinemaVO() {
		super();
	}

	public String getCinema_name() {
		return cinema_name;
	}

	public void setCinema_name(String cinema_name) {
		this.cinema_name = cinema_name;
	}

	public int getTotal_numofseat() {
		return total_numofseat;
	}

	public void setTotal_numofseat(int total_numofseat) {
		this.total_numofseat = total_numofseat;
	}

	public String getCinema_id() {
		return cinema_id;
	}

	public void setCinema_id(String cinema_id) {
		this.cinema_id = cinema_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	@Override
	public String toString() {
		return "CINEMA_NAME = " + getCinema_name() + "\nTOTAL_NUMOFSEAT = " + getTotal_numofseat() + "\nCINEMA_ID = "
				+ getCinema_id() + "\nREGION_ID = " + getRegion_id();

	}

}
