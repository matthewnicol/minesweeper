import java.util.ArrayList;
import java.util.Random;

public class minesweeper {

  ArrayList<Integer> squares;
  int columns;

  public minesweeper(int rows, int cols, int bombs) {
    validate(rows, cols, bombs);
    squares = new ArrayList<>();
    populateMatrix(rows*cols);
    squares.set(0, 0-bombs); 
    columns = cols;
  }

  public int openSquare(int square) {
    if (squares.get(0) < -1) {
      addBombs(square);
      squares.set(0, 0);
    }
    return squares.get(square);     
  }
  
  public boolean isLeftEdge(int cell) { return (cell-1 + columns) % columns == 0; }
  public boolean isRightEdge(int cell) { return cell / columns == 0; }
  public boolean isTopEdge(int cell) { return cell <= columns; }
  public boolean isBottomEdge(int cell) { return cell > squares.size()-1 - columns; }

  private void validate(int rows, int cols, int bombs) {
    assert (rows > 0 && cols > 0 && bombs > 0 && bombs < rows*cols & bombs > 1);
  }

  private void populateMatrix(int sq) {
    for (int i = 0; i <= sq+1; i++) {
      squares.add(0);
    }
  }

  private void addBombs(int notpos) {
    Random r = new Random();
    int bombs = Math.abs(squares.get(0));
    while (bombs > 0) {
      int bombloc = (int)(r.nextDouble()*(squares.size()-1));
      if (bombloc != notpos && squares.get(bombloc) != -1 && bombloc < squares.size() && bombloc > 0) {
        squares.set(bombloc, -1);
        updateSurroundingCells(bombloc);
        bombs--;
      }
    }
  }

  private void updateSurroundingCells(int cell) {
    updateTopLeft(cell);
    updateTop(cell);
    updateTopRight(cell);
    updateLeft(
cell);
    updateRight(cell);
    updateBottomLeft(cell);
    updateBottom(cell);
    updateBottomRight(cell);
    
  }
  
  private void updateTopLeft(int cell) { if (!isTopEdge(cell) && !isLeftEdge(cell)) { inc(cell-columns-1); } }  
  private void updateTop(int cell) { if (!isTopEdge(cell)) { inc(cell-columns); } }
  private void updateTopRight(int cell) { if (!isTopEdge(cell) && !isRightEdge(cell)) { inc(cell-columns+1); } }
  private void updateLeft(int cell) { if (!isLeftEdge(cell)) { inc(cell-1); } }
  private void updateRight(int cell) { if (!isRightEdge(cell)) { inc(cell+1); } }
  private void updateBottomLeft(int cell) { if (!isBottomEdge(cell) && !isLeftEdge(cell)) { inc(cell+columns-1); } }
  private void updateBottom(int cell) { if (!isBottomEdge(cell)) { inc(cell+columns); } }
  private void updateBottomRight(int cell) { if (!isBottomEdge(cell) && !isRightEdge(cell)) { inc(cell+columns+1); } }

  private void inc(int cell) {
    int val = squares.get(cell);
    if (val != -1) {
      val++;
      squares.set(cell, val);
    }
  }


}

