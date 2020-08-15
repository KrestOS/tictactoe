import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleField extends JPanel {
    private GameWindow gameWindow;
    private int mode;
    private int fieldSize;
    private int winningLength;
    private int player = 0;
    private boolean isInit;

    private int cellWidth;
    private int cellHeight;


    public BattleField(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        setBackground(Color.DARK_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickBattleField(e);
            }
        });
    }



    private void clickBattleField(MouseEvent e) {

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        Logic.whoIsWin = 0;

        if(!Logic.isFinished){
            if (mode != 0){
                if (player == 0){
                    Logic.humanTurn(cellX, cellY,player);
                    player = 1;
                }else {
                    Logic.humanTurn(cellX,cellY,player);
                    player = 0;
                }
            }else {
                Logic.humanTurn(cellX, cellY,player);
            }

       }

        repaint();
       switch (Logic.whoIsWin){
           case (1):
               if (mode != 0){
                   JOptionPane.showMessageDialog(this,"Player " + player + " is WIN!","Game over",JOptionPane.DEFAULT_OPTION);
                   break;
               }
               JOptionPane.showMessageDialog(this,"You WIN!","Game over",JOptionPane.DEFAULT_OPTION);
               break;
           case (2):
               JOptionPane.showMessageDialog(this,"Draw!\nTry again!","Game over",JOptionPane.DEFAULT_OPTION);
               break;
           case (3):
               if (mode != 0){
                   JOptionPane.showMessageDialog(this,"Player " + (player+2) + " is WIN!","Game over",JOptionPane.DEFAULT_OPTION);
                   break;
               }
               JOptionPane.showMessageDialog(this,"You Lose):\nTry again!","Game over",JOptionPane.DEFAULT_OPTION);
               break;
       }

    }


    public void startNewGame(int mode, int fieldSize, int winningLength) {
        this.mode = mode;
        this.fieldSize = fieldSize;
        this.winningLength = winningLength;

        isInit = true;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isInit) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellHeight = panelHeight / fieldSize;
        cellWidth = panelWidth / fieldSize;
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);

        for (int i = 0; i < fieldSize; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSize; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (Logic.map[i][j] == Logic.DOT_X) {
                    drawX(g, j, i);
                }
                if (Logic.map[i][j] == Logic.DOT_O) {
                    drawO(g, j, i);
                }
            }
        }
    }

    private void drawX(Graphics g, int x, int y) {
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.setColor(Color.RED);
        g.drawLine(cellWidth * x, cellHeight * y, cellWidth * (x + 1), cellHeight * (y + 1));
        g.drawLine(cellWidth * x, cellHeight * (y+1), cellWidth * (x+1), cellHeight * (y));
    }

    private void drawO(Graphics g, int x, int y) {
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.setColor(Color.BLUE);
        g.drawOval(cellWidth * x , cellHeight * y, cellWidth, cellHeight);
    }
}
