package com.mystudy.movieinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mystudy.common.CommonJdbcUtil;

public class DAO {

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.18.27:1521";
	private static final String USER = "MOVIE";
	private static final String PASSWORD = "pw";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	static {
		try {
			Class.forName(DRIVER);

		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] JDBC 드라이버 로딩 실패");
			// e.printStackTrace();
		}
	}

	// 회원가입 (이름, 아이디, 비번, 이메일)
	public void Sign_Up(UserVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			vo.setMembership_id("GEN");

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO CUSTOMER ");
			sql.append(" 	(CUSTOMER_ID, CUSTOMER_NAME, ID, PASSWORD, EMAIL, MEMBERSHIP_ID ) ");
			sql.append(" VALUES((SELECT MAX(CUSTOMER_ID)+1 FROM CUSTOMER), ?, ?, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getCustomer_name());
			pstmt.setString(2, vo.getUser_id());
			pstmt.setString(3, vo.getUser_pw());
			pstmt.setString(4, vo.getUser_email());
			pstmt.setString(5, vo.getMembership_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// customer_id로 회원정보 조회
	public List<UserVO> View_user(UserVO ve) {
		List<UserVO> list = null;

		try {
			conn = CommonJdbcUtil.getConnection();
			if (conn == null)
				return null;

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CUSTOMER_ID, CUSTOMER_NAME, MEMBERSHIP_ID, ID, PASSWORD, EMAIL ");
			sql.append(" FROM CUSTOMER WHERE CUSTOMER_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ve.customer_id);

			rs = pstmt.executeQuery();

			list = new ArrayList<UserVO>();
			while (rs.next()) {
				UserVO vo = new UserVO();
				vo.setCustomer_id(rs.getInt("CUSTOMER_ID"));
				vo.setCustomer_name(rs.getString("CUSTOMER_NAME"));
				vo.setMembership_id(rs.getString("MEMBERSHIP_ID"));
				vo.setUser_id(rs.getString("ID"));
				vo.setUser_pw(rs.getString("PASSWORD"));
				vo.setUser_email(rs.getString("EMAIL"));

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}

		return list;
	}

	// 개인정보 수정
	public void Update(UserVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CUSTOMER ");
			sql.append(" SET CUSTOMER_NAME = ?, ID = ?, PASSWORD = ?, EMAIL = ? ");
			sql.append(" WHERE CUSTOMER_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getCustomer_name());
			pstmt.setString(2, vo.getUser_id());
			pstmt.setString(3, vo.getUser_pw());
			pstmt.setString(4, vo.getUser_email());
			pstmt.setInt(5, vo.getCustomer_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// 개인정보 삭제
	public void Delete(UserVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM CUSTOMER ");
			sql.append(" WHERE CUSTOMER_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, vo.getCustomer_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// 로그인
	public boolean Login(UserVO ve) {

		boolean bool = false;
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, PASSWORD ");
			sql.append(" FROM CUSTOMER ");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			UserVO vo = new UserVO();
			ArrayList<UserVO> list = new ArrayList<UserVO>();

			int k = 0;

			while (rs.next()) {

				vo.setUser_id(rs.getString("ID"));
				vo.setUser_pw(rs.getString("PASSWORD"));

				list.add(vo);

				if (list.get(k).getUser_id().equals(ve.getUser_id())
						&& list.get(k).getUser_pw().equals(ve.getUser_pw())) {
					System.out.println("로그인 성공!");
					bool = true;
				} else {
					continue;
				}
				k++;
			}
			if (bool == false) {
				System.out.println("로그인 실패");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return bool;
	}

	// 고객정보 전달
	public UserVO return_vo(String id) {
		UserVO vo = new UserVO();
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CUSTOMER_ID, CUSTOMER_NAME, MEMBERSHIP_ID, ID, PASSWORD, EMAIL ");
			sql.append(" FROM CUSTOMER WHERE ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				vo.setCustomer_id(rs.getInt("CUSTOMER_ID"));
				vo.setCustomer_name(rs.getString("CUSTOMER_NAME"));
				vo.setCustomer_name(rs.getString("CUSTOMER_NAME"));
				vo.setMembership_id(rs.getString("MEMBERSHIP_ID"));
				vo.setUser_id(rs.getString("id"));
				vo.setUser_pw(rs.getString("PASSWORD"));
				vo.setUser_email(rs.getString("EMAIL"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return vo;

	}

	// 영화 상세 보기
	public List<MovieVO> View_movie(int choice) {
		List<MovieVO> list = null;

		try {
			conn = CommonJdbcUtil.getConnection();
			if (conn == null)
				return null;

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID, MOVIE_NAME, RUNNING_TIME, ");
			sql.append(" ACTORS, OPENDATE, SUMMARY, R.RATING_ID, R.RATING_NAME, COMPANY, ");
			sql.append(" DIRECTOR, AVG_GRADE, " + "(SELECT GENRE_NAME FROM MOVIE M, GENRE G "
					+ "WHERE M.GENRE1_ID = G.GENRE_ID AND MOVIE_ID = ?) GENRE1 ,"
					+ "(SELECT GENRE_NAME FROM MOVIE M, GENRE G "
					+ "WHERE M.GENRE2_ID = G.GENRE_ID AND MOVIE_ID = ?) GENRE2, BOOKING_RATE ");
			sql.append(" FROM MOVIE M, RATING R");
			sql.append(" WHERE M.RATING_ID = R.RATING_ID  AND " + " MOVIE_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, choice);
			pstmt.setInt(2, choice);
			pstmt.setInt(3, choice);

			rs = pstmt.executeQuery();

			list = new ArrayList<MovieVO>();
			while (rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setMovie_id(rs.getInt("MOVIE_ID"));
				vo.setMovie_name(rs.getString("MOVIE_NAME"));
				vo.setRunning_time(rs.getInt("RUNNING_TIME"));
				vo.setActors(rs.getString("ACTORS"));
				vo.setOpendate(rs.getString("OPENDATE"));
				vo.setSummary(rs.getString("SUMMARY"));
				vo.setRating_id(rs.getString("RATING_ID"));
				vo.setRating_name(rs.getString("RATING_NAME"));
				vo.setCompany(rs.getString("COMPANY"));
				vo.setDirector(rs.getString("DIRECTOR"));
				vo.setAvg_grade(rs.getDouble("AVG_GRADE"));
				vo.setGenre1_id(rs.getString("GENRE1"));
				vo.setGenre2_id(rs.getString("GENRE2"));
				vo.setBooking_rate(rs.getDouble("BOOKING_RATE"));

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}

		return list;
	}

	// 영화 목록 조회
	public void View_movie_list() {
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_NAME ");
			sql.append(" FROM MOVIE ");
			sql.append(" ORDER BY MOVIE_ID ");
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			int i = 1;
			while (rs.next()) {
				System.out.println(i + ". " + rs.getString("MOVIE_NAME"));
				i++;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}

	}

	// 영화 확인
	public int confirm_movie_id() {
		int max = 0;
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MAX(MOVIE_ID) MAX ");
			sql.append(" FROM MOVIE ");
			
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			max = rs.getInt("MAX");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return max;	
	}

	// 영화 이름 확인
	public boolean confirm_movie_name(MovieVO vo) {
		Boolean bool = false;
		int num =0;
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) CNT ");
			sql.append(" FROM MOVIE WHERE MOVIE_NAME = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getMovie_name());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				num = rs.getInt("CNT");

			}

			if (num != 0) {
				bool = true;
			} else {
				bool = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return bool;	
	}
	
	// 스크린코드 확인
	public boolean confirm_movieprice_id(String id) {
		Boolean bool = false;
		int num =0;
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) CNT ");
			sql.append(" FROM MOVIEPRICE WHERE MOVIEPRICE_ID = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				num = rs.getInt("CNT");

			}

			if (num != 0) {
				bool = true;
			} else {
				bool = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return bool;	
	}
	
	// 시네마번호 확인
	public boolean confirm_cinema_id(String id) {
		Boolean bool = false;
		int num =0;
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) CNT ");
			sql.append(" FROM CINEMA WHERE CINEMA_ID = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				num = rs.getInt("CNT");

			}

			if (num != 0) {
				bool = true;
			} else {
				bool = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return bool;	
	}
		
	// <관리자> 영화 추가
	public void insert_movie(MovieVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO MOVIE ");
			sql.append(" 	(MOVIE_ID, MOVIE_NAME," + " RUNNING_TIME, ACTORS," + " OPENDATE, SUMMARY, RATING_ID,"
					+ " COMPANY, DIRECTOR" + " GENRE1_ID, GENRE2_ID ) ");
			sql.append(" VALUES((SELECT MAX(MOVIE_ID)+1 FROM MOVIE)," + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getMovie_name());
			pstmt.setInt(2, vo.getRunning_time());
			pstmt.setString(3, vo.getActors());
			pstmt.setString(4, vo.getOpendate());
			pstmt.setString(5, vo.getSummary());
			pstmt.setString(6, vo.getRating_id());
			pstmt.setString(7, vo.getCompany());
			pstmt.setString(8, vo.getDirector());
			pstmt.setString(9, vo.getGenre1_id());
			pstmt.setString(10, vo.getGenre2_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// <관리자> 영화 변경
	public void update_movie(MovieVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MOVIE ");
			sql.append(" SET MOVIE_NAME =?," + " RUNNING_TIME =?, ACTORS =?,"
					+ " OPENDATE =?, SUMMARY =?, RATING_ID =?," + " COMPANY =?, DIRECTOR =? "
					+ " GENRE1_ID =?, GENRE2_ID =? " + " WHERE MOVIE_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getMovie_name());
			pstmt.setInt(2, vo.getRunning_time());
			pstmt.setString(3, vo.getActors());
			pstmt.setString(4, vo.getOpendate());
			pstmt.setString(5, vo.getSummary());
			pstmt.setString(6, vo.getRating_id());
			pstmt.setString(7, vo.getCompany());
			pstmt.setString(8, vo.getDirector());
			pstmt.setString(9, vo.getGenre1_id());
			pstmt.setString(10, vo.getGenre2_id());
			pstmt.setInt(11, vo.getMovie_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// <관리자> 영화 삭제
	public void delete_movie(int num) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM MOVIE ");
			sql.append(" WHERE MOVIE_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, num);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// <관리자> 예산 확인
	public List<TicketVO> budget() {
		List<TicketVO> list = null;

		try {
			conn = CommonJdbcUtil.getConnection();
			if (conn == null)
				return null;

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT TICKET_DATE, " + "P.PRICE_ID, PRICE_NUM, PAYMENT_METHOD_ID ");
			sql.append(" FROM TICKET T, PRICE P " + "WHERE T.PRICE_ID = P.PRICE_ID " + "ORDER BY TICKET_DATE ");
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			list = new ArrayList<TicketVO>();
			while (rs.next()) {
				TicketVO vo = new TicketVO();
				vo.setTicket_date(rs.getString("TICKET_DATE"));
				vo.setPrice_id(rs.getString("PRICE_ID"));
				vo.setPrice_num(rs.getInt("PRICE_NUM"));
				vo.setPayment_method_id(rs.getString("PAYMENT_METHOD_ID"));

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return list;

	}

	// <관리자> 시간표 추가
	public void insert_timetable(TimeTableVO vo) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TIMETABLE ");
			sql.append(" 	(TIMETABLE_ID, START_TIME, END_TIME," + " CINEMA_ID, MOVIE_ID, MOVIEPRICE_ID) ");
			sql.append(" VALUES((SELECT MAX(TIMETABLE_ID)+1 FROM TIMETABLE)," + " ?, ?, ?, ?, ? )");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setDate(1, new java.sql.Date(vo.getStart_time().getTime()));
			pstmt.setDate(2, new java.sql.Date(vo.getEnd_time().getTime()));
			pstmt.setString(3, vo.getCinema_id());
			pstmt.setInt(4, vo.getMovie_id());
			pstmt.setString(5, vo.getMoviePrice_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// 아이디 찾기
	public void find_id(UserVO vo) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID ");
			sql.append(" FROM CUSTOMER WHERE CUSTOMER_NAME = ? AND EMAIL = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getCustomer_name());
			pstmt.setString(2, vo.getUser_email());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo.setUser_id(rs.getString("ID"));
			}

			if (vo.getUser_id() == null) {
				System.out.println("잘못 입력하셨습니다");
			} else {
				System.out.println("찾은 ID = " + vo.getUser_id());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// 유저 정보 확인
	public boolean confirm_info(UserVO ve) {
		boolean bool = false;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, CUSTOMER_NAME, EMAIL ");
			sql.append(" FROM CUSTOMER ");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			UserVO vo = new UserVO();
			ArrayList<UserVO> list = new ArrayList<UserVO>();

			int k = 0;

			while (rs.next()) {

				vo.setUser_id(rs.getString("ID"));
				vo.setCustomer_name(rs.getString("CUSTOMER_NAME"));
				vo.setUser_email(rs.getString("EMAIL"));

				list.add(vo);

				if (list.get(k).getUser_id().equals(ve.getUser_id())
						&& list.get(k).getCustomer_name().equals(ve.getCustomer_name())
						&& list.get(k).getUser_email().equals(ve.getUser_email())) {

					bool = true;
				} else {
					continue;
				}
				k++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
		return bool;

	}

	// 비밀번호 찾기(변경)
	public void update_pw(UserVO ve) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuilder sql = new StringBuilder();
			sql = new StringBuilder();
			sql.append("UPDATE CUSTOMER ");
			sql.append(" SET PASSWORD = ? WHERE CUSTOMER_NAME = ? AND ID = ? AND EMAIL = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, ve.getUser_pw());
			pstmt.setString(2, ve.getCustomer_name());
			pstmt.setString(3, ve.getUser_id());
			pstmt.setString(4, ve.getUser_email());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

	}

	// -----------------------------------------------------------

	// 고객(customer_id)가 예매했던 티켓 목록 조회
	// selNum이 0이면 모든 티켓을, 1이면 관람예정인 티켓만 조회
	public int selectTicket(int customer_id, int selNum) {
		List<TicketVO> ticketList = new ArrayList<>();
		int ticketCnt = 0;

		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM TICKET ");
			sql.append("WHERE CUSTOMER_ID = ? ");
			sql.append("ORDER BY TICKET_ID");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customer_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TicketVO vo = new TicketVO(rs.getInt("ticket_id"), rs.getInt("timetable_id"), rs.getInt("customer_id"),
						rs.getString("seat_id"), rs.getDate("ticket_date").toString(), rs.getString("price_id"),
						rs.getString("payment_method_id"));
				ticketList.add(vo);
			}

			for (TicketVO ticket : ticketList) {
				// 영화 시작시간(start_time) 조회
				Date start_time = null;

				sql = new StringBuilder();
				sql.append("SELECT START_TIME ");
				sql.append("FROM TIMETABLE ");
				sql.append("WHERE TIMETABLE_ID = ?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, ticket.getTimetable_id());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					start_time = rs.getDate("start_time");
				}

				// 관람예정인 티켓만 조회하는 경우(selNum = 1)
				// 시작시간이(start_time)이 오늘(today)보다 이전인 티켓은 건너뜀(continue)
				Date today = new Date();
				if (selNum == 1 && today.after(start_time)) {
					continue;
				} else {
					ticketCnt++;
					printTicket(ticket);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return ticketCnt;
	}

	// 티켓 출력(예매번호, 예매날짜, 영화제목, 영화시간, 관람장소, 영화종류, 영화가격, 결제방법)
	public void printTicket(TicketVO ticket) {
		int ticket_id = ticket.getTicket_id(); // 예매번호
		String ticket_date = ticket.getTicket_date(); // 예매날짜
		String movie_name = null; // 영화제목
		StringBuilder movie_time = new StringBuilder(); // 영화시간
		StringBuilder cinema_name = new StringBuilder(); // 관람장소
		String movieprice_name = null; // 영화종류
		int price_num = 0; // 영화가격
		String payment_method_name = null; // 결제방법

		// 영화시간표(TimeTableVO)
		try {
			TimeTableVO timeTable = null;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE TIMETABLE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ticket.getTimetable_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				timeTable = new TimeTableVO(rs.getInt("timetable_id"), rs.getDate("start_time"), rs.getDate("end_time"),
						rs.getString("cinema_id"), rs.getInt("movie_id"), rs.getString("movieprice_id"));
			}

			// 영화제목
			sql = new StringBuilder();
			sql.append("SELECT MOVIE_NAME ");
			sql.append("FROM MOVIE ");
			sql.append("WHERE MOVIE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, timeTable.getMovie_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				movie_name = rs.getString("movie_name");
			}

			// 영화시간
			DateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			DateFormat endFormat = new SimpleDateFormat("HH:mm");
			movie_time.append(startFormat.format(timeTable.getStart_time()));
			movie_time.append(" ~ ");
			movie_time.append(endFormat.format(timeTable.getEnd_time()));

			// 관람장소(영화관)
			sql = new StringBuilder();
			sql.append("SELECT CINEMA_NAME ");
			sql.append("FROM CINEMA ");
			sql.append("WHERE CINEMA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, timeTable.getCinema_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cinema_name.append(rs.getString("cinema_name"));
			}

			// 관람장소(좌석)
			sql = new StringBuilder();
			sql.append("SELECT SEAT_NAME ");
			sql.append("FROM SEAT ");
			sql.append("WHERE SEAT_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, ticket.getSeat_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cinema_name.append(" ");
				cinema_name.append(rs.getString("seat_name"));
			}

			// 영화종류
			sql = new StringBuilder();
			sql.append("SELECT MOVIEPRICE_NAME ");
			sql.append("FROM MOVIEPRICE ");
			sql.append("WHERE MOVIEPRICE_ID = ");
			sql.append("(SELECT MOVIEPRICE_ID ");
			sql.append("FROM PRICE ");
			sql.append("WHERE PRICE_ID = ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, ticket.getPrice_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				movieprice_name = rs.getString("movieprice_name");
			}

			// 영화가격
			sql = new StringBuilder();
			sql.append("SELECT PRICE_NUM ");
			sql.append("FROM PRICE ");
			sql.append("WHERE PRICE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, ticket.getPrice_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price_num = rs.getInt("price_num");
			}

			// 결제방법
			sql = new StringBuilder();
			sql.append("SELECT PAYMENT_METHOD_NAME ");
			sql.append("FROM PAYMENT_METHOD ");
			sql.append("WHERE PAYMENT_METHOD_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, ticket.getPayment_method_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				payment_method_name = rs.getString("payment_method_name");
			}

			System.out.println("예매번호: " + ticket_id);
			System.out.println("예매날짜: " + ticket_date);
			System.out.println("영화제목: " + movie_name);
			System.out.println("영화시간: " + movie_time);
			System.out.println("관람장소: " + cinema_name);
			System.out.println("영화종류: " + movieprice_name);
			System.out.println("영화가격: " + price_num + "원");
			System.out.println("결제방법: " + payment_method_name);
			System.out.println("--------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------

	// 영화 목록 조회
	public void showMovies() {
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_NAME ");
			sql.append("FROM MOVIE ");
			sql.append("ORDER BY MOVIE_ID");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			int idx = 1;
			while (rs.next()) {
				System.out.println(idx + ": " + rs.getString("movie_name"));
				idx++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 영화관 목록 조회
	public ArrayList<String> showCinema() {
		ArrayList<String> cinema_ids = new ArrayList<>();
		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CINEMA_ID, CINEMA_NAME ");
			sql.append("FROM CINEMA");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String cinema_id = rs.getString("cinema_id");
				System.out.println(cinema_id + ": " + rs.getString("cinema_name"));
				cinema_ids.add(cinema_id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
		return cinema_ids;
	}

	// 선택한 영화(movie_id)와 영화관(cinema_id)이 포함된 영화 시간표 조회: 영화 시간표 개수 리턴
	public ArrayList<Integer> showTimeTable(int movie_id, String cinema_id) {
		ArrayList<TimeTableVO> timetables = new ArrayList<>();
		ArrayList<Integer> timetable_ids = new ArrayList<>();

		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE MOVIE_ID = ? ");
			sql.append("AND CINEMA_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, movie_id);
			pstmt.setString(2, cinema_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				timetables.add(
						new TimeTableVO(rs.getInt("timetable_id"), rs.getDate("start_time"), rs.getDate("end_time"),
								rs.getString("cinema_id"), rs.getInt("movie_id"), rs.getString("movieprice_id")));
			}
			if (timetables.isEmpty()) {
				System.out.println("해당 조건을 만족하는 영화 시간표가 없습니다");
				System.out.println("--------------------");
			}
			// 영화 시간표 출력
			for (TimeTableVO tt : timetables) {
				timetable_ids.add(tt.getTimetable_id());

				Date start_time = tt.getStart_time();
				Date today = new Date();
				// 시작 시간이 오늘 이후인 영화 시간표만 출력
				if (today.before(start_time)) {
					// 영화시간
					DateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					DateFormat endFormat = new SimpleDateFormat("HH:mm");
					StringBuilder se_time = new StringBuilder();
					se_time.append(startFormat.format(tt.getStart_time()));
					se_time.append(" ~ ");
					se_time.append(endFormat.format(tt.getEnd_time()));

					// 영화종류
					String movieprice_name = null;
					sql = new StringBuilder();
					sql.append("SELECT MOVIEPRICE_NAME ");
					sql.append("FROM MOVIEPRICE ");
					sql.append("WHERE MOVIEPRICE_ID = ?");

					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, tt.getMoviePrice_id());
					rs = pstmt.executeQuery();

					if (rs.next()) {
						movieprice_name = rs.getString("movieprice_name");
					}

					System.out.println("시간표번호: " + tt.getTimetable_id());
					System.out.println("영화시간: " + se_time);
					System.out.println("영화종류: " + movieprice_name);
					System.out.println("--------------------");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

		return timetable_ids;
	}

	// 영화 예매(고객코드, 영화관코드, 시간표코드, 좌석종류, 좌석번호): 티켓코드(ticket_id) 리턴
	public int bookTicket(int customer_id, String cinema_id, int timetable_id, int ticketType, String seatStr) {
		int ticket_id = 0; // 티켓코드
		StringBuilder price_id = new StringBuilder(); // 가격코드 = 영화종류 + '_' + 좌석종류 예) 2D_NO

		seatStr = seatStr.substring(0, 1) + seatStr.substring(2); // 좌석번호에서 '-' 제거 예) A-1 → A1
		String seat_id = cinema_id + "_" + seatStr; // 좌석코드 = 영화관코드 + '_' + 좌석번호 예) N01_A1

		try {
			conn = CommonJdbcUtil.getConnection();

			ArrayList<String> seat_ids = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SEAT_ID ");
			sql.append("FROM SEAT ");
			sql.append("WHERE CINEMA_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, cinema_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				seat_ids.add(rs.getString("seat_id"));
			}

			if (seat_ids.contains(seat_id)) {
				// 청소년은 R18 영화 선택 불가
				// 영화등급 조회
				String rating_id = null;
				sql = new StringBuilder();
				sql.append("SELECT RATING_ID ");
				sql.append("FROM MOVIE ");
				sql.append("WHERE MOVIE_ID = ");
				sql.append("(SELECT MOVIE_ID ");
				sql.append("FROM TIMETABLE ");
				sql.append("WHERE TIMETABLE_ID = ?)");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, timetable_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					rating_id = rs.getString("rating_id");
				}
				// 영화등급이 R18이고 청소년이면 구매 불가
				if (rating_id.equals("18") && ticketType == 2) {
					System.out.println("청소년은 청소년 관람 불가 영화를 관람하실 수 없습니다");
				} else {
					// 영화종류(movieprice_id)
					sql = new StringBuilder();
					sql.append("SELECT MOVIEPRICE_ID ");
					sql.append("FROM TIMETABLE ");
					sql.append("WHERE TIMETABLE_ID = ?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setInt(1, timetable_id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						price_id.append(rs.getString("movieprice_id"));
					}

					// 좌석코드(영화종류 + '_' + 좌석종류)
					switch (ticketType) {
					case 1:
						price_id.append("_NO");
						break;
					case 2:
						price_id.append("_AD");
						break;
					case 3:
						price_id.append("_SE");
						break;
					case 4:
						price_id.append("_SP");
						break;
					}

					// 이미 예매된 좌석인지 확인
					int seat_true_false = 2;
					sql = new StringBuilder();
					sql.append("SELECT SEAT_TRUE_FALSE ");
					sql.append("FROM TIMETABLE_SEAT ");
					sql.append("WHERE SEAT_ID = ? ");
					sql.append("AND TIMETABLE_ID = ? ");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, seat_id);
					pstmt.setInt(2, timetable_id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						seat_true_false = rs.getInt("seat_true_false");
					}

					if (seat_true_false != 1) {
						// 영화티켓 예매(INSERT)
						sql = new StringBuilder();
						sql.append("INSERT INTO TICKET ");
						sql.append("(TICKET_ID, TIMETABLE_ID, CUSTOMER_ID, SEAT_ID, TICKET_DATE, PRICE_ID) ");
						sql.append("VALUES ((SELECT MAX(TICKET_ID) + 1 FROM TICKET), ?, ?, ?, ?, ?)");
						pstmt = conn.prepareStatement(sql.toString());
						long ticket_date = new Date().getTime(); // 현재시간
						pstmt.setInt(1, timetable_id);
						pstmt.setInt(2, customer_id);
						pstmt.setString(3, seat_id);
						pstmt.setDate(4, new java.sql.Date(ticket_date));
						pstmt.setString(5, price_id.toString());
						pstmt.executeUpdate();
						System.out.println("예매 완료");

						// 좌석 예매 여부 변경(SEAT_TRUE_FALSE = 1)
						// seat_true_false = 2이면 INSERT
						if (seat_true_false == 2) {
							sql = new StringBuilder();
							sql.append("INSERT INTO TIMETABLE_SEAT ");
							sql.append("(TIMETABLE_SEAT_ID, TIMETABLE_ID, SEAT_ID) ");
							sql.append("VALUES ((SELECT MAX(TIMETABLE_SEAT_ID) + 1 FROM TIMETABLE_SEAT), ");
							sql.append("?, ?) ");
							pstmt = conn.prepareStatement(sql.toString());
							pstmt.setInt(1, timetable_id);
							pstmt.setString(2, seat_id);
							pstmt.executeUpdate();
						} else { // seat_true_false = 0이면 UPDATE
							sql = new StringBuilder();
							sql.append("UPDATE TIMETABLE_SEAT ");
							sql.append("SET SEAT_TRUE_FALSE = 1 ");
							sql.append("WHERE SEAT_ID = ? ");
							sql.append("AND TIMETABLE_ID = ? ");
							pstmt = conn.prepareStatement(sql.toString());
							pstmt.setString(1, seat_id);
							pstmt.setInt(2, timetable_id);
							pstmt.executeUpdate();
						}

						// 티켓코드(ticket_id)
						sql = new StringBuilder();
						sql.append("SELECT MAX(TICKET_ID) FROM TICKET");
						pstmt = conn.prepareStatement(sql.toString());
						rs = pstmt.executeQuery();
						if (rs.next()) {
							ticket_id = rs.getInt("MAX(TICKET_ID)");
						}

						// 구매횟수 확인해서 멤버십 갱신
						// 구매횟수 확인
						int ticketCnt = 0;
						sql = new StringBuilder();
						sql.append("SELECT COUNT(*) ");
						sql.append("FROM TICKET ");
						sql.append("WHERE CUSTOMER_ID = ?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setInt(1, customer_id);
						rs = pstmt.executeQuery();
						if (rs.next()) {
							ticketCnt = rs.getInt("count(*)");
						}

						// 멤버십 갱신
						if (ticketCnt >= 10) {
							sql = new StringBuilder();
							sql.append("UPDATE CUSTOMER ");
							sql.append("SET MEMBERSHIP_ID = 'VIP' ");
							sql.append("WHERE CUSTOMER_ID = ?");
							pstmt = conn.prepareStatement(sql.toString());
							pstmt.setInt(1, customer_id);
							pstmt.executeUpdate();
						}

						updateBookingRate(ticket_id); // 예매율 변경
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

		return ticket_id;
	}
	
	// 총 결제금액(고객코드, 티켓코드배열)
	public int getTotalPrice(int customer_id, int[] ticket_ids) {
		int totalPrice = 0;
		try {
			conn = CommonJdbcUtil.getConnection();

			// 바인드변수 만들기
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			sb.append(ticket_ids[0]);
			for (int i = 1; i < ticket_ids.length; i++) {
				sb.append(", ");
				sb.append(ticket_ids[i]);
			}
			sb.append(")");

			// 결제금액 더해서 총결제금액 구하기
			// 가격코드(price_id) 구하기
			ArrayList<String> price_ids = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PRICE_ID ");
			sql.append("FROM TICKET ");
			sql.append("WHERE TICKET_ID IN " + sb.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				price_ids.add(rs.getString("price_id"));
			}

			// 가격(price_num) 구하기
			for (String price_id : price_ids) {
				sql = new StringBuilder();
				sql.append("SELECT PRICE_NUM ");
				sql.append("FROM PRICE ");
				sql.append("WHERE PRICE_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, price_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					totalPrice += rs.getInt("price_num");
				}
			}

			totalPrice = getDiscount(customer_id, totalPrice);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

		return totalPrice;
	}

	// 멤버십 할인 적용
	public int getDiscount(int customer_id, int totalPrice) {
		String membership_id = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT MEMBERSHIP_ID ");
			sql.append("FROM CUSTOMER ");
			sql.append("WHERE CUSTOMER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customer_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				membership_id = rs.getString("membership_id");
			}

			sql = new StringBuilder();
			sql.append("SELECT DISCOUNT_RATE ");
			sql.append("FROM MEMBERSHIP ");
			sql.append("WHERE MEMBERSHIP_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, membership_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int discount_rate = rs.getInt("discount_rate");
				int discountPrice = totalPrice;
				if (membership_id.equals("VIP")) {
					totalPrice *= (100 - discount_rate) / 100.0;
					discountPrice -= totalPrice;
					System.out.println("고객님은 VIP 등급으로 " + discount_rate + "% 할인이 적용되어 " + discountPrice + "원이 할인되었습니다");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
		return totalPrice;
	}

	// 결제방법 선택(고객코드, 티켓코드배열, 결제방법)
	public void setPaymentMethod(int customer_id, int[] ticket_ids, int paymentType) {
		String payment_method_id = null;

		// 결제방법코드
		switch (paymentType) {
		case 1:
			payment_method_id = "CARD";
			break;
		case 2:
			payment_method_id = "CASH";
			break;
		case 3:
			payment_method_id = "PHONE";
			break;
		}

		try {
			conn = CommonJdbcUtil.getConnection();

			// 바인드변수 만들기
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			sb.append(ticket_ids[0]);
			for (int i = 1; i < ticket_ids.length; i++) {
				sb.append(", ");
				sb.append(ticket_ids[i]);
			}
			sb.append(")");

			// 결제방법 선택
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE TICKET ");
			sql.append("SET PAYMENT_METHOD_ID = ?");
			sql.append(" WHERE TICKET_ID IN " + sb.toString());
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, payment_method_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// -------------------------------------------------------------

	// 티켓(ticket_id)과 같은 영화(movie_id)와 영화관(cinema_id)이 포함된 영화시간표 출력
	public void showTime(int customer_id, int ticket_id) {
		int movie_id = 0;
		String cinema_id = null;

		try {
			conn = CommonJdbcUtil.getConnection();

			// movie_id, cinema_id 조회
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID, CINEMA_ID ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE TIMETABLE_ID = ");
			sql.append("(SELECT TIMETABLE_ID ");
			sql.append("FROM TICKET ");
			sql.append("WHERE TICKET_ID = ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ticket_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				movie_id = rs.getInt("movie_id");
				cinema_id = rs.getString("cinema_id");
			}

			showTimeTable(movie_id, cinema_id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 티켓코드(ticket_id)에 있는 영화관의 영화 좌석 조회
		public void showTicketSeat(int ticket_id) {
			int timetable_id = 0;

			try {
				conn = CommonJdbcUtil.getConnection();

				// 시간표코드(timetable_id) 조회
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT TIMETABLE_ID ");
				sql.append("FROM TICKET ");
				sql.append("WHERE TICKET_ID = ?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, ticket_id);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					timetable_id = rs.getInt("timetable_id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJdbcUtil.close(pstmt, conn);
			}

			showSeat(timetable_id);
		}


		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// 결제좌석 변경(고객코드, 티켓코드, 좌석번호)
		public void updateSeat(int customer_id, int ticket_id, String seatStr) {
			String seat_id = null;
			int timetable_id = 0;
			try {
				conn = CommonJdbcUtil.getConnection();

				// 좌석코드(seat_id) 조회
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT SEAT_ID, TIMETABLE_ID ");
				sql.append("FROM TICKET ");
				sql.append("WHERE TICKET_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, ticket_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					seat_id = rs.getString("seat_id");
					timetable_id = rs.getInt("timetable_id");
				}

				// 해당 좌석코드의 예매 여부 변경(SEAT_TRUE_FALSE = 0)
				sql = new StringBuilder();
				sql.append("UPDATE TIMETABLE_SEAT ");
				sql.append("SET SEAT_TRUE_FALSE = 0 ");
				sql.append("WHERE SEAT_ID = ? ");
				sql.append("AND TIMETABLE_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, seat_id);
				pstmt.setInt(1, timetable_id);
				pstmt.executeUpdate();

				// 좌석번호(seatStr)를 좌석코드(seat_id) 형식으로 변환
				seatStr = seatStr.substring(0, 1) + seatStr.substring(2);
				int len = seat_id.length();
				seat_id = seat_id.substring(0, len - 2) + seatStr;

				// 티켓의 좌석코드 변경
				sql = new StringBuilder();
				sql.append("UPDATE TICKET ");
				sql.append("SET SEAT_ID = ? ");
				sql.append("WHERE TICKET_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, seat_id);
				pstmt.setInt(2, ticket_id);
				pstmt.executeUpdate();

				// 바꾼 좌석의 예매 여부 변경(SEAT_TRUE_FALSE = 1)
				boolean seatBool = false;
				sql = new StringBuilder();
				sql.append("SELECT * ");
				sql.append("FROM TIMETABLE_SEAT ");
				sql.append("WHERE SEAT_ID = ? ");
				sql.append("AND TIMETABLE_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, seat_id);
				pstmt.setInt(2, timetable_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					seatBool = true;
				}

				// 좌석 존재하면 UPDATE
				if (seatBool) {
					sql = new StringBuilder();
					sql.append("UPDATE TIMETABLE_SEAT ");
					sql.append("SET SEAT_TRUE_FALSE = 1 ");
					sql.append("WHERE SEAT_ID = ? ");
					sql.append("AND TIMETABLE_ID = ? ");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, seat_id);
					pstmt.setInt(2, timetable_id);
					pstmt.executeUpdate();
				} else { // 좌석 없으면 INSERT
					sql.append("INSERT INTO TIMETABLE_SEAT ");
					sql.append("(TIMETABLE_SEAT_ID, TIMETABLE_ID, SEAT_ID) ");
					sql.append("VALUES (SELECT MAX(TIMETABLE_SEAT_ID) + 1 FROM TIMETABLE_SEAT, ");
					sql.append("?, ?) ");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setInt(1, timetable_id);
					pstmt.setString(2, seat_id);
					pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJdbcUtil.close(pstmt, conn);
			}
		}


	// 영화 좌석 조회(영화관코드, 시간표코드)
	public void showSeat(int timetable_id) {
			String cinema_id = null;

			try {
				conn = CommonJdbcUtil.getConnection();

				// 시네마코드(cinema_id) 조회
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT CINEMA_ID ");
				sql.append("FROM TIMETABLE ");
				sql.append("WHERE TIMETABLE_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, timetable_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					cinema_id = rs.getString("cinema_id");
				}

				// 좌석 배열(seats) 만들기
				sql = new StringBuilder();
				sql.append("SELECT TOTAL_NUMOFSEAT ");
				sql.append("FROM CINEMA ");
				sql.append("WHERE CINEMA_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, cinema_id);
				rs = pstmt.executeQuery();
				int[][] seats = null;
				if (rs.next()) {
					seats = new int[rs.getInt("total_numofseat") / 10 + 1][10 + 1];
				}

				// 좌석 예약 여부 확인
				// 비어있는 좌석: seat_true_false = 0 && 배열값: 0
				// 예약된 좌석: seat_true_false = 1 && 배열값: 1
				sql = new StringBuilder();
				sql.append("SELECT ROWW, COL, SEAT_TRUE_FALSE ");
				sql.append("FROM SEAT s, TIMETABLE_SEAT ts ");
				sql.append("WHERE s.SEAT_ID = ts.SEAT_ID ");
				sql.append("AND ts.TIMETABLE_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, timetable_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					if (rs.getInt("seat_true_false") == 1) {
						seats[rs.getInt("roww")][rs.getInt("col")] = 1;
					}
				}

				// 좌석 출력
				System.out.println("  1 2 3 4 5 6 7 8 9 10"); // 행
				for (int i = 1; i < seats.length; i++) {
					for (int j = 1; j < seats[i].length; j++) {
						if (j == 1)
							System.out.print((char) (i + 64) + " "); // 열
						if (seats[i][j] == 1)
							System.out.print("■ "); // 예약된 좌석: ■
						else
							System.out.print("□ "); // 비어있는 좌석: □
					}
					System.out.println();
				}
			} catch (

			SQLException e) {
				e.printStackTrace();
			} finally {
				// 클로징 처리에 의한 자원 반납
				CommonJdbcUtil.close(pstmt, conn);
			}
		}

	// 영화시간 변경: 추가 결제금액 리턴
	public int updateTime(int customer_id, int ticket_id, int timetable_id, String seatStr) {
		int addPay = 0;
		String old_price_id = null;
		String new_price_id = null;

		try {
			conn = CommonJdbcUtil.getConnection();

			// 예전 영화종류 조회
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PRICE_ID ");
			sql.append("FROM TICKET ");
			sql.append("WHERE TICKET_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ticket_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				old_price_id = rs.getString("price_id");
			}

			// 바뀐 영화종류 조회
			sql = new StringBuilder();
			sql.append("SELECT MOVIEPRICE_ID ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE TIMETABLE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, timetable_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				new_price_id = rs.getString("movieprice_id");
			}
			int len = old_price_id.length();
			new_price_id = new_price_id + old_price_id.substring(len - 3);

			// 티켓의 영화시간표, 예매시간, 영화종류 변경
			sql = new StringBuilder();
			sql.append("UPDATE TICKET ");
			sql.append("SET TIMETABLE_ID = ?");
			sql.append(", TICKET_DATE = ?");
			sql.append(", PRICE_ID = ?");
			sql.append("WHERE TICKET_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			long ticket_date = new Date().getTime();
			pstmt.setInt(1, timetable_id);
			pstmt.setDate(2, new java.sql.Date(ticket_date));
			pstmt.setString(3, new_price_id);
			pstmt.setInt(4, ticket_id);
			pstmt.executeUpdate();

			// 영화종류(price_id) 달라진 경우 추가 결제
			int old_price = 0; // 바뀐 영화가격
			int new_price = 0; // 예전 영화가격

			if (!old_price_id.equals(new_price_id)) {
				// 바뀐 영화가격(price_num) 조회
				sql = new StringBuilder();
				sql.append("SELECT PRICE_NUM ");
				sql.append("FROM PRICE ");
				sql.append("WHERE PRICE_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, new_price_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					new_price = rs.getInt("price_num");
					new_price = getDiscount(customer_id, new_price);
				}

				// 예전 영화가격(price_num) 조회
				sql = new StringBuilder();
				sql.append("SELECT PRICE_NUM ");
				sql.append("FROM PRICE ");
				sql.append("WHERE PRICE_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, old_price_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					old_price = rs.getInt("price_num");
					old_price = getDiscount(customer_id, old_price);
				}

				addPay = new_price - old_price;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}

		// 좌석변경
		updateSeat(customer_id, ticket_id, seatStr);

		return addPay;
	}

	

	// 결제방법 변경(고객코드, 티켓코드, 결제방법)
	public void updatePayment(int customer_id, int ticket_id, int paymentType) {
		int[] ticket_ids = new int[1];
		ticket_ids[0] = ticket_id;
		setPaymentMethod(customer_id, ticket_ids, paymentType);
		System.out.println("결제방법 변경 완료");
	}

	// -------------------------------------------------------------
	// 티켓 예매 취소(고객코드, 티켓코드)
	public void deleteTicket(int customer_id, int ticket_id) {
			String seat_id = null; // 좌석코드
			int timetable_id = 0;

			try {
				conn = CommonJdbcUtil.getConnection();

				// 좌석코드 조회
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT SEAT_ID, TIMETABLE_ID ");
				sql.append("FROM TICKET ");
				sql.append("WHERE TICKET_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, ticket_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					seat_id = rs.getString("seat_id");
					timetable_id = rs.getInt("timetable_id");
				}

				// 해당 좌석코드(seat_id)의 예약 여부 변경(SEAT_TRUE_FALSE = 0)
				sql = new StringBuilder();
				sql.append("UPDATE TIMETABLE_SEAT ");
				sql.append("SET SEAT_TRUE_FALSE = 0 ");
				sql.append("WHERE SEAT_ID = ? ");
				sql.append("AND TIMETABLE_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, seat_id);
				pstmt.setInt(2, timetable_id);
				pstmt.executeUpdate();

				// 티켓 삭제
				sql = new StringBuilder();
				sql.append("DELETE FROM TICKET ");
				sql.append("WHERE TICKET_ID = ? ");
				sql.append("AND CUSTOMER_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, ticket_id);
				pstmt.setInt(2, customer_id);
				pstmt.executeUpdate();
				System.out.println("예매 취소 완료");

				// 구매횟수 확인해서 멤버십 갱신
				// 구매횟수 확인
				int ticketCnt = 0;
				sql = new StringBuilder();
				sql.append("SELECT COUNT(*) ");
				sql.append("FROM TICKET ");
				sql.append("WHERE CUSTOMER_ID = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, customer_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					ticketCnt = rs.getInt("count(*)");
				}

				// 멤버십 갱신
				if (ticketCnt < 10) {
					sql = new StringBuilder();
					sql.append("UPDATE CUSTOMER ");
					sql.append("SET MEMBERSHIP_ID = 'GEN' ");
					sql.append("WHERE CUSTOMER_ID = ?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setInt(1, customer_id);
					pstmt.executeUpdate();
				}

				updateBookingRate(ticket_id); // 예매율 변경
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJdbcUtil.close(pstmt, conn);
			}
		}

	// 예매율 변경
	public void updateBookingRate(int ticket_id) {
		int movie_id = 0;
		try {
			conn = CommonJdbcUtil.getConnection();

			// 영화코드 조회
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE TIMETABLE_ID = ");
			sql.append("(SELECT TIMETABLE_ID ");
			sql.append("FROM TICKET ");
			sql.append("WHERE TICKET_ID = ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ticket_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				movie_id = rs.getInt("movie_id");
			}

			// 해당 영화를 관람한 티켓수 조회
			int booking_rate = 0;
			sql = new StringBuilder();
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM TICKET ");
			sql.append("WHERE TIMETABLE_ID IN ");
			sql.append("(SELECT TIMETABLE_ID ");
			sql.append("FROM TIMETABLE ");
			sql.append("WHERE MOVIE_ID = ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, movie_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				booking_rate = rs.getInt("count(*)");
			}

			// 예매율 변경
			sql = new StringBuilder();
			sql.append("UPDATE MOVIE ");
			sql.append("SET BOOKING_RATE = ? ");
			sql.append("WHERE MOVIE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setDouble(1, booking_rate);
			pstmt.setInt(2, movie_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 고객(customer_id)이 등록한 평점 목록 조회
	public int selectGrade(int customer_id) {
		List<GradeVO> gradeList = null;
		// 평점 목록 개수 확인
		int gradeCnt = 0;

		try {
			conn = CommonJdbcUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM GRADE ");
			sql.append("WHERE CUSTOMER_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customer_id);
			rs = pstmt.executeQuery();

			gradeList = new ArrayList<>();
			while (rs.next()) {
				GradeVO vo = new GradeVO(rs.getInt("grade_id"), rs.getInt("movie_id"), rs.getInt("customer_id"),
						rs.getDouble("grade"));
				gradeList.add(vo);
				gradeCnt++;
			}

			// 평점 목록이 없는 경우
			if (gradeCnt == 0) {
				System.out.println("평점 정보가 존재하지 않습니다");
			} else {
				System.out.println("영화\t\t평점");
				System.out.println("--------------------");
				for (GradeVO grade : gradeList) {
					sql = new StringBuilder();
					sql.append("SELECT MOVIE_NAME ");
					sql.append("FROM MOVIE ");
					sql.append("WHERE MOVIE_ID = ?");
					pstmt = conn.prepareStatement(sql.toString());

					pstmt.setInt(1, grade.getMovie_id());
					rs = pstmt.executeQuery();

					if (rs.next()) {
						String movie_name = rs.getString("movie_name");
						if (movie_name.length() < 4)
							System.out.println(movie_name + "\t\t" + grade.getGrade());
						else
							System.out.println(movie_name + "\t" + grade.getGrade());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(rs, pstmt, conn);
		}
		return gradeCnt;

	}

	// 전체 영화 목록 조회
	public ArrayList<Integer> showMovie() {
			ArrayList<Integer> movie_ids = new ArrayList<>();
			try {
				conn = CommonJdbcUtil.getConnection();

				StringBuilder sql = new StringBuilder();
				sql.append("SELECT MOVIE_NAME, MOVIE_ID ");
				sql.append("FROM MOVIE ");
				sql.append("ORDER BY MOVIE_ID");

				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();

				int idx = 1;
				while (rs.next()) {
					System.out.println(idx + ": " + rs.getString("movie_name"));
					movie_ids.add(rs.getInt("movie_id"));
					idx++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJdbcUtil.close(pstmt, conn);
			}
			return movie_ids;
		}

	// 고객(customer_id)이 입력한 영화(movie_id)의 평점(grade) 추가
	public void insertGrade(int customer_id, int movie_id, double grade) {
		try {
			conn = CommonJdbcUtil.getConnection();

			// 고객이 평점을 입력했던 영화 목록 조회
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID ");
			sql.append("FROM GRADE ");
			sql.append("WHERE CUSTOMER_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customer_id);
			rs = pstmt.executeQuery();

			ArrayList<Integer> movie_ids = new ArrayList<>();
			while (rs.next()) {
				movie_ids.add(rs.getInt("movie_id"));
			}

			// 추가하려는 영화가 기존 목록과 중복되는지 확인
			if (movie_ids.contains(movie_id)) {
				System.out.println("[오류] 이미 등록된 영화입니다");
			} else {
				// 중복 아니면 평점 추가
				sql = new StringBuilder();
				sql.append("INSERT INTO GRADE ");
				sql.append("(GRADE_ID, MOVIE_ID, CUSTOMER_ID, GRADE) ");
				sql.append("VALUES ((SELECT MAX(GRADE_ID) + 1 FROM GRADE), ?, ?, ?)");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, movie_id);
				pstmt.setInt(2, customer_id);
				pstmt.setDouble(3, grade);
				pstmt.executeUpdate();
				System.out.println("평점 추가 완료");

				editGradeAvg(movie_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 평균평점 수정
	private void editGradeAvg(int movie_id) {
		int gradeCnt = 0; // 평점 개수
		double gradeSum = 0; // 평점합
		try {
			// 평점 개수, 평점합 구하기
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*), SUM(GRADE) ");
			sql.append("FROM GRADE ");
			sql.append("WHERE MOVIE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, movie_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				gradeCnt = rs.getInt("count(*)");
				gradeSum = rs.getDouble("sum(grade)");
			}

			// 평균 구해서 movie 테이블 수정
			double gradeAvg = gradeSum / gradeCnt;
			sql = new StringBuilder();
			sql.append("UPDATE MOVIE ");
			sql.append("SET AVG_GRADE = ? ");
			sql.append("WHERE MOVIE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setDouble(1, gradeAvg);
			pstmt.setInt(2, movie_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 영화이름(movie_name)을 영화코드(movie_id)로 변경
	public int nameToId(String movie_name) {
		int movie_id = 0;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID ");
			sql.append("FROM MOVIE ");
			sql.append("WHERE MOVIE_NAME = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, movie_name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				movie_id = rs.getInt("movie_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movie_id;
	}

	// 고객(customer_id)이 등록한 영화(movie_name)의 평점을 새로운 평점(newGrade)으로 수정
	public void updateGrade(int customer_id, String movie_name, double newGrade) {
		try {
			conn = CommonJdbcUtil.getConnection();

			int movie_id = nameToId(movie_name);

			// 영화 평점 수정
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE GRADE ");
			sql.append("SET GRADE = ?");
			sql.append("WHERE MOVIE_ID = ? ");
			sql.append("AND CUSTOMER_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setDouble(1, newGrade);
			pstmt.setInt(2, movie_id);
			pstmt.setInt(3, customer_id);
			int result = pstmt.executeUpdate();

			// 평점이 등록된 영화인지 확인
			if (result == 0) {
				System.out.println("[오류] 평점이 등록되지 않은 영화입니다");
			} else {
				System.out.println("평점 수정 완료");
				editGradeAvg(movie_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 고객(customer_id)이 등록한 영화(movie_name)의 평점 삭제
	public void deleteGrade(int customer_id, String movie_name) {
		try {
			conn = CommonJdbcUtil.getConnection();

			int movie_id = nameToId(movie_name);

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM GRADE ");
			sql.append("WHERE CUSTOMER_ID = ? ");
			sql.append("AND MOVIE_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customer_id);
			pstmt.setInt(2, movie_id);
			int result = pstmt.executeUpdate();

			// 평점이 등록된 영화인지 확인
			if (result == 0) {
				System.out.println("[오류] 평점이 등록되지 않은 영화입니다");
			} else {
				System.out.println("평점 삭제 완료");
				editGradeAvg(movie_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

	// 영화 예매율 표기
	public void printBookingRate(MovieVO ve) {
		try {
			conn = CommonJdbcUtil.getConnection();

			int allCnt = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) ");
			sql.append(" FROM TICKET");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				allCnt = rs.getInt("count(*)");
			}

			sql = new StringBuilder();
			sql.append("SELECT BOOKING_RATE ");
			sql.append(" FROM MOVIE ");
			sql.append(" WHERE MOVIE_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ve.getMovie_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("예매율 = " + rs.getInt("booking_rate") * 1000 / allCnt / 10.0 + "%");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJdbcUtil.close(pstmt, conn);
		}
	}

}
