#include <stdio.h>
#include <stdlib.h>
int main() {
printf("Hello_world!\n");
printf("Hello world!\n");
system("dir");
printf("delete_test.txt\n");
printf("delete test.txt\n");
system("del test_operation.txt");
system("del \"test operation.txt\"");
system("dir > Directory_List.txt");
system("dir > \"Directory List.txt\"");
printf("********test.txt********\n");
system("type Directory_List.txt");
system("type \"Directory List.txt\"");
}
