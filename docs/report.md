# Develoment Report
## **1. 개발 배경**
스마트폰 사용자가 90% 이상이 된 현재 모바일 세상에 살고있다. 이 사회의 흐름에 따라 학교 도서관의 서비스를 모바일에서도 사용가능하게 개발해보려고 한다.

## **2. 개발의 목표 및 내용**
### 2.1. 최종 목표 및 내용

> 본 Term Project는 다음 기능을 구현하는 것을 목표로 한다.

1. 회원 관리
    - 로그인 (Sign In)
    - 회원가입 (Sign Up)
    - 로그아웃 (Sign Out)
2.  도서관 자리예약 서비스
    - 자리 예약 상태 확인 (Valid Seat Check)
    - 자리 예약 (Reserved Seat)
    - 자리 예약 취소 (Unreserved Seat)

### 2.2. 설계 과정

- **개발 환경:** MacOs
- **개발 IDE:** Android Studio
- **개발 버전:** 28 API(Android)
- **테스트 장치:** VM(nexus 5)

### 2.3. 세부 개발 내용
1. 구조 설계하기

    모바일 자리 예약 시스템을 구현하기 위해 시스템 구조를 설계하고 다음과 같이 UML로 작성했습니다.
<p ALIGN="CENTER">
<img WIDTH="400px" src="image/mobile-lib-diagram.png">
</p>

2. 프로젝트 구조화 하기 

    프로젝트 중간 중간에 생길 수 있는 개발 이슈를 원활하게 해결하기 위해 다음과 같이 클래스와 디렉토리를 분류하여 설계하였습니다.
```
.
├── AndroidManifest.xml
├── java
│   └── me
│       └── j911
│           └── term_project
│               └── mobile_library
│                   ├── Reserve.java
│                   ├── SignIn.java
│                   ├── SignUp.java
│                   ├── Splash.java
│                   ├── controller
│                   │   ├── AccountController.java
│                   │   └── ReserveController.java
│                   ├── entites
│                   │   └── Seat.java
│                   ├── helper
│                   │   └── DBHelper.java
│                   └── interfaces
│                       ├── IAccount.java
│                       ├── IAccountController.java
│                       ├── IReserveController.java
│                       └── ISeat.java
└── res
    ├── drawable
    │   ├── background.jpeg
    │   ├── rounded.xml
    │   └── splash.png
    ├── layout
    │   ├── reservescreen.xml
    │   ├── signinscreen.xml
    │   ├── signupscreen.xml
    │   └── splashscreen.xml
    ├── values
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── styles.xml
    └── values-ko
        └── strings.xml

21 directories, 38 files

```

3. 유저 인터페이스 작성하기
    다음과 같이 유저 인터페이스를 작성했습니다.
<p ALIGN="CENTER">
<img src="screenshot/splash.png" WIDTH="160px">
<img src="screenshot/signin.png" WIDTH="160px">
<img src="screenshot/signup.png" WIDTH="160px">
<img src="screenshot/main.png" WIDTH="160px">
</p>
<p ALIGN="CENTER">(왼쪽 부터 스플래시, 로그인, 회원가입, 자리예약)</p>

4. 데이터베이스 설계 
    실제 구동 가능한 시스템을 개발하기 위하여 간단하게 다음과 같은 데이터베이스를 설계하였습니다.
    <p ALIGN="CENTER">
    <img src="image/db.png">
    </p>
5. 기능 구현하기 

    다음과 같은 기능을 구현하였습니다. 
    
    **Account 관련**    
    - public String getUserId()
    - public String getUserName()
    - public String getUserPassword()
    - public int signin(int stdId, String stdPassword)
    - public int signup(String stdName, int stdId, String stdPassword)
    - public int signout()
    - public boolean isLoggedIn()

    **Reserve 관련**    
    - public boolean reserveSeat(String seatId, int accountId)
    - public boolean unreserveSeat(String seatId)
    - public ISeat getReservedInfoById(String seatId)
    - public ISeat[] getAllReservedInfo()
    - public int getReservedStdId()
    - public String getSeatId()
    - public boolean isReserved()

    ** 자세한 내용은 **3.1 소스코드 분석 참고**

