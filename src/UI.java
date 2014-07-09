import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class UI extends JFrame {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private JTextField limiteInferior;
	private JTextField limiteSuperior;
	private JTextField qtdPartes;
	private JButton calcular;
	private JComboBox funcoes;
	private JLabel result;
	private TableModelOperandos modelo;
	private JTable jtb;
	private JScrollPane jscp;
	private JRadioButton simpleForm;
	private JRadioButton compositeForm;
	private ButtonGroup bg;

	public UI() {
		lookNFeel();
		setTitle("Simpson`s Rule");
		initializeVariables();
		organize();
		addActionOnButtons();
		preencherFuncoes();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeVariables() {
		limiteInferior = new JTextField(10);
		limiteSuperior = new JTextField(10);
		qtdPartes = new JTextField(10);
		calcular = new JButton("Calcular");
		result = new JLabel("--------------------");
		funcoes = new JComboBox();
		result.setFont(new Font("", Font.BOLD, 20));
		modelo = new TableModelOperandos();
		jtb = new JTable(modelo);
		jscp = new JScrollPane(jtb);
		simpleForm = new JRadioButton("Forma 1/3");
		compositeForm = new JRadioButton("Forma 3/8");
		bg = new ButtonGroup();
		simpleForm.setSelected(true);
		bg.add(simpleForm);
		bg.add(compositeForm); 
	}

	private void organize() {
		setLayout(new BorderLayout());
		JPanel gridp = new JPanel(new GridBagLayout());
		gridp.add(funcoes, constraints(0, 0, true));
		gridp.add(simpleForm, constraints(0, 1));
		gridp.add(compositeForm, constraints(1, 1));
		gridp.add(new JLabel("Limite Inferior: "), constraints(0, 2));
		gridp.add(limiteInferior, constraints(1, 2));
		gridp.add(new JLabel("Limite Superior: "), constraints(0, 3));
		gridp.add(limiteSuperior, constraints(1, 3));
		gridp.add(new JLabel("Quantidade de Partes: "), constraints(0, 4));
		gridp.add(qtdPartes, constraints(1, 4));
		gridp.add(calcular, constraints(0, 5, true));
		gridp.add(result, constraints(0, 6, true));
		add(gridp, BorderLayout.WEST);
		add(jscp, BorderLayout.CENTER);
	}

	private GridBagConstraints constraints(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.insets = new Insets(5, 5, 5, 5);
		return c;
	}

	private GridBagConstraints constraints(int x, int y, boolean a) {
		GridBagConstraints c = constraints(x, y);
		c.gridwidth = 2;
		c.fill = GridBagConstraints.VERTICAL;

		return c;
	}

	private void addActionOnButtons() {
		calcular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!limiteInferior.getText().equals("")
						&& !limiteSuperior.getText().equals("")
						&& !qtdPartes.getText().equals("")) {
					double inferior = Double.parseDouble(limiteInferior.getText());
					double superior = Double.parseDouble(limiteSuperior.getText());
					double partes = Double.parseDouble(qtdPartes.getText());
					simpsonRule simpson = new simpsonRule(new Double(inferior), new Double(
							superior), new Double(partes));
					double resultado = 0.0;
					if (simpleForm.isSelected())
						resultado = simpson.applySimpleForm((String) funcoes.getSelectedItem());
					else if (compositeForm.isSelected())
						resultado = simpson.applyCompositeForm((String) funcoes.getSelectedItem());
					DecimalFormat df = new DecimalFormat("0.0000000000000000");
					result.setText(df.format(resultado));
					adicionarTabela(simpson.getOperandos());
					validate();
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Digite os valores.");
				}
			}
		});
	}

	private void preencherFuncoes() {
		funcoes.addItem("Custom");
		funcoes.addItem("Seno");
		funcoes.addItem("Cosseno");
		funcoes.addItem("Tangente");
		funcoes.addItem("sqrt(1 + x^3)");
		funcoes.addItem("2 + cos(2*sqrt(x))");
		funcoes.addItem("(3*x+11)/(pow(x, 2)-x-6)");
		funcoes.addItem("1/(1+x)");
		funcoes.addItem("x^2");
		funcoes.addItem("e^x");
	}
	
	private void adicionarTabela(ArrayList<Operandos> ops){
		getContentPane().remove(jscp);
		modelo = new TableModelOperandos();
		jtb = new JTable(modelo);
		jscp = new JScrollPane(jtb);
		modelo.clear();
		modelo.addAll(ops);
		add(jscp, BorderLayout.CENTER);
	}

	private void lookNFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException exc) {
		} catch (ClassNotFoundException exc) {
		} catch (InstantiationException exc) {
		} catch (IllegalAccessException exc) {
		}
	}
}
