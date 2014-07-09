import java.util.ArrayList;

public class simpsonRule {

	private Double a, b, n; // limites e quantidade de divisões
	private Double h;
	private ArrayList<Operandos> ops;

	/**
	 * limite inferior, limite superior, quantidade de partes.
	 * */
	public simpsonRule(Double a, Double b, Double n) {
		this.a = a;
		this.b = b;
		this.n = n;
		this.h = (this.b - this.a) / this.n;
		ops = new ArrayList<Operandos>();
	}

	public double applySimpleForm(String function) {
		double valores[] = new double[(int) n.doubleValue() + 1];
		Operandos op;
		for (double x = a, i = 0; x <= b; x += h, i++) {
			double FXi = funcao(x, function);
			valores[(int) i] = (FXi);
			op = new Operandos();
			op.setI((int) i);
			op.setXi(x);
			op.setFXi(FXi);
			op.setKi(1);
			if (i % 2 == 0 && i != 0 && i != n)
				op.setKi(2);
			else if (i % 2 != 0 && i != 0 && i != n) {
				op.setKi(4);
			}
			op.setKiFXi(op.getKi() * op.getFXi());
			ops.add(op);
			if (a.equals(b) && i + 1 == n)
				break;
		}
		double total = valores[0] + valores[valores.length - 1];
		for (int i = 1; i < valores.length - 1; i++) {
			if (i % 2 == 0)
				total += (2 * valores[i]);
			else if (i % 2 != 0) {
				total += (4 * valores[i]);
			}
		}
		total *= h / 3;
		return total;
	}

	private Double funcao(double x, String funcao) {
		if (funcao.equals("Seno")) {
			return Math.sin(x);
		} else if (funcao.equals("Cosseno"))
			return Math.cos(x);
		else if (funcao.equals("Tangente"))
			return Math.tan(x);
		else if (funcao.equals("sqrt(1 + x^3)"))
			return Math.sqrt(1 + Math.pow(x, 3));
		else if (funcao.equals("2 + cos(2*sqrt(x))"))
			return 2 + Math.cos(2 * Math.sqrt(x));
		else if (funcao.equals("(3*x+11)/(pow(x, 2)-x-6)"))
			return (3 * x + 11) / (Math.pow(x, 2) - x - 6);
		else if (funcao.equals("1/(1+x)"))
			return 1 / (x + 1);
		else if (funcao.equals("e^x"))
			return Math.pow(Math.E, x);
		else if (funcao.equals("x^2"))
			return Math.pow(x,2);
		return Math.sin(x)/x;
	}

	public ArrayList<Operandos> getOperandos() {
		return ops;
	}

	public static void main(String[] args) {
		new UI();
	}

	public double applyCompositeForm(String function) {
		Operandos op;
		double valores[] = new double[(int) n.doubleValue() + 1];
		for (double x = a, i = 0; x <= b; x += h, i++) {
			double FXi = funcao(x, function);
			valores[(int) i] = (FXi);
			op = new Operandos();
			op.setI((int) i);
			op.setXi(x);
			op.setFXi(FXi);
			op.setKi(1);
			if (i % 3 != 0 && i != valores.length - 1 && i != 0)
				op.setKi(3);
			else if (i % 3 == 0 && i != valores.length - 1 && i != 0)
				op.setKi(2);
			op.setKiFXi(op.getKi() * op.getFXi());
			ops.add(op);
		}
		double total = valores[0] + valores[valores.length - 1];
		for (int i = 1; i < valores.length - 1; i++) {
			if (i % 3 == 0)
				total += (2 * valores[i]);
			else
				total += (3 * valores[i]);
		}
		total *= (3 * h) / 8;
		return total;
	}

}