## **3. 개발 결과**
### 3.1. 소스코드 분석
#### SQLite 연동
본 시스템을 개발하기 위해선 데이터베이스 연동이 필수적이였다. 따라서 안드로이드에 기본 내장되어있는 `SQLite`를 활용해 개발을 준비했다. 
그렇게 하여 다음과 같이 데이터베이스를 제어하는 `DBHelper`클래스를 작성하게 되었다.

```java
package me.j911.term_project.mobile_library.helper;

public class DBHelper extends SQLiteOpenHelper { // SQLite를 사용하기 위해서는 SQLiteOpenHelper 클래스를 상속 받아서 사용해야 한다.

    private SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version); // SQLiteOpenHelper의 생성자에 Context를 입력하여 호출한다. 
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 초기 데이터베이스를 호출할 때 실행되는 함수로 앱이 설치되고 최초 실행에서만 실행된다.
        this.db = db;
        initializeTables(); // 데이터베이스 테이블을 초기화 하기 위해 만든 메서드
    }

    private void initializeTables() {
        db.execSQL("CREATE TABLE ACCOUNT (idx INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name TEXT, password TEXT);"); // 유저 관련 테이블을 생성한다.
        db.execSQL("CREATE TABLE RESERVE (idx INTEGER PRIMARY KEY AUTOINCREMENT, seat_id TEXT, account_id INTEGER, reserved TINYINT(1) DEFAULT 0);"); // 자리 예약과 관련돤 테이블을 생성한다.
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-1', '')"); // 기본적인 자리정보를 테이블에 삽입한다.
        // (...) 생략
        db.execSQL("INSERT INTO RESERVE (seat_id, account_id) VALUES('A-8', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { } 

}
```

#### AccountController
회원관리를 위한 컨트롤러 클래스를 작성했다.
```java
package me.j911.term_project.mobile_library.controller;

public class AccountController implements IAccountController { // Interface를 구현한다. (참조: me/j911/term-project/mobile-lib/interface)

    private static AccountController instance; // 싱글톤 패턴을 구현하기 위한 스태틱 인스턴스 선언

    private boolean isLoggedIn; // 로그인 유무를 저장할 변수 선언
    private DBHelper dbHelper; // DB 컨트롤러 객체를 담을 변수 선언
    private SQLiteDatabase db; // 쿼리문을 전송할 수 있는 인스턴스 변수 선언

    private int accountIdx; // 유저 인덱스를 담을 변수 선언
    private int accountId; // 유저 아이디(학번)을 담을 변수 선언
    private String accountName; // 유저 이름을 담을 변수 선언

    public String dbName = "account.db"; // 사용할 데이터베이스 파일 이름 지정
    public int dbVersion = 1; // 데이터 버전 정보
    public String tag = "SQLite"; // Log.d로 로그 님길때 사용할 태그

    public AccountController(Context context) { // AccountController생성자
        isLoggedIn = false; // 처음 로그인 상태는 false로 지정
        dbHelper = new DBHelper(context, dbName, null, dbVersion); // DBHelper 인스턴스 생성
        try {
            db = dbHelper.getWritableDatabase(); // db 객체를 DBHelper를 통해 생성
        } catch (SQLiteException e) {
            e.printStackTrace(); // 에러시 에러 출력
            Log.e(tag, "database error"); // 데이터베이스 에러 메세지 출력
        }
    }

    @Override
    public int signin(int stdId, String stdPassword) { // 로그인 기능 구현
        String VALID_CHECK_SQL = "SELECT * FROM ACCOUNT WHERE id = " + stdId + ";"; // stdId로 account를 조회하는 SQL문
        Cursor cursor = db.rawQuery(VALID_CHECK_SQL, null); // SQL 질의
        if (!cursor.moveToNext()) return 404; // 데이터가 없을 시 404 리턴

        String password = cursor.getString(3); // password를 저장
        if (!stdPassword.equals(password)) return 403; // 암호가 불일치시 403 리턴

        accountIdx = cursor.getInt(0); // 사용자 인덱스 저장
        accountId = cursor.getInt(1); // 사용자 아이디 저장
        accountName = cursor.getString(2); // 사용자 이름 저장
        isLoggedIn = true; // 로그인 상태를 true로 변경
        return 200; // 200을 리턴
    }

    @Override
    public int signup(String stdName, int stdId, String stdPassword) { // 회원가입 구현
        String CONFLICT_CHECK_SQL = "SELECT * FROM ACCOUNT WHERE id = " + stdId + ";"; // 아이디가 존재하는지 확인하는 SQL문
        Cursor cursor = db.rawQuery(CONFLICT_CHECK_SQL, null); // 질의
        if (cursor.moveToNext()) return 409; // 계정이 존재하면 409 Conflict 리턴

        String INSERT_ACCOUNT_SQL = "INSERT INTO ACCOUNT (id, name, password) VALUES("+stdId+", '"+ stdName +"', '"+ stdPassword +"');"; // 계정을 추가하는 SQL문
        db.execSQL(INSERT_ACCOUNT_SQL); // 질의
        return 201; // 201 리턴
    }

    @Override
    public int signout() { // 로그아웃 구현
        isLoggedIn = false; // 로그인 상태를 false로 변경
        accountName = null; // 계정 정보를 초기화함
        accountId = 0; // 계정 정보를 초기화함
        accountIdx = 0; // 계정 정보를 초기화함
        return 200; // 200 리턴
    }

    @Override
    public boolean isLoggedIn() { // 로그인 여부를 확인
        return isLoggedIn;
    }

    public int getAccountIdx() { // 계정 인덱스를 확인
        return accountIdx;
    }

    public int getAccountId() { // 계정 아이디를 확인
        return accountId;
    }

    public String getAccountName() { // 계정 이름을 확인
        return accountName;
    }

    public static AccountController getInstance (Context context) { // 싱글톤을 구현하기 위한 Instance 호출 메서드
        if (instance == null) { // 인스턴스가 없으면
            instance = new AccountController(context); // 인스턴스 생성
        }
        return instance; // 인스턴스 반환
    }


}

```
#### ReserveController
자리예약 관리를 위한 컨트롤러 클래스를 작성했다.

