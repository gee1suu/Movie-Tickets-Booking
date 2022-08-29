package com.mystudy.movieinfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DAOtest {
	static String name;
	static String id;
	static String pw;
	static String email;
	static Integer choice;
	static int customer_id;
	static Scanner sc = new Scanner(System.in);
	static DAO dao = new DAO();
	static UserVO vo = new UserVO();

	public static void main(String[] args) {
		showmenu();
	}

	// 시작 메뉴
	public static void showmenu() {

		System.out.println("--------------------");
		System.out.println("영화예매 시스템");
		System.out.println("--------------------");
		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");
		System.out.println("3. 아이디 찾기");
		System.out.println("4. 비밀번호 찾기");
		System.out.println("0. 종료하기");
		System.out.println("--------------------");
		System.out.print("메뉴 번호 입력: ");
		try {
			choice = Integer.parseInt(sc.nextLine());
			choose(choice);
		} catch (NumberFormatException e) {
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			if (vo.customer_id == 1) {
				show_manager_menu();
			} else {
				showmenu();
			}
		}

	}

	// 회원 로그인 후 메뉴
	public static void show_login_menu() {
		System.out.println("\n--------------------");
		System.out.println("로그인 선택화면");
		System.out.println("--------------------");
		System.out.println("1. 예매하기");
		System.out.println("2. 예매 가능한 영화정보");
		System.out.println("3. 회원정보");
		System.out.println("4. 평점");
		System.out.println("0. 종료하기");
		System.out.println("--------------------");
		System.out.print("메뉴 번호 입력: ");
		try {
			choice = Integer.parseInt(sc.nextLine());
			choose_login_menu(choice);
		} catch (NumberFormatException e) {
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			show_login_menu();
		}

	}

	// 시작 메뉴 선택
	public static void choose(int choice) {

		switch (choice) {
		case 0:
			System.out.println("시스템을 종료합니다");
			break;
		case 1:
			signup();
			break;
		case 2:
			login();
			break;
		case 3:
			find_id();
			break;
		case 4:
			update_pw();
			break;
		default:
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			showmenu();
			break;
		}

	}

	// 비밀번호 찾기
	private static void update_pw() {
		System.out.print("사용자의 아이디를 입력해주세요");
		id = sc.nextLine();
		System.out.print("사용자의 이름을 입력해주세요");
		name = sc.nextLine();
		System.out.print("사용자의 이메일을 입력해주세요");
		email = sc.nextLine();
		boolean bool = false;

		vo = new UserVO();
		vo.setUser_id(id);
		vo.setCustomer_name(name);
		vo.setUser_email(email);

		bool = dao.confirm_info(vo);

		if (bool == true) {
			System.out.print("변경할 비밀번호를 입력해주세요");
			pw = sc.nextLine();

			vo.setUser_pw(pw);
			dao.update_pw(vo);
			System.out.println("비밀번호가 재설정되었습니다");

		} else {
			System.out.println("잘못된 값을 입력하셨습니다");
		}

		showmenu();

	}

	// 아이디 찾기
	private static void find_id() {
		System.out.print("사용자의 이름을 입력해주세요");
		name = sc.nextLine();
		System.out.print("사용자의 이메일을 입력해주세요");
		email = sc.nextLine();

		vo = new UserVO();
		vo.setCustomer_name(name);
		vo.setUser_email(email);
		dao.find_id(vo);
		showmenu();

	}

	// 회원 로그인 후 메뉴 선택
	static void choose_login_menu(int choice) {

		switch (choice) {
		case 0:
			System.out.println("시스템을 종료합니다");
			break;
		case 1:
			int menuNumber = 0;

			System.out.println("--------------------");
			do {
				System.out.println("1. 예매목록조회");
				System.out.println("2. 예매");
				System.out.println("3. 예매변경");
				System.out.println("4. 예매취소");
				System.out.println("0. 뒤로가기");
				System.out.println("--------------------");
				System.out.print("메뉴 번호 입력: ");

				menuNumber = Integer.parseInt(sc.nextLine());

				switch (menuNumber) {
				case 1:
					selectTicket();
					break;
				case 2:
					insertTicket();
					break;
				case 3:
					updateTicket();
					break;
				case 4:
					deleteTicket();
					break;
				case 0:
					show_login_menu();
					break;
				default:
					System.out.println("--------------------");
					System.out.println("잘못된 입력값입니다");
					System.out.println("--------------------");
					show_login_menu();
					break;
				}
			} while (menuNumber != 0);
			break;
		case 2:

			dao.View_movie_list();
			System.out.println("--------------------");
			System.out.print("정보를 조회할 영화 번호 입력: ");
			choice = Integer.parseInt(sc.nextLine());
			int max =dao.confirm_movie_id();
			if (1<=choice && choice <= max) {
			
			
			MovieVO vo = new MovieVO();
			vo.setMovie_id(choice);
			
			List<MovieVO> list = null;
			list = dao.View_movie(choice);

			for (MovieVO ve : list) {
				System.out.println("--------------------");
				System.out.println(ve);
				dao.printBookingRate(ve);
			}
			} else {
				System.out.println("--------------------");
				System.out.println("잘못된 입력값입니다");
				System.out.println("--------------------");
			}
			show_login_menu();
			break;

		case 3:
			System.out.println("--------------------");
			System.out.println("1. 회원정보 조회");
			System.out.println("2. 회원정보 수정");
			System.out.println("3. 회원탈퇴");
			System.out.println("0. 뒤로가기");
			System.out.println("--------------------");
			System.out.print("메뉴 번호 입력: ");
			choice = Integer.parseInt(sc.nextLine());
			if (choice == 0) {
				show_login_menu();
				break;
			} else if (choice == 1) {
				show_user_info();
			} else if (choice == 2) {
				update_user_info();
			} else if (choice == 3) {
				delete_user_info();
			} else {
				System.out.println("--------------------");
				System.out.println("잘못된 입력값입니다");
				System.out.println("--------------------");
				show_login_menu();
			}
			while (choice != 0 && choice != 3)
				;
			break;
		case 4:
			int gradeMenuNum = 0;

			System.out.println("--------------------");
			do {
				System.out.println("1. 평점조회");
				System.out.println("2. 평점등록");
				System.out.println("3. 평점수정");
				System.out.println("4. 평점삭제");
				System.out.println("0. 뒤로가기");
				System.out.println("--------------------");
				System.out.print("메뉴 번호 입력: ");
				gradeMenuNum = Integer.parseInt(sc.nextLine());
				switch (gradeMenuNum) {
				case 1:
					selectGrade();
					break;
				case 2:
					insertGrade();
					break;
				case 3:
					updateGrade();
					break;
				case 4:
					deleteGrade();
					break;
				case 0:
					show_login_menu();
					break;
				default:
					System.out.println("--------------------");
					System.out.println("잘못된 입력값입니다");
					System.out.println("--------------------");
					show_login_menu();
					break;
				}
			} while (gradeMenuNum != 0);
			break;
		default:
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			show_login_menu();
			break;
		}

	}

	// 회원가입
	public static void signup() {

		System.out.println("회원정보를 입력합니다");
		System.out.println("--------------------");
		System.out.print("사용자의 이름을 입력: ");

		name = sc.nextLine();
		System.out.print("사용자의 id를 입력: ");

		id = sc.nextLine();
		System.out.print("사용자의 pw를 입력:");

		pw = sc.nextLine();
		System.out.print("사용자의 email을 입력:");

		email = sc.nextLine();
		UserVO vo = new UserVO(name, id, pw, email);
		dao.Sign_Up(vo);
		System.out.println("회원가입 완료!");
		showmenu();

	}

	// 로그인
	public static void login() {

		System.out.print("id를 입력: ");
		id = sc.nextLine();
		System.out.print("pw를 입력: ");
		pw = sc.nextLine();
		vo.setUser_id(id);
		vo.setUser_pw(pw);
		boolean bool = dao.Login(vo);
		if (bool == false) {
			showmenu();
		} else {
			vo = dao.return_vo(id);
			customer_id = vo.getCustomer_id();
			if (customer_id == 1) {
				show_manager_menu();
			} else {
				show_login_menu();

			}
		}

	}

	// 개인 정보 조회
	public static void show_user_info() {
		List<UserVO> list = null;
		list = dao.View_user(vo);

		for (UserVO ve : list) {
			System.out.println(ve);
		}
		show_login_menu();

	}

	// 개인 정보 수정
	public static void update_user_info() {

		System.out.println("어떤 개인정보를 수정하시겠습니까?");
		System.out.println("1. 이름");
		System.out.println("2. ID");
		System.out.println("3. PW");
		System.out.println("4. EMAIL");
		System.out.println("0. 뒤로가기");

		choice = Integer.parseInt(sc.nextLine());
		switch (choice) {
		case 0:
			show_login_menu();

			break;
		case 1:
			System.out.print("변경할 이름 : ");
			name = sc.nextLine();
			vo.setCustomer_name(name);
			dao.Update(vo);
			System.out.println("완료되었습니다");
			show_login_menu();
			break;
		case 2:
			System.out.print("변경할 ID : ");
			id = sc.nextLine();
			vo.setUser_id(id);
			dao.Update(vo);
			System.out.println("완료되었습니다");
			show_login_menu();
			break;
		case 3:
			System.out.print("변경할 PW : ");
			pw = sc.nextLine();
			vo.setUser_pw(pw);
			dao.Update(vo);
			System.out.print("완료되었습니다");
			show_login_menu();
			break;
		case 4:
			System.out.print("변경할 EMAIL : ");
			email = sc.nextLine();
			vo.setUser_email(email);
			dao.Update(vo);
			System.out.println("완료되었습니다");
			show_login_menu();
			break;
		default:
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			show_login_menu();
			break;

		}

	}

	// 개인 정보 삭제
	public static void delete_user_info() {
		System.out.println(vo);
		System.out.println("--------------------");
		System.out.print("정말 삭제하시겠습니까?(y/n)");
		String ans = sc.nextLine();
		if (ans.toUpperCase().equals("Y")) {
			dao.Delete(vo);
			System.out.println("삭제되었습니다");
			showmenu();
		} else {
			show_login_menu();
		}

	}

	// <관리자> 메뉴
	private static void show_manager_menu() {
		System.out.println("\n--------------------");
		System.out.println("관리자 계정 선택화면");
		System.out.println("--------------------");
		System.out.println("1. 영화 조회하기");
		System.out.println("2. 영화 추가하기");
		System.out.println("3. 영화 정보 수정하기");
		System.out.println("4. 영화 삭제하기");
		System.out.println("5. 시간표 추가하기");
		System.out.println("6. 예산 확인하기");
		System.out.println("0. 종료하기");
		System.out.println("--------------------");
		System.out.print("메뉴 번호 입력: ");
		choice = Integer.parseInt(sc.nextLine());

		switch (choice) {
		case 0:
			break;
		case 1:
			
			
			dao.View_movie_list();

			choice = Integer.parseInt(sc.nextLine());
			int max =dao.confirm_movie_id();
			
			if (choice>=1 && choice<=max) {
			List<MovieVO> list = null;
			list = dao.View_movie(choice);

			for (MovieVO ve : list) {
				System.out.println(ve);
				dao.printBookingRate(ve);
			}
				} else {
					System.out.println("--------------------");
					System.out.println("잘못된 입력값입니다");
					System.out.println("--------------------");
				}
			show_manager_menu();
			break;
		case 2:
			make_movie();
			break;
		case 3:
			update_movie();
			break;
		case 4:
			delete_movie();
			break;
		case 5:
			make_timetable();
			break;
		case 6:
			calculate_price();
			break;
		default:
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			show_manager_menu();
			break;
		}
	}

	// <관리자> 시간표 추가
	private static void make_timetable() {
		System.out.println("새로운 시간표 추가하기");
		System.out.println("--------------------");
		System.out.println("새로운 시간표의 정보를 입력합니다");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		TimeTableVO vo = new TimeTableVO();
		int num;

		System.out.print("영화관번호 :");
		id = sc.nextLine();
		vo.setCinema_id(id);
		boolean bool = dao.confirm_cinema_id(id);
		if (bool == false) {
			System.out.println("일치하는 영화관번호가 아닙니다");
			show_manager_menu();
		} 
		
		System.out.print("영화번호 :");
		num = Integer.parseInt(sc.nextLine());
		int max = dao.confirm_movie_id();
		
		if (num >= 0 && num <= max ) {
		
		vo.setMovie_id(num);

		System.out.print("스크린코드 :");
		id = sc.nextLine();
		 bool = dao.confirm_movieprice_id(id);
		vo.setMoviePrice_id(id);
		if (bool == false) {
			System.out.println("일치하는 스크린코드가 아닙니다");
			show_manager_menu();
		} 
		
		try {
			System.out.print("시작시간 :");
			Date datetime = format.parse(sc.nextLine());
			vo.setStart_time(datetime);

			System.out.print("종료시간 :");
			datetime = format.parse(sc.nextLine());
			vo.setEnd_time(datetime);

			dao.insert_timetable(vo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
			System.out.println("일치하는 영화번호가 아닙니다");
		}
		
		show_manager_menu();
	}

	// <관리자> 예산 확인
	private static void calculate_price() {
		int total = 0;
		List<TicketVO> list2 = null;
		list2 = dao.budget();
		System.out.println("--------------------");
		System.out.print("\t티켓날짜\t\t");
		System.out.print("요금번호\t\t");
		System.out.print("결제수단\t\t");
		System.out.print("요금\n");

		for (TicketVO ve : list2) {
			System.out.print(ve.getTicket_date() + "\t");
			System.out.print(ve.getPrice_id() + "\t\t");
			System.out.print(ve.getPayment_method_id() + "\t\t");
			System.out.print(ve.getPrice_num() + "\n");
			total += ve.getPrice_num();
		}
		System.out.println("--------------------");
		System.out.println("합계 : " + total);
		show_manager_menu();

	}

	// <관리자> 영화 삭제
	private static void delete_movie() {
		System.out.print("삭제할 영화의 영화번호를 입력: ");
		
		int num;
		num = Integer.parseInt(sc.nextLine());
		int max = dao.confirm_movie_id();
		if (num >= 0 && num <= max) {
		dao.delete_movie(num);
		System.out.println("삭제되었습니다");
		} else {
			System.out.println("일치하는 영화번호가 아닙니다");
		}
		show_manager_menu();
	}

	// <관리자> 영화 수정
	private static void update_movie() {
		System.out.print("변경할 영화의 영화번호를 입력: ");
		MovieVO vo = new MovieVO();
		int num;
		num = Integer.parseInt(sc.nextLine());
		vo.setMovie_id(num);
		int max = dao.confirm_movie_id();
		if (num>=0 && num<=max) {
		System.out.println("변경할 영화의 정보를 입력합니다.");
		System.out.println("--------------------");
		System.out.print("이름 : ");
		String str;

		str = sc.nextLine();
		vo.setMovie_name(str);
		System.out.print("상영시간 : ");
		num = Integer.parseInt(sc.nextLine());
		vo.setRunning_time(num);
		System.out.print("배우들 : ");
		str = sc.nextLine();
		vo.setActors(str);
		System.out.print("출시일 : ");
		str = sc.nextLine();
		vo.setOpendate(str);
		System.out.print("요약 : ");
		str = sc.nextLine();
		vo.setSummary(str);
		System.out.print("심의등급 : ");
		str = sc.nextLine();
		vo.setRating_id(str);
		System.out.print("배급사 : ");
		str = sc.nextLine();
		vo.setCompany(str);
		System.out.print("감독 : ");
		str = sc.nextLine();
		vo.setDirector(str);
		System.out.print("평균평점 : ");
		double num2;
		num2 = Double.parseDouble(sc.nextLine());
		vo.setAvg_grade(num2);
		System.out.print("장르1 : ");
		str = sc.nextLine();
		vo.setGenre1_id(str);
		System.out.print("장르2 : ");
		str = sc.nextLine();
		vo.setGenre2_id(str);

		dao.update_movie(vo);
		System.out.println("완료되었습니다");
		}
		else {
			System.out.println("일치하는 영화번호가 아닙니다");
		}
		show_manager_menu();

	}

	// <관리자> 영화 추가
	private static void make_movie() {
		MovieVO vo = new MovieVO();
		System.out.println("추가할 영화의 정보를 입력합니다.");
		System.out.println("--------------------");
		System.out.print("이름 : ");
		String str;
		str = sc.nextLine();
		vo.setMovie_name(str);
		System.out.print("상영시간 : ");
		int num;
		num = Integer.parseInt(sc.nextLine());
		vo.setRunning_time(num);
		System.out.print("배우들 : ");
		str = sc.nextLine();
		vo.setActors(str);
		System.out.print("출시날짜 : ");
		str = sc.nextLine();
		vo.setOpendate(str);
		System.out.print("요약 : ");
		str = sc.nextLine();
		vo.setSummary(str);
		System.out.print("심의등급 : ");
		str = sc.nextLine();
		vo.setRating_id(str);
		System.out.print("배급사 : ");
		str = sc.nextLine();
		vo.setCompany(str);
		System.out.print("감독 : ");
		str = sc.nextLine();
		vo.setDirector(str);
		System.out.print("평균평점 : ");
		double num2;
		num2 = Double.parseDouble(sc.nextLine());
		vo.setAvg_grade(num2);
		System.out.print("장르1 : ");
		str = sc.nextLine();
		vo.setGenre1_id(str);
		System.out.print("장르2 : ");
		str = sc.nextLine();
		vo.setGenre2_id(str);
		System.out.println("완료되었습니다");
		dao.insert_movie(vo);
		show_manager_menu();

	}

	// 예매하기
	private static void insertTicket() {
		ArrayList<Integer> movie_ids = dao.showMovie();
		System.out.println("--------------------");
	System.out.print("영화 번호 입력: ");
	int movie_id = Integer.parseInt(sc.nextLine());
	if (movie_ids.contains(movie_id)) {
		System.out.println("--------------------");
	ArrayList<String> cinema_ids = dao.showCinema();
	System.out.println("--------------------");
	System.out.print("영화관 코드 입력: ");
	String cinema_id = sc.nextLine();
		if (cinema_ids.contains(cinema_id)) {
			System.out.println("--------------------");
			ArrayList<Integer> timetable_ids = dao.showTimeTable(movie_id, cinema_id);
			if (!timetable_ids.isEmpty()) {
				System.out.print("시간표 번호 입력: ");
				int timetable_id = Integer.parseInt(sc.nextLine());
		
				if (timetable_ids.contains(timetable_id)) {
					System.out.println("--------------------");
					System.out.print("좌석 개수 입력: ");
					int seatNum = Integer.parseInt(sc.nextLine());
		
				int[] arr = new int[seatNum];
				for (int i = 0; i < seatNum; i++) {
					System.out.println("--------------------");
					dao.showSeat(timetable_id);
	
					int ticketType = 0;
					while (true) {
						System.out.println("--------------------");
						System.out.println("1. 일반");
						System.out.println("2. 청소년");
						System.out.println("3. 경로");
						System.out.println("4. 우대");
						System.out.println("--------------------");
						System.out.print("종류 번호 입력: ");
						ticketType = Integer.parseInt(sc.nextLine());
						if (ticketType == 1 || ticketType == 2 || ticketType == 3 || ticketType == 4) {
							break;
						} else {
							System.out.println("잘못된 번호입니다");
						}
					}
					while (true) {
						System.out.print("좌석 입력(A열 1번 좌석이면 'A-1' 입력): ");
						String seatStr = sc.nextLine();
						arr[i] = dao.bookTicket(customer_id, cinema_id, timetable_id, ticketType, seatStr);
						if (arr[i] == 0) {
							System.out.println("잘못된 좌석 코드입니다");
						} else {
							break;
						}
					}
				}
	
				System.out.println("--------------------");
				System.out.println("총 결제금액: " + dao.getTotalPrice(customer_id, arr));
				int payNum = 0;
				while (true) {
					System.out.println("--------------------");
					System.out.println("1. 카드   2. 현금   3. 핸드폰");
					System.out.println("--------------------");
					System.out.print("결제 방법 선택: ");
					payNum = Integer.parseInt(sc.nextLine());
					if (payNum == 1 || payNum == 2 || payNum == 3) {
						break;
					} else {
						System.out.println("잘못된 번호입니다");
					}
				}
				dao.setPaymentMethod(customer_id, arr, payNum);
	
			} else {
				System.out.println("잘못된 시간표 번호입니다");
			}
		}
	} else {
		System.out.println("잘못된 영화관 코드입니다");
		}
	} else {
		System.out.println("잘못된 영화 번호입니다");
		}
	}
	
	// 예매목록 조회
	private static void selectTicket() {
		int cnt = dao.selectTicket(customer_id, 0);
		if (cnt == 0) {
			System.out.println("예매한 내역이 없습니다");
			System.out.println("--------------------");
		}
	}

	// 티켓 변경
	private static void updateTicket() {

		int cnt = dao.selectTicket(customer_id, 1);
		if (cnt == 0) {
			System.out.println("예매한 내역이 없습니다");
			System.out.println("--------------------");
		} else {
			System.out.print("예매 번호 입력: ");
			int ticket_id = Integer.parseInt(sc.nextLine());

			System.out.println("--------------------");
			System.out.println("1. 영화시간 변경\n2. 좌석 변경\n3. 결제방법 변경");
			System.out.println("--------------------");
			System.out.print("메뉴 번호 입력: ");
			int menuNum = Integer.parseInt(sc.nextLine());

			System.out.println("--------------------");
			switch (menuNum) {
			case 1:
				updateTime(ticket_id); // 영화시간 변경
				break;
			case 2:
				updateSeat(ticket_id); // 좌석 변경
				break;
			case 3:
				updatePayment(ticket_id); // 결제방법 변경
				break;
			default:
				System.out.println("--------------------");
				System.out.println("잘못된 입력값입니다");
				System.out.println("--------------------");
				show_login_menu();
				break;
			}
		}

	}

	// 시간 변경
	private static void updateTime(int ticket_id) {
		dao.showTime(customer_id, ticket_id);
		System.out.print("시간표 번호 입력: ");
		int timetable_id = Integer.parseInt(sc.nextLine());

		System.out.println("--------------------");
		dao.showSeat(timetable_id);
		System.out.println("--------------------");
		System.out.print("좌석 입력(A열 1번 좌석이면 'A-1' 입력): ");
		String seatStr = sc.nextLine();
		int addPay = dao.updateTime(customer_id, ticket_id, timetable_id, seatStr);
		if (addPay > 0) {
			System.out.println("--------------------");
			System.out.println("추가 결제금액: " + addPay);
			System.out.println("--------------------");
			System.out.println("결제 완료");
		} else if (addPay < 0) {
			System.out.println("--------------------");
			System.out.println("환불금액: " + -(addPay));
			System.out.println("--------------------");
			System.out.println("환불 완료");
		}
		System.out.println("--------------------");
		System.out.println("시간 변경 완료");
	}

	// 좌석 변경
	private static void updateSeat(int ticket_id) {
		dao.showTicketSeat(ticket_id);
		System.out.println("--------------------");
		System.out.print("좌석 입력(A열 1번 좌석이면 'A-1' 입력): ");
		String seatStr = sc.nextLine();
		dao.updateSeat(customer_id, ticket_id, seatStr);
	}

	// 결제 수단 변경
	private static void updatePayment(int ticket_id) {
		System.out.println("1. 카드   2. 현금   3. 핸드폰");
		System.out.println("--------------------");
		System.out.print("결제 방법 선택: ");
		int payNum = Integer.parseInt(sc.nextLine());

		dao.updatePayment(customer_id, ticket_id, payNum);
	}

	// 티켓 삭제
	private static void deleteTicket() {
		int cnt = dao.selectTicket(customer_id, 1);
		if (cnt == 0) {
			System.out.println("예매한 내역이 없습니다");
			System.out.println("--------------------");
		} else {
			System.out.print("예매 번호 입력: ");
			int ticket_id = Integer.parseInt(sc.nextLine());
			dao.deleteTicket(customer_id, ticket_id);
		}
	}

	// 평점 조회
	private static void selectGrade() {
		dao.selectGrade(customer_id);
		System.out.println("--------------------");
	}

	// 평점 변경
	private static void updateGrade() {
		int cnt = dao.selectGrade(customer_id);
		if (cnt != 0) {
			System.out.println("--------------------");
			System.out.print("영화 제목을 입력: ");
			String movie_name = sc.nextLine();
			MovieVO vo = new MovieVO();
			vo.setMovie_name(movie_name);
			
			boolean bool = dao.confirm_movie_name(vo);
			if (bool == true) {
			Double newGrade;
			while (true) {
				System.out.print("평점을 입력: ");
				newGrade = Double.parseDouble(sc.nextLine());
				if (newGrade > 10 || newGrade < 0) {
					System.out.println("[오류] 잘못된 평점입니다");
				} else {
					break;
				}
			}
			dao.updateGrade(customer_id, movie_name, newGrade);
			} else {
				System.out.println("일치하는 영화제목이 아닙니다.");
			}
			
		}
		System.out.println("--------------------");
	}

	// 평점 삭제
	private static void deleteGrade() {
		int cnt = dao.selectGrade(customer_id);
		if (cnt != 0) {
			System.out.println("--------------------");
			System.out.print("영화 제목을 입력: ");
			String movie_name = sc.nextLine();
			MovieVO vo = new MovieVO();
			vo.setMovie_name(movie_name);
			
			boolean bool = dao.confirm_movie_name(vo);
			if (bool == true) {
			dao.deleteGrade(customer_id, movie_name);
			} else {
				System.out.println("일치하는 영화제목이 아닙니다.");
			}
		}
		System.out.println("--------------------");
	}

	// 평점 추가
	private static void insertGrade() {
		dao.showMovie();
		System.out.println("--------------------");
		System.out.print("영화 번호를 입력: ");
		
		int max =dao.confirm_movie_id();
		
		
		int movie_id = Integer.parseInt(sc.nextLine());
		if (1<=movie_id && movie_id <= max) {
		Double grade;
		while (true) {
			System.out.print("평점을 입력: ");
			grade = Double.parseDouble(sc.nextLine());
			if (grade > 10 || grade < 0) {
				System.out.println("[오류] 잘못된 평점입니다");
			} else {
				break;
			}
		}
		dao.insertGrade(customer_id, movie_id, grade);
		System.out.println("--------------------");
		} else {
			System.out.println("--------------------");
			System.out.println("잘못된 입력값입니다");
			System.out.println("--------------------");
			show_login_menu();
		}
	}

}
