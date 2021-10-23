/*
 * Main
 *
 * Version 1.0
 *
 * %W% %E% Pasha Emshanov
 *
 * Copyright 2021
 */

package Dev_will_work.hse.math;

import java.util.Scanner;

/**
 * Class Main shows examples of using Complex and Matrix classes
 * @version 1.0 03 Feb 2021
 * @author Pasha Emshanov
 */
public class Main {

    public static void main(String[] args) {
	Complex one = new Complex(1, 2, 0);
	Complex two = new Complex(6, 0);
	Complex sum = one.add(two);
	Complex diff = one.sub(two);
	Complex product = one.mul(two);
	Complex ratio = one.div(two, 3);

	//Консольный ввод первой матрицы
	Scanner read = new Scanner(System.in);
	int precision;
	int type = 0;
	int rows = 0;
	int columns = 0;
	double re, im;

	while (rows <= 0) {
		System.out.println("Enter number of rows:");
		while (!read.hasNextInt()) {
			System.out.println("Wrong!Enter valid number of rows");
			read.next();
		}
		rows = read.nextInt();
	}

	while (columns <= 0) {
		System.out.println("Enter number of columns:");
		while (!read.hasNextInt()) {
			System.out.println("Wrong!Enter valid number of columns");
			read.next();
		}
		columns = read.nextInt();
	}

	System.out.println("Enter needed precision of numbers:");
	while (!read.hasNextInt()) {
		System.out.println("Wrong type!Enter number of precision");
		read.next();
	}
	precision = read.nextInt(); /*without defense from negative numbers*/
												  /*because abs() later*/
	Matrix mOne = new Matrix(rows, columns);
	while (type <= 0 || type > 2) {
		System.out.println("""
				Choose the type of your matrix:
				1: Complex
				2: Real""");
		while (!read.hasNextInt()) {
			System.out.println("Wrong type!Enter 1 for Complex and "
											+ "2 for Real");
			read.next();
		}
		type = read.nextInt();
	}

	System.out.println("Enter the matrix number-by-number: (if complex: "
							+ "first is real, second is imaginary part)");
	switch (type) {
		case 1:
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns;j++) {
					while (!read.hasNextDouble()) {
						System.out.println("Wrong input!Enter any number, " +
																"please");
						read.next();
					}
					re = read.nextDouble();
					while (!read.hasNextDouble()) {
						System.out.println("Wrong input!Enter any number, " +
																	"please");
						read.next();
					}
					im = read.nextDouble();
					mOne.matrix[i][j] = new Complex(re, im, precision);
				}
			}
			break;
		case 2:
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns;j++) {
					while (!read.hasNextDouble()) {
						System.out.println("Wrong input!Enter any number, " +
																	"please");
						read.next();
					}
					re = read.nextDouble();
					mOne.matrix[i][j] = new Complex(re, 0, precision);
				}

			}
			break;
		default:
			break;
	}

	//Создание второй матрицы и операции с ними
	int size = 16;
	Complex[] arr = new Complex[size];
	for (int i = 0; i < size - 4; i++) {
		arr[i] = new Complex(i + Math.random(), 0);
	}
	Matrix mTwo = new Matrix(4, 4, arr);
	Matrix minor = mOne.minor(3, 3);
	System.out.println("Minor from the matrix below:");
	minor.print();
	Matrix mSum = mOne.add(mTwo);
	Matrix mDiff = mOne.sub(mTwo);
	Matrix mProduct = mOne.mul(mTwo);
	Matrix trans = mOne.transpose();
	Complex det = mOne.det();
	System.out.println("First matrix:");
	mOne.print();
	System.out.println("Determinator:");
	System.out.println(det.algForm);
    }
}
