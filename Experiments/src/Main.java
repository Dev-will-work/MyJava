#include <iostream>
#define print(x) int main(){std::cout<<x;return 0;}
print("Hello, world!")













import java.util.Scanner;

class Typetester {
    void printType(byte x) {
        System.out.println(x + " is an byte");
    }
    void printType(int x) {
        System.out.println(x + " is an int");
    }
    void printType(float x) {
        System.out.println(x + " is an float");
    }
    void printType(double x) {
        System.out.println(x + " is an double");
    }
    void printType(char x) {
        System.out.println(x + " is an char");
    }
}

//public class Main {
//    public static void main(String[] args) {
//        Typetester t = new Typetester();
//        Scanner sc = new Scanner(System.in);
//        double z = sc.nextDouble();
//        for (int i = 0; i < 100; i++) {
//            int y = i;
//            t.printType(Math.PI * y * y);
//            t.printType(y * y * Math.PI);
//            //System.out.println(Math.PI * y * y);
//            //System.out.println(y * y * Math.PI);
//            System.out.println();
//        }
//    }
//}
