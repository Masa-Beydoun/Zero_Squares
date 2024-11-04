import java.util.ArrayList;

public class FullGame {


    BoardLogic boardLogic = new BoardLogic();

    ArrayList <BoardLogic> visitedStates = new ArrayList<>();

    public BoardLogic move(String dir,boolean flag){
//        System.out.println("full game");
        BoardLogic myBoardLogic = boardLogic.move(dir);
        boardLogic.possibleBoards();
        boardLogic = myBoardLogic;

        boolean found = false;
        for(BoardLogic visited : visitedStates){
            if(visited.equals(boardLogic)){
                System.out.println("this state has already been visited");
                found = true;
            }
        }
        if(!found){
            visitedStates.add(boardLogic);
        }
//        System.out.println("visited states "+ visitedStates.toString());
        return boardLogic;
    }
}
