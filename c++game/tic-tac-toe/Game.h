#include <stdexcept>
#include <iostream>
#include <stdio.h>
#include <string>
using namespace std;

class Game{
    
    int board[3][3] = {{0,0,0},{0,0,0},{0,0,0}};   // 게임판 크기 [3][3] int 배열
    bool isEnd = false;    // 게임 종료 여부
    char winner;   // 승자
    int round = 1;
    
    
    public:
        Game(){};  // 기본 생성자
 
        // 현재 게임판의 상태 표시
        void getBoardStatus(){
            for(int i = 0; i < 3; i++) {
                for(int j = 0;  j < 3; j++){
                    if(board[i][j] == 0){
                        cout << "   ";
                    }else if (board[i][j] == 1){
                        cout << " X ";
                    }else if (board[i][j] == 2){
                        cout << " O ";
                    }
                    cout << " || ";
                }
                cout << endl;
            }
        }; 
        
        
        // 사용자 입력값 게임판 반영
        bool doAction(int i, int x, int y){
            if(board[x-1][y-1] == 0){    
                board[x-1][y-1] = i;
                round++;
                return true;
            }
            else{ 
               return false;
            }
        };
        
        
        // 게임판 검증, return 승자
        char checkBingo(){
        
            for(int i = 0; i < 3; i ++){
                if(board[i][0] > 0){
                    if((board[i][0] == board[i][1]) && (board[i][1] == board[i][2])){
                        if(board[0][0] == 1){
                            winner = 'X';
                        }else{
                            winner = 'O';
                        }
                        isEnd = true;
                        return winner;
                    }
                }
            }; // 승자 검증 : row
         
         
            for(int j = 0; j < 3; j ++){
                if(board[0][j] > 0){
                    if((board[0][j] == board[1][j]) && (board[1][j] == board[2][j])){
                        if(board[0][0] == 1){
                            winner = 'X';
                        }else{
                            winner = 'O';
                        }
                        isEnd = true;
                        return winner;
                    }
                }
            }; // 승자 검증 : colum
            
            if((board[0][0] > 0) && (board[0][0] == board[1][1]) && (board[1][1] == board[2][2])){
                if(board[0][0] == 1){
                    winner = 'X';
                }else{
                    winner = 'Y';
                }
                isEnd = true;
                return winner;
            }// 승자 검증 대각선 1
            
            if((board[0][2] > 0) && (board[0][2] == board[1][1]) && (board[1][1] == board[2][0])){
                if(board[0][2] == 1){
                    winner = 'X';
                }else{
                    winner = 'Y';
                }
                isEnd = true;
                return winner;
            }// 승자 검증 대각선 2
            
            return NULL; 
        };
        
        //field isEnd값 return
        bool getIsEnd(){
            return isEnd;
        }
        
        int getRound(){
            return round;
        }
    
};