```java
package me.j911.term_project.mobile_library.controller;

public class ReserveController implements IReserveController { // Interface를 구현한다. (참조: me/j911/term-project/mobile-lib/interface)
    private static ReserveController instance; // 싱글톤 패턴을 구현하기 위한 스태틱 인스턴스 선언

    private DBHelper dbHelper; // DB 컨트롤러 객체를 담을 변수 선언
    private SQLiteDatabase db;// 쿼리문을 전송할 수 있는 인스턴스 변수 선언

    public String dbName = "reserve.db"; // 사용할 데이터베이스 파일 이름 지정
    public int dbVersion = 1; // 데이터 버전 정보
    public String tag = "SQLite"; // Log.d로 로그 님길때 사용할 태그

    public ReserveController(Context context) { // ReserveController 생성자
         dbHelper = new DBHelper(context, dbName, null, dbVersion); // DBHelper 인스턴스 생성
        try {
            db = dbHelper.getWritableDatabase(); // db 객체를 DBHelper를 통해 생성
        } catch (SQLiteException e) {
            e.printStackTrace(); // 에러시 에러 출력
            Log.e(tag, "database error"); // 데이터베이스 에러 메세지 출력
        }
    }

    @Override
    public boolean unreserveSeat(String seatId) { // 자리 예약을 취소하는 메서트
        String UNRESERVE_SEAT = "UPDATE RESERVE SET reserved = 0 WHERE seat_id = '"+seatId+"'"; // 자리 예약을 취소하는 SQL문
        db.execSQL(UNRESERVE_SEAT); // 질의
        return true; // 성공 반환
    }

    @Override
    public Seat getReservedInfoById(String seatId) { // Seat ID를 통해 자리 정보를 받은 메서트
        String GET_ALL_RESERVED = "SELECT * FROM RESERVE WHERE seat_id = '"+seatId+"'"; // 자리정보를 검색하는 SQL문
        Cursor cursor = db.rawQuery(GET_ALL_RESERVED, null); // 질의
        if (!cursor.moveToNext()) return null; // 값이 비여있으면 null반환
        int accountId = cursor.getInt(2); // accountId 초기화
        boolean isReserve = cursor.getInt(3) != 0; // 예약 유무 초기화
        Seat seat = new Seat(seatId, accountId, isReserve); // 반환할 자리 객체 선언

        return seat; // 자리 반환
    }

    @Override
    public Seat[] getAllReservedInfo() { // 모든 자리 정보를 조회
        String GET_ALL_RESERVED_INFO = "SELECT * FROM RESERVE"; // 자리정보를 검색하는 SQL문
        Cursor cursor = db.rawQuery(GET_ALL_RESERVED_INFO, null); // 잘의
        Seat[] seats = new Seat[cursor.getCount()]; // 조회 개수만큼의 자리 배열 선언
        while (cursor.moveToNext()) { // 레코드를 넘기며 반복
            String seatId = cursor.getString(1); // 자리 아이디 저장
            int accountId = cursor.getInt(2); // 자리 예약자 저장
            boolean isReserve = cursor.getInt(3) != 0; // 예약 유무 저장
            seats[cursor.getPosition()] = new Seat(seatId, accountId, isReserve); // 객체 선언
        }
        return seats; // 자리 정보 반환
    }

    @Override
    public boolean reserveSeat(String seatId, int accountId) { // 자리를 예약하는 메서트
        String RESERVE_SEAT = "UPDATE RESERVE SET reserved = 1, account_id = "+ accountId +" WHERE seat_id = '"+seatId+"'"; // 자리를 예약하는 SQL문
        db.execSQL(RESERVE_SEAT); //질의 
        return true; // true 반환
    }

    public static ReserveController getInstance(Context context) { // 싱글톤을 구현하기 위한 Instance 호출 메서드 
        if (instance == null) { // 인스턴스가 없으면
            instance = new ReserveController(context); // 인스턴스 생성
        }
        return instance; // 인스턴스 반환
    }
}

```

