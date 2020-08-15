import java.util.Random;

public class Logic {

    static int difficultMode;
    static int mode;
    static int SIZE;
    static int DOTS_TO_WIN;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static Random random = new Random();

    static boolean isFinished;
    static int whoIsWin = 0;

  public static void go() {

       isFinished = true;

        printMap();
        if (checkWinLines(DOT_X)) {
            System.out.println("Ты победил! ");
            whoIsWin = 1;
            return;
        }
        if (isFull()) {
            System.out.println("Ничья!");
            whoIsWin = 2;
            return;
        }
        if (mode == 0){
            aiTurn();
            printMap();
        }

        if (checkWinLines(DOT_O)) {
            System.out.println("Компьютер победил! ");
            whoIsWin = 3;
            return;
        }
        if (isFull()) {
            System.out.println("Ничья!");
            return;
        }

        isFinished = false;
    }


    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }


  public static void humanTurn(int x, int y,int player) {

      if(isCellValid(y, x)&&player == 0){
          map[y][x] = DOT_X;
          go();
      }

      if (isCellValid(y, x)&&player == 1){
          map[y][x] = DOT_O;
          go();
      }
  }

    public static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void aiTurn() {
        int x, y;

        if (difficultMode > 0) {

            // Попытка победить самому
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (isCellValid(i, j)) {
                        map[i][j] = DOT_O;
                        if (checkWinLines(DOT_O)) {
                            return;
                        }
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }

            // Сбить победную линии противника, если осталось 1 ход для победы
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (isCellValid(i, j)) {
                        map[i][j] = DOT_X;
                        if (checkWinLines(DOT_X)) {
                            map[i][j] = DOT_O;
                            return;
                        }
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }
        if (difficultMode > 1) {
            // Сбить победную линии противника, если осталось 2 хода для победы
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (isCellValid(i, j)) {
                        map[i][j] = DOT_X;
                        if (checkWinLines(DOT_X, DOTS_TO_WIN - 1) &&
                                Math.random() < 0.5) { //  фактор случайности, чтобы сбивал не все время первый попавшийся путь.
                                map[i][j] = DOT_O;
                            return;
                        }
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }
    // Сходить в произвольную не занятую ячейку
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(y, x));

        map[y][x] = DOT_O;
    }

    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot) {
        return checkWinLines(dot, DOTS_TO_WIN);
    }


    static boolean checkLine(int cy, int cx, int vy, int vx, char dot, int dotsToWin) {
        if (cx + vx * (dotsToWin - 1) > SIZE - 1 || cy + vy * (dotsToWin - 1) > SIZE - 1 ||
                cy + vy * (dotsToWin - 1) < 0) {
            return false;
        }

        for (int i = 0; i < dotsToWin; i++) {
            if (map[cy + i * vy][cx + i * vx] != dot) {
                return false;
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot, int dotsToWin) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 0, 1, dot, dotsToWin) ||
                        checkLine(i, j, 1, 0, dot, dotsToWin) ||
                        checkLine(i, j, 1, 1, dot, dotsToWin) ||
                        checkLine(i, j, -1, 1, dot, dotsToWin)) {
                    return true;
                }
            }
        }
        return false;
    }
}
