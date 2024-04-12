# Java-SQL-Connection-minibank

<aside>
💡 Java와 DataBase 연동한 프로그램 구현
</aside>

# 요구사항
- 회원 Entity를 포함한 최소 2개 이상의 Entity 설계
- 최소 두 개의 Entity는 Relation을 가지고 있음
- 기능은 기본적인 CRUD 기능을 가지고 있음
- 회원과 관련된 주제는 본인(팀원)과 협의 후 아이디어 선정하여 개발
- 객체지향, 자료구조, 디자인패턴, 예외처리 등 기본 기능 외 추가하거나 보완사항 발표내용 포함
- 주제 및 기능정의, 설계외 협업과정과 협의내용등도 발표 포함

# 4월 8일 2인 DB 연동 실습과제

### 요구사항

- 회원 Entity를 포함한 최소 2개 이상의 Entity 설계
- 최소 두 개의 Entity는 Relation을 가지고 있음
- 기능은 기본적인 CRUD 기능을 가지고 있음
- 회원과 관련된 주제는 본인(팀원)과 협의 후 아이디어 선정하여 개발
- 객체지향, 자료구조, 디자인패턴, 예외처리 등 기본 기능 외 추가하거나 보완사항 발표내용 포함
- 주제 및 기능정의, 설계외 협업과정과 협의내용등도 발표 포함

### 주제: 은행 관리 프로그램

### ERD

<img width="622" alt="image" src="https://github.com/Jyebin/shinhan-academy/assets/108725996/aac658a2-f6b7-4f99-919e-66f0f680218d">

### Table

| 테이블 | 컬럼 이름 | 데이터 타입 |
| --- | --- | --- |
| USERS | USER_ID | NUMBER(10) |
|  | USER_NAME | VARCHAR2(10) |
|  | PW | VARCHAR(20) |
|  | BIRTH | VARCHAR2(8) |
|  | EMAIL | VARCHAR2(30) |
|  | ADDRESS | VARCHAR2(30) |
|  | SEX_CODE | NUMBER(1) |
|  | ACCOUNT_NUM | NUMBER(12) |

| 테이블 | 컬럼 이름 | 데이터 타입 |
| --- | --- | --- |
| ACCOUNT | ACCOUNT_ID | NUMBER(10) |
|  | OPEN_DATE | DATE |
|  | OPEN_POINT | VARCHAR2(20) |
|  | ACCOUNT_OWNER | VARCHAR2(10) |
|  | BALANCE | NUMBER(15) |
|  | IS_OPEN_BANK | VARCHAR2(1) |
|  | ACCOUNT_NUM | VARCHAR2(15) |

| 테이블 | 컬럼 이름 | 데이터 타입 |
| --- | --- | --- |
| BANK | BANK_ID | NUMBER(10) |
|  | BANK_NAME | VARCHAR2(20) |
|  | BANK_TEL | VARCHAR2(20) |
|  | BANK_LOCATION | VARCHAR2(20) |
|  | ACCOUNT_ID | NUMBER(10) |

### Coding Conventions

- Class, Method : PascalCase
- Function, Variable : camelCase
- SQL : snake_case, UPPER_CASE

### Design Pattern

- DAO Pattern 사용
    - 이 프로젝트에서 사용된 DAO (Data Access Object) 패턴은 데이터베이스와의 상호 작용을 추상화하고 분리하여 관리하기 위한 디자인 패턴이다. 이 패턴은 데이터베이스의 CRUD(Create, Read, Update, Delete) 작업을 수행하는 메소드를 별도의 클래스로 구현하여 비즈니스 로직과 데이터 액세스를 분리하는 데 사용된다.
    - 여기서 사용된 DAO 클래스는 각각 UserDAO, AccountDAO, BankDAO로 구성되어 있다. 각 DAO 클래스는 데이터베이스의 특정 테이블과 상호 작용하고 해당 테이블에 대한 CRUD 작업을 제공한다.

### 기능

- 사용자 생성, 조회, 삭제
    - 이름, 비밀번호, 생년월일, 이메일, 주소, 성별, 계좌번호를 입력받아 user 객체에 설정
    - 사용자 ID를 통해 사용자 정보를 읽고, 삭제한다.
- 계좌 생성, 조회, 삭제
    - 계좌 ID, 개설지점, 계좌주, 잔액, 오픈뱅킹여부, 사용자 ID를 입력받아 account 객체에 설정
    - 계좌 ID를 통해 계좌 정보를 읽고, 삭제한다.
- 은행 생성, 조회, 삭제
    - 은행 ID, 은행 이름, 은행 전화번호, 은행 위치, 계좌 ID를 입력받아 account 객체에 설정
    - 은행 ID를 통해 은행 정보를 읽고, 삭제한다.

### 예외처리

- 각 DAO 클래스에서 데이터베이스 작업을 수행할 때 발생할 수 있는 예외 상황을 처리하는 데 사용된다. 예를 들어, 데이터베이스 연결 오류, SQL 문법 오류, 데이터베이스 제약 조건 위배 등의 예외가 발생할 수 있다.
- 각 DAO 클래스에서는 try-catch 블록을 사용하여 예외를 처리한다. catch 블록에서는 발생한 예외의 종류에 따라 적절한 안내 메시지를 출력하고, 문제를 해결하거나 사용자에게 알린다. 이를 통해 프로그램이 예상치 못한 상황에 대처하고 안정적으로 실행될 수 있도록 한다.
- 또한, 사용자 정의 예외 메시지를 출력하여 사용자가 발생한 문제를 이해하고 쉽게 해결할 수 있도록 돕는다. 이러한 예외 처리를 통해 프로그램의 안정성과 사용자 경험을 향상시킬 수 있다.

### UI

- 유니코드 사용
<img src="https://github.com/Jyebin/shinhan-academy/assets/108725996/38f02d85-8ff5-4a0e-b58e-5ea502d3abdf" alt="이미지1" width="220" height="70">
<img src="https://github.com/Jyebin/shinhan-academy/assets/108725996/dbd2d7f3-c473-4816-8123-f9bbd618b286" alt="이미지2" width="220" height="70">
