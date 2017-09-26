package ecse420A1;

public class MatrixMultiply {

	public static void main(String[] args) {
		
		double[][] multiplicand = new double[][] {
            {3, -1, 2},
            {2,  0, 1},
            {1,  2, 1}
    };
    double[][] multiplier = new double[][] {
            {2, -1, 1},
            {0, -2, 3},
            {3,  0, 1}
    };
    
    showMatrix(sequentialMultiplyMatrix(multiplicand, multiplier));
   
		// TODO Auto-generated method stub

	}
	
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b){
		//number of rows is matrix.length and number of columns is matrix[0].length
		
		double[][] result = new double[a.length][b[0].length];
			
		for (int i = 0; i < a.length; i++) {  //iterating through the rows of a
			for (int j = 0; j < b[0].length; j++) {  //iterating through the columns of b
				for (int k = 0; k < a[0].length; k++) {   //iterating through the values of a in the columns
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return result;
	}
	
	public static void showMatrix(double[][] matrix) {
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(String.valueOf(matrix[i][j]) + " ");
			}
			System.out.println("");
		}
		
	}

}
