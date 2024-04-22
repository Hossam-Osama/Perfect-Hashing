import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

class UniversalHashFunction {

    private static final int numberOfBits = 33;



    public int[][] initRandomHash(int rows) {

        int[][] hashMatrix = new int[rows][numberOfBits];

        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfBits; j++) {
                // Generate a random binary value (0 or 1)
                int binaryValue = random.nextInt(2);
                hashMatrix[i][j] = binaryValue;
            }
        }

        // Print the randomly generated binary array
        System.out.println("Randomly generated binary array:");
        System.out.println(hashMatrix.toString());

        return hashMatrix;

    }

    public static int binaryToDecimal(int[] binaryArray) {
        int decimalValue = 0;

        // Iterate over the binary array from left to right
        for (int i = 0; i < binaryArray.length; i++) {
            // Multiply the current digit by 2 raised to the power of its position
            int power = binaryArray.length - 1 - i;
            decimalValue += binaryArray[i] * Math.pow(2, power);
        }

        return decimalValue;
    }

    int multiplyByHash(int[] binaryArray, int[][] h, int sizeOfTable) {

        int value = 0;

        int[] binary= new int[h.length];

        for (int i = 0; i < h.length; i++) {
            binary[i] = 0;
            for (int j = 0; j < numberOfBits; j++) {
                binary[i] += h[i][j] * binaryArray[j];
            }
            binary[i] = binary[i] % 2;
        }
        int index = binaryToDecimal(binary);
        System.out.println(index);
        return index%sizeOfTable;

    }


    public int generateHash(String key, int[][] h, int sizeOfTable) {
        int value = 0;
        try {
            byte[] arrOfBytes = key.getBytes("US-ASCII");

            for (int i = 0; i < arrOfBytes.length; i++) {
//                value.concat(Integer.toBinaryString(ele));
                value += (arrOfBytes[i] * Math.pow((i + 1), 2)) % (Integer.MAX_VALUE);
                i++;
            }
//            System.out.println(Arrays.toString(arrOfBytes) + "  value is " + value + "  binary " + Integer.toBinaryString(value));

        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }

        String binarystr = Integer.toBinaryString(value);

        int[] binaryArray = new int[numberOfBits];

        for (int i = 0; i < binarystr.length(); i++) {
            binaryArray[i] = Character.getNumericValue(binarystr.charAt(i));
        }
        return multiplyByHash(binaryArray, h, sizeOfTable);

    }
}
