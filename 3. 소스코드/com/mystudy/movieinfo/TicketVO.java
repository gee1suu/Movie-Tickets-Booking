package com.mystudy.movieinfo;
public class TicketVO {
	private int ticket_id;
	private int timetable_id;
	private int customer_id;
	private String seat_id;
	private String ticket_date;
	private String price_id;
	private String payment_method_id;
	private int price_num;

	public TicketVO() {

	}

	public TicketVO(int ticket_id, int timetable_id, int customer_id, String seat_id, String ticket_date,
			String price_id, String payment_method_id) {
		this.ticket_id = ticket_id;
		this.timetable_id = timetable_id;
		this.customer_id = customer_id;
		this.seat_id = seat_id;
		this.ticket_date = ticket_date;
		this.price_id = price_id;
		this.payment_method_id = payment_method_id;
	}

	public int getPrice_num() {
		return price_num;
	}

	public void setPrice_num(int price_num) {
		this.price_num = price_num;
	}

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public int getTimetable_id() {
		return timetable_id;
	}

	public void setTimetable_id(int timetable_id) {
		this.timetable_id = timetable_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}

	public String getTicket_date() {
		return ticket_date;
	}

	public void setTicket_date(String ticket_date) {
		this.ticket_date = ticket_date;
	}

	public String getPrice_id() {
		return price_id;
	}

	public void setPrice_id(String price_id) {
		this.price_id = price_id;
	}

	public String getPayment_method_id() {
		return payment_method_id;
	}

	public void setPayment_method_id(String payment_method_id) {
		this.payment_method_id = payment_method_id;
	}

}
