package ecse420A1;
/*
ECSE 420 - Parallel Computing 
Dennis Giannacopoulos
Assignment 1
Question 1
Malkolm Alburquenque - 260562740 - malkolm.alburquenque@mail.mcgill.ca
Ivan Arredondo - 260566638 - ivan.arredondo@mail.mcgill.ca
*/

import java.util.concurrent.Callable;

public class MultiplyTask implements Runnable {

	double[][] matrixA;
	double[][] matrixB;
	double[][] matrixResult;
	int position;  //where the thread starts 
	int offset;  //how many rows the threads multiplies

	public MultiplyTask(double[][] matrixA, double[][] matrixB, double[][] matrixResult, int position, int offset) {
		
		this.matrixA = matrixA;
		this.matrixB = matrixB;
		this.matrixResult = matrixResult;
		this.position = position;
		this.offset = offset;
	}

	@Override
	public void run() {

		for (int i = position; i < (position + offset); i++) {  //iterating through the rows starting at the right position
			for (int j = 0; j < matrixB[0].length; j++) {
				for (int k = 0; k < matrixA[0].length; k++) {
					matrixResult[i][j] += matrixA[i][k] * matrixB[k][j];  //matrix multiplication dot product
				}

			}

		}
	}
}

