package homework0409;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountDAO {
	private Connection connection;

	public AccountDAO(Connection connection) {
		this.connection = connection;
	}

	public void createAccount(Account account) {
		try {
			String sql = "INSERT INTO ACCOUNT (ACCOUNT_ID, OPEN_DATE, OPEN_POINT, ACCOUNT_OWNER, BALANCE, IS_OPEN_BANK, ACCOUNT_NUM, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			LocalDate today = LocalDate.now();
			pstmt.setInt(1, account.getUserId());
			pstmt.setDate(2, java.sql.Date.valueOf(today)); // LocalDate를 java.sql.Date로 변환
			pstmt.setString(3, account.getOpenPoint());
			pstmt.setString(4, account.getAccountOwner());
			pstmt.setInt(5, account.getBalance());
			pstmt.setString(6, account.getIsOpenBank());
			pstmt.setString(7, account.getAccountNum());
			pstmt.setInt(8, account.getUserId());
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("계좌가 생성되었습니다.");
			} else {
				System.out.println("계좌 생성에 실패했습니다.");
			}
		} catch (SQLException e) {
			System.out.println("계좌 생성 오류: " + e.getMessage());
			if (e.getMessage().contains("ORA-00001")) {
				System.out.println("중복된 계좌 ID입니다.");
			} else if (e.getMessage().contains("ORA-02291")) {
				System.out.println("부모 키가 존재하지 않습니다. 올바른 회원 ID를 입력하세요.");
			} else {
				System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
			}
		} catch (Exception e) {
			System.out.println("에러: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Account readAccount(int accountId) {
		Account account = null;
		try {
			String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setOpenDate(rs.getDate("OPEN_DATE"));
				account.setOpenPoint(rs.getString("OPEN_POINT"));
				account.setAccountOwner(rs.getString("ACCOUNT_OWNER"));
				account.setBalance(rs.getInt("BALANCE"));
				account.setIsOpenBank(rs.getString("IS_OPEN_BANK"));
				account.setAccountNum(rs.getString("ACCOUNT_NUM"));
				account.setUserId(rs.getInt("USER_ID"));

				System.out.println("계좌 이름: " + account.getAccountId());
				System.out.println("개설 일자: " + account.getOpenDate());
				System.out.println("개설 지점: " + account.getOpenPoint());
				System.out.println("계좌주: " + account.getAccountOwner());
				System.out.println("잔액: " + account.getBalance());
				System.out.println("오픈뱅킹여부: " + account.getIsOpenBank());
				System.out.println("계좌번호: " + account.getAccountNum());
				System.out.println("회원ID: " + account.getUserId());
			} else {
				System.out.println("해당 ID의 계좌가 존재하지 않습니다.");
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("계좌 조회 오류: " + e.getMessage());
			e.printStackTrace();
		}
		return account;
	}

	public void deleteAccount(int accountId) {
		try {
			String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, accountId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("계좌가 삭제되었습니다.");
			} else {
				System.out.println("해당 ID의 계좌가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("계좌 삭제 오류: " + e.getMessage());
			e.printStackTrace();
		}
	}
}