/* 사용자 테이블*/

CREATE TABLE USER_INFO (
user_id varchar(100) not null,
login_id varchar(100) not null,
"password" varchar(200) NOT null,
user_name varchar(20) NOT null,
employee_number varchar(100) null,
mobile_number varchar(20) null,
email_address varchar(50) null,
dept_name varchar(50) null,
position_name varchar(50) null,
company_id int8 not null,
authority_id varchar(100) null,
login_fail_count int8 null default 0,
lock_yn boolean null default false,
unlock_user_id varchar(100) null,
unlock_reason varchar(1000) null,
use_yn boolean DEFAULT true NOT null,
last_login_date timestamptz null,
last_login_ip varchar(100) null,
regist_date timestamptz DEFAULT now() null,
regist_user_id varchar(100) DEFAULT 'system'::character varying,
change_date timestamptz DEFAULT now() null,
change_user_id varchar(100) DEFAULT 'system'::character varying,
CONSTRAINT user_info_pk PRIMARY KEY (user_id)
);

comment on column USER_INFO.USER_ID is '사용자 ID';
comment on column USER_INFO.login_id is '로그인 ID';
comment on column USER_INFO.password is '비밀번호';
comment on column USER_INFO.user_name is '사용자명';
comment on column USER_INFO.employee_number is '사원번호';
comment on column USER_INFO.mobile_number is '휴대폰번호';
comment on column USER_INFO.email_address is '이메일주소';
comment on column USER_INFO.position_name is '직위명';
comment on column USER_INFO.company_id is '회사 ID';
comment on column USER_INFO.authority_id is '권한 ID';
comment on column USER_INFO.login_fail_count is '로그인실패 횟수';
comment on column USER_INFO.lock_yn is '잠금여부';
comment on column USER_INFO.unlock_user_id is '잠금해제자 ID';
comment on column USER_INFO.unlock_reason is '잠금해제사유';
comment on column USER_INFO.use_yn is '사용여부';
comment on column USER_INFO.last_login_date is '마지막로그인시간';
comment on column USER_INFO.last_login_ip is '마지막로그인IP';
comment on column USER_INFO.regist_date is '등록일시';
comment on column USER_INFO.regist_user_id is '등록사용자 ID';
comment on column USER_INFO.change_date is '변경일시';
comment on column USER_INFO.change_user_id is '변경사용자 ID';
comment on column USER_INFO.dept_name is '부서명';

/* 코드 그룹 테이블 */

CREATE TABLE CODE_GROUP (
GROUP_ID varchar(100) NOT NULL,
GROUP_NAME varchar(100) NULL,
USE_YN BOOLEAN DEFAULT TRUE NOT NULL,
REGIST_DATE timestamptz DEFAULT now() NOT NULL,
REGIST_USER_ID varchar(100) DEFAULT 'system'::character varying NOT NULL,
change_date timestamptz DEFAULT now() NOT NULL,
change_user_id varchar(100) DEFAULT 'system'::character varying NOT NULL,
ATTRIBUTE1 VARCHAR(100),
ATTRIBUTE2 VARCHAR(100),
ATTRIBUTE3 VARCHAR(100),
ATTRIBUTE4 VARCHAR(100),
ATTRIBUTE5 VARCHAR(100),
ATTRIBUTE6 VARCHAR(100),
ATTRIBUTE7 VARCHAR(100),
ATTRIBUTE8 VARCHAR(100),
ATTRIBUTE9 VARCHAR(100),
CONSTRAINT CODE_GROUP_PK PRIMARY KEY (GROUP_ID)
);

comment on column CODE_GROUP.GROUP_ID is '그룹코드ID';
comment on column CODE_GROUP.GROUP_NAME is '그룹명';
comment on column CODE_GROUP.USE_YN is '사용여부';
comment on column CODE_GROUP.REGIST_DATE is '등록일시';
comment on column CODE_GROUP.REGIST_USER_ID is '등록사용자 ID';
comment on column CODE_GROUP.change_date is '변경일시';
comment on column CODE_GROUP.change_user_id is '변경사용자 ID';
comment on column CODE_GROUP.ATTRIBUTE1 is '사용자정의1';
comment on column CODE_GROUP.ATTRIBUTE2 is '사용자정의2';
comment on column CODE_GROUP.ATTRIBUTE3 is '사용자정의3';
comment on column CODE_GROUP.ATTRIBUTE4 is '사용자정의4';
comment on column CODE_GROUP.ATTRIBUTE5 is '사용자정의5';
comment on column CODE_GROUP.ATTRIBUTE6 is '사용자정의6';
comment on column CODE_GROUP.ATTRIBUTE7 is '사용자정의7';
comment on column CODE_GROUP.ATTRIBUTE8 is '사용자정의8';
comment on column CODE_GROUP.ATTRIBUTE9 is '사용자정의9';

