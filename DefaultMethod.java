package homework0409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DefaultMethod {
    private Scanner sc = new Scanner(System.in);
    private Connection connection;
    private UserDAO userDAO;
    private BankDAO bankDAO;
    private AccountDAO accountDAO;

    public DefaultMethod() {
        try {
            // JDBC Driver 등록
            Class.forName("oracle.jdbc.OracleDriver");
            // 연결하기
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "BANKUSER", "BANK1234");
            userDAO = new UserDAO(connection);
            bankDAO = new BankDAO(connection);
            accountDAO = new AccountDAO(connection);
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 오류: " + e.getMessage());
            e.printStackTrace();
            exit();
        }
    }

    public void list() {
        System.out.println();
        // 이모티콘 유니코드
        System.out.println("\uD83C\uDFE6 \uD83D\uDCB5 \uD83D\uDCB0 \uD83C\uDFE6 \uD83D\uDCB5 \uD83D\uDCB0 \uD83C\uDFE6 \uD83D\uDCB5 \uD83D\uDCB0 \uD83C\uDFE6");
        System.out.println("\uD83D\uDCB0 어서오세요 은행 프로그램입니다! \uD83D\uDCB5");
        System.out.println("\uD83D\uDCB5 \uD83D\uDCB0 \uD83C\uDFE6 \uD83D\uDCB0 \uD83D\uDCB5 \uD83C\uDFE6 \uD83D\uDCB0 \uD83D\uDCB5 \uD83C\uDFE6 \uD83D\uDCB0");

        mainMenu();
    }

    public void mainMenu() {
        String menuNo = null;
        while (!"0".equals(menuNo)) {
            System.out.println();
            System.out.println("메인 메뉴");
            System.out.println("1. CreateUser     | 2. ReadUser       | 3. DeleteUser");
            System.out.println("4. CreateAccount  | 5. ReadAccount    | 6. DeleteAccount");
            System.out.println("7. CreateBank     | 8. ReadBank       | 9. DeleteBank");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("메뉴 선택 : ");
            menuNo = sc.nextLine();
            System.out.println();

            switch (menuNo) {
                case "0": exit(); break;
                case "1": createUser(); break;
                case "2": readUser(); break;
                case "3": deleteUser(); break;
                case "4": createAccount(); break;
                case "5": readAccount(); break;
                case "6": deleteAccount(); break;
                case "7": createBank(); break;
                case "8": readBank(); break;
                case "9": deleteBank(); break;
                default:
                    System.out.println("잘못된 메뉴 선택입니다. 다시 입력하세요.");
                    break;
            }
        }
    }

    // 사용자 생성 메소드
    public void createUser() {
        Users user = new Users();

        System.out.println("CreateUser");
        // 사용자 정보 입력 받기
        // 유저ID, 이름, 비밀번호, 생년월일, 이메일, 주소, 성별, 계좌번호를 입력받아 user 객체에 설정
        
        System.out.print("유저ID: ");
        user.setUserId(Integer.parseInt(sc.nextLine()));
        System.out.print("이름: ");
        user.setUserName(sc.nextLine());
        System.out.print("비밀번호: ");
        user.setPw(sc.nextLine());
        System.out.print("생년월일: ");
        user.setBirth(sc.nextLine());
        System.out.print("이메일: ");
        user.setEmail(sc.nextLine());
        System.out.print("주소: ");
        user.setAddress(sc.nextLine());
        System.out.print("성별(M/F): ");
        user.setSexCode(Integer.parseInt(sc.nextLine()));
        System.out.print("계좌번호: ");
        user.setAccountNum(sc.nextLine());

        System.out.println("이대로 생성하시겠습니까?");
        System.out.print("1.생성 | 2.취소 : ");
        String createFlag = sc.nextLine();
        if (createFlag.equals("1")) {
            try {
                // 데이터베이스에 사용자 정보 추가
                userDAO.createUser(user);
            } catch (Exception e) {
                System.out.println("사용자 생성 오류: " + e.getMessage());
                if (e.getMessage().contains("ORA-00001")) {
                    System.out.println("중복된 사용자 ID입니다. 다른 사용자 ID를 입력하세요.");
                } else if (e.getMessage().contains("ORA-02291")) {
                    System.out.println("부모 키가 존재하지 않습니다. 올바른 계좌번호를 입력하세요.");
                } else {
                    System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
                }
            }
        } else {
            System.out.println("사용자 정보 입력이 취소되었습니다.");
        }
    }

    // 사용자 조회 메소드
    public void readUser() {
        System.out.println("Read User");
        System.out.print("조회할 사용자 ID를 입력하세요: ");
        int userId = Integer.parseInt(sc.nextLine());
        userDAO.readUser(userId);
    }

    // 사용자 삭제 메소드
    public void deleteUser() {
        System.out.println("Delete User");
        System.out.print("삭제할 사용자 ID를 입력하세요: ");
        int userId = Integer.parseInt(sc.nextLine());
        userDAO.deleteUser(userId);
    }

    // 계좌 생성 메소드
    private void createAccount() {
        Account account = new Account();
        System.out.println("Create Account");
        System.out.print("계좌 ID: ");
        account.setAccountId(Integer.parseInt(sc.nextLine()));
        System.out.print("개설 지점: ");
        account.setOpenPoint(sc.nextLine());
        System.out.print("계좌주: ");
        account.setAccountOwner(sc.nextLine());
        System.out.print("잔액: ");
        account.setBalance(Integer.parseInt(sc.nextLine()));
        System.out.print("오픈뱅킹여부(Y/N): ");
        account.setIsOpenBank(sc.nextLine());
        System.out.print("회원 ID: ");
        account.setUserId(Integer.parseInt(sc.nextLine()));

        System.out.println("이대로 생성하시겠습니까?");
        System.out.print("1.생성 | 2.취소 ");
        String createFlag = sc.nextLine();
        if (createFlag.equals("1")) {
            // 데이터베이스에 계좌 정보 추가
            try {
                accountDAO.createAccount(account);
            } catch (Exception e) {
                System.out.println("계좌 생성 오류: " + e.getMessage());
                if (e.getMessage().contains("ORA-00001")) {
                    System.out.println("중복된 계좌 ID입니다. 다른 계좌 ID를 입력하세요.");
                } else if (e.getMessage().contains("ORA-02291")) {
                    System.out.println("부모 키가 존재하지 않습니다. 올바른 회원 ID를 입력하세요.");
                } else {
                    System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
                }
            }
        } else {
            System.out.println("계좌 개설이 취소되었습니다.");
        }
    }

    // 계좌 조회 메소드
    private void readAccount() {
        System.out.println("Read Account");
        System.out.print("조회할 계좌 ID를 입력하세요: ");
        int accountId = Integer.parseInt(sc.nextLine());
        accountDAO.readAccount(accountId);
    }

    // 계좌 삭제 메소드
    private void deleteAccount() {
        System.out.println("Delete Account");
        System.out.print("삭제할 계좌 ID를 입력하세요: ");
        int accountId = Integer.parseInt(sc.nextLine());
        accountDAO.deleteAccount(accountId);
    }

    // 은행 생성 메소드
    private void createBank() {
        Bank bank = new Bank();
        System.out.println("Create Bank");
        System.out.print("은행 ID: ");
        bank.setBankId(Integer.parseInt(sc.nextLine()));
        System.out.print("은행 이름: ");
        bank.setBankName(sc.nextLine());
        System.out.print("은행 전화번호: ");
        bank.setBankTel(sc.nextLine());
        System.out.print("은행 위치: ");
        bank.setBankLocation(sc.nextLine());
        System.out.print("계좌 ID: ");
        bank.setAccountId(Integer.parseInt(sc.nextLine()));

        System.out.println("이대로 생성하시겠습니까?");
        System.out.print("1.생성 | 2.취소 : ");
        String createFlag = sc.nextLine();
        if (createFlag.equals("1")) {
            // 데이터베이스에 은행 정보 추가
            try {
                bankDAO.createBank(bank);
            } catch (Exception e) {
                System.out.println("은행 생성 오류: " + e.getMessage());
                if (e.getMessage().contains("ORA-00001")) {
                    System.out.println("중복된 은행 ID입니다. 다른 은행 ID를 입력하세요.");
                } else if (e.getMessage().contains("ORA-02291")) {
                    System.out.println("부모 키가 존재하지 않습니다. 올바른 계좌 ID를 입력하세요.");
                } else {
                    System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
                }
            }
        } else {
            System.out.println("은행 정보 입력이 취소되었습니다.");
        }
    }

    // 은행 조회 메소드
    private void readBank() {
        System.out.println("Read Bank");
        System.out.print("조회할 은행 ID를 입력하세요: ");
        int bankId = Integer.parseInt(sc.nextLine());
        bankDAO.readBank(bankId);
    }

    // 은행 삭제 메소드
    private void deleteBank() {
        System.out.println("Delete Bank");
        System.out.print("삭제할 은행 ID를 입력하세요: ");
        int bankId = Integer.parseInt(sc.nextLine());
        bankDAO.deleteBank(bankId);
    }

    // 프로그램 종료 메소드
    public void exit() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("데이터베이스 연결 종료 오류: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("\uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B");
        System.out.println("\uD83D\uDC9B 감사합니다 프로그램 종료합니다! \uD83D\uDC97");
        System.out.println("\uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B \uD83D\uDC97 \uD83D\uDC9B");
        System.exit(0);
    }

    public static void main(String[] args) {
        DefaultMethod dm = new DefaultMethod();
        dm.list();
    }
}
