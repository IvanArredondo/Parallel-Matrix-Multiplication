package ecse420A1;

import java.awt.Dimension;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiply {
	
	static int numberOfThreads = 6;
	static int dimensions = 34;
	//int 

	public static void main(String[] args) {
		
		
	double[][] multiplicand = createRandomSquareMatrix();
    double[][] multiplier = createRandomSquareMatrix();
    
    showMatrix(sequentialMultiplyMatrix(multiplicand, multiplier));
    
    if(numberOfThreads > dimensions) {
    	System.out.println("Please make number of threads less than dimmensions");
    	
    }else {
    	showMatrix(parallelMultiplyMatrix(multiplicand, multiplier));
    }
   
		// TODO Auto-generated method stub

	}
	
	
	
	
	
	public static double[][] createRandomSquareMatrix() {
		double[][] matrix = new double[dimensions][dimensions];
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				matrix[i][j] = Math.random()*10;
			}
		}
		
		return matrix;
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
			System.out.println("    " + i);
		}
		
	}
	
	public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b){
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		
		double[][] result  = new double[dimensions][dimensions];
		
		int lastThread  = 0;
		
		int numberRowsPerThread = dimensions/numberOfThreads;
		if(numberRowsPerThread > 1) {
			lastThread = numberRowsPerThread + dimensions%numberOfThreads;
		}else {
			lastThread = dimensions%numberOfThreads;
		}
		
		System.out.println(" numb of rows per " + numberRowsPerThread + "       " + "last one" + lastThread);
		
		
		for (int i = 0; i < numberOfThreads-1; i++) {
			executor.execute(new MultiplyTask(a, b, result, i*numberRowsPerThread, numberRowsPerThread));
		}
		executor.execute(new MultiplyTask(a, b, result, (numberOfThreads-1)*numberRowsPerThread, lastThread));
		
		
		try {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

}
