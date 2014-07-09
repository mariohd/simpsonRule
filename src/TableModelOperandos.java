

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TableModelOperandos extends AbstractTableModel {
	private String[] colunas = new String[] { "i", "Xi", "F(Xi)", "Ki" ,"Ki * F(Xi)" };
	private List<Operandos> dados;

	public TableModelOperandos() {
		dados = new ArrayList<Operandos>();
	}

	public void addAll(Collection<? extends Operandos> c) {
		dados.addAll(c);
		fireTableDataChanged();
	}

	public void clear() {
		dados.clear();
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	public void remove(String o) {
		dados.remove(o);
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Operandos op = dados.get(linha);
		DecimalFormat df = new DecimalFormat("0.0000000");
		switch (coluna) {
		case 0:
			return op.getI();
		case 1:
			return df.format(op.getXi());
		case 2:
			return df.format(op.getFXi());
		case 3:
			return op.getKi();
		case 4:
			return df.format(op.getKiFXi());
		}
		return "";
	}


	public Operandos get(int selectedRow) {
		return dados.get(selectedRow);
	}

}
