package sel;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Sel {

	// Atributos
	private MatrizMath matrizCoeficientes;
	private VectorMath vectorIndependientes;
	private VectorMath vectorResultado;
	private double error;

	// Constructores
	public Sel(String path) {

		try {
			Scanner sc = new Scanner(new File(path));

			int dimension = sc.nextInt();
			this.matrizCoeficientes = new MatrizMath(dimension, dimension); // A
			this.vectorIndependientes = new VectorMath(dimension); // B
			this.vectorResultado = new VectorMath(dimension); // X
			// A*X=B
			// X=A^(-1)*B

			for (int i = 0; i < dimension * dimension; i++) {
				this.matrizCoeficientes.setValorMatriz(sc.nextInt(), sc.nextInt(), sc.nextDouble());
			}

			for (int i = 0; i < dimension; i++) {
				this.vectorIndependientes.setValor(i, sc.nextDouble());
			}

			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Metodos Propios
	public void grabarSalida(String path) {

		try {
			PrintWriter salida = new PrintWriter(new FileWriter(path + "salida.out"));

			salida.println(this.vectorResultado.getDim());

			for (int i = 0; i < this.vectorIndependientes.getDim(); i++) {
				salida.println(this.vectorResultado.getValor(i));
			}

			salida.println();
			salida.println(this.error);
			salida.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Acotar el Error
	private void calcularErrorSolucion(){
			
			/*VectorMath obj = new VectorMath(vectorIndependientes.getDim());
			
			for(int i=0;i<obj.getDim(),i++)
				obj.setValor(i,vectorIndependientes.getValor(i));
				*/
				
			VectorMath obj = vectorIndependientes.clone();
			
			this.error = obj.restar(matrizCoeficientes.producto(vectorResultado)).normaDos();
			// ||B-(A*X)||2
		}

	// Procesar el SEL
	public void resolver() throws Exception {

		VectorMath obj = matrizCoeficientes.inversa().producto(vectorIndependientes);

		for (int i = 0; i < obj.getDim(); i++)
			vectorResultado.setValor(i, obj.getValor(i));

		calcularErrorSolucion();
	}

	public static void main(String[] args) {

		Sel sel = new Sel("entradaSel.in");

		try {
			Calendar timeInit = new GregorianCalendar();
			sel.resolver();
			Calendar timeEnd = new GregorianCalendar();
			double timeLapse = timeEnd.getTimeInMillis() - timeInit.getTimeInMillis();
			System.out.println("El tiempo de duracion del proceso fue : " + timeLapse + "\n");
			sel.grabarSalida("salida.out");
			System.out.println("El error estimado es: " + sel.error + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