#### Seat
자리정보를 담기 위한 클래스를 작성했다.

```java
package me.j911.term_project.mobile_library.entites;

public class Seat implements ISeat { // Interface를 구현한다. (참조: me/j911/term-project/mobile-lib/interface)

    private boolean isReserved; // 자리 예약 유무
    private String seatId; // 자리 아이디
    private int accountId; // 예약자 아이디

    public Seat(String seatId, int accountId, boolean isReserved) { // 자리 생성자
        this.seatId = seatId; // 자리 아이디 초기회
        this.accountId = accountId; // 예약자 아이디 초기화
        this.isReserved = isReserved; // 예약 유무 초기화
    }

    @Override
    public int getReservedStdId() { // 예약자 아이디 조회
        return accountId;
    }

    @Override
    public String getSeatId() { // 자리 아이디 조회
        return seatId;
    }

    @Override
    public boolean isReserved() { // 자리 예약 유무 조회
        return isReserved;
    }
}

```


#### Splash, SplashScreen
초기 로딩 화면을 구현하기 위한 화면과 클래스 생성

```java
package me.j911.term_project.mobile_library;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000; // 디스플레이 시간

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable(){ // 지정된 시간 이후 실행
            @Override
            public void run() {
                Intent loginIntent = new Intent(Splash.this, SignIn.class); // 페이지 이동 인텐트 생성
                Splash.this.startActivity(loginIntent); // 인텐트 실행
                Splash.this.finish(); // 다음 인텐트 종료시 해당 컴포넌트도 종료
            }
        }, SPLASH_DISPLAY_LENGTH); // 시간 설정
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" // vertical 모드
    android:padding="0dp">

    <ImageView
        android:id="@+id/splashscreen"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_gravity="top"
        android:layout_weight="1" // 가중치 1로 설정
        android:adjustViewBounds="true"
        android:background="@drawable/splash" // 배경이미지 설정
        android:cropToPadding="false"
        android:scaleType="centerCrop" // 화면을 꽉차게 하기위해 설정
        android:src="@drawable/splash" />

    <TextView
        android:id="@+id/footer" // 바닥글
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/background_dark" // 배경 색상을 설정
        android:gravity="bottom" // 바닥에 붙게 설정
        android:text="@string/footer"
        android:textAlignment="center" // 정렬을 가운데 정렬로 설정
        android:textColor="@android:color/darker_gray" /> // 폰트 색상을 설정
</LinearLayout>
```

