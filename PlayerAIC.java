package MuTorere;
// plan
// write code
// testing
// set up git
// Our solution will compete against the class, so get good sonny


// Ideas:
// We could have a function to figure out all legal move available to the active player
// If no legal moves are available, the active player loses the game
// Active player selects a legal move based off our "SMART" algorithm
// Notes:
// Avoid infinitely reccuring logic for our "SMART" thingy
// This problem may be brute forcable, or not, depending on our partitioning algorithm
// Look up Deep Blue for how the computer beat a chess grandmaster in the 60's
// Our algorithms complete of Thursday

  
import MuTorere.Player;
import java.util.ArrayList;
import java.util.Random;

public class PlayerAIC extends Player {
  
/**
  BoardReader provides a method pieceAt(int index) which returns either
  Board.Piece.BLANK, Board.Piece.ONE, or Board.Piece.TWO for the empty space,
  first player's pieces, or second player's pieces. The index is the location 
  from 0 - 8. 0-7 are the kaawai, clockwise around the board, and 8 is the 
  puutahi:
    7   0
  6       1
      8
  5       2
    4   3
 */
  protected final int CIRCLE_SIZE = 8; // our magic number for the default size
  protected Board.Piece opponentID = (super.playerID == Board.Piece.ONE) ? Board.Piece.TWO : Board.Piece.ONE; // Check this out
 
 /**
  Player ID, either Board.Piece.ONE or Board.Piece.TWO
 */
 
 /**
  Constructor
  
  boardReader provides access to the current state of the game
  playerID determines whether you are player 1 or 2.
  You must provide a constructor with the same signature that calls 
  this to create a concrete Player object.
  @param boardReader provides access to the current board state.
  @param playerID player ID, either Board.Piece.ONE or Board.Piece.TWO
 */
// public Player(BoardReader boardReader, Board.Piece playerID) {
//  this.boardReader = boardReader;
//  this.playerID = playerID;
// }
 
  public PlayerAI2(BoardReader boardReader, Board.Piece playerID) {
    super(boardReader, playerID);
  }
 
 /**
  Need to implement this.
  Return the index of the piece that you want to move.
  If the result is not a valid move, you lose.
  If there are no valid moves, just return something - don't leave us hanging!
  @return The location of the piece that you wish to move to the empty space.
 */
  public int getMove(){ 
    int temp = availableMoves();
    System.out.println("Moving piece " + temp);
    if (temp == -1) return 0;
    return temp;
  }
  
  private Boolean isValidMove(int pieceToMove) {
    int empty = -1, spaceToLeft, spaceToRight; // set empty to -1 to applease the compiling gods
    
    for (int i = 0; i < CIRCLE_SIZE + 1; i++) {
      if (super.boardReader.pieceAt(i) == Board.Piece.BLANK) {
        empty = i;
      }
    }
    
    spaceToRight = ((pieceToMove + 1) % CIRCLE_SIZE);
    spaceToLeft = ((pieceToMove - 1) + CIRCLE_SIZE) % CIRCLE_SIZE;
    
    if (pieceToMove == CIRCLE_SIZE) { 
      // move  from middle to side
      
      //is valid move (move to empty)
      
    } else if (empty == CIRCLE_SIZE && (super.boardReader.pieceAt(spaceToRight) == opponentID || super.boardReader.pieceAt(spaceToLeft) == opponentID)) {
      // check if you can move the piece to the middle
      
      //is valid move
      
    } else if (super.boardReader.pieceAt(spaceToRight) == Board.Piece.BLANK || super.boardReader.pieceAt(spaceToLeft) == Board.Piece.BLANK) { 
      // side to side
      
      //check position (empty+1)%CIRCLE_SIZE
      //check (empty + CIRCLE_SIZE-1)% CIRCLE_SIZE
      //check CIRCLE_SIZE
      //If piece_to_move is one of these, return true
     
     //is valid move
    
    } else {
     //System.out.println(pieceToMove + " is INVALID");
     return false;
    }
    //System.out.println(pieceToMove + " is VALID");
    return true;
}
  
/**
 * Calculate best availabe move
 * @return the move we wish to play
*/
  private int availableMoves() { // Mayhaps we use this function
    ArrayList<Integer> validMoves = new ArrayList<>();
    //Random rand = new Random();

    for (int i = 0; i <= CIRCLE_SIZE; i++) {
      if (super.boardReader.pieceAt(i) == super.playerID) {
        if (isValidMove(i)) {
          validMoves.add(i);
        }
      }
    }

    /* For each of the valid moves:
        Simulate move
        Assess whether other player can make a valid move after ours
        If not, play that move
        If all moves do not prevent other player from moving, just move any (for now)
    */
    
    if (validMoves.isEmpty()) {
      return -1;
    }
    if (validMoves.size() > 1) {
      //int temp = rand.nextInt(validMoves.size());
      return isBestMove(validMoves);
    } else {
      return validMoves.get(0);
    }
  }
  
  /**
   * Checks if a valid move has a piece of the same team adjacent to it.
   * @int postion is the piece that is being checked for adjacency.
   * @return true if a team piece is adjacent, otherwise false.
   */
  private boolean checkAdjacent(int postion) {
    int[] board = new int[CIRCLE_SIZE]; 
    for (int i = 0; i <= CIRCLE_SIZE; i++) {
      if (super.boardReader.pieceAt(i) == playerID) {
        board[i] = 1;
      } else {
        board[i] = 0;
      }
    }
    if (postion == CIRCLE_SIZE) {
      return false;
    }
    if (board[(postion + 1) % CIRCLE_SIZE] == 1 || board[(position - 1 + CIRCLE_SIZE) % CIRCLE_SIZE] == 1) {
      return true;
    }
    return false;
  }
  
  /**
   * Checks valid moves to determine which is the best.
   * @ArrayList<Integer> moves the array of valid moves.
   * @return the best piece to move.
   */
  private int isBestMove(ArrayList<Integer> moves) {
    if (moves.size() == 2) {
      for(int i = 0; i < moves.size(); i++) {
        if(moves.get(i) == CIRCLE_SIZE) {
          return moves.get((i + 1) % moves.size());
        }
        if (checkAdjacent(moves.get(i)) {
          return moves.get(i);
        }
      }
      for(int i = 0; i < moves.size(); i++) {
        if (checkAdjacent(moves.get((i + 1) % CIRCLE_SIZE))) {
          return moves.get(i);
        }
        if (checkAdjacent(moves.get((i - 1 + CIRCLE_SIZE) % CIRCLE_SIZE))) {
          return moves.get(i);
        }
      }
    }
    if (moves.size() == 3) {
      for(int i = 0; i < moves.size(); i++) {
        if (checkAdjacent(moves.get(i))) {
          return moves.get(i);
        }
      }
    }
    if (moves.size() == 4) {
      for(int i = 0; i < moves.size(); i++) {
        if (!(checkAdjacent(moves.get(i)))) {
          return moves.get(i);
        }
      }
      for(int i = 0; i < moves.size(); i++) {
        int possible = moves.get(i);
        if (moves.contains((possible + 2) % CIRCLE_SIZE)) {
          return possible;
        }
      }
    }
    return moves.get(0);
  }
}
