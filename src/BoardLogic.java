import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardLogic {



     int gridX, gridY;
     char [][] board;
     ArrayList<Stone> stones = new ArrayList<Stone>();
     ArrayList<Goal> goals = new ArrayList<Goal>();

     static boolean finished = false;

    BoardLogic(){}
    BoardLogic(int gridX, int gridY, char [][] board, ArrayList<Stone> stones, ArrayList<Goal> goals)
    {
        this.gridX = gridX;
        this.gridY = gridY;
        this.board = board;
        this.stones = stones;
        this.goals = goals;
    }






    public BoardLogic move(String dir,boolean flag){
        BoardLogic boardLogic;
        if(flag){
            boardLogic = this;
        }
        else{
            char[][] newBoard = new char[gridX][gridY];
            for (int i = 0; i < gridX; i++) {
                System.arraycopy(board[i], 0, newBoard[i], 0, gridY);
            }
            ArrayList<Stone> newStones = new ArrayList<>();
            for (Stone s : stones) {
                newStones.add(new Stone(s.getC(),s.getColor(), s.getX(), s.getY(), s.isInGoal()));
            }
            ArrayList<Goal> newGoals = new ArrayList<>();
            for (Goal g : goals) {
                newGoals.add(new Goal(g.getC(),g.getX(), g.getY(), g.getColor()));
            }
            boardLogic = new BoardLogic(gridX, gridY, newBoard, newStones, newGoals);
        }


        int [][] canMove = new int [4][2];
        boolean[] getInGoal = new boolean[4];
        for(int i=0;i<stones.size();i++){
            Stone s = stones.get(i);
            canMove[i][0] = s.getX();
            canMove[i][1] = s.getY();
            getInGoal[i]=false;
        }

        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);

            if(s.isInGoal()) continue;
            char c = s.getC();

            //check movement
            if (dir == "UP") {
                System.out.println("up");
                int temp = s.getX() - 1;
                while(temp>=0 && checkIfYouCanWalk(temp,s.getY())){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    temp--;
                }
                canMove[i][0] = temp+1;
                canMove[i][1] = s.getY();
            } else if (dir == "DOWN") {
                System.out.println("down");
                int temp = s.getX() + 1;
                while(temp<boardLogic.gridX && checkIfYouCanWalk(temp,s.getY())){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    temp++;
                }
                canMove[i][0] = temp-1;
                canMove[i][1] = s.getY();
            } else if (dir == "LEFT") {
                System.out.println("left");
                int temp = s.getY() - 1;
                while(temp>=0 && checkIfYouCanWalk(s.getX(),temp)){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    temp--;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp+1;
            } else {
                System.out.println("right");
                int temp = s.getY() + 1;
                while(temp<boardLogic.gridY && checkIfYouCanWalk(s.getX(),temp)){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    temp++;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp-1;
            }


        }

        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);
            if(s.isInGoal()) continue;
            s.setX(canMove[i][0]);
            s.setY(canMove[i][1]);
            if(getInGoal[i]){
                s.setInGoal(true);
                for(Goal g : goals){
                    if(g.getC() == Character.toUpperCase(s.getC())) {
                        boardLogic.board[g.getX()][g.getY()]='_';
                    }
                }

            }
        }
        return new BoardLogic(boardLogic.gridX, boardLogic.gridY, boardLogic.board, boardLogic.stones, boardLogic.goals);
    }


    public boolean checkIfYouCanWalk(int i,int j){
        if(board[i][j]=='#'){
            return false;
        }
        for(Stone s : stones){
            if(!s.isInGoal() && s.getX()==i && s.getY()==j ){
                return false;
            }
        }

        return true;

    }








    public void printGrid(){

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean checkGameOver(){
        for(Stone stone: stones){
            if(!stone.isInGoal()) {
                finished = true;
                return false;
            }
        }
        return true;
    }

    public List<BoardLogic> possibleBoards(){

        List<BoardLogic> boardLogics = new ArrayList<>();

        System.out.println("possible boards: ");
        System.out.println("UP");
        boardLogics.add(move("UP",false));
        boardLogics.get(0).printGrid();

        System.out.println("DOWN");
        boardLogics.add(move("DOWN",false));
        boardLogics.get(1).printGrid();

        System.out.println("LEFT");
        boardLogics.add(move("LEFT",false));
        boardLogics.get(2).printGrid();

        System.out.println("RIGHT");
        boardLogics.add(move("RIGHT",false));
        boardLogics.get(3).printGrid();

        return boardLogics;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
