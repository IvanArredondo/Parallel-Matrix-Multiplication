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
	int position;
	int offset;

	public MultiplyTask(double[][] a, double[][] b, double[][] c, int position, int offset) {
		// TODO Auto-generated constructor stub

		this.matrixA = a;
		this.matrixB = b;
		this.matrixResult = c;
		this.position = position;
		this.offset = offset;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		for (int i = position; i < (position + offset); i++) {
			for (int j = 0; j < matrixB[0].length; j++) {
				for (int k = 0; k < matrixA[0].length; k++) {
					matrixResult[i][j] += matrixA[i][k] * matrixB[k][j];  //doing stuff
				}

			}

		}
	}
}

