import java.util.Arrays;

public class MainIntArrayIntArray{

    public static void main(String[] args) {
        Code code = new Code();
        String inputText = args[0];
        String expectedOutputText = args[1];

        String[] split1 = inputText.substring(1, inputText.length()-1).split(",");
        String[] split2 = expectedOutputText.substring(1, expectedOutputText.length()-1).split(",");

        int[] input = new int[split1.length];
        int[] expectedOutput = new int[split2.length];

        for(int i=0; i<split1.length; i++){
            input[i] = Integer.parseInt(split1[i]);
            expectedOutput[i] = Integer.parseInt(split2[i]);
        }

        int[] output = code.output(input);
        boolean isEqual = Arrays.equals(output, expectedOutput);
        int statusCode = isEqual? 0: 1;
        System.exit(statusCode);
    }
}