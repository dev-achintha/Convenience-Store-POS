package View.Repository;

import View.PanelAction;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelAction action = new PanelAction();
        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
            setFont(new java.awt.Font("Segoe UI", 1, 20));
        } else {
            action.setBackground(new java.awt.Color(242, 242, 242));
            setFont(new java.awt.Font("Segoe UI", 1, 20));
        }
        return action;
    }
}
