import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;

//ALL IN ONE FILE BECAUSE WHY NOT
public class window extends Frame {

  Panel timerScreen;
  Panel gameScreen;
  final int square = 30;
  final int bombs = square*square/5;
  boolean started = false;
  BombList squares;

  public window() {
    //gui stuff
    super("MINESWEEPER");
    addWindowListener(new Monitor());
    gameScreen = new Panel();
    gameScreen.setLayout(new GridLayout(square,square));
    setSize(800, 600);

    squares = new BombList(square);
    for (int i = 1; i < (square*square)+1; i++) {
      buttonBomb bb = new buttonBomb(i);
      bb.addMouseListener(new getMouse());
      squares.add(bb);
      gameScreen.add(bb);
    }

    
    add(gameScreen);
  }

  private class getMouse extends MouseAdapter {
 
    public void mousePressed(MouseEvent e) {

      buttonBomb b = (buttonBomb)e.getSource();
      if (!started) {
        squares.insertBombs(bombs, b.elementno);
        started = true;
      }

      if (e.getButton() == MouseEvent.BUTTON1) {
        squares.get(b.elementno).trigger();
      } 
      else if (e.getButton() == MouseEvent.BUTTON3) {
        squares.get(b.elementno).flag();
      }

    }
  }

  public void mousePressed(MouseEvent e) {
    buttonBomb b = (buttonBomb)e.getSource();
    if (!started) {
      squares.insertBombs(bombs, b.elementno);
      started = true;
    }
    squares.get(b.elementno).trigger();
  } 

  private class buttonBomb extends Button {

    public int elementno;
    public boolean clicked;  
    public boolean flag;
    public boolean question;
    public int nearbyBombs;

    public buttonBomb(int el) {
      super("");
      elementno = el;
      clicked = false;
      flag = false;
      question = false;
      nearbyBombs = 0;
    }

     public void inc() {
       if (nearbyBombs != -1) { nearbyBombs++; }
     }

     public void setBomb() {
       nearbyBombs = -1;
     }

     public void flag() {
       if (!clicked) {
         if (!flag && !question) {
           setLabel(Character.toString((char)9873));
           flag = true;
         }
         else if (flag) {
           setLabel("?");
           question = true;
           flag = false;
         }
         else if (question) {
           setLabel("");
           question=false;
         }
       }
     }
   
     public void trigger() {
       if (!clicked && !flag && !question) { 
         clicked = true;
         if (nearbyBombs == -1) {
           System.out.println("BOMB");
           setLabel("*");
         }
         else {
           setLabel(Integer.toString(nearbyBombs));
           if (nearbyBombs == 0) {
             if (squares.getNorth(elementno) != null) squares.getNorth(elementno).trigger();
             if (squares.getEast(elementno) != null) squares.getEast(elementno).trigger();
             if (squares.getSouth(elementno) != null) squares.getSouth(elementno).trigger();
             if (squares.getWest(elementno) != null) squares.getWest(elementno).trigger();
             if (squares.getNorthEast(elementno) != null) squares.getNorthEast(elementno).trigger();
             if (squares.getNorthWest(elementno) != null) squares.getNorthWest(elementno).trigger();
             if (squares.getSouthWest(elementno) != null) squares.getSouthWest(elementno).trigger();
             if (squares.getSouthEast(elementno) != null) squares.getSouthEast(elementno).trigger();
           }
         }
       }
     }
  }

  private class BombList extends ArrayList<buttonBomb> {
    int columns;
    public BombList(int cols) {
      super();
      columns = cols;
      add(new buttonBomb(0));
    }

    public int elements() { return size()-1; }

    public buttonBomb getNorth(int cell) {
      if (cell <= columns) { return null; }
      return get(cell-columns);
    }

    public buttonBomb getWest(int cell) {
      if ((cell-1 + columns) % columns == 0) { return null; }
      return get(cell-1);
    }

    public buttonBomb getEast(int cell) {
      if (cell % columns == 0) { return null; }
      return get(cell+1);
    }  

    public buttonBomb getSouth(int cell) {
      if (cell > size()-1 - columns) { return null; }
      return get(cell+columns);
    }

    public buttonBomb getNorthEast(int cell) {
      if (getEast(cell) == null || getNorth(cell) == null) { return null; } 
      return get(cell-columns+1);
    }

    public buttonBomb getNorthWest(int cell) {
      if (getNorth(cell) == null || getWest(cell) == null) { return null; }
      return get(cell-columns-1);
    }

    public buttonBomb getSouthEast(int cell) {
      if (getSouth(cell) == null || getEast(cell) == null) { return null; }
      return get(cell+columns+1);
    }

    public buttonBomb getSouthWest(int cell) {
      if (getSouth(cell) == null || getWest(cell) == null) { return null; }
      return get(cell+columns-1);
    }

    public void incrementAround(int b) {
      if(getNorth(b) != null) getNorth(b).inc();
      if(getSouth(b) != null) getSouth(b).inc();
      if(getEast(b) != null) getEast(b).inc();
      if(getWest(b) != null) getWest(b).inc();
      if(getNorthEast(b) != null) getNorthEast(b).inc();
      if(getNorthWest(b) != null) getNorthWest(b).inc();
      if(getSouthEast(b) != null) getSouthEast(b).inc();
      if(getSouthWest(b) != null) getSouthWest(b).inc();
    }

    void insertBombs(int bombcount, int squareClicked) {
      Random r = new Random();
      int insertedBombs = 0;
      while (insertedBombs < bombcount) {
        int bombpos = (int)(r.nextDouble() * (elements()+1));
        if (bombpos == squareClicked) { continue; }
        if (bombpos < 1 || bombpos > elements()) { continue; }
        if (get(bombpos).nearbyBombs == -1) { continue; }
        get(bombpos).setBomb();
        incrementAround(bombpos);
        insertedBombs++;
      }
    }

  }

  //for exiting out of the window
  private class Monitor extends WindowAdapter{
    
    public void windowClosing(WindowEvent e) {
      Window w = e.getWindow();
      w.setVisible(false);
      w.dispose();
      System.exit(0);
    }
  }

  public static void main(String args[]) {
    System.out.println("Hello World!");
    window gui = new window();
    gui.setVisible(true);
  }
}
