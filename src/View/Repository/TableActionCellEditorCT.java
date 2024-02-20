package View.Repository;


import View.PanelActionCT;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableActionCellEditorCT extends DefaultCellEditor {

    private TableActionEventCT event;

    public TableActionCellEditorCT(TableActionEventCT event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        //int code = (int) jtable.getModel().getValueAt(row, column-5);
        String code = String.valueOf(jtable.getModel().getValueAt(row, column-5));
        System.out.println(code);
        PanelActionCT action = new PanelActionCT();
        action.initEvent(event, row, code);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
