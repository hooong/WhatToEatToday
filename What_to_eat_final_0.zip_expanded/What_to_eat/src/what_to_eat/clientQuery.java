package what_to_eat;

// SQL���� �����ϱ� ���� Ŭ����
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
 * @DBQuery DB�� ���� Ŭ����
 * @author 201513467_�輺Ź
 * @version 1.3
 */

public class clientQuery {

	String driver = "org.sqlite.JDBC"; // sqlite driver
	String url = "jdbc:sqlite:C:\\Users\\tjdxk\\Desktop\\DB\\WhatToEat.db"; // DB ���
	Client client;
	// id ��ȿ�� �˻�, ���ҹ��ڸ� �����ؾ��Ѵ�
	// String idPatternAlphaNum = "^[a-zA-Z0-9]*$";
	String idPatternAlphaNum = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+)$";
	// pw ��ȿ�� �˻�1, ����, Ư�����ڰ� ���ԵǾ���Ѵ�
	String pwPatternSymbol = "([0-9].*[!,@,#,^,&,%,*,(,)])|([!,@,#,^,&,%,*,(,)].*[0-9])";
	// pw ��ȿ�� �˻�2, ������ �빮�ڰ� ��� �ϳ����� ���ԵǾ�� �Ѵ�
	String pwPatternAlpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";

	// id, pw Ȯ�� �����
	public static final int emptyID = -2;
	public static final int emptyPW = -3;
	public static final int lengthID = -4;
	public static final int lengthPW = -5;
	public static final int valID = -6;
	public static final int valPWSymbol = -7;
	public static final int valPWAlpha = -8;
	public static final int dupliID = -9;

	// SQLite�� jdbc����̹��� SQLite DB�� ������¸� Ȯ���ϴ� �޼ҵ�
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

	// DB�� ��� ���� ���� ��� ���� 1~3���� ����ϵ��� ����
	public void dbView() {
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			// client���̺��� ��� �࿭���� SELECT�ϴ� ������
			sql = "SELECT * FROM client";
			// sql�� ������ �����Ͽ� ResultSet���� ����
			rs = stmt.executeQuery(sql);

			// ResultSet�� rs.next �޼ҵ�� column(��)���� �̵����� true�̸� get...()�޼ҵ带 �̿��� ���� �����´�
			// 1��, 2��, 3���� ���� varchar���������� getString()�޼ҵ带 ����Ѵ�
			while (rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
			}

			// ���� ���
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database View ����");
		} finally {
			// ����� ResultSet, Statement, Connection�� close()�޼ҵ带 ȣ���� �����Ѵ�
			// �������� ������ �ý��� �ڿ����� �� �����߻��ϴ� ��������� �ݵ�� �����ϴ� ���� ����Ѵ�
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
	 * DB�� ���ο� ���� �߰��ϴ� ȸ������ �޼ҵ�
	 * 
	 * @param DB��
	 *            ���� �߰��� ȸ�������� �Էµ� Client
	 * @return �����۵��ϸ� true���� return return ���� void, int�� ���� return�� int�̸� ������� ���� ����
	 *         ���. �����۵��ϸ� 1, �����ϸ� 0
	 */
	public int dbRegist(Client client) {

		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;

		// ȸ������ ��� �Է�
		int check = 0;
		try {
			conn = DriverManager.getConnection(url);
			// client ���̺� INSERT�ϴ� ������. ������ ?�� ǥ��
			sql = "INSERT INTO client VALUES(?,?,?,?)";
			// DB�� id, pw, name, email ����
			String id = client.getId();
			String password = client.getPw();
			String name = client.getName();
			String email = client.getEmail();

			// �α��� �Լ��� ȣ���� �α��� ������ ������������ �α��� ��� ����Ͽ� ����
			check = dbLogin(id, password);
			if (check != 1)
				return check;

			// PreparedStatement�� ������ ����
			pstmt = conn.prepareStatement(sql);

			// INSERT �������� ?�� �����ϴ� ���� �տ��� ���� ������� setString���� �Է�
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ȸ������ ����");
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
	 * id�� pw�� ����ڷκ��� �Է¹޾� �α���
	 * 
	 * @param id
	 *            ����ڷκ��� �Է¹��� id
	 * @param pw
	 *            ����ڷκ��� �Է¹��� pw
	 * @return �α��� ���� 1, �α��� ���� 0, �� �ܿ� id�� pw�� Ȯ���Ͽ��Ͽ� ������ ����� ��ȯ
	 */
	public int dbLogin(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// idȮ�λ���� pwȮ�λ���� ����
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

			// id�� pw��� �Է¹޾� ����
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

			// id�� pw�� ������ Ȯ��
			else if (rs.next()) {
				// id�� ���� �࿡ ��ġ�� password ���� dbPW�� ����
				dbPW = rs.getString("password");
				// dbPW�� �Ű����� pw�� ������ �˻�
				if (dbPW.equals(pw))
					return 1; // �α��� ����
				else
					return check; // �α��� ����
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ȸ���α��ν���");
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
	 * id�� ��ȿ�� ���� Ȯ��
	 * 
	 * @param id
	 *            ����ڷκ��� �Է¹��� id
	 * @param pw
	 *            ����ڷκ��� �Է¹��� pw
	 * @return �ߺ��̸� 0, �ߺ��� �ƴϸ� 1, �� �ܿ� ������ ��� ��ȯ
	 */
	public int dbCheckId(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// id�� �������� Ȯ���Ͽ� ������ ��� ����
		int check = 0;

		try {
			// �Է¹��� id�� �ش��ϴ� ������ ��ȯ
			conn = DriverManager.getConnection(url);
			sql = "SELECT * FROM client WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// id�� ���ҹ��ڸ� �����ϴ��� Ȯ��
			Pattern idPattern = Pattern.compile(idPatternAlphaNum);
			Matcher matcher = idPattern.matcher(id);

			// id �������� Ȯ��
			if (id.equals("")) {
				return emptyID;
			}
			// id ���� �˻�
			else if (!(id.length() > 5))
				return lengthID;
			// id ���̵� ��ȿ�� �˻�
			else if (matcher.find()) {
				return valID;
			}
			// id �ߺ����� Ȯ��
			else if (rs.next()) {
				return dupliID;
			} else
				// id Ȯ�� ����
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
	 * pw�� ��ȿ�� ���� Ȯ��
	 * 
	 * @param pw
	 *            ����ڰ� �Է��� pw
	 * @return ��ȿ�� ������ 1, ��ȿ�� ���н� 0, �� �ܿ� ������ pw��� ��ȯ
	 */
	public int dbCheckPw(String pw) {

		// pw�� �������� Ȯ���Ͽ� ��� ����
		int check = 0;

		// pw�� ����ǥ������ �����ϴ��� Ȯ��
		Pattern pwSymbol = Pattern.compile(pwPatternSymbol);
		Pattern pwAlpha = Pattern.compile(pwPatternAlpha);
		Matcher matcherSymbol = pwSymbol.matcher(pw);
		Matcher matcherAlpha = pwAlpha.matcher(pw);

		// pw�� �������� Ȯ��
		if (pw.equals(""))
			return emptyPW;
		// pw�� ũ�� Ȯ��
		else if (!(pw.length() > 5))
			return lengthPW;
		// pw�� ���ڿ� Ư�����ڰ� ���ԵǾ��ִ��� Ȯ��
		else if (!(matcherSymbol.find()))
			return valPWSymbol;
		// pw�� �����ڿ� �빮�ڰ� ��� �ϳ� �̻� ���ԵǾ��ִ��� Ȯ��
		else if (!(matcherAlpha.find()))
			return valPWAlpha;
		// pw�� �� ����ǥ������ �����ϴ��� Ȯ��, �����ϸ� ����
		else if (matcherSymbol.find() && matcherAlpha.find())
			return 1;
		// pwȮ�� ����
		else
			return check;
	}

	/**
	 * ���, ��������, ���� �޾� DB�� �ش��ϴ� ���� ���� ��ȯ
	 * 
	 * @param place
	 *            ����, ����, �Ĺ�, ���� �� 1��
	 * @param style
	 *            ��������, �ڽ����� üũ�� ��
	 * @param price
	 *            ���ϴ� ���İ���
	 * @return
	 */
	public int getRows(String place, String style, String price) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// �ش��ϴ� ���� ���� ����
		int rowCount = -1;

		// �Է¹��� ���� ������
		// �������� 1������ �߰��Ͽ� �ְ����� ����
		String price_lower = price;
		String price_higher = null;
		String priceRange = price + 10000;
		price_higher = "" + priceRange;

		try {
			conn = DriverManager.getConnection(url);
			// DB�� ��ҿ� �������� �� ���ݴ뿡 �ش��ϴ� ���� ���� ����ϱ����� ������
			sql = "SELECT COUNT(*) FROM restaurants WHERE place = ? AND style = ? AND price BETWEEN ? and ?";
			pstmt = conn.prepareStatement(sql);
			// ?�� ������� ����
			pstmt.setString(1, place);
			pstmt.setString(2, style);
			pstmt.setString(3, price_lower);
			pstmt.setString(4, price_higher);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// ���� ���� ����
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
		// ���� �� ��ȯ
		return rowCount;
	}


	/** id���� �޾� DB�� �ش��ϴ� ���� ���� ��ȯ
	 * 
	 * @param place
	 *            ����, ����, �Ĺ�, ���� �� 1��
	 * @param style
	 *            ��������, �ڽ����� üũ�� ��
	 * @param price
	 *            ���ϴ� ���İ���
	 * @return
	 */
	public int getIdxRows(String id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		// �ش��ϴ� ���� ���� ����
		int rowCount = -1;
		try {
			conn = DriverManager.getConnection(url);
			// DB�� ��ҿ� �������� �� ���ݴ뿡 �ش��ϴ� ���� ���� ����ϱ����� ������
			sql = "SELECT COUNT(*) FROM restaurants WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			// ?�� ������� ����
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// ���� ���� ����
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
		// ���� �� ��ȯ
		return rowCount;
	}
	
	
	/**
	 * ���� ��, ���, ��������, ������ �Է¹޾� �������� �ε����� ����
	 * 
	 * @param rows
	 *            ã�����ϴ� ���� ���� ��
	 * @param selPlace
	 * @param selStyle
	 * @param selPrice
	 * @return
	 */
	public int getIndex(String place, String style, String price) {

		// �Է¹��� ���� ������
		// �������� 1������ �߰��Ͽ� �ְ����� ����
		String price_lower = price;
		String price_higher = null;
		String priceRange = price + 10000;
		price_higher = "" + priceRange;
		
		// ���� ��ü
		Random ran = new Random();
		
		int idx = 0;
		int idxRow = 0;
		
		// �Է¹��� �����Ϳ� �ش��ϴ� ���� ������ ����
		int rows = getRows(place, style, price);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;


		//String contents[][] = new String[rows][9];

		try {
			conn = DriverManager.getConnection(url);
			// ���, ��������, ���ݴ뿡 �ش��ϴ� id���� ���� �������� ������
			sql = "SELECT id FROM restaurants WHERE place = ? AND style = ? AND price BETWEEN ? and ?";
			System.out.println(sql);
			
			String[] id = new String[rows];
			
			// ?�� ���ʷ� ����
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
			
			// ���� �� �� �������� �� �� ����
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
	 * �̻��
	 * 
	 * DB���� param�� ��ġ�ϴ� ���� ������ �����ϴ� ȸ���������� �޼ҵ�
	 * 
	 * @param id
	 *            ȸ���������� ���� Ȯ���� Client id
	 * @param pw
	 *            ȸ���������� ���� Ȯ���� Client pw
	 * @return id, pw�� ��ġ�� ȸ���� ������ ��� Client
	 */
	public Client dbExtrac(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url);
			// client ���̺� Ư�� id�� password�� ��ġ�� ���� SELECT�ϴ� ������
			String sql = "SELECT * FROM client WHERE id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			// ? ������� �Ű������� id, pw�� setString
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery(); // sql�� ������ �����Ͽ� ResultSet���� ����

			// rs.next�� �Ű������� �ִ� �����ڸ� ��ü�� �����Ͽ� �޾��� ���� ���ο� client������ ��ü ����
			if (rs.next()) {
				client = new Client(rs.getString("id"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ȸ�������������");
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
	 * �̻��
	 * 
	 * DB���� param�� ��ġ�ϴ� ���� ������ �����ϴ� ȸ��Ż�� �޼ҵ�
	 * 
	 * @param id
	 *            ȸ��Ż���ϱ� ���� Ȯ���� Client id
	 * @param pw
	 *            ȸ��Ż���ϱ� ���� Ȯ���� Client pw
	 * @return ���������� ������ ���� ������ return. �����۵��ϸ� 1, �����ϸ� 0
	 */
	public int dbDel(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// ����޴� ���� ������ ����, �� 1�� �ƴϸ� ����
		int check = 0;

		try {
			conn = DriverManager.getConnection(url);
			// client ���̺� Ư�� id�� password�� ��ġ�� ���� DELETE�ϴ� ������
			String sql = "DELETE FROM client WHERE id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			// ? ������� �Ű������� id, pw�� setString
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			// i���� ����޴� ��(row)�� ����(1)�� ����. 0�̸� ������� ���� ����
			check = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ȸ��Ż�����");
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
	 * �̻��
	 * 
	 * DB���� param�� ��ġ�ϴ� ���� ������ �����ϴ� ȸ���������� �޼ҵ�
	 * 
	 * @param modifyClient
	 *            ������ ȸ�������� �Էµ� Client
	 * @param originId
	 *            ������ ȸ���� ���� Client id, UPDATE������ WHERE���� ���� ���� �ʿ�
	 * @return ���������� ������ ���� ������ return. �����۵��ϸ� 1, �����ϸ� 0
	 */
	public int dbModify(Client modifyClient, String originId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// ����޴� ���� ����ŭ ����, �� 1�� �ƴϸ� ����
		int check = 0;

		try {
			conn = DriverManager.getConnection(url);
			// client ���̺� id,password,name�� �缳���ϴ� ������, �� WHERE���� ��ġ�ϴ� �࿡�� ����
			String updateInform = "UPDATE client SET id=?, password = ?, name = ? WHERE id = ?";
			String id = modifyClient.getId();
			String pw = modifyClient.getPw();
			String name = modifyClient.getName();

			// �������� ���� PreparedStatement�� ������ �����Ͽ� ������ ����
			// ? ������� id,pw,name,originId ����
			pstmt = conn.prepareStatement(updateInform);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, originId);
			check = pstmt.executeUpdate(); // ������ �����Ͽ� ������ �޴� ���� ���� i�� ����
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
