package mainframe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// FileSpooler receives converted .C lines from <<Main Class>>.
// and then write them to output file.

public class FileSpooler {
    String[] stringArray = new String[99]; // IMPORTANT : MAX COVERTIBLE LINES //
    int arrayIndex = 0;
    public void saveDataToArray(String inputStr){ // will save string data to class array
        this.stringArray[this.arrayIndex] = inputStr;
        arrayIndex++;
    }
    public void endGwalho(){
        this.stringArray[this.arrayIndex] = "}";
        arrayIndex++;
    }
    public void writeFile() { // will collect string from array and write to file.
        File file = new File("output.c");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < this.arrayIndex; i++){
                writer.write(this.stringArray[i] + "\n");
            }
            writer.close();
            this.arrayIndex = 0;
            System.out.println(">> File Successfully created. <<");
            System.out.println("[!] Output File Directory >> " + file.getAbsolutePath());
            System.out.println(" _________________");
            System.out.println("|# :           : #|");
            System.out.println("|  :           :  |");
            System.out.println("|  :           :  |");
            System.out.println("|  :           :  |");
            System.out.println("|  :___________:  |");
            System.out.println("|     _________   |");
            System.out.println("|    | __      |  |");
            System.out.println("|    ||  |     |  |");
            System.out.println("\\____||__|_____|__|");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
