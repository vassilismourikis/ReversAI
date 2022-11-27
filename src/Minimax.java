import java.util.ArrayList;
import java.util.Random;


public class Minimax{
    int grayCount=0;
    int maxDepth;
    public Minimax(int depth){
        this.maxDepth=depth;
    }

    public Move nextBestMove(Board board, String color)
    {
        if(color.equals("B"))//ayto ginetai epeidh orizoyme sto evaluate tou board mia synarthsh poy tah kanei mia praxh typoy blackdisks-whitedisks opote o mayros thelei thetika kai o aspros arnhtika
        {
            //If the X plays then it wants to maximize the heuristics value
            return max(new Board(board), 0);
        }
        else
        {
            //If the O plays then it wants to minimize the heuristics value
            return min(new Board(board), 0);
        }
    }

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
    ArrayList<Board> getChildren(String color,Board board)
    {
        ArrayList<Board> children = new ArrayList<>();
        for(int i = 0; i < 64; i++)
        {
			if(board.getButtons()[i].getFont().getName().equals("G")) {
                    Board child = new Board(board);
                    child.placeDisk(i,color);
					child.flipDisks(i);
					child.setIsBlack(!board.getIsBlack());
					child.setLastMove(new Move(i,true));
					child.possibleMoves();
                    children.add(child);
                }
        }
        return children;
    }

    // The max and min functions are called one after another until a max depth is reached or tic-tac-toe.
    // We create a tree using backtracking DFS.
    Move max(Board board, int depth)
    {
        Random r = new Random();
        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if(board.checkGame() || (depth == this.maxDepth))
        {
            return new Move(board.getLastMove().getPos(), board.evaluate());
        }
        //The children-moves of the state are calculated
        ArrayList<Board> children = getChildren("B",board);
        Move maxMove = new Move(Integer.MIN_VALUE); // put max node initially to smallest value.
        for(Board child: children)
        {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if(move.getValue() >= maxMove.getValue())
            {
                //If the heuristic has the save value then we randomly choose one of the two moves
                if((move.getValue()) == maxMove.getValue())
                {
                    if(r.nextInt(2) == 0)
                    {
                        maxMove.setPos(child.getLastMove().getPos());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                    maxMove.setPos(child.getLastMove().getPos());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

    //Min works similarly to max
    Move min(Board board, int depth)
    {
        Random r = new Random();
        if(board.checkGame() || (depth == this.maxDepth))
        {
            return new Move(board.getLastMove().getPos(), board.evaluate());
        }
        ArrayList<Board> children = getChildren("W",board);
        Move minMove = new Move(Integer.MAX_VALUE);
        for(Board child: children)
        {
            Move move = max(child, depth + 1);
            if(move.getValue() <= minMove.getValue())
            {
                if((move.getValue()) == minMove.getValue())
                {
                    if(r.nextInt(2) == 0)
                    {
                        minMove.setPos(child.getLastMove().getPos());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                    minMove.setPos(child.getLastMove().getPos());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }    
}

