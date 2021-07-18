// Example program
#include <stdlib.h>
#include "Game.h"

int main()
{
    
    int nowPlayer = 0;
    bool palyerValidation = false;
    int x,y;
    char winnerResult;
    
    Game tic1 = Game();
    cout << "====== Game start! ======" << endl;
    
    do{
        char valid;
        cout << " Who is first player ( X or O )" ;
        cin >> valid;
        if((valid == 0x58) || (valid == 0x78)){
            nowPlayer = 1;
            palyerValidation = true;
        }else if((valid == 0x4F) || (valid == 0x6F)){
            nowPlayer = 2;
            palyerValidation = true;
        }else{
            cout << "select again (input X or O)" << endl;
        }
    }while(!palyerValidation); // 첫 플레이어 선택 X = 1, O = 0
    
    
    
    tic1.getBoardStatus();
    cout << endl;
    do{
        cout << "Round " << tic1.getRound() << " start!" << endl;
        cout << "(board size is 3x3) please input row number you want to select : ";
        cin >> x;
        cout << "(board size is 3x3) please input colum number you want to select : ";
        cin >> y;
        
        //사용자 입력값 게임 보드 반영
        if(!tic1.doAction(nowPlayer, x, y)){
            cout << "this is already filled, please select other one" << endl;
            continue;
        }
        
        //현재 보드 상황
        tic1.getBoardStatus();
        
        //승리 여부 확인
        winnerResult= tic1.checkBingo();
        
        //다음 플레이어로 변경
        if(nowPlayer == 1){
            nowPlayer = 2;
        }else{
            nowPlayer =1;
        }
        
        cout << endl;
    }while(!tic1.getIsEnd());
    
    cout << " Winner is " << winnerResult << "!!" << endl;
    cout << "====== Game end thank you! ======" << endl;
    system("pause");
	return 0;
}
