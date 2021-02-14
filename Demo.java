import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {
    public static void main(String args[]) {

        navigateRover();

    }

    public static void navigateRover() {

        List<Rover> roversList = new ArrayList<>();

        //get the input
        Scanner scan = new Scanner(System.in);

        //UPC : Upper Right Coordinates
        String UPC = scan.nextLine();
        //validate the first line -- UPC
        if (!isValidateInput(UPC, InputType.UPPER_RIGHT_COORDINATE)) {
            return;
        }

        //to restrict the user inputs
        int inputCount = 0;

        int inputTotalRowCount = 1;
        String nextLine = null;
        String roverPostion = null;
        String instructions = null;

        //if user enters 'exit' then program finish getting input
        while (!scan.hasNext("exit")) {

            nextLine = scan.nextLine();
            inputTotalRowCount++;

            if (inputCount == 0) {
                roverPostion = nextLine;

                //validate rover position
                if (!isValidateInput(roverPostion, InputType.ROVER_POSITION)) {
                    return;
                }
                inputCount++;
                continue;

            }

            if (inputCount == 1) {

                instructions = nextLine;

                if (!isValidateInput(instructions, InputType.INSTRUCTIONS)) {
                    return;
                }
                Rover rover = new Rover(roverPostion.toCharArray(), instructions.toCharArray());
                roversList.add(rover);
                inputCount = 0;
                continue;

            }

        }

        //check input rows are enough to proceed
        if (inputTotalRowCount < 3 || inputTotalRowCount % 2 == 0) {
            System.out.println("Insuffienct inputs");
            return;
        }

        //getting output
        List<RoverPosition> processedPositions = process(roversList);

        for (RoverPosition roverPosition : processedPositions) {
            System.out.println(roverPosition.getxPoint() + " " + roverPosition.getyPoint() + " " + roverPosition.getDirection());
        }

    }

    private static List<RoverPosition> process(List<Rover> roversList) {

        char[] postion_points = null;
        char[] instructions = null;

        List<RoverPosition> roverPositionsOut = new ArrayList<>();

        if (!roversList.isEmpty()) {
            for (Rover r : roversList) {
                postion_points = r.getPosition();
                RoverPosition position = new RoverPosition(Integer.parseInt(String.valueOf(postion_points[0])), Integer.parseInt(String.valueOf(postion_points[2])), postion_points[4]);

                //get instruction and process
                instructions = r.getInstructions();
                for (char instruction : instructions) {

                    switch (instruction) {
                        case 'L': {
                            switch (position.getDirection()) {
                                case 'N':
                                    position.setDirection('W');
                                    break;

                                case 'E':
                                    position.setDirection('N');
                                    break;

                                case 'S':
                                    position.setDirection('E');
                                    break;

                                case 'W':
                                    position.setDirection('S');
                                    break;
                            }
                            break;
                        }

                        case 'R': {
                            switch (position.getDirection()) {
                                case 'N':
                                    position.setDirection('E');
                                    break;

                                case 'E':
                                    position.setDirection('S');
                                    break;

                                case 'S':
                                    position.setDirection('W');
                                    break;

                                case 'W':
                                    position.setDirection('N');
                                    break;
                            }
                            break;
                        }

                        case 'M': {
                            int yPoint;
                            int xPoint;
                            switch (position.getDirection()) {

                                case 'N':
                                    yPoint = position.getyPoint();
                                    yPoint++;
                                    position.setyPoint(yPoint);
                                    break;

                                case 'E':
                                    xPoint = position.getxPoint();
                                    xPoint++;
                                    position.setxPoint(xPoint);
                                    break;

                                case 'S':
                                    yPoint = position.getyPoint();
                                    yPoint--;
                                    position.setyPoint(yPoint);
                                    break;

                                case 'W':
                                    xPoint = position.getxPoint();
                                    xPoint--;
                                    position.setxPoint(xPoint);
                                    break;
                            }
                            break;
                        }
                    }
                }
                roverPositionsOut.add(position);
            }
        }

        return roverPositionsOut;
    }


    private static boolean isValidateInput(String input, InputType inputType) {

        boolean isValid = false;

        switch (inputType) {

            case UPPER_RIGHT_COORDINATE: {
                char[] upperRightCoord = input.trim().toCharArray();
                if (upperRightCoord.length == 3 &&
                        Character.isDigit(upperRightCoord[0]) &&
                        Character.isDigit(upperRightCoord[2]) &&
                        Character.isSpaceChar(upperRightCoord[1])) {

                    isValid = true;
                } else {
                    System.out.println("Invalid Upper Right Coordinates");
                    isValid = false;
                }
                break;
            }

            case ROVER_POSITION: {

                char[] roverPosition = input.trim().toCharArray();

                if (roverPosition.length == 5 &&
                        Character.isDigit(roverPosition[0]) &&
                        Character.isDigit(roverPosition[2]) &&
                        Character.isSpaceChar(roverPosition[1]) &&
                        Character.isSpaceChar(roverPosition[3]) &&
                        (roverPosition[4] == 'N' || roverPosition[4] == 'E' || roverPosition[4] == 'S' || roverPosition[4] == 'W')) {

                    isValid = true;

                } else {
                    System.out.println("Invalid Rover Position Coordinates");
                    isValid = false;
                }

                break;
            }

            case INSTRUCTIONS: {
                isValid = true;
                char[] instructions = input.trim().toCharArray();

                for (char instruction : instructions) {
                    if (!(instruction == 'L' || instruction == 'R' || instruction == 'M')) {
                        isValid = false;
                    }
                }

                if (!isValid) {
                    System.out.println("Invalid Instructions");
                }

                break;
            }

        }
        return isValid;
    }
}

enum InputType {
    UPPER_RIGHT_COORDINATE,
    ROVER_POSITION,
    INSTRUCTIONS;
}

class Rover {

    private char[] position;
    private char[] instructions;

    Rover(char[] position, char[] instructions) {
        this.position = position;
        this.instructions = instructions;
    }

    public char[] getPosition() {
        return position;
    }

    public void setPosition(char[] position) {
        this.position = position;
    }

    public char[] getInstructions() {
        return instructions;
    }

    public void setInstructions(char[] instructions) {
        this.instructions = instructions;
    }
}

class RoverPosition {

    private int xPoint;
    private int yPoint;
    private char direction;

    RoverPosition(int xP, int yP, char direction) {
        this.xPoint = xP;
        this.yPoint = yP;
        this.direction = direction;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        this.xPoint = xPoint;
    }

    public int getyPoint() {
        return yPoint;
    }

    public void setyPoint(int yPoint) {
        this.yPoint = yPoint;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
