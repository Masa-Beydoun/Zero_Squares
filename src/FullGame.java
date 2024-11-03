import java.util.ArrayList;

public class FullGame {


    BoardLogic boardLogic = new BoardLogic();
    ArrayList <BoardLogic> visitedStates;


    FullGame(){

    }

    public BoardLogic move(String dir,boolean flag){
        boardLogic.move(dir,flag);
        for(BoardLogic visited : visitedStates){
            if(visited.equals(boardLogic)){
                System.out.println("visited state");
            }
        }
        return boardLogic;
    }
}
