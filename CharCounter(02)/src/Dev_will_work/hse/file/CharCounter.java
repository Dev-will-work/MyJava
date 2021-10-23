package Dev_will_work.hse.file;

import java.io.*;

/**
 *  This class allows counting the occurrences of each english symbol in
 *  the given file and writes it into another file
 *  @version 1.0 17 Feb 2021
 * @author Pasha Emshanov
 */
public class CharCounter {
    private final File filenameRead;
    private final int[] small_alphabet;
    private final int[] big_alphabet;

    /**
     * Constructor for character counter
     * @param filename to read from
     */
    CharCounter(File filename) {
    this.filenameRead = filename;
    small_alphabet = new int[26];
    big_alphabet = new int[26];
    }

    /**
     * Method for counting each english character
     */
    void calcUsage() {
        FileReader fr = null;
        char symbol = (char) 0;
        try {
            fr = new FileReader(this.filenameRead);
        }  catch (FileNotFoundException ex) {
            System.out.println("Can't open file for reading");
        }
        if (fr != null) {
            while (symbol != (char)-1) {
                try {
                    symbol = (char)fr.read();
                } catch (IOException ex) {
                    System.out.println("Can't read symbol");
                }
                if (symbol >= 'a' && symbol <= 'z') {
                    small_alphabet[symbol - 'a']++;
                } else if (symbol >= 'A' && symbol <= 'Z') {
                    big_alphabet[symbol - 'A']++;
                }
            }
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println("Can't close file for reading");
            }
        }
    }

    /**
     * Method for writing the table of occurrences in the given file
     * @param filename to write in
     */
    void printResults(File filename) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
        } catch (IOException ex) {
            System.out.println("Can't open file for writing");
        }
        if (fw != null) {
            try {
                fw.write("The frame of occurrences of each " +
                                    "english character in text:\n");
                for (int i = 0; i < this.small_alphabet.length; i++) {

                    if (this.small_alphabet[i] > 0) {
                        fw.write(String.format("\"%c\": %d\t\t\t",
                                        i + 97, this.small_alphabet[i]));
                    } else if (this.big_alphabet[i] > 0) {
                        fw.write("\t\t\t");
                    }
                    if (this.big_alphabet[i] > 0) {
                        fw.write(String.format("\"%c\": %d\n",
                                            i + 65, this.big_alphabet[i]));
                    } else if (this.small_alphabet[i] > 0) {
                        fw.write('\n');
                    }
                }
            } catch (IOException ex) {
                System.out.println("Can't write string, output interrupted");
            }
            try {
                fw.close();
            } catch (IOException ex) {
                System.out.println("Can't close file for writing");
            }
        }
    }

}
