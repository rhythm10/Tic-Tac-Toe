import java.util.Scanner;

public class TicTacToe {
    static char[][] board = new char[3][3];
    static int usercount=0,computercount=0,drawcount=0,ttlmatches=0;
    static boolean playagain = true;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("************** Let's Play Tik Tac Toe **************\n");
        System.out.println("Enter Move according to Specific Criteria: ( [Row][Col] )");
        while (playagain){
            initializeBoard();
            printBoard();
            String gameStatus = "InProgress";
            while (true) {
                String userMove;
                userMove = getUserMove();
                markMoveOnBoard('X', userMove);
                gameStatus = getGameStatus(board);
                if (!gameStatus.equals("InProgress")) {
                    break;
                }
                String computerMove = getComputerMove();
                System.out.println("computer move:" + computerMove);
                markMoveOnBoard('O', computerMove);
                gameStatus = getGameStatus(board);
                if (!gameStatus.equals("InProgress")) {
                    break;
                }
            }
            System.out.println(gameStatus);
            totalwins();
        }
    }

    public static void initializeBoard() {
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 2; c++) {
                board[r][c] = '-';
            }
        }
    }

    public static void printBoard() {
        for (int r = 0; r <= 2; r++) {
            System.out.print("\n");
            for (int c = 0; c <= 2; c++) {
                System.out.print(board[r][c]);
                System.out.print("    ");
            }
        }
        System.out.print("\n" + "\n");
    }

    public static String getUserMove() {

        String usermove = "";
        boolean isValidMove = false;
        while (!isValidMove) {
            System.out.println("user move:");
            usermove = scanner.next();
            isValidMove = validateMove('X', usermove);
        }

        return usermove;
    }

    public static String getComputerMove() {
        char[][] boardcopy = createBoardCopy(board);

        //Choose winning move if available
        for (int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                if (boardcopy[row][column] == '-') {
                    boardcopy[row][column] = 'O';
                    if (isWinning('O', boardcopy)) {
                        return ("[" + row + "][" + column + "]");
                    } else {
                        boardcopy[row][column] = '-';
                    }
                }
            }
        }

        //Choose blocking move if available
        for (int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                if (boardcopy[row][column] == '-') {
                    boardcopy[row][column] = 'X';
                    if (isWinning('X', boardcopy)) {
                        return "[" + row + "][" + column + "]";
                    } else {
                        boardcopy[row][column] = '-';
                    }
                }
            }
        }

        //Choose center if available
        if (boardcopy[1][1] == '-') {
            return "[1][1]";
        }

        //Choose a corner if available
        if (boardcopy[0][0] == '-') {
            return "[0][0]";
        }

        if (boardcopy[0][2] == '-') {
            return "[0][2]";
        }

        if (boardcopy[2][0] == '-') {
            return "[2][0]";
        }

        if (boardcopy[2][2] == '-') {
            return "[2][2]";
        }

        //Choose a random move
        for (int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                if (boardcopy[row][column] == '-') {
                    return "[" + row + "][" + column + "]";
                }
            }
        }
        return "no move found";
    }

    public static boolean validateMove(char player, String move) {
        boolean isValidMove = false;
        int row = Character.getNumericValue(move.charAt(1));
        int column = Character.getNumericValue(move.charAt(4));
        if (board[row][column] == '-') {
            isValidMove = true;
        } else if ((row < 0 && row > 2) || (column < 0 && column > 2)) {
            System.out.println("Invalid Move");
        }

        return isValidMove;
    }

    public static void markMoveOnBoard(char player, String move) {
        int row = Character.getNumericValue(move.charAt(1));
        int column = Character.getNumericValue(move.charAt(4));
        board[row][column] = player;

        printBoard();
    }

    public static String getGameStatus(char[][] board) {
        boolean userWins = isWinning('X', board);
        if (userWins) {
            usercount++;
            ttlmatches++;
            return "User Wins!";
        }
        boolean computerWins = isWinning('O', board);
        if (computerWins) {
            computercount++;
            ttlmatches++;
            return "Computer Wins!";
        }
        if (isDraw()) {
            drawcount++;
            ttlmatches++;
            return "Draw";
        }
        return "InProgress";
    }

    public static boolean isWinning(char player, char[][] inputboard) {
        for (int r = 0; r <= 2; r++) {
            if (inputboard[r][0] == player && inputboard[r][1] == player && inputboard[r][2] == player) {
                return true;
            }
        }

        for (int c = 0; c <= 2; c++) {
            if (inputboard[0][c] == player && inputboard[1][c] == player && inputboard[2][c] == player) {
                return true;
            }
        }
        if (inputboard[0][0] == player && inputboard[1][1] == player && inputboard[2][2] == player) {
            return true;
        }
        if (inputboard[0][2] == player && inputboard[1][1] == player && inputboard[2][0] == player) {
            return true;
        }
        return false;
    }

    public static boolean isDraw() {
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 2; c++) {
                if (board[r][c] == '-') {
                    return false;
                }
            }
        }

        return true;
    }

    public static char[][] createBoardCopy(char[][] board) {
        char[][] boardcopy = new char[3][3];
        for (int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                boardcopy[row][column] = board[row][column];
            }
        }
        return boardcopy;
    }

    public static void totalwins()
    {
        System.out.println("\nTotal Matches Played : " + ttlmatches);
        System.out.println("User Wins : " + usercount);
        System.out.println("Computer Wins : " + computercount);
        System.out.println("Total Matched Draws : " + drawcount);

        System.out.println("\nDo you want to Play more(y/n)?");
        scanner.nextLine();
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("N"))
        {
            playagain = false;
        }

    }
}