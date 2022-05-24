create table mvcboard (
    idx number primary key, 
    name varchar2(50) not null,
    title varchar2(200) not null, 
    content varchar2(2000) not null, 
    postdate date default sysdate not null, 
    ofile varchar2(200), -- 파일 경로 자체를 모두 긁어서 넣는 컬럼
    sfile varchar2(50) , -- 파일의 이름만을 저장하는 컬럼
    downcount number(5) default 0 not null, 
    pass varchar2 (50) not null, 
    visitcount number default 0 not null
    ); 
    
create sequence seq_board_num 
  increment by 1
  start with 1
  nocache
  
-- 더미 데이터 입력 (5개 남짓)
insert into mvcboard (idx, name, title,content,pass)
values (seq_board_num.nextval, '김유신', '자료실 제목1 입니다', '내용1','1234');
insert into mvcboard (idx, name, title,content,pass)
values (seq_board_num.nextval, '장보고', '자료실 제목2 입니다', '내용2','1234');
insert into mvcboard (idx, name, title,content,pass)
values (seq_board_num.nextval, '이순신', '자료실 제목3 입니다', '내용3','1234');
insert into mvcboard (idx, name, title,content,pass)
values (seq_board_num.nextval, '강감찬', '자료실 제목4 입니다', '내용4','1234');
insert into mvcboard (idx, name, title,content,pass)
values (seq_board_num.nextval, '대조영', '자료실 제목5 입니다', '내용5','1234');
commit; 

select * from mvcboard;