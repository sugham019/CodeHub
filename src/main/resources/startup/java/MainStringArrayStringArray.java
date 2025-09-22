import java.util.Arrays;

public class MainStringArrayStringArray {

    public static void main(String[] args) {
        Code code = new Code();
        String inputText = args[0];
        String expectedOutputText = args[1];

        String[] input = inputText.substring(1, inputText.length() - 1).split(",");
        String[] expectedOutput = expectedOutputText.substring(1, expectedOutputText.length() - 1).split(",");

        String[] output = code.output(input);

        boolean isEqual = Arrays.equals(output, expectedOutput);
        int statusCode = isEqual ? 0 : 1;
        System.exit(statusCode);
    }
}
