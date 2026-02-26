package UI.swing;

import Modelo.Transaccion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TxTableModel extends AbstractTableModel {

    private final String[] cols = {"ID", "Fecha", "Tipo", "Origen", "Destino", "Monto", "Comisión"};
    private List<Transaccion> data = new ArrayList<>();

    public void setData(List<Transaccion> list) {
        data = list == null ? new ArrayList<>() : list;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaccion t = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return t.getId();
            case 1: return t.getFecha() == null ? "" : t.getFecha().toString();
            case 2: return t.getTipo() == null ? "" : t.getTipo().name();
            case 3: return t.getCuentaOrigenId() == null ? "-" : t.getCuentaOrigenId();
            case 4: return t.getCuentaDestinoId() == null ? "-" : t.getCuentaDestinoId();
            case 5: return t.getMonto() == null ? "" : t.getMonto().toPlainString();
            case 6: return t.getComision() == null ? "0" : t.getComision().toPlainString();
            default: return "";
        }
    }
}