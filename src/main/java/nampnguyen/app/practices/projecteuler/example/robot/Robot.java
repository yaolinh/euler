package nampnguyen.app.practices.projecteuler.example.robot;

import java.util.Random;

public class Robot {
    // Platform size
    private static final int SIZE = 8;
    private static int[][] visited = new int[8][8];
    // Directions
    private enum Direction{
        UP, RIGHT, DOWN, LEFT;

        // Get the next direction when turning right
        public Direction turnRight() {
            return switch (this) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
            };
        }

        public Direction turnLeft(){
            return switch (this) {
                case UP -> LEFT;
                case RIGHT -> UP;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
            };
        }

        public Direction turnUpward(){
            return switch (this) {
                case UP -> UP;
                case RIGHT -> RIGHT;
                case DOWN -> DOWN;
                case LEFT -> LEFT;
            };
        }

        public Direction turnDownward(){
            return switch (this) {
                case UP -> DOWN;
                case RIGHT -> LEFT;
                case DOWN -> UP;
                case LEFT -> RIGHT;
            };
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        // Object's initial position (randomly placed within the grid)
        int x = random.nextInt(SIZE); // X-coordinate (column)
        int y = random.nextInt(SIZE); // Y-coordinate (row)

        Direction currentDirection = getRandomDirection(random); // Initial random direction
        int prevX = -1, prevY = -1; // To track the previous position

        System.out.println("Starting position: (" + x + ", " + y + ")");
        System.out.println("The object is represented by 'O'. Empty spaces are represented by '.'.\n");

        while (true) {
            // Display the grid
            printGrid(x, y);

            // Save the previous position
            int lastX = x, lastY = y;

            // Try moving in the current direction
            int newX = x, newY = y;
            switch (currentDirection) {
                case UP -> newY--;
                case RIGHT -> newX++;
                case DOWN -> newY++;
                case LEFT -> newX--;
            }

            // Check for edge hit
            if (newX < 0 || newX >= SIZE || newY < 0 || newY >= SIZE || visited[newX][newY] == -2) {
                System.out.println("Hit the edge! Turning right.");
                if(newX > lastX && newY == lastY){
                    currentDirection.turnUpward();
                }else if(newX <= lastX && newY == lastY){
                    currentDirection.turnDownward();
                }else if(newX == lastX && newY <= lastY){
                    currentDirection.turnRight();
                }else{
                    currentDirection.turnLeft();
                }
                currentDirection = currentDirection.turnRight(); // Turn right
                continue; // Retry with the new direction
            }

            // Check if the object is turning back to the previous position
            if (newX == prevX && newY == prevY) {
                System.out.println("No valid move available. The object has stopped.");
                System.exit(0);; // Stop the program
            }

            // Update position
            x = newX;
            y = newY;
            prevX = lastX;
            prevY = lastY;
            visited[prevX][prevY] = -2;

            // Print move details
            System.out.println("Moved " + currentDirection + " -> New position: (" + x + ", " + y + ")\n");

            // Sleep to make movement visible
            try {
                Thread.sleep(300); // 1-second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Random direction generator
    private static Direction getRandomDirection(Random random) {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }

    // Method to print the 8x8 grid with the object's position
    private static void printGrid(int x, int y) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(visited[j][i] == -2){
                    System.out.print("x ");
                    continue;
                }
                     // The object
                if (i == y && j == x) {
                    System.out.print("O "); // The object
                } else {
                    System.out.print(". "); // Empty space
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}