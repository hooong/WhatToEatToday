package what_to_eat;

// SQL문을 구현하기 위한 클래스
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @DBQuery DB의 관련 클래스
 * @author 201513467_김성탁
 * @version 1.3
 */

public class clientQuery {

	String driver = "org.sqlite.JDBC"; // sqlite driver
	String url = "jdbc:sqlite:C:\\Users\\tjdxk\\Desktop\\DB\\WhatToEat.db"; // DB 경로
	Client client;
	// id 유효성 검사, 영소문자만 포함해야한다
	// String idPatternAlphaNum = "^[a-zA-Z0-9]*$";
	String idPatternAlphaNum = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+)$";
	// pw 유효성 검사1, 숫자, 특수문자가 포함되어야한다
	String pwPatternSymbol = "([0-9].*[!,@,#,^,&,%,*,(,)])|([!,@,#,^,&,%,*,(,)].*[0-9])";
	// pw 유효성 검사2, 영문자 대문자가 적어도 하나씩은 포함되어야 한다
	String pwPatternAlpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";

	// id, pw 확인 상수들
	public static final int emptyID = -2;
	public static final int emptyPW = -3;
	public static final int lengthID = -4;
	public static final int lengthPW = -5;
	public static final int valID = -6;
	public static final int valPWSymbol = -7;
	public static final int valPWAlpha = -8;
	public static final int dupliID = -9;

	// SQLite의 jdbc드라이버와 SQLite DB의 연결상태를 확인하는 메소드
	public void dbConnect() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
			System.out.println("DB Connection Success...");
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Database Driver Class Not Load
		} catch (SQLException e) {
			e.printStackTrace(); // Database Connection Failed
		} catch (Exception e) {
			e.printStackTrace(); // Unknown Exception
		}
	}

	// DB의 모든 행의 값을 출력 현재 1~3열만 출력하도록 설정
	public void dbView() {
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			// client테이블의 모든 행열값을 SELECT하는 쿼리문
			sql = "SELECT * FROM client";
			// sql의 쿼리문 실행하여 ResultSet문에 저장
			rs = stmt.executeQuery(sql);

			// ResultSet의 rs.next 메소드로 column(열)값을 이동시켜 true이면 get...()메소드를 이용해 값을 가져온다
			// 1열, 2열, 3열이 각각 varchar형식임으로 getString()메소드를 사용한다
			while (rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
			}

			// 에러 출력
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database View 실패");
		} finally {
			// 사용한 ResultSet, Statement, Connection은 close()메소드를 호출해 종료한다
			// 종료하지 않으면 시스템 자원낭비 및 에러발생하니 사용했으면 반드시 종료하는 것을 명심한다
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * DB에 새로운 값을 추가하는 회원가입 메소드
	 * 
	 * @param DB에
	 *            새로 추가할 회원정보가 입력된 Client
	 * @return 정상작동하면 true값을 return return 값은 void, int도 가능 return이 int이면 영향받은 행의 수를
	 *         출력. 정상작동하면 1, 실패하면 0
	 */
	public int dbRegist(Client client) {

		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;

		// 회원가입 상수 입력
		int check = 0;
		try {
			conn = DriverManager.getConnection(url);
			// client 테이블에 INSERT하는 쿼리문. 변수는 ?로 표시
			sql = "INSERT INTO client VALUES(?,?,?,?)";
			// DB에 id, pw, name, email 저장
			String id = client.getId();
			String password = client.getPw();
			String name = client.getName();
			String email = client.getEmail();

			// 로그인 함수를 호출해 로그인 조건을 갖추지않으면 로그인 상수 출력하여 종료
			check = dbLogin(id, password);
			if (check != 1)
				return check;

			// PreparedStatement에 쿼리문 대입
			pstmt = conn.prepareStatement(sql);

			// INSERT 쿼리문의 ?에 대응하는 열을 앞에서 부터 순서대로 setString으로 입력
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원가입 실패");
		} finally {
			// PreparedStatement, Connection close();
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * id와 pw를 사용자로부터 입력받아 로그인
	 * 
	 * @param id
	 *            사용자로부터 입력받은 id
	 * @param pw
	 *            사용자로부터 입력받은 pw
	 * @return 로그인 성공 1, 로그인 실패 0, 그 외에 id와 pw를 확인하여하여 적절한 상수를 반환
	 */
	public int dbLogin(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// id확인상수와 pw확인상수를 저장
		int checkId = dbCheckId(id);
		int checkPw = dbCheckPw(pw);

		String dbPW = "";
		int check = 0;

		try {
			System.out.println(id);
			System.out.println(pw);
			String query = "SELECT password FROM client WHERE id = ?";
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// id와 pw상수 입력받아 리턴
			if (checkId == emptyID)
				return emptyID;
			else if (checkPw == emptyPW)
				return emptyPW;
			else if (checkId == lengthID)
				return lengthID;
			else if (checkPw == lengthPW)
				return lengthPW;
			else if (checkId == valID)
				return valID;
			else if (checkPw == valPWSymbol)
				return valPWSymbol;
			else if (checkPw == valPWAlpha)
				return valPWAlpha;

			// id와 pw가 같은지 확인
			else if (rs.next()) {
				// id와 같은 행에 위치한 password 값을 dbPW에 저장
				dbPW = rs.getString("password");
				// dbPW와 매개변수 pw와 같은지 검사
				if (dbPW.equals(pw))
					return 1; // 로그인 성공
				else
					return check; // 로그인 실패
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원로그인실패");
		} finally {
			// ResultSet, PreparedStatement, Connection close();
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * id의 유효성 여부 확인
	 * 
	 * @param id
	 *            사용자로부터 입력받은 id
	 * @param pw
	 *            사용자로부터 입력받은 pw
	 * @return 중복이면 0, 중복이 아니면 1, 그 외엔 적절한 상수 반환
	 */
	public int dbCheckId(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// id가 적절한지 확인하여 적절한 상수 대입
		int check = 0;

		try {
			// 입력받은 id에 해당하는 값들을 반환
			conn = DriverManager.getConnection(url);
			sql = "SELECT * FROM client WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// id에 영소문자만 포함하는지 확인
			Pattern idPattern = Pattern.compile(idPatternAlphaNum);
			Matcher matcher = idPattern.matcher(id);

			// id 공백인지 확인
			if (id.equals("")) {
				return emptyID;
			}
			// id 갯수 검사
			else if (!(id.length() > 5))
				return lengthID;
			// id 아이디 유효성 검사
			else if (matcher.find()) {
				return valID;
			}
			// id 중복여부 확인
			else if (rs.next()) {
				return dupliID;
			} else
				// id 확인 성공
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * pw의 유효성 여부 확인
	 * 
	 * @param pw
	 *            사용자가 입력한 pw
	 * @return 우효성 성공시 1, 유효성 실패시 0, 그 외엔 적절한 pw상수 반환
	 */
	public int dbCheckPw(String pw) {

		// pw가 적절한지 확인하여 상수 대입
		int check = 0;

		// pw의 정구표현식을 만족하는지 확인
		Pattern pwSymbol = Pattern.compile(pwPatternSymbol);
		Pattern pwAlpha = Pattern.compile(pwPatternAlpha);
		Matcher matcherSymbol = pwSymbol.matcher(pw);
		Matcher matcherAlpha = pwAlpha.matcher(pw);

		// pw가 공백인지 확인
		if (pw.equals(""))
			return emptyPW;
		// pw의 크기 확인
		else if (!(pw.length() > 5))
			return lengthPW;
		// pw가 숫자와 특수문자가 포함되어있는지 확인
		else if (!(matcherSymbol.find()))
			return valPWSymbol;
		// pw가 영문자와 대문자가 적어도 하나 이상 포함되어있는지 확인
		else if (!(matcherAlpha.find()))
			return valPWAlpha;
		// pw의 두 정규표현식을 만족하는지 확인, 만족하면 성공
		else if (matcherSymbol.find() && matcherAlpha.find())
			return 1;
		// pw확인 실패
		else
			return check;
	}

	/**
	 * 장소, 음식종루, 값을 받아 DB의 해당하는 행의 수를 반환
	 * 
	 * @param place
	 *            정문, 자쪽, 후문, 동문 중 1개
	 * @param style
	 *            음식종류, 박스에서 체크한 값
	 * @param price
	 *            원하는 음식가격
	 * @return
	 */
	public int getRows(String place, String style, String price) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// 해당하는 행의 수를 저장
		int rowCount = -1;

		// 입력받은 값을 최저값
		// 최저값에 1만원을 추가하여 최고값으로 대입
		String price_lower = price;
		String price_higher = null;
		String priceRange = price + 10000;
		price_higher = "" + priceRange;

		try {
			conn = DriverManager.getConnection(url);
			// DB에 장소와 음식종루 및 가격대에 해당하는 행의 수를 계산하기위한 쿼리문
			sql = "SELECT COUNT(*) FROM restaurants WHERE place = ? AND style = ? AND price BETWEEN ? and ?";
			pstmt = conn.prepareStatement(sql);
			// ?에 순서대로 대입
			pstmt.setString(1, place);
			pstmt.setString(2, style);
			pstmt.setString(3, price_lower);
			pstmt.setString(4, price_higher);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 행의 값을 저장
				rowCount = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 행의 값 반환
		return rowCount;
	}


	/** id값을 받아 DB의 해당하는 행의 수를 반환
	 * 
	 * @param place
	 *            정문, 자쪽, 후문, 동문 중 1개
	 * @param style
	 *            음식종류, 박스에서 체크한 값
	 * @param price
	 *            원하는 음식가격
	 * @return
	 */
	public int getIdxRows(String id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// 해당하는 행의 수를 저장
		int rowCount = -1;
		try {
			conn = DriverManager.getConnection(url);
			// DB에 장소와 음식종루 및 가격대에 해당하는 행의 수를 계산하기위한 쿼리문
			sql = "SELECT COUNT(*) FROM restaurants WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			// ?에 순서대로 대입
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 행의 값을 저장
				rowCount = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 행의 값 반환
		return rowCount;
	}
	
	
	/**
	 * 행의 수, 장소, 음식종류, 가격을 입력받아 랜덤으로 인덱스값 생성
	 * 
	 * @param rows
	 *            찾고자하는 값의 행의 수
	 * @param selPlace
	 * @param selStyle
	 * @param selPrice
	 * @return
	 */
	public int getIndex(String place, String style, String price) {

		// 입력받은 값을 최저값
		// 최저값에 1만원을 추가하여 최고값으로 대입
		String price_lower = price;
		String price_higher = null;
		String priceRange = price + 10000;
		price_higher = "" + priceRange;
		
		// 랜덤 객체
		Random ran = new Random();
		
		int idx = 0;
		int idxRow = 0;
		
		// 입력받은 데이터에 해당하는 행의 갯수를 저장
		int rows = getRows(place, style, price);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;


		//String contents[][] = new String[rows][9];

		try {
			conn = DriverManager.getConnection(url);
			// 장소, 음식종류, 가격대에 해당하는 id행의 값만 가져오는 쿼리문
			sql = "SELECT id FROM restaurants WHERE place = ? AND style = ? AND price BETWEEN ? and ?";
			System.out.println(sql);
			
			String[] id = new String[rows];
			
			// ?에 차례로 대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, place);
			System.out.println(place);
			pstmt.setString(2, style);
			System.out.println(style);
			pstmt.setString(3, price_lower);
			System.out.println(price_lower);
			pstmt.setString(4, price_higher);
			System.out.println(price_higher);
			rs = pstmt.executeQuery();

			for (int count = 0; count < rows; count++) {
				id[count] = rs.getString("id");
				System.out.println("id([" + count + "]) = " + id[count]);
				rs.next();
			}
			
			// 행의 값 중 랜덤으로 한 개 저장
			idxRow = ran.nextInt(rows);
			System.out.println("clientQuery_getIndex : idxRow = " + idxRow);
			idx = Integer.parseInt(id[idxRow]);
			System.out.println("clientQuery_getIndex : idx = " + idx);
			return idx;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return idx;
	}

	/**
	 * 미사용
	 * 
	 * DB에서 param과 일치하는 행의 정보를 추출하는 회원정보추출 메소드
	 * 
	 * @param id
	 *            회원정보추출 위해 확인할 Client id
	 * @param pw
	 *            회원정보추출 위해 확인할 Client pw
	 * @return id, pw가 일치한 회원의 정보가 담긴 Client
	 */
	public Client dbExtrac(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url);
			// client 테이블에 특정 id와 password가 일치한 행을 SELECT하는 쿼리문
			String sql = "SELECT * FROM client WHERE id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			// ? 순서대로 매개변수의 id, pw로 setString
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery(); // sql의 쿼리문 실행하여 ResultSet문에 저장

			// rs.next로 매개변수가 있는 생성자를 객체로 생성하여 받아진 정보 새로운 client형태의 객체 생성
			if (rs.next()) {
				client = new Client(rs.getString("id"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원정보추출실패");
		} finally {
			// ResultSet, PreparedStatement, Connection close();
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	/**
	 * 미사용
	 * 
	 * DB에서 param과 일치하는 행의 정보를 삭제하는 회원탈퇴 메소드
	 * 
	 * @param id
	 *            회원탈퇴하기 위해 확인할 Client id
	 * @param pw
	 *            회원탈퇴하기 위해 확인할 Client pw
	 * @return 정상적으로 삭제된 행의 갯수를 return. 정상작동하면 1, 실패하면 0
	 */
	public int dbDel(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 영향받는 행의 갯수를 저장, 즉 1이 아니면 오류
		int check = 0;

		try {
			conn = DriverManager.getConnection(url);
			// client 테이블에 특정 id와 password가 일치한 행을 DELETE하는 쿼리문
			String sql = "DELETE FROM client WHERE id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			// ? 순서대로 매개변수의 id, pw로 setString
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			// i에는 영향받는 행(row)의 갯수(1)가 대입. 0이면 영향받은 행이 없다
			check = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원탈퇴실패");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * 미사용
	 * 
	 * DB에서 param과 일치하는 행의 정보를 수정하는 회원정보수정 메소드
	 * 
	 * @param modifyClient
	 *            수정할 회원정보가 입력된 Client
	 * @param originId
	 *            수정할 회원의 기존 Client id, UPDATE문에서 WHERE절을 쓰기 위해 필요
	 * @return 정상적으로 수정한 행의 갯수를 return. 정상작동하면 1, 실패하면 0
	 */
	public int dbModify(Client modifyClient, String originId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 영향받는 행의 수만큼 저장, 즉 1이 아니면 오류
		int check = 0;

		try {
			conn = DriverManager.getConnection(url);
			// client 테이블에 id,password,name을 재설정하는 쿼리문, 단 WHERE절에 일치하는 행에만 동작
			String updateInform = "UPDATE client SET id=?, password = ?, name = ? WHERE id = ?";
			String id = modifyClient.getId();
			String pw = modifyClient.getPw();
			String name = modifyClient.getName();

			// 안전성을 위해 PreparedStatement의 생성자 생성하여 쿼리문 대입
			// ? 순서대로 id,pw,name,originId 대입
			pstmt = conn.prepareStatement(updateInform);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, originId);
			check = pstmt.executeUpdate(); // 쿼리문 실행하여 영향을 받는 행의 수를 i에 저장
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
}
