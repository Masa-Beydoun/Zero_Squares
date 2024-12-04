import java.util.*;

import static java.lang.Math.abs;

public class State{



     char [][] board;
     State parent = null;
     ArrayList<Stone> stones = new ArrayList<>();
     int cost=0,heuristic=0;


     boolean finished = false;
     boolean lost = false;

    State(){

    }
    State(char [][] board, ArrayList<Stone> stones)
    {
        this.board = board;
        this.stones = stones;
    }

    public State move(String dir){


        State state = new State();

        char[][] newBoard = new char[this.board.length][this.board[0].length];
        for (int i = 0; i < this.board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, this.board[0].length);
        }
        ArrayList<Stone> newStones = new ArrayList<>();
        for (Stone s : stones) {
            newStones.add(new Stone(s.getC(), s.getX(), s.getY(), s.isInGoal()));
        }

        state.board = newBoard;
        state.stones = newStones;


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
            if (dir.equals("UP")) {
//                System.out.println("UP");
                int temp = s.getX() - 1;
                while(temp>=0 && checkIfYouCanWalk(temp,s.getY(),state)){
                    if(state.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[temp][s.getY()] == '?'){
                        state.board[temp][s.getY()] = Character.toUpperCase(c);
                    }
                    temp--;
                }
                canMove[i][0] = temp+1;
                canMove[i][1] = s.getY();
            } else if (dir.equals("DOWN")) {
//                System.out.println("DOWN");
                int temp = s.getX() + 1;
                while(temp< this.board.length && checkIfYouCanWalk(temp,s.getY(),state)){

                    if(state.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[temp][s.getY()] == '?'){
                        state.board[temp][s.getY()] = Character.toUpperCase(c);
                    }
                    temp++;
                }
                canMove[i][0] = temp-1;
                canMove[i][1] = s.getY();
            } else if (dir.equals("LEFT")) {
//                System.out.println("LEFT");
                int temp = s.getY() - 1;
                while(temp>=0 && checkIfYouCanWalk(s.getX(),temp,state)){
                    if(state.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    if(state.board[s.getX()][temp] == '?'){
                        state.board[s.getX()][temp] = Character.toUpperCase(c);
                    }
                    temp--;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp+1;
            } else {
//                System.out.println("RIGHT");
                int temp = s.getY() + 1;
                while(temp<this.board[0].length && checkIfYouCanWalk(s.getX(),temp,state)){
                    if(board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    if(board[s.getX()][temp] == '?'){
                        board[s.getX()][temp] = Character.toUpperCase(c);
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
                for(int k =0 ;k <board.length;k++){
                    for(int j =0 ;j <board[i].length;j++){
                        if(Character.toUpperCase(s.getC()) == board[k][j]){
                            state.board[k][j]='_';
                        }
                    }
                }
            }
        }
        return state;
    }

    public boolean checkIfYouCanWalk(int i,int j,State state){
        if(board[i][j]=='#'){
            return false;
        }
        if(board[i][j]=='O'){
            state.finished=true;
            state.lost = true;
        }
        for(Stone s : stones){
            if(!s.isInGoal() && s.getX()==i && s.getY()==j ){
                return false;
            }
        }
        return true;
    }

    @Override
    public String
    toString() {
        StringBuilder b= new StringBuilder();
//        for(int i=0;i<this.board.length;i++){
//            for(int j=0;j<this.board[0].length;j++){
//                b.append(board[i][j]);
//            }
//            b.append("\n");
//        }
        for(Stone s : stones){
            b.append(s);
            b.append("\n");
        }
        return "dimensions : " + this.board.length + " , " + this.board[0].length +'\n'+
                "cost= " + cost + '\n'+
                "finished= " + finished + '\n'+
                "lost= " + lost + '\n'+
                "expectedMoves= " + heuristic() + '\n'+
                b + '}';
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

        ArrayList<State> nextStates= new ArrayList<>();
        State state;
        state = move("UP");
        if(!state.lost)
            nextStates.add(state);

        state = move("DOWN");
        if(!state.lost)
            nextStates.add(state);

        state = move("LEFT");
        if(!state.lost)
            nextStates.add(state);

        state = move("RIGHT");
        if(!state.lost)
            nextStates.add(state);
        return nextStates;
    }


    public int heuristic(){
        if(lost) return Integer.MAX_VALUE;
        int [][] goals = new int[6][2];
        int index=0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='R'){
                    goals[index][0]=i;
                    goals[index][1]=j;
                    index++;
                }
                if(board[i][j]=='G'){
                    goals[index][0]=i;
                    goals[index][1]=j;
                    index++;
                }
                if(board[i][j]=='B'){
                    goals[index][0]=i;
                    goals[index][1]=j;
                    index++;
                }
                if(board[i][j]=='P'){
                    goals[index][0]=i;
                    goals[index][1]=j;
                    index++;
                }
                if(board[i][j]=='Y'){
                    goals[index][0]=i;
                    goals[index][1]=j;
                    index++;
                }
            }

        }
        int sum = 0;
        for(Stone s : stones){
            if(s.isInGoal()) continue;
            sum++;
            if(s.getC() == 'r'){
                if(goals[0][0] == -1) continue;
                sum+=abs(s.getX()-goals[0][0])+abs(s.getY()-goals[0][1]);
            }
            if(s.getC() == 'g'){
                if(goals[1][0] == -1) continue;
                sum+=abs(s.getX()-goals[1][0])+abs(s.getY()-goals[1][1]);
            }
            if(s.getC() == 'b'){
                if(goals[2][0] == -1) continue;
                sum += abs(s.getX()-goals[2][0])+abs(s.getY()-goals[2][1]);
            }
            if(s.getC() == 'p'){
                if(goals[3][0] == -1) continue;
                sum+=abs(s.getX()-goals[3][0])+abs(s.getY()-goals[3][1]);
            }
            if(s.getC() == 'y'){
                if(goals[4][0] == -1) continue;
                sum += abs(s.getX()-goals[4][0])+abs(s.getY()-goals[4][1]);
            }
        }

        return sum;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;
        return  finished == state.finished && lost == state.lost && Arrays.deepEquals(board, state.board) && stones.equals(state.stones);
    }



}
