package homework0409;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAO {
	private Connection connection;

	public BankDAO(Connection connection) {
		this.connection = connection;
	}

	public void createBank(Bank bank) {
		try {
			String sql = "INSERT INTO BANK (BANK_ID, BANK_NAME, BANK_TEL, BANK_LOCATION, ACCOUNT_ID) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, bank.getBankId());
			pstmt.setString(2, bank.getBankName());
			pstmt.setString(3, bank.getBankTel());
			pstmt.setString(4, bank.getBankLocation());
			pstmt.setInt(5, bank.getAccountId());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("은행이 생성되었습니다.");
			} else {
				System.out.println("은행 생성에 실패했습니다.");
			}
		} catch (SQLException e) {
			System.out.println("은행 생성 오류: " + e.getMessage());
			if (e.getMessage().contains("ORA-00001")) {
				System.out.println("계좌 ID가 중복됩니다. 다른 계좌 ID를 입력하세요.");
			} else if (e.getMessage().contains("ORA-02291")) {
				System.out.println("부모 키가 존재하지 않습니다. 올바른 계좌 ID를 입력하세요.");
			}
		} catch (Exception e) {
			System.out.println("에러: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Bank readBank(int bankId) {
		Bank bank = null;
		try {
			String sql = "SELECT * FROM BANK WHERE BANK_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, bankId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				bank = new Bank();
				bank.setBankId(rs.getInt("BANK_ID"));
				bank.setBankName(rs.getString("BANK_NAME"));
				bank.setBankTel(rs.getString("BANK_TEL"));
				bank.setBankLocation(rs.getString("BANK_LOCATION"));
				bank.setAccountId(rs.getInt("ACCOUNT_ID"));

				// 은행 정보 출력
				System.out.println("은행 ID: " + bank.getBankId());
				System.out.println("은행 이름: " + bank.getBankName());
				System.out.println("은행 전화번호: " + bank.getBankTel());
				System.out.println("은행 위치: " + bank.getBankLocation());
				System.out.println("계좌 ID: " + bank.getAccountId());
			} else {
				System.out.println("해당 ID의 은행이 존재하지 않습니다.");
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("은행 조회 오류: " + e.getMessage());
			e.printStackTrace();
		}
		return bank;
	}

	public void deleteBank(int bankId) {
		try {
			String sql = "DELETE FROM BANK WHERE BANK_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, bankId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("은행이 삭제되었습니다.");
			} else {
				System.out.println("해당 ID의 은행이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("은행 삭제 오류: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
