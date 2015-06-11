import java.awt.*;
import java.awt.event.*;

public class window extends Frame implements ActionListener {

  minesweeper game;

  public window() {
    super("MINESWEEPER");

    game = new minesweeper(30, 30, 30*30/5);
    setSize(800, 600);

    Panel gameScreen = new Panel();
    GridLayout g = new GridLayout(30,30);
    gameScreen.setLayout(g);
    for (int i = 1; i < 901; i++) {
      Button a = new buttonBomb("", i);
      a.addActionListener(this);
      gameScreen.add(a);
    }
    add(gameScreen);
  }

  public static void main(String args[]) {

    System.out.println("Hello World!");
    window gui = new window();
    gui.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    buttonBomb b = (buttonBomb)e.getSource();
    if (!b.clicked) {
      int lev = game.openSquare(b.elementno);
      if (lev == -1) { System.out.println("BOMB"); b.setLabel("*"); }
      else { b.setLabel(Integer.toString(lev)); }
    }
  } 

  private class buttonBomb extends Button {

    public int elementno;
    public boolean clicked;

    public buttonBomb(String title, int el) {
      super(title);
      elementno = el;
      clicked = false;
    }
  }

}
