package likz;

public class MathFunc {

    public static double[][] multiplyMatrix(double[][] firstMatrix, double[][] secondMatrix)
    {
        int rowCount = firstMatrix.length;             // Число строк результирующей матрицы.
        int colCount = secondMatrix[0].length;         // Число столбцов результирующей матрицы.
        int sumLength = secondMatrix.length;           // Число членов суммы при вычислении значения ячейки.
        double[][] result = new double[rowCount][colCount];  // Результирующая матрица.

        for (int row = 0; row < rowCount; ++row) {  // Цикл по строкам матрицы.
            for (int col = 0; col < colCount; ++col) {  // Цикл по столбцам матрицы.
                double sum = 0;
                for (int i = 0; i < sumLength; ++i)
                    sum += firstMatrix[row][i] * secondMatrix[i][col];
                result[row][col] = sum;
            }
        }

        return result;
    }

    // вычитание двух матриц
    public static double[][] substract (double[][] first, double[][] second) throws IllegalArgumentException{
        // размерность матриц должна быть одинакова
        if (first.length == second.length && first[0].length == second[0].length){
            double[][] c = new double[first.length][first[0].length];
            for (int i = 0; i < first.length; i++){
                for (int j = 0; j < first[0].length; j++){
                    c[i][j] = first[i][j] - second[i][j];
                }
            }

            return c;
        }
        // если матрицы разного размера, бросаем исключение
        else throw new IllegalArgumentException ("Matrix's dimensions should be same");
    }

    // перемножение [1,2] * [2,3] = [2,6]
    public static double[][] dotMultiply(double[][] first, double[][] second){
        double[][] res = new double[first.length][first[0].length];
        for(int i = 0; i < first.length; i++){
            for (int j = 0; j < first[i].length; j++){
                res[i][j] = first[i][j] * second[i][j];
            }
        }
        return res;
    }

    // транспонирование матрицы
    public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    // сложение двух матриц
    public static double[][] add(double[][] first, double[][] second) throws IllegalArgumentException{
        // размерность матриц должна быть одинакова
        if (first.length == second.length && first[0].length == second[0].length){
            double[][] c = new double[first.length][first[0].length];
            for (int i = 0; i < first.length; i++){
                for (int j = 0; j < first[0].length; j++){
                    c[i][j] = first[i][j] + second[i][j];
                }
            }

            return c;
        }
        // если матрицы разного размера, бросаем исключение
        else throw new IllegalArgumentException ("Matrix's dimensions should be same");
    }

    public static double averageMatrixValue(double[][] m){
        double avg;
        double sum = 0;

        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[i].length; j++){
                sum += Math.abs(m[i][j]);
            }
        }
        avg = sum / (m.length * m[0].length);

        return avg;
    }

}
