package com.company;


import com.company.Matrix;

public class Main {

    public static void main(String[] args) {
        double[][] a = {{1,1,1},{3,2,2}};
        Matrix b = new Matrix(a);
        Matrix c = new Matrix(a);
        boolean d = Matrix.isEqual(b,c);
        System.out.println(d);

        System.out.println(c.addition(b));


	// write your code here
    }
}
