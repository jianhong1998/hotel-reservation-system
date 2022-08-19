package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileFunction {
    private static final FileFunction instance;
    private FileFunction(){}
    static {instance = new FileFunction();}
    public static FileFunction getInstance() {
        return instance;
    }

    public ArrayList<String[]> fileReading(String fileName) {
        ArrayList<String[]> arrayList = new ArrayList<>();

        try {
            File fileReading = new File(fileName);
            Scanner fileInput = new Scanner(fileReading);

            while (fileInput.hasNextLine()) {
                String dataInString = fileInput.nextLine();
                String[] dataArray = dataInString.split(",");
                arrayList.add(dataArray);
            }

            fileInput.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("ERROR: File is not found!\n");
        }
        return  arrayList;
    }

    public void fileWriting(ArrayList<String[]> arrayList , String fileName) {
        try {
            FileWriter output = new FileWriter(fileName , false);

            for (String[] dataArray : arrayList) {
                String dataInString = "";
                for (int i = 0 ; i < dataArray.length ; i++) {
                    String data = dataArray[i];
                    if (i != dataArray.length - 1) {
                        dataInString += (data + ",");
                    } else {
                        dataInString += data + "\n";
                    }
                }
                output.write(dataInString);
            }

            output.close();
        } catch (IOException ioe) {
            System.out.println("File is not found!\n");
        }

    }
}
