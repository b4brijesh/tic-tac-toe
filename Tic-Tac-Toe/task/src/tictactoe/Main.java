package tictactoe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //System.out.print("Enter cells: ");
        //String input = sc.nextLine();

        char[][] arr = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(arr[i], ' ');
        }
        //fillGameState(input, arr);
        printGameState(arr);

        char player = ' ';

        boolean gameStillRunning = true;
        while (gameStillRunning) {
            int gameState = checkGameState(arr);
            switch (gameState) {
                case -1:
                    System.out.println("Impossible");
                    gameStillRunning = false;
                    break;
                case 1:
                    System.out.println(player + " wins");
                    gameStillRunning = false;
                    break;
                case 2:
                    System.out.println("Draw");
                    gameStillRunning = false;
                    break;
                default:
                    if (player != 'X')
                        player = 'X';
                    else
                        player = 'O';
                    System.out.println("Game not finished");
                    while (!getNextState(player, arr));
                    printGameState(arr);
                    break;
            }
        }
    }

    private static boolean getNextState(char player, char[][] arr) {
        Scanner sc = new Scanner(System.in);
        int x = 0, y = 0, actualX = -1, actualY = -1;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Enter the coordinates: ");
            try {
                x = sc.nextInt();
                y = sc.nextInt();
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                return false;
            }
            if(x <= 0 || y <= 0 || x > 3 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            }
            actualX = 3 - y;
            actualY = x - 1;
            isValid = isCellEmpty(actualX, actualY, arr);
            if (!isValid) {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
        arr[actualX][actualY] = player;
        return true;
    }

    private static void printGameState(char[][] arr) {
        System.out.println("---------");
        for (int i = 1; i <= 3; i++) {
            System.out.print("| ");
            for (int j = 1; j <= 3; j++) {
                System.out.print(arr[i-1][j-1] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static boolean isCellEmpty(int x, int y, char[][] arr) {
        if(arr[x][y] == ' ' || arr[x][y] == '_')
            return true;
        return false;
    }

    static void fillGameState(String input, char[][] arr) {
        int index = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                arr[i-1][j-1] = input.charAt(index);
                index++;
            }
        }
    }

    private static int checkGameState(char[][] arr) {
        //Check if too high difference
        int countX = 0, countO = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (arr[i-1][j-1] == 'X')
                    countX++;
                if (arr[i-1][j-1] == 'O')
                    countO++;
            }
        }
        if (Math.abs(countX-countO) > 1) {
            return -1;
        }

        HashSet<Character> winner = new HashSet<>();
        // Check for row win
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == ' ' || arr[i][0] == '_')
                continue;
            if (arr[i][0] == arr[i][1] && arr[i][0] == arr[i][2]) {
                winner.add(arr[i][0]);
            }
        }

        // Check for column win
        for (int i = 0; i < 3; i++) {
            if (arr[0][i] == ' ' || arr[0][i] == '_')
                continue;
            if (arr[0][i] == arr[1][i] && arr[0][i] == arr[2][i]) {
                winner.add(arr[0][i]);
            }
        }

        // Check for diagonal win
        if (arr[0][0] == arr[1][1] && arr[0][0] == arr[2][2]) {
            if (arr[1][1] != ' ' && arr[1][1] != '_')
                winner.add(arr[1][1]);
        }
        if (arr[0][2] == arr[1][1] && arr[0][2] == arr[2][0]) {
            if (arr[1][1] != ' ' && arr[1][1] != '_')
                winner.add(arr[1][1]);
        }

        if (winner.size() > 1) {
            return -1;
        }
        if (winner.size() == 1) {
            return 1;
        }

        // Game not finished or draw
        int blankCount = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (arr[i-1][j-1] == ' ' || arr[i-1][j-1] == '_') {
                    blankCount++;
                }
            }
        }
        if (blankCount != 0) {
            return 0;
        } else {
            return 2;
        }
    }
}
