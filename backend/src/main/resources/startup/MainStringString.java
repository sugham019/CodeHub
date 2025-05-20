
public class MainStringString{

    public static void main(String[] args) {
        Code code = new Code();
        String input = args[0];
        String expectedOutput = args[1];
        int statusCode = (code.output(input).equals(expectedOutput))? 0: 1;
        System.exit(statusCode);
    }
}