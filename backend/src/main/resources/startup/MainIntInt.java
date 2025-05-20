

public class MainIntInt{

    public static void main(String[] args) {
        Code code = new Code();
        int input = Integer.parseInt(args[0]);
        int expectedOutput = Integer.parseInt(args[1]);
        int statusCode = (code.output(input) == expectedOutput)? 0: 1;
        System.exit(statusCode);
    }
}