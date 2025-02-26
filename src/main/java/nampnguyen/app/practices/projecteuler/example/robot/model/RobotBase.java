package nampnguyen.app.practices.projecteuler.example.robot.model;

public abstract class RobotBase{
    
    public abstract void initialize();
    public abstract Direction move();
    
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
}