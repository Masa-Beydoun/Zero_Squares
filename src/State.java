import java.util.*;

public class State {



     int gridX, gridY;
     char [][] board;
     ArrayList<Stone> stones = new ArrayList<>();
     ArrayList<Goal> goals = new ArrayList<>();
     ArrayList<State> nextStates = new ArrayList<>();


     boolean finished = false;
     boolean lost = false;

    State(){}
    State(int gridX, int gridY, char [][] board, ArrayList<Stone> stones, ArrayList<Goal> goals)
    {
        this.gridX = gridX;
        this.gridY = gridY;
        this.board = board;
        this.stones = stones;
        this.goals = goals;
    }

    public State move(String dir){
        State state;

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
        state = new State(gridX, gridY, newBoard, newStones, newGoals);


        int [][] canMove = new int [stones.size()][2];
        boolean[] getInGoal = new boolean[stones.size()];
        for(int i=0;i<stones.size();i++){
            Stone s = stones.get(i);
            canMove[i][0] = s.getX();
            canMove[i][1] = s.getY();
            getInGoal[i]=false;
        }

        for(int i = 0; i< state.stones.size(); i++) {
            Stone s = state.stones.get(i);

            if(s.isInGoal()) continue;
            char c = s.getC();

            //check movement
            if (dir == "UP") {
                System.out.println("UP");
                int temp = s.getX() - 1;
                while(temp>=0 && checkIfYouCanWalk(temp,s.getY(),true)){
                    if(state.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[temp][s.getY()] == '?'){
                        state.board[temp][s.getY()] = Character.toUpperCase(c);
                        goals.add(new Goal(Character.toUpperCase(c),temp,s.getY(),s.getColor()));
                    }
                    temp--;
                }
                canMove[i][0] = temp+1;
                canMove[i][1] = s.getY();
            } else if (dir == "DOWN") {
                System.out.println("DOWN");
                int temp = s.getX() + 1;
                while(temp< state.gridX && checkIfYouCanWalk(temp,s.getY(),true)){

                    if(state.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[temp][s.getY()] == '?'){
                        state.board[temp][s.getY()] = Character.toUpperCase(c);
                        goals.add(new Goal(Character.toUpperCase(c),temp,s.getY(),s.getColor()));
                    }
                    temp++;
                }
                canMove[i][0] = temp-1;
                canMove[i][1] = s.getY();
            } else if (dir == "LEFT") {
                System.out.println("LEFT");
                int temp = s.getY() - 1;
                while(temp>=0 && checkIfYouCanWalk(s.getX(),temp,true)){
                    if(state.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[s.getX()][temp] == '?'){
                        state.board[s.getX()][temp] = Character.toUpperCase(c);
                        goals.add(new Goal(Character.toUpperCase(c),s.getX(),temp,s.getColor()));
                    }
                    temp--;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp+1;
            } else {
                System.out.println("RIGHT");
                int temp = s.getY() + 1;
                while(temp<gridY && checkIfYouCanWalk(s.getX(),temp,true)){
                    if(board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    if(board[s.getX()][temp] == '?'){
                        board[s.getX()][temp] = Character.toUpperCase(c);
                        goals.add(new Goal(Character.toUpperCase(c),s.getX(),temp,s.getColor()));
                    }
                    temp++;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp-1;
            }


        }

        for(int i = 0; i< state.stones.size(); i++) {
            Stone s = state.stones.get(i);
            if(s.isInGoal()) continue;
            s.setX(canMove[i][0]);
            s.setY(canMove[i][1]);
            if(getInGoal[i]){
                s.setInGoal(true);
                for(Goal g : goals){
                    if(g.getC() == Character.toUpperCase(s.getC())) {
                        state.board[g.getX()][g.getY()]='_';
                    }
                }

            }
        }
        return state;
    }

    public boolean checkIfYouCanWalk(int i,int j,boolean flag){
        if(board[i][j]=='#'){
            return false;
        }
        if(board[i][j]=='O'){
            if(flag){
                finished=true;
                lost = true;
            }
            else{
                //TODO
            }
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
        for(Stone s : stones){
            System.out.println(s.toString());
        }
        System.out.println();
    }

    public boolean checkGameOver(){
        for(Stone stone: stones){
            if(!stone.isInGoal()) {

                return false;
            }
        }
        finished = true;
        return true;
    }

    public ArrayList<State> possibleBoards(){


        System.out.println("possible boards: ");
        nextStates.add(move("UP"));
        nextStates.get(0).printGrid();

        nextStates.add(move("DOWN"));
        nextStates.get(1).printGrid();

        nextStates.add(move("LEFT"));
        nextStates.get(2).printGrid();

        nextStates.add(move("RIGHT"));
        nextStates.get(3).printGrid();

        return nextStates;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State that = (State) o;
        return gridX == that.gridX && gridY == that.gridY && finished == that.finished && lost == that.lost && Objects.deepEquals(board, that.board) && Objects.equals(stones, that.stones) && Objects.equals(goals, that.goals);
    }
}
