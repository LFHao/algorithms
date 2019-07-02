package dropbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReadWriteFile {

    int lineNumber = 0;
    public void readFile3By3(String filePath, List<int[]> matrix) {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((i <= 2) && (line = br.readLine()) != null) {
                matrix.add(convertCharToIntArr(line.toCharArray()));
                i++;
            }
            lineNumber++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String filePath, List<int[]> matrix) {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            int i = 0;
            String line;
            while((line = br.readLine()) != null) {
                if (i == lineNumber - 1) {
                    bw.write(new String(convertIntToCharArr(matrix.get(0))));
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] convertCharToIntArr(char[] charArray) {
        int[] row = new int[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            row[i] = charArray[i] - '0';
        }

        return row;
    }

    private char[] convertIntToCharArr(int[] intArray) {
        char[] row = new char[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            row[i] = (char)intArray[i];
        }

        return row;
    }
}
