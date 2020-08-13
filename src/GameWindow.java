import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    static final int WINDOW_POS_X = 500;
    static final int WINDOW_POS_Y = 300;
    static final int WINDOW_HEIGHT = 555;
    static final int WINDOW_WIDTH = 505;

    private SettingWindow settingWindow;
    private BattleField battleField;

    public GameWindow() {
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe");

        settingWindow = new SettingWindow(this);
        battleField = new BattleField(this);

        add(battleField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton buttonOk = new JButton("Start new game");
        panel.add(buttonOk);
        JButton buttonExit = new JButton("Exit");
        panel.add(buttonExit);
        add(panel, BorderLayout.SOUTH);

        buttonExit.addActionListener(e -> {
            System.exit(0);
        });

        buttonOk.addActionListener(e -> {
            settingWindow.setVisible(true);
        });

        setVisible(true);
    }

    public void startNewGame(int mode, int fieldSize, int winningLength){
        battleField.startNewGame(mode, fieldSize, winningLength);
    }
}
