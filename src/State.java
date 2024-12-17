import java.util.*;

import static java.lang.Math.abs;

public class State{



     char [][] board;
     State parent = null;
     ArrayList<Stone> stones = new ArrayList<>();
     int cost=0,heuristic=0;
     Move comeFrom=null;


     boolean finished = false;
     boolean lost = false;

    State(){
    }
    public State move(Move dir){
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
            if (dir == Move.UP) {
                state.comeFrom=Move.UP;
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
            } else if (dir == Move.DOWN) {
                state.comeFrom=Move.DOWN;
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
            } else if (dir == Move.LEFT) {
                state.comeFrom=Move.LEFT;
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
                state.comeFrom=Move.RIGHT;
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
        checkLost();
        return state;
    }

    public boolean checkLost(){
        ArrayList<Character> unsolved = new ArrayList<>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]!='#' && board[i][j]!='_' && board[i][j]!='?' &&board[i][j]!='O'){
                    unsolved.add(board[i][j]);
                }
            }
        }
        for(Stone s: stones){
            if(s.isInGoal())
                continue;
            for(int i=0;i<unsolved.size();i++){
                if(unsolved.get(i)==Character.toUpperCase(s.getC())){
                    unsolved.remove(i);
                    break;
                }
            }

        }
        System.out.println(unsolved);
        if(!unsolved.isEmpty()){
            lost=true;
            return true;
        }
        return false;
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
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<this.board[0].length;j++){
                b.append(board[i][j]);
            }
            b.append("\n");
        }
        for(Stone s : stones){
            b.append(s);
            b.append("\n");
        }
        return "dimensions : " + this.board.length + " , " + this.board[0].length +'\n'+
                "cost= " + cost + '\n'+
                "finished= " + finished + '\n'+
                "lost= " + lost + '\n'+
                "expectedMoves= " + heuristic() + '\n'+
                "come From= "+comeFrom+'\n'+
                b + '}';
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
        state = move(Move.UP);
        state.checkLost();
        if(!state.lost)
            nextStates.add(state);
        else
            nextStates.add(null);

        state = move(Move.DOWN);
        state.checkLost();
        if(!state.lost)
            nextStates.add(state);
        else
            nextStates.add(null);

        state = move(Move.LEFT);
        state.checkLost();
        if(!state.lost)
            nextStates.add(state);
        else
            nextStates.add(null);

        state = move(Move.RIGHT);
        state.checkLost();
        if(!state.lost)
            nextStates.add(state);
        else
            nextStates.add(null);

//        System.out.println(nextStates);
        return nextStates;
    }

    public int advancedHeuristic(){
        if(lost) return Integer.MAX_VALUE;
        int [][] goals = new int[5][2];
        for(int i=0;i<5;i++){
            goals[i][0]=-1;
            goals[i][1]=-1;
        }
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
        int sum=0;
        for(Stone s : stones){
            if(s.isInGoal())continue;
            if(s.getC()=='r' && goals[0][0]!=-1)
                sum+=oneHeu(s,goals[0][0],goals[0][1]);
            else if(s.getC()=='b' && goals[2][0]!=-1)
                sum+=oneHeu(s,goals[2][0],goals[2][1]);
            else if(s.getC()=='g'&& goals[1][0]!=-1)
                sum+=oneHeu(s,goals[1][0],goals[1][1]);
            else if(s.getC()=='y'&& goals[4][0]!=-1)
                sum+=oneHeu(s,goals[4][0],goals[4][1]);
            else if(s.getC()=='p'&& goals[3][0]!=-1)
                sum+=oneHeu(s,goals[3][0],goals[3][1]);
        }
        return 0;
    }
    public int oneHeu(Stone s,int i,int j){
        int sum=1;
        int tmpI=i,tmpJ=j;
        if(s.getX()>tmpI){
            while(s.getX()>tmpI){
                if(board[tmpI][j]=='#')
                    sum+=2;
                tmpI++;
            }
        }
        else{
            while(s.getX()<tmpI){
                if(board[tmpI][j]=='#')
                    sum+=2;
                tmpI--;
            }
        }
        if(s.getY()>tmpJ){
            while(s.getY()>tmpJ){
                if(board[i][tmpJ]=='#')
                    sum+=2;
                tmpJ++;
            }
        }
        else {
            while(s.getY()<tmpJ){
                if (board[i][tmpJ]=='#')
                    sum+=2;
                tmpJ--;
            }
        }
        return sum;
    }


    public int heuristic(){
        return calculateCost();
    }

    public int calculateCost(){
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
