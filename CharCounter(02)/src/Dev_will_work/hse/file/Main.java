package Dev_will_work.hse.file;

import java.io.File;
import java.util.Scanner;

/**
 * Main class for character counting
 * @version 1.0 17 Feb 2021
 * @author Pasha Emshanov
 */
public class Main {
    public static void main(String[] args) {
        CharCounter cc = null;
        Scanner read = new Scanner(System.in);
        String filenameRead;
        File file;
        while (cc == null) {
            System.out.println("Enter filename for reading:");
            filenameRead = read.next();
            file = new File(filenameRead);
            if (file.exists() && file.canRead() && !file.isDirectory()) {
                cc = new CharCounter(file);
            }
        }
        cc.calcUsage();
        String filenameWrite;
        File wfile;
        do {
            System.out.println("Enter filename to write:");
            filenameWrite = read.next();
            wfile = new File(filenameWrite);
            //write into out.txt or create a new file for the safety reasons
        } while (wfile.isDirectory() || (wfile.exists() &&
                        !wfile.getName().equals("out.txt")));
        cc.printResults(wfile);
    }
}

//    File wfile = new File("output.txt");
//        while (wfile.isDirectory() || (wfile.exists() &&
//                !wfile.getName().equals("out.txt"))) {
//                System.out.println("Enter filename to write:");
//                filenameWrite = read.next();
//                wfile = new File(filenameWrite);
//                //write into out.txt or create a new file for the safety reasons
//                };

