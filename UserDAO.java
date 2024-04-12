package homework0409;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	public void createUser(Users user) {
		try {
			String sql = "INSERT INTO USERS (USER_ID, USER_NAME, PW, BIRTH, EMAIL, ADDRESS, SEX_CODE, ACCOUNT_NUM) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getPw());
			pstmt.setString(4, user.getBirth());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getAddress());
			pstmt.setInt(7, user.getSexCode());
			pstmt.setString(8, user.getAccountNum());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("사용자가 생성되었습니다.");
			} else {
				System.out.println("사용자 생성에 실패했습니다.");
			}
		} catch (SQLException e) {
			System.out.println("사용자 생성 오류: " + e.getMessage());
			if (e.getMessage().contains("ORA-00001")) {
				System.out.println("중복된 사용자 ID입니다.");
			} else if (e.getMessage().contains("ORA-02291")) {
				System.out.println("부모 키가 존재하지 않습니다. 올바른 계좌번호를 입력하세요.");
			} else {
				System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
			}
		} catch (Exception e) {
			System.out.println("사용자 생성 오류: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Users readUser(int userId) {
		Users user = null;
		try {
			String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new Users();
				user.setUserId(rs.getInt("USER_ID"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setPw(rs.getString("PW"));
				user.setBirth(rs.getString("BIRTH"));
				user.setEmail(rs.getString("EMAIL"));
				user.setAddress(rs.getString("ADDRESS"));
				user.setSexCode(rs.getInt("SEX_CODE"));
				user.setAccountNum(rs.getString("ACCOUNT_NUM"));

				// 사용자 정보 출력
				System.out.println("사용자 이름: " + user.getUserName());
				System.out.println("비밀번호: " + user.getPw());
				System.out.println("생년월일: " + user.getBirth());
				System.out.println("이메일: " + user.getEmail());
				System.out.println("주소: " + user.getAddress());
				System.out.println("성별 코드: " + user.getSexCode());
				System.out.println("계좌 번호: " + user.getAccountNum());
			} else {
				System.out.println("해당 ID의 사용자가 존재하지 않습니다.");
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("사용자 조회 오류: " + e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

	public void deleteUser(int userId) {
		try {
			String sql = "DELETE FROM USERS WHERE USER_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, userId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("사용자가 삭제되었습니다.");
			} else {
				System.out.println("해당 ID의 사용자가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("사용자 삭제 오류: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
