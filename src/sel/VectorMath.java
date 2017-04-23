package sel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class VectorMath {
	
		//Atributos
		private int dim;
		private double[] coord;

		//Constructores
		public VectorMath(String path) throws FileNotFoundException {
		
			Scanner sc = new Scanner(new File(path));
			sc.useLocale(Locale.ENGLISH);
			this.dim = sc.nextInt();
			this.coord = new double[this.dim];

			for (int i = 0; i < dim; i++) {
				this.coord[i] = sc.nextDouble();
			}
			sc.close();
		}

		public VectorMath(int dim) {
		
			this.dim = dim;
			this.coord = new double[dim];
		}

		//Gettters & Setters
		public double[] getVec() {
			return coord;
		}

		public void setVec(double[] coord) {
			this.coord = coord;
		}

		public void setDim(int dim) {
			this.dim = dim;
		}

		public int getDim() {
			return this.dim;
		}
		
		//Metodos Propios
		public double getValor(int indice) {
			if(indice>=0)
				return this.coord[indice];
			else
				return -999999;
		}

		public void setValor(int indice, double valor) {
			if(indice>=0)
				this.coord[indice] = valor;
		}
		
		public VectorMath sumar(VectorMath vec) throws DistDimException {
		
			if (this.dim != vec.dim)
				throw new DistDimException("Dimensiones Distintas");
				
			VectorMath vec_ret = new VectorMath(vec.dim);
			
			for (int i = 0; i < this.dim; i++)
				vec_ret.coord[i] = (this.coord[i] + vec.coord[i]);

			return vec_ret;
		}

		public VectorMath restar(VectorMath vec) throws DistDimException {
			
			if (this.dim != vec.dim)
				throw new DistDimException("Dimensiones Distintas");
			
			VectorMath vec_ret = new VectorMath(vec.dim);
			
			for (int i = 0; i < this.dim; i++)
				vec_ret.coord[i] = (this.coord[i] - vec.coord[i]);

			return vec_ret;
		}

		public double producto(VectorMath vec) throws DistDimException {
		
			if (this.dim != vec.dim)
				throw new DistDimException("Dimensiones Distintas");
			
			double suma = 0;
			
			for (int i = 0; i < this.dim; i++)
				suma += this.coord[i] * vec.coord[i];
			
			return suma;
		}

		public VectorMath producto(double real) {
			
			VectorMath vec_ret = new VectorMath(this.dim);
			
			for (int i = 0; i < this.dim; i++)
				vec_ret.coord[i] = this.coord[i] * real;
			
			return vec_ret;
		}
		
		public double normaUno() {
		
			double suma = 0;
			
			for (int i = 0; i < dim; i++) {
				suma += Math.abs(coord[i]);
			}
			
			return suma;
		}
		
		public double normaDos() {
		
			double suma = 0;
			
			for (int i = 0; i < dim; i++) {
				suma += Math.pow(coord[i], 2);
			}
			
			return Math.sqrt(suma);
		}
		
		public double normaInfinito() {
		
			double mayor = 0;
			
			for (int i = 0; i < this.dim; i++) {
				if(i==0 || mayor < Math.abs(coord[i]))
					mayor = Math.abs(coord[i]);
			}
			
			return mayor;
		}
			
		public VectorMath producto(MatrizMath mat) throws DistDimException{ //PRODUCTO POR UNA MATRIZ
			
		if(this.dim != mat.getFilas())
			throw new DistDimException("Dimensiones Distintas");
			
		VectorMath vec_ret = new VectorMath(mat.getColumnas());
			
		for(int j=0;j<mat.getColumnas();j++)
			for(int i=0;i<mat.getFilas();i++)
				vec_ret.coord[j] += this.coord[i]*mat.getValorMatriz(i, j);
			
		return vec_ret;
		}

		//Overrides
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + dim;
			result = prime * result + Arrays.hashCode(coord);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VectorMath other = (VectorMath) obj;
			if (dim != other.dim)
				return false;
			if (!Arrays.equals(coord, other.coord))
				return false;
			return true;
		}

		public VectorMath clone() {
		
			VectorMath vec_ret = new VectorMath(this.dim);
		
			for (int i = 0; i < this.dim; i++) {

				vec_ret.coord[i] = this.coord[i];
			}
		
			return vec_ret;
		}

		public String toString() {
			return "" + Arrays.toString(coord) + "";
		}
		
		public static void main(String[] args) throws FileNotFoundException {

			VectorMath vec1 = new VectorMath("entradaVec.in");
			VectorMath vec2 = new VectorMath(6);
			VectorMath vec3 = new VectorMath(6);
			
			vec2 = vec1.clone();
			vec3 = vec1.sumar(vec2);

			System.out.println("2 + 3: "+vec2.sumar(vec3));
			System.out.println(vec1.toString());
			System.out.println(vec2.producto(8));
			VectorMath vec4 = vec1.clone();
			System.out.println(vec4);
		}
}
