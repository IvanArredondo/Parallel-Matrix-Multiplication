package ecse420A1;
/*
ECSE 420 - Parallel Computing 
Dennis Giannacopoulos
Assignment 1
Question 1
Malkolm Alburquenque - 260562740 - malkolm.alburquenque@mail.mcgill.ca
Ivan Arredondo - 260566638 - ivan.arredondo@mail.mcgill.ca
 */


import java.awt.Dimension; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiply {

	static int numberOfThreads = 4;  //number of threads to split up the parallel multiplication, must be less than dimmensions
	static int dimensions = 8;//dimensions of matrices to be multiplied, square matrices

	static double sequentialTime;
	static double parallelTIme;
	static double startTime;
	static double finishTime;

	public static void main(String[] args) {

		double[][] multiplicand = createRandomSquareMatrix();
		double[][] multiplier = createRandomSquareMatrix();

		startTime = System.currentTimeMillis();  //starting the timer
		showMatrix(sequentialMultiplyMatrix(multiplicand, multiplier));
		finishTime = System.currentTimeMillis();  //stopping the timer

		sequentialTime = finishTime - startTime;

		System.out.println("Sequential time: " + sequentialTime);
		if(numberOfThreads > dimensions) {
			System.out.println("Please make number of threads less than dimmensions");

		}else {	
			startTime = System.currentTimeMillis();  //starting the timer
			showMatrix(parallelMultiplyMatrix(multiplicand, multiplier));	
			finishTime = System.currentTimeMillis();  //stopping the timer
		}

		parallelTIme = finishTime - startTime;

		System.out.println("Parallel time:  " + parallelTIme);
	}

	public static double[][] createRandomSquareMatrix() {

		double[][] matrix = new double[dimensions][dimensions];  //creating square matrix

		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				matrix[i][j] = Math.random()*10;  //assigning random number at each position of matrix
			}
		}

		return matrix;
	} 

	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b){
		//number of rows is matrix.length and number of columns is matrix[0].length

		double[][] result = new double[a.length][b[0].length];

		for (int i = 0; i < dimensions; i++) {  //iterating through the rows of a  
			for (int j = 0; j < dimensions; j++) {  //iterating through the columns of b
				for (int k = 0; k < dimensions; k++) {   //iterating through the values of a in the columns
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return result;
	}

	public static void showMatrix(double[][] matrix) {  //use this method to print out any matrix

		for (int i = 0; i < matrix.length; i++) {  //iterating through the rows of matrix
			for (int j = 0; j < matrix[0].length; j++) {  //iterating through the columns of matrix
				System.out.print(String.valueOf(matrix[i][j]) + " ");
			}
			System.out.println("");  //starting a new line
		}
	}

	public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b){

		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);  //creating a pool of threads

		double[][] result  = new double[dimensions][dimensions];

		int numberRowsPerThread = dimensions/numberOfThreads;  //to set the number of rows each thread will multiply

		int	lastThread = numberRowsPerThread + dimensions%numberOfThreads;  // the last one will have a bit more work than the rest

		System.out.println(" numb of rows per " + numberRowsPerThread + "       " + "last one" + lastThread);

		for (int i = 0; i < numberOfThreads-1; i++) {  //creates the all the threads minus the last one
			executor.execute(new MultiplyTask(a, b, result, i*numberRowsPerThread, numberRowsPerThread));
		}
		executor.execute(new MultiplyTask(a, b, result, (numberOfThreads-1)*numberRowsPerThread, lastThread));  //creates the last thread

		try {
			executor.shutdown();  //shutting threads down
			executor.awaitTermination(20, TimeUnit.SECONDS);  //gives threads time to finish
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
