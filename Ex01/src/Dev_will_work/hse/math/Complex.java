/*
 * Complex
 *
 * Version 1.0
 *
 * %W% %E% Pasha Emshanov
 *
 * Copyright 2021
 */
package Dev_will_work.hse.math;

/**
 *The Complex class provides tools for working with complex
 * and real numbers,
 * exactly all common arithmetic operations 
 * and algebraic and trigonometric representations.
 *
 * @version 1.0 03 Feb 2021
 * @author Pasha Emshanov
 */
public class Complex {
    final double re;
    final double im;
    final String algForm;
    final String trigForm;
    final int precision;

    /**
     * Constructor for numbers with precision
     * @param re real part of the number
     * @param im imaginary part of the number
     * @param precision - amount of numbers, meaning accuracy of the number
     */
    Complex(double re, double im, int precision) {
        this.re = re;
        this.im = im;
        this.precision = Math.abs(precision);
        this.algForm = this.createAlgForm(precision);
        if (this.im != 0 || this.re != 0) {
            this.trigForm = this.addTrigForm(precision);
        } else {
            this.trigForm = "";
        }
    }

    /**
     * Constructor for numbers without any precision
     * @param re real part of the number
     * @param im imaginary part of the number
     */
    Complex(double re, double im) {
        this.re = re;
        this.im = im;
        this.algForm = this.createAlgForm();
        if (this.im != 0 || this.re != 0) {
            this.trigForm = this.addTrigForm();
        } else {
            this.trigForm = "";
        }
        this.precision = 0;
    }

    /**
     * Sum of two complex numbers with the exact precision
     * @param arg second complex number
     * @param precision accuracy of the result
     * @return result complex number
     */
    Complex add(Complex arg, int precision) {
        return new Complex(this.re + arg.re,
                            this.im + arg.im, precision);
    }

    /**
     * Sum of two complex numbers
     * @param arg second complex number
     * @return result complex number
     */
    Complex add(Complex arg) {
        return new Complex(this.re + arg.re,
                            this.im + arg.im);
    }

    /**
     * Subtraction of two complex numbers with the exact precision
     * @param arg second complex number
     * @param precision accuracy of the result
     * @return result complex number
     */
    Complex sub(Complex arg, int precision) {
        return new Complex(this.re - arg.re,
                            this.im - arg.im, precision);
    }

    /**
     * Subtraction of two complex numbers
     * @param arg second complex number
     * @return result complex number
     */
    Complex sub(Complex arg) {
        return new Complex(this.re - arg.re,
                            this.im - arg.im);
    }

    /**
     * Multiplication of two complex numbers with the exact precision
     * @param arg second complex number
     * @param precision accuracy of the result
     * @return result complex number
     */
    Complex mul(Complex arg, int precision) {
        double new_re_part = this.re * arg.re
                                - this.im * arg.im;
        double new_im_part = this.re * arg.im
                                + this.im * arg.re;
        return new Complex(new_re_part, new_im_part, precision);
    }

    /**
     * Multiplication of two complex numbers
     * @param arg second complex number
     * @return result complex number
     */
    Complex mul(Complex arg) {
        double new_re_part = this.re * arg.re
                                - this.im * arg.im;
        double new_im_part = this.re * arg.im
                                + this.im * arg.re;
        return new Complex(new_re_part, new_im_part);
    }

    /**
     * Division of two complex numbers with the exact precision
     * @param arg second complex number
     * @param precision accuracy of the result
     * @return result complex number
     */
    Complex div(Complex arg, int precision) {
        double arg_pow = (Math.pow(arg.re, 2) + Math.pow(arg.im, 2));
        double new_re_part = (this.re * arg.re
                                + this.im * arg.im) / arg_pow;
        double new_im_part = (arg.re * this.im
                                - this.re * arg.im) / arg_pow;
        return new Complex(new_re_part, new_im_part, precision);
    }

    /**
     * Division of two complex numbers
     * @param arg second complex number
     * @return result complex number
     */
    Complex div(Complex arg) {
        double arg_pow = (Math.pow(arg.re, 2) + Math.pow(arg.im, 2));
        double new_re_part = (this.re * arg.re
                                + this.im * arg.im) / arg_pow;
        double new_im_part = (arg.re * this.im
                                - this.re * arg.im) / arg_pow;
        return new Complex(new_re_part, new_im_part);
    }

    /**
     * Method, which creates
     * the trigonometric string representation of complex number
     * @param precision accuracy of the result
     * @return trigonometric representation string
     */
    String addTrigForm(int precision) {
        double module = Math.sqrt(Math.pow(this.re, 2)
                                    + Math.pow(this.im, 2));
        double fi = this.re / module;
        return String.format("%.1f(cos%.1f + isin%.1f)".replace("1",
                                String.valueOf(precision)), module, fi, fi);
    }

    /**
     * Method, which creates
     * the trigonometric string representation of complex number
     * @return trigonometric representation string
     */
    String addTrigForm() {
        double module = Math.sqrt(Math.pow(this.re, 2)
                                    + Math.pow(this.im, 2));
        double fi = this.re / module;
        return String.format("%.0f(cos%.0f + isin%.0f)", module, fi, fi);
    }

    /**
     * Method, which creates
     * the algebraic string representation of complex number
     * @param precision accuracy of the result
     * @return algebraic representation string
     */
    String createAlgForm(int precision) {
        String res;
        double epsilon = 0.000001;
        if (Math.abs(this.re) > epsilon && this.im > epsilon) {
                res = String.format("%.1f + %.1fi".replace("1",
                        String.valueOf(precision)),
                                    this.re, this.im);
        } else if (Math.abs(this.re) <= epsilon &&
                    Math.abs(this.im) > epsilon) {
                res = String.format("%.1fi".replace("1",
                        String.valueOf(precision)), this.im);
        } else if (Math.abs(this.im) <= epsilon) {
                res = String.format("%.1f".replace("1",
                        String.valueOf(precision)), this.re);
        } else {
                res = String.format("%.1f - %.1fi".replace("1",
                String.valueOf(precision)), this.re,
                                    Math.abs(this.im));
        }
        if (Math.abs(this.im) == 1) {
            res = res.replace("1i", "i");
        }
        return res;
    }

    /**
     * Method, which creates
     * the algebraic string representation of complex number
     * @return algebraic representation string
     */
    String createAlgForm() {
        String res;
        double epsilon = 0.000001;
        if (Math.abs(this.re) > epsilon && this.im > epsilon) {
            res = String.format("%.0f + %.0fi", this.re, this.im);
        } else if (Math.abs(this.re) <= epsilon &&
                Math.abs(this.im) > epsilon) {
            res = String.format("%.0fi", this.im);
        } else if (Math.abs(this.im) <= epsilon) {
            res = String.format("%.0f", this.re);
        } else {
            res = String.format("%.0f - %.0fi", this.re,
                                        Math.abs(this.im));
        }
        if (Math.abs(this.im) <= 1 + epsilon) {
            res = res.replace("1i", "i");
        }
        return res;
    }

}