#### Signin, SigninScreen
로그인을 구현하기 위한 화면과 클래스 생성
```java
package me.j911.term_project.mobile_library;

public class SignIn extends AppCompatActivity {

    private AccountController accountController; // AccountController를 담을 객체 변수 선언
    private EditText stdIdInput; // id입력창을 선언
    private EditText stdPwInput; // pw입력창을 선언
    private Button signInButton; // 로그인 버튼을 선언
    private TextView signUpButton; // 회원가입 버튼(텍스트)를 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinscreen);

        accountController = AccountController.getInstance(getApplicationContext()); // AccountInstance를 초기화
        stdIdInput = (EditText) findViewById(R.id.loginStdIdInput); // xml 바인딩
        stdPwInput = (EditText) findViewById(R.id.loginStdPwInput); // xml 바인딩
        signInButton = (Button) findViewById(R.id.loginSigninBtn); // xml 바인딩
        signUpButton = (TextView) findViewById(R.id.loginSignupBtn); // xml 바인딩
        signInButton.setOnClickListener(new View.OnClickListener() { // 로그인 버튼 클릭 이벤트
            @Override
            public void onClick(View v) {
                signin(); // 로그인 메서드 실행
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼 클릭 이벤트
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(SignIn.this, SignUp.class); // 회원가입 액티비티 전환 인텐트 선언
                SignIn.this.startActivity(signupIntent); // 화면 이동
            }
        });

    }

    private void signin() { // 로그인 메서드
        String rawId = stdIdInput.getText().toString(); // id 를 담음
        String password = stdPwInput.getText().toString(); // pw 를 담음

        if (rawId.equals("") || password.equals("")) { // 아이디 또는 비밀번호가 빈칸일시
            Toast.makeText(getApplicationContext(), R.string.bad_inputs, Toast.LENGTH_SHORT).show(); // 잘못된 리퀘스트 알림(토스트)
            return;
        }

        int id = Integer.parseInt(rawId, 10); // 아이디를 INT로 변환
        int result = accountController.signin(id, password); // 로그인 메서트 호출
        switch (result) {
            case 200: // 성공시
                Toast.makeText(getApplicationContext(),  getString(R.string.hello) + " " + accountController.getAccountName(),  Toast.LENGTH_SHORT).show(); // 환영 알림 
                Intent mainIntent = new Intent(SignIn.this, Reserve.class); // 예약 페이지 이동 인텐트
                SignIn.this.startActivity(mainIntent); // 이동
                break;
            case 403: // 비밀번호기 일치하지 않을 시
                Toast.makeText(getApplicationContext(), R.string.account_no_mached_password, Toast.LENGTH_SHORT).show(); // 비밀번호가 잘못됬다는 알림
                break;
            case 404: // 계정을 찾을 수 없을 시 
                Toast.makeText(getApplicationContext(), R.string.account_not_found, Toast.LENGTH_SHORT).show(); // 계정을 찾을 수 없음 호출
                break;
            default: // 기타 오류일 시
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show(); // 오류 알림
        }
    }
}

```

``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/background"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".SignIn">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:background="@drawable/rounded"
        android:baselineAligned="false"
        android:orientation="vertical"
        android:padding="20dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/signin"
            android:textAlignment="center"
            android:textSize="30dp" />

        <EditText
            android:id="@+id/loginStdIdInput"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/student_id"
            android:inputType="number" />

        <EditText
            android:id="@+id/loginStdPwInput"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/student_password"
            android:inputType="textPassword" />

        <Button
            android:id="@+id/loginSigninBtn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#e96363"
            android:text="@string/signin" />

        <TextView
            android:id="@+id/loginSignupBtn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingTop="20dp"
            android:text="@string/signup"
            android:textAlignment="center" />
    </LinearLayout>
</LinearLayout>
```
### 3.2 실행결과
### 3.3 디버깅 리포트

## **4. 결과물 평가 항목**
|순번|평가항목|목표|결과|
|---|--|--|--|
|01|중복 학번을 통한 회원가입|회원가입 실패|회원가입 실패|
|02|정상적인 회원가입|회원가입 성공|회원가입 성공|
|03|정상적인 로그인|로그인 실패|로그인 실패|
|04|존재하지 않는 계정으로 로그인|로그인 실패|로그인 실패|
|05|일치하지 않는 비밀번호로 로그인|로그인 실패|로그인 실패|
|06|도서관 자료 조회|조회 성공|조회 성공|
|07|예약된 자리 예약|예약 실패|예약 실패|
|08|본인이 예약한 자리 예약 취소|취소 성공|취소 성공|
|09|타인이 예약한 자리 예약 취소|취소 실패|취소 실패|


## **5. 활용성 및 기대효과**
- 시스템화된 도서관 자리 시스템을 통한 도서관 활성화 및 학생이 학업 증진
- 편리한 자리 예약 및 취소 

## **6. 참고할 기술 및 자료**
> 프로젝트 진행시 참고할 기술
- SQLite
- Android Cache
- Android 교재