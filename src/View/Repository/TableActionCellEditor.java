package View.Repository;

import View.PanelAction;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        //int code = (int) jtable.getModel().getValueAt(row, column-5);
        String code = String.valueOf(jtable.getModel().getValueAt(row, column-5));
        System.out.println(code);
        PanelAction action = new PanelAction();
        action.initEvent(event, row, code);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
