package model;

public class LinearRegression {
	private double k;
	private double n;

	public LinearRegression() {
		super();
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getN() {
		return n;
	}

	public void setN(double n) {
		this.n = n;
	}

	public void fit(int[] x, int[] y) {
		double suma_proizvoda_x_y = 0;
		double suma_x = 0; 
		double suma_y = 0; 
		double suma_x_na_kvadrat = 0;

		for (int i = 0; i < x.length; i++) {
			suma_proizvoda_x_y += x[i] * y[i];
			suma_x += x[i];
			suma_y += y[i];
			suma_x_na_kvadrat += x[i] * x[i];
		}

		this.k = (x.length * suma_proizvoda_x_y - suma_x * suma_y)
				/ (x.length * suma_x_na_kvadrat - suma_x * suma_x);
		this.n = (suma_y - this.k * suma_x) / x.length;
	}

	public double predict(double x) {
		return k * x + n;
	}

}
