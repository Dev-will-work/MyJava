/*
 * Matrix
 *
 * Version 1.0
 *
 * %W% %E% Pasha Emshanov
 *
 * Copyright 2021
 */

package Dev_will_work.hse.math;

/**
 * The Matrix class provides tools for working with matrices of
 * real and complex numbers,
 * exactly all common arithmetic operations except the division,
 * transposing, creating minor matrices, calculating
 * determinator and comfortable printing of the whole matrix.
 * @version 1.0 03 Feb 2021
 * @author Pasha Emshanov
 */
public class Matrix {
    final Complex[][] matrix;
    final int rows;
    final int columns;

    /**
     * Constructor for matrix, initializes all by zero values
     * @param rows number of rows
     * @param columns number of columns
     */
    Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            this.rows = 5;
            this.columns = 5;
        } else {
            this.rows = rows;
            this.columns = columns;
        }
        this.matrix = new Complex[rows][columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns;j++) {
                this.matrix[i][j] = new Complex(0, 0);
            }
        }
    }

    /**
     * Constructor for matrix of complex numbers with precision, initialises
     * all by given or random values, if insufficient
     * @param rows number of rows
     * @param columns number of columns
     * @param numbers array, containing numbers for the matrix
     */
    Matrix(int rows, int columns, Complex[] numbers) {
        int count = 0;
        if (rows < 1 || columns < 1) {
            this.rows = 5;
            this.columns = 5;
        } else {
            this.rows = rows;
            this.columns = columns;
        }
        int argSize = 0;
        for (Complex number : numbers) {
            if (number != null) argSize++;
        }
        this.matrix = new Complex[rows][columns];
        if (argSize < rows * columns) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns;j++) {
                    if (count >= argSize) {
                        this.matrix[i][j] = new Complex(0, 0);
                    } else {
                        this.matrix[i][j] = numbers[count++];
                    }
                }
            }
        } else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns;j++) {
                    this.matrix[i][j] = numbers[count++];
                }
            }
        }
    }

    /**
     * Sum of two matrices
     * @param arg second matrix
     * @return result matrix
     */
    Matrix add(Matrix arg) {
        if (this.rows != arg.rows || this.columns != arg.columns) {
            return this;
        }
        Matrix res = new Matrix(this.rows, this.columns);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.columns;j++) {
                res.matrix[i][j] = this.matrix[i][j].add(arg.matrix[i][j]);
            }
        }
        return res;
    }

    /**
     * Subtraction of two matrices
     * @param arg second matrix
     * @return result matrix
     */
    Matrix sub(Matrix arg) {
        if (this.rows != arg.rows || this.columns != arg.columns) {
            return this;
        }
        Matrix res = new Matrix(this.rows, this.columns);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.columns;j++) {
                res.matrix[i][j] = this.matrix[i][j].sub(arg.matrix[i][j]);
            }
        }
        return res;
    }

    /**
     * Multiplication of two matrices
     * @param arg second matrix
     * @return result matrix
     */
    Matrix mul(Matrix arg) {
        if (this.columns != arg.rows) return this;
        Matrix res = new Matrix(this.rows, arg.columns);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < arg.columns;j++) {
                for (int k = 0; k < this.columns;k++) {
                    res.matrix[i][j] = res.matrix[i][j].add(this.matrix[i][k]
                                                    .mul(arg.matrix[k][j]));
                }
            }
        }
        return res;
    }

    /**
     * Method for transposing the matrix
     * @return transposed matrix
     */
    Matrix transpose() {
        Matrix res = new Matrix(this.rows, this.columns);
        for (int i = 0; i < this.rows;i++) {
            for (int j = 0; j < this.columns;j++) {
                res.matrix[i][j] = this.matrix[j][i];
            }
        }
        return res;
    }

    /**
     * Method for finding minor matrix in the source matrix
     * @param row row coordinate, which is excluded from the minor matrix
     * @param column column coordinate, which is excluded from the minor matrix
     * @return minor matrix or the source matrix if error
     */
    Matrix minor(int row, int column) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) return this;
        Matrix res = new Matrix(this.rows - 1, this.columns - 1);
        int iRes = 0, jRes = 0;
        for (int i = 0; i < this.rows; i++) {
            if (i == row) {
                continue;
            }
            for (int j = 0; j < this.columns; j++) {
                if (j == column) {
                    continue;
                }
                res.matrix[iRes][jRes++] = this.matrix[i][j];
            }
            iRes++;
            jRes = 0;
        }
        return res;
    }

    /**
     * recursive method for calculating the determinator
     * uses depletion by a 1st column
     * @return complex or real number, representing the determinator
     * of this matrix
     */
    Complex det() {
        Complex determinator = new Complex(0, 0);
        if (this.columns != this.rows) return determinator;
        switch (this.rows) {
            case 1:
                determinator = determinator.add(this.matrix[0][0]);
                break;
            case 2:
                determinator = determinator.add(this.matrix[0][0]
                                            .mul(this.matrix[1][1])
                                            .sub(this.matrix[0][1]
                                            .mul(this.matrix[1][0])));
                break;
            default:
                for (int i = 0, j = 0; i < this.rows; i++) {
                    Complex sign = new Complex(Math.pow(-1, i + 1 + j + 1),
                                                                        0);
                    determinator = determinator.add(this.matrix[i][j]
                                                .mul(sign).mul(this.minor(i,
                                                  j).det()));
                }
            break;
        }
        return determinator;
    }

    /**
     * Method for matrix pretty-printing
     */
    void print(){
        for (int i = 0; i < this.rows;i++) {
            for (int j = 0; j < this.columns;j++) {
                System.out.print(this.matrix[i][j].algForm);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
