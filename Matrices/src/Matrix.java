package com.company;

public class Matrix {
    private double[][] matrix;
    private int length;
    private int rows;
    private int columns;
    //Methods to check conditions
    private boolean isValidMatrix(double[][] matrix) {
        boolean valid = true;
        double[] row0 = matrix[0];
        for (double[] row : matrix) {
            if (row.length != row0.length)
                valid = false;
        }
        return valid;
    }
    public static boolean isEqual(Matrix a, Matrix b){
       return a.matrix == b.matrix;
    }

    private boolean isSquaredMatrix(double[][] matrix) {
        boolean valid = true;
        for (double[] row : matrix) {
            if (row.length != matrix.length)
                valid = false;
        }
        return valid;
    }

    //Constructor
    public Matrix(double[][] matrix) {
        if (isValidMatrix(matrix)) {
            this.matrix = matrix;
            this.length = matrix.length;
            this.rows = matrix.length;
            this.columns = matrix[0].length;
            }
        else {
            throw new IllegalArgumentException("The passed Matrix is  not valid.");
        }
    }

    //Method to Calculate Determinant
    private double calcDet(double[][] matrix) {
        if (isSquaredMatrix(matrix)){
            if (matrix.length == 2) {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            } else {
                int lengthMatrix = matrix.length;
                int result = 0;
                for (int j = 0; j < lengthMatrix; j++) {
                    int sign;
                    if (j % 2 == 0) {
                        sign = 1;
                    } else {
                        sign = -1;
                    }
                    double coefficient = matrix[0][j];
                    double[][] newMatrix = getNewMatrix(matrix, lengthMatrix, j);
                    result += coefficient * (sign) * calcDet(newMatrix);
                }
                return result;
            }
        }
        else {
            throw new ExceptionInInitializerError("Not Valid Matrix");
        }
    }
    //Method to Create the new Matrix required in the method to calculate the determinant
    private double[][] getNewMatrix(double[][] oldMatrix, int oldLength, int column) {
        double[][] newMatrix = new double[oldLength - 1][oldLength - 1];
        int contador = 0;
        for (int i = 1; i < oldLength; i++) {
            for (int j = 0; j < oldLength; j++) {
                if (j == column) {
                    continue;
                } else {
                    if (contador == oldLength - 1) {
                        contador = 0;
                    }
                    newMatrix[i - 1][contador] = oldMatrix[i][j];
                    contador++;
                }
            }
        }
        return newMatrix;
    }
    //Public Method to calculate the determinant of a Matrix object
    public double determinant() {
        return calcDet(this.matrix);
    }
    //Public Method to add Matrix object  to Matrix 2
    public Matrix addition(Matrix s) {
        if(this.rows != s.rows || this.columns !=s.columns){
            throw new ExceptionInInitializerError("Not valid Matrices");
        }
        else {
            double[][] matrix1 = this.matrix;
            double[][] matrix2 = s.matrix;
            double[][] result = new double[this.rows][this.columns];
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    result[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            return new Matrix(result);
        }
    }
    //Private method to transponse a matrix
    private Matrix transposeInsides(Matrix a) {
        double[][] matrix = a.matrix;
        double[][] tMatrix = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                tMatrix[i][j] = matrix[j][i];
            }
        }
        return new Matrix(tMatrix);
    }
    //Method to obtain the transpose of a given Matrix
    public Matrix transpose() {
        return transposeInsides(this);
    }
    // Method to obtain scalar product
    private double scalarProduct(double[] arg1, double[] arg2) {
        double result = 0;
        if (arg1.length != arg2.length) {
            throw new ExceptionInInitializerError("The arguments are not valid");
        } else {
            for (int i = 0; i < arg1.length; i++) {
                result += arg1[i] * arg2[i];
            }
            return result;
        }
    }
    // Method to multiply two to matrixes
    public Matrix multiplication(Matrix a) {
        if (this.matrix[0].length == a.length) {
            double[][] matrix1 = this.matrix;
            double[][] matrix2 = a.transpose().matrix;
            double[][] matrix3 = new double[this.length][matrix2.length];
            int length2 = matrix2.length;
            for (int i = 0; i < this.length; i++) {
                for (int j = 0; j < length2; j++) {
                    matrix3[i][j] = scalarProduct(matrix1[i], matrix2[j]);
                }
            }
            return new Matrix(matrix3);
        } else {
            throw new ExceptionInInitializerError("The arguments are not valid");
        }
    }
    private Matrix calcInverse(Matrix a){
        if (a.determinant()!= 0 ){
            double determinante = a.determinant();
            double [][] inverseMatrix = new double[a.length][a.length];
            if (a.length == 2){
                double temp = inverseMatrix[0][0];
                inverseMatrix[0][0] = -a.matrix[1][1];
                inverseMatrix[1][1] = -a.matrix[0][0];
                inverseMatrix[0][1] = a.matrix[1][0];
                inverseMatrix[1][0] = a.matrix[0][1];
            }
            else {
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < a.length; j++) {
                        int sign;
                        if ((i + j) % 2 == 0) {
                            sign = 1;
                        } else {
                            sign = -1;
                        }
                        double[][] newMatrix = new double[a.length - 1][a.length - 1];
                        newMatrix = getNewMatrix2(a.matrix, i, j, a.length);
                        double numPos = calcDet(newMatrix);
                        inverseMatrix[i][j] = sign * numPos / determinante;
                        if (inverseMatrix[i][j] == -0.0) {
                            inverseMatrix[i][j] = 0.0;
                        }
                    }
                }
            }
            Matrix inverseTrans = new Matrix(inverseMatrix);
            inverseTrans = inverseTrans.transpose();
            return inverseTrans;
            }
        else {
            throw new ExceptionInInitializerError("The Matrix does not have Inverse");
        }
    }
    private double[][] getNewMatrix2(double[][] matrixOld, int rowToAvoid, int columnToAvoid, int lengthOld){
        double[][] newMatrix = new double[lengthOld - 1][lengthOld - 1];
        int contadorRows = 0;
        int contadorColumns = 0;
        for (int i = 0; i < lengthOld; i++) {
            if (i == rowToAvoid) {
                continue;
            } else {
                if (contadorRows == lengthOld -1){
                    contadorRows = 0;
                }
                for (int j = 0; j < lengthOld; j++) {
                    if (j == columnToAvoid) {
                        continue;
                    } else {
                        if (contadorColumns == lengthOld - 1) {
                            contadorColumns = 0;
                        }
                        newMatrix[contadorRows][contadorColumns] = matrixOld[i][j];
                        contadorColumns++;

                    }
                }
                contadorRows++;
            }
        }
        return newMatrix;
    }
    public Matrix inverse(){
        return calcInverse(this);
    }
    @Override
    public String toString() {
        String b = "";
        for (int i = 0; i < this.matrix.length; i++) {
            b += "[";
            for (int c = 0; c < this.matrix[0].length; c++) {
                b += this.matrix[i][c] + " , ";
            }
            b += "\b" + "\b" + "\b" + "],"+"\n";
        }
        return b;
    }
}




