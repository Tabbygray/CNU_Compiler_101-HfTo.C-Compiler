이 프로그램은 충남대학교 2021 2학기 컴파일러개론 1주차 과제를 위해 작성되었습니다.
작성자: 201702000 김준홍

이 프로그램은 .hf파일의 문법을 .c의 문법으로 변환하여 출력합니다.

<<.hf 문법>>
(echo “아무 내용”) 아무 내용 부분과 newline을 화면에 출력한다.
(del “파일 이름”) 현재 디렉토리의 해당 파일을 제거 한다
(list_dir) 현재 디렉토리의 모든 파일을 보여준다.
(show “파일 이름”) 파일 내용 전체를 화면에 보여준다.
(mov 명령 “파일 이름”) 명령 수행후 화면 출력 결과를 해당 파일에 저장한다.


>> 예제 <<
입력 : 파일명 test.hf
(echo “Hello world!”)
(list_dir)
(echo “delete test.txt”)
(del “test.txt”)
(mov list_dir “res”)
(echo “*********test.txt********”)
(show “test.txt”)

출력: 파일명 test.c
// 이건 어느 output 이나 미리 존재
#include <stdio.h>
#include <stdlib.h>
int main(){
// 여기부터 시작
printf(“Hello world!”);
system(“ls -al”);
printf(“delete test.txt”);
system(“rm ./test.txt”);
// …
// 이런 식으로 계속 바꾼다.
// system 대신 적당한 시스템 함수를 써도 무방함
// 권한에 주의한다.

