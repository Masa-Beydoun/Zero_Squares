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
    BoardLogic(int gridX, int gridY, char [][] board, ArrayList<Stone> stones, ArrayList<Goal> goals){
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

        /*

        List<Boolean> canMove= new ArrayList<>(List.of(false, false, false, false));
        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);

            if (dir.equals("UP")) {
                int temp = s.getX();
                if (temp >= 0 && boardLogic.board[temp][s.getY()] == '_') {
                    canMove.set(i,true);
                }

            } else if (dir.equals("DOWN")) {
                int temp = s.getX();
                if (temp < boardLogic.gridX && boardLogic.board[temp][s.getY()] == '_') {
                    canMove.set(i,true);
                }

            } else if (dir.equals("LEFT")) {
                int temp = s.getY();
                if (temp >= 0 && boardLogic.board[s.getX()][temp] == '_') {
                    canMove.set(i,true);
                }

            } else {
                int temp = s.getY();
                if (temp < boardLogic.gridY && boardLogic.board[s.getX()][temp] == '_') {
                    canMove.set(i,true);
                }
            }

        }

         */
//        System.out.println(Arrays.toString(canMove.toArray()));

        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);

//            if(s.isInGoal() && !canMove.get(i)) continue;
            if(s.isInGoal()) continue;
            //get the char from the board
            char c = s.getC();

            //check movement
            if (dir == "UP") {
                System.out.println("up");
                int temp = s.getX() - 1;
                while(temp>=0 && checkIfYouCanWalk(temp,s.getY())){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        s.setInGoal(true);
                        boardLogic.board[temp][s.getY()]='_';
                        break;
                    }
                }
                boardLogic.stones.get(i).setX(temp-1);
            } else if (dir == "DOWN") {
                System.out.println("down");
                int temp = s.getX() + 1;
                while(temp<boardLogic.gridX && checkIfYouCanWalk(temp,s.getY())){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        boardLogic.board[temp][s.getY()]='_';
                        s.setInGoal(true);
                        break;
                    }
                    temp++;
                }
                boardLogic.stones.get(i).setX(temp-1);
            } else if (dir == "LEFT") {
                System.out.println("left");
                int temp = s.getY() - 1;
                while(temp>=0 && boardLogic.board[s.getX()][temp]!='#'){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        s.setInGoal(true);
                        boardLogic.board[s.getX()][temp]='_';
                        break;
                    }
                    temp--;
                }
                boardLogic.stones.get(i).setY(temp+1);
            } else {
                int temp = s.getY() + 1;
                System.out.println("right");
                while(temp<boardLogic.gridY && checkIfYouCanWalk(s.getX(),temp)){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        s.setInGoal(true);
                        boardLogic.board[s.getX()][temp]='_';
                        break;
                    }
                    temp++;
                }
                boardLogic.stones.get(i).setY(temp-1);
            }
            if(!s.isInGoal()){
                boardLogic.board[s.getX()][s.getY()]=c;
            }
        }
        return new BoardLogic(boardLogic.gridX, boardLogic.gridY, boardLogic.board, boardLogic.stones, boardLogic.goals);
    }


    public boolean checkIfYouCanWalk(int i,int j){
        System.out.println(i + " " + j + " " + board[i][j]);
        if(board[i][j]=='#'){
            return false;
        }
        for(Stone s : stones){
            if(s.getX()==i && s.getY()==j){
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