/* 상세 코드 테이블 */
CREATE TABLE CODE_INFO (
GROUP_ID varchar(100) NOT NULL,
CODE VARCHAR(100) not null,
CODE_NAME varchar(100) NULL,
USE_YN BOOLEAN DEFAULT TRUE NOT NULL,
SORT_NUMBER INT not null,
REGIST_DATE timestamptz DEFAULT now() NOT NULL,
REGIST_USER_ID varchar(100) DEFAULT 'system'::character varying NOT NULL,
change_date timestamptz DEFAULT now() NOT NULL,
change_user_id varchar(100) DEFAULT 'system'::character varying NOT NULL,
ATTRIBUTE1 VARCHAR(100),
ATTRIBUTE2 VARCHAR(100),
ATTRIBUTE3 VARCHAR(100),
ATTRIBUTE4 VARCHAR(100),
ATTRIBUTE5 VARCHAR(100),
ATTRIBUTE6 VARCHAR(100),
ATTRIBUTE7 VARCHAR(100),
ATTRIBUTE8 VARCHAR(100),
ATTRIBUTE9 VARCHAR(100),
CONSTRAINT CODE_INFO_PK PRIMARY KEY (GROUP_ID, CODE)
);

comment on column CODE_INFO.GROUP_ID is '그룹 ID';
comment on column CODE_INFO.CODE is '코드값';
comment on column CODE_INFO.CODE_NAME is '코드명';
comment on column CODE_INFO.USE_YN is '사용여부';
comment on column CODE_INFO.SORT_NUMBER is '정렬순서';
comment on column CODE_INFO.REGIST_DATE is '등록일시';
comment on column CODE_INFO.REGIST_USER_ID is '등록사용자 ID';
comment on column CODE_INFO.change_date is '변경일시';
comment on column CODE_INFO.change_user_id is '변경사용자 ID';
comment on column CODE_INFO.ATTRIBUTE1 is '사용자정의1';
comment on column CODE_INFO.ATTRIBUTE2 is '사용자정의2';
comment on column CODE_INFO.ATTRIBUTE3 is '사용자정의3';
comment on column CODE_INFO.ATTRIBUTE4 is '사용자정의4';
comment on column CODE_INFO.ATTRIBUTE5 is '사용자정의5';
comment on column CODE_INFO.ATTRIBUTE6 is '사용자정의6';
comment on column CODE_INFO.ATTRIBUTE7 is '사용자정의7';
comment on column CODE_INFO.ATTRIBUTE8 is '사용자정의8';
comment on column CODE_INFO.ATTRIBUTE9 is '사용자정의9';

/* 메뉴 테이블 */
CREATE TABLE MENU_INFO (
MENU_ID varchar(100) NOT NULL,
UPPER_MENU_ID varchar(100) NULL,
MENU_NAME varchar(100) NOT NULL,
URL varchar(200) NULL,
SORT_NUMBER int8 not NULL,
DESCRIPTION varchar(300) NULL,
COMPANY_ID int8 NULL,
USE_YN BOOLEAN DEFAULT TRUE NOT NULL,
SYSTEM_TYPE varchar(100) not NULL,
LANGUAGE varchar(100) NOT NULL,
REGIST_DATE timestamptz NULL,
REGIST_USER_ID varchar(100) NULL,
CHANGE_DATE timestamptz NULL,
CHANGE_USER_ID varchar(100) NULL,
CONSTRAINT MENU_INFO_PK PRIMARY KEY (MENU_ID)
);

comment on column MENU_INFO.MENU_ID is '메뉴 ID';
comment on column MENU_INFO.UPPER_MENU_ID is '상위메뉴 ID';
comment on column MENU_INFO.MENU_NAME is '메뉴명';
comment on column MENU_INFO.URL is 'URL';
comment on column MENU_INFO.SORT_NUMBER is '정렬 순서';
comment on column MENU_INFO.DESCRIPTION is '메뉴 설명';
comment on column MENU_INFO.COMPANY_ID is '회사 ID';
comment on column MENU_INFO.USE_YN is '사용 여부';
comment on column CODE_INFO.REGIST_DATE is '등록일시';
comment on column CODE_INFO.REGIST_USER_ID is '등록사용자 ID';
comment on column CODE_INFO.CHANGE_DATE is '변경일시';
comment on column CODE_INFO.CHANGE_USER_ID is '변경사용자 ID';
comment on column MENU_INFO.SYSTEM_TYPE is '시스템 구분';
comment on column MENU_INFO.LANGUAGE is '언어';