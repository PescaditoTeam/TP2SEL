package sel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MatrizMath {

	// Atributos
	private int filas, columnas;
	private double[][] matriz;

	// Constructores
	public MatrizMath(int filas, int columnas) {

		this.filas = filas;
		this.columnas = columnas;
		this.matriz = new double[filas][columnas];
	}

	public MatrizMath(String path) throws FileNotFoundException {

		Scanner sc = new Scanner(new File(path));
		sc.useLocale(Locale.ENGLISH);
		filas = sc.nextInt();
		columnas = sc.nextInt();

		this.matriz = new double[filas][columnas];

		for (int i = 0; i < filas * columnas; i++)
			this.matriz[sc.nextInt()][sc.nextInt()] = sc.nextDouble();

		sc.close();
	}

	// Getters & Setters
	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public double[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	// Metodos Propios

	public double getValorMatriz(int i, int j) {
		return this.matriz[i][j];
	}

	public void setValorMatriz(int i, int j, double valor) {
		this.matriz[i][j] = valor;
	}

	public MatrizMath sumar(MatrizMath mat) throws DistDimException {

		if (this.filas != mat.filas || this.columnas != mat.columnas)
			throw new DistDimException("No se puede sumar porque las dimensiones de las matrices son distintas");

		MatrizMath mat_ret = new MatrizMath(this.filas, this.columnas);

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				mat_ret.matriz[i][j] = this.matriz[i][j] + mat.matriz[i][j];
			}
		}

		return mat_ret;
	}

	public MatrizMath restar(MatrizMath mat) throws DistDimException {

		if (this.filas != mat.filas || this.columnas != mat.columnas)
			throw new DistDimException("No se puede restar porque las dimensiones de las matrices son distintas");

		MatrizMath mat_ret = new MatrizMath(this.filas, this.columnas);

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				mat_ret.matriz[i][j] = this.matriz[i][j] - mat.matriz[i][j];
			}

		}

		return mat_ret;
	}

	public MatrizMath producto(double real) { // MATRIZ POR UN ESCALAR

		MatrizMath mat_ret = new MatrizMath(this.filas, this.columnas);

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				mat_ret.matriz[i][j] = this.matriz[i][j] * real;
			}
		}

		return mat_ret;
	}

	public MatrizMath producto(MatrizMath mat) throws DistDimException {// MATRIZ
																		// POR
																		// MATRIZ

		if (this.columnas != mat.filas)
			throw new DistDimException(
					"No se puede multiplicar porque no coinciden la cantidad de filas y columnas de las matrices");

		MatrizMath mat_ret = new MatrizMath(this.filas, mat.columnas);

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < mat.columnas; j++) {
				mat_ret.matriz[i][j] = 0;
				for (int k = 0; k < this.columnas; k++) {
					mat_ret.matriz[i][j] += this.matriz[i][k] * mat.matriz[k][j];
				}
			}
		}
		return mat_ret;
	}

	public VectorMath producto(VectorMath vec) throws DistDimException { // MATRIZ
																			// POR
																			// VECTOR

		if (this.columnas != vec.getDim())
			throw new DistDimException(
					"No se puede multiplicar porque el numero de columnas de la matriz es distinto a la dimension del vector");

		VectorMath vec_ret = new VectorMath(vec.getDim());

		for (int i = 0; i < this.filas; i++) {

			double suma = 0;

			for (int j = 0; j < vec.getDim(); j++) {
				suma += this.matriz[i][j] * vec.getValor(j);
			}

			vec_ret.setValor(i, suma);
		}

		return vec_ret;
	}

	public double normaUno() { // maxima suma en la columnas

		double result = 0;

		VectorMath vec_ret = new VectorMath(this.columnas);

		for (int j = 0; j < this.columnas; j++) {

			result = 0;

			for (int i = 0; i < this.filas; i++)
				result += Math.abs(this.matriz[i][j]);

			vec_ret.setValor(j, result);
		}

		return vec_ret.normaInfinito();
	}

	public double normaDos() { // raiz de la suma de los elementos al cuadrado

		double suma = 0;

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				suma += Math.pow(Math.abs(this.matriz[i][j]), 2);
			}
		}

		return Math.sqrt(suma);
	}

	public double normaInfinito() { // maxima suma en las filas

		double result = 0;

		VectorMath vec_ret = new VectorMath(this.filas);

		for (int i = 0; i < this.filas; i++) {

			result = 0;

			for (int j = 0; j < this.columnas; j++)
				result += Math.abs(this.matriz[i][j]);

			vec_ret.setValor(i, result);
		}

		return vec_ret.normaInfinito();
	}

	// Overrides
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrizMath other = (MatrizMath) obj;
		if (columnas != other.columnas)
			return false;
		if (filas != other.filas)
			return false;
		if (!Arrays.deepEquals(matriz, other.matriz))
			return false;
		return true;
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++)
				result += this.matriz[i][j] + " ";
			result += "\n";
		}
		return result;
	}

	public MatrizMath clone() {

		MatrizMath mat_ret = new MatrizMath(this.getFilas(), this.getColumnas());

		for (int i = 0; i < this.filas; i++)
			for (int j = 0; j < this.columnas; j++)
				mat_ret.setValorMatriz(i, j, this.getValorMatriz(i, j));

		return mat_ret;
	}

	// Metodos Adicionales
	public MatrizMath identidad() throws DistDimException {

		if (this.filas != this.columnas)
			throw new DistDimException("Solo se puede calcular la Matriz Identidad de matrices cuadradas.");

		MatrizMath mat_ret = new MatrizMath(this.filas, this.columnas);

		for (int i = 0; i < this.filas; i++) {
			mat_ret.setValorMatriz(i, i, 1);
		}

		return mat_ret;
	}

	// ERROR INVERSA
	public double errorInversa() {

		double rtado = this.producto(this.inversa()).restar(this.identidad()).normaDos();

		return rtado;
	}
	// ||(A^(-1)�)-(I)||2
	// ||(A * (A^(-1)�))-(I)||2

	public MatrizMath inversa() throws DistDimException {
		// Se valida que la matriz siempre va a ser cuadrada y con det(mat)!=0
		MatrizMath m1 = gaussJordan(this.clone().matriz);

		return m1;
	}

	public MatrizMath gaussJordan(double[][] matriz) {

		MatrizMath mat_ret = this.identidad();

		int count = 0;
		boolean cambio = true, marca;

		while (count < matriz.length && cambio == true) {

			cambio = false;

			for (int i = 0; i < matriz.length; i++) {
				if (matriz[i][i] == 0) {
					eliminar_cero_diagonal(matriz, mat_ret.matriz, i);
					cambio = true;
				}
			}
			count++;
		}

		for (int i = 0; i < matriz.length; i++) {
			for (int j = i; j < matriz.length; j++) {
				marca = dividir_fila(matriz, mat_ret.matriz, i, j);
				if (j > i && marca == true)
					resta_fila(matriz, mat_ret.matriz, i, j);
			}
		}

		for (int i = matriz.length - 1; i >= 0; i--) {
			for (int j = i; j >= 0; j--) {
				// dividr_fila2(matriz, mat_ret.matriz, i, j);
				dividir_fila(matriz, mat_ret.matriz, i, j);
				if (j < i)
					resta_fila(matriz, mat_ret.matriz, i, j);
			}
		}
		return mat_ret;

	}

	public static void eliminar_cero_diagonal(double[][] matriz, double[][] mat_ret, int i) {

		for (int j = 0; j < matriz.length; j++) {
			if (i == (matriz.length - 1)) {

				matriz[i][j] = matriz[i][j] + matriz[i - 1][j];
				mat_ret[i][j] = mat_ret[i][j] + mat_ret[i - 1][j];
			} else {

				matriz[i][j] = matriz[i][j] + matriz[i + 1][j];
				mat_ret[i][j] = mat_ret[i][j] + mat_ret[i + 1][j];
			}
		}
	}

	public static boolean dividir_fila(double[][] matriz, double[][] mat_ret, int columna, int fila) {

		double divisor = matriz[fila][columna];
		boolean flag = false;

		while (fila == columna && divisor == 0) {
			eliminar_cero_diagonal(matriz, mat_ret, fila);
			divisor = matriz[fila][columna];
		}

		if (divisor != 0) {

			flag = true;

			if (divisor != 1) {
				for (int i = 0; i < matriz.length; i++) {

					matriz[fila][i] = matriz[fila][i] / divisor;
					mat_ret[fila][i] = mat_ret[fila][i] / divisor;
				}
			}
		}
		return flag;

	}

	public static void resta_fila(double[][] matriz, double[][] mat_ret, int columna, int fila) {

		for (int i = 0; i < matriz.length; i++) {

			matriz[fila][i] = matriz[fila][i] - matriz[columna][i];
			mat_ret[fila][i] = mat_ret[fila][i] - mat_ret[columna][i];
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * MatrizMath m1 = new MatrizMath(2, 2); MatrizMath m2 = new
		 * MatrizMath(2, 4);
		 * 
		 * m1.setValorMatriz(0, 0, 1); m1.setValorMatriz(0, 1, 5);
		 * m1.setValorMatriz(1, 0, 2); m1.setValorMatriz(1, 1, 6);
		 * 
		 * System.out.println(m1);
		 * 
		 * System.out.println(m1.identidad());
		 * 
		 * m2.setValorMatriz(0, 0, 7); m2.setValorMatriz(0, 1, 8);
		 * m2.setValorMatriz(0, 2, 9); m2.setValorMatriz(0, 3, 10);
		 * m2.setValorMatriz(1, 0, 11); m2.setValorMatriz(1, 1, 12);
		 * m2.setValorMatriz(1, 2, 13); m2.setValorMatriz(1, 3, 14);
		 * 
		 * System.out.println(m2);
		 * 
		 * MatrizMath mResult = m1.producto(m2);
		 * 
		 * System.out.println(" Resultado: \n" + mResult + "\n");
		 * 
		 * MatrizMath m4 = new MatrizMath(3, 3);
		 * 
		 * m4.setValorMatriz(0, 0, 3); m4.setValorMatriz(0, 1, -1);
		 * m4.setValorMatriz(0, 2, 4); m4.setValorMatriz(1, 0, -5);
		 * m4.setValorMatriz(1, 1, 0); m4.setValorMatriz(1, 2, 2);
		 * m4.setValorMatriz(2, 0, 1); m4.setValorMatriz(2, 1, -2);
		 * m4.setValorMatriz(2, 2, 6);
		 * 
		 * System.out.println("Matriz 4: \n" + m4.toString() + "\n");
		 * 
		 * System.out.println("Norma 1: \n" + m4.normaUno() + "\n");
		 * 
		 * System.out.println("Norma 2: \n" + m4.normaDos() + "\n");
		 * 
		 * System.out.println("Norma Infinito: \n" + m4.normaInfinito() + "\n");
		 * 
		 * System.out.println("Inversa: \n" + m4.inversa().toString() + "\n");
		 */

		MatrizMath mat1 = new MatrizMath("entradaMat.IN");
		MatrizMath m2 = new MatrizMath("entradaMat.in");
		System.out.println(m2.toString());
		System.out.println(mat1.toString());
	}
}
