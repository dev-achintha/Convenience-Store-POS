package View.Repository;


import View.PanelActionCT;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRenderCT extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelActionCT action = new PanelActionCT();
        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
            setFont(new java.awt.Font("Segoe UI", 1, 20));
        } else {
            action.setBackground(Color.WHITE);
            setFont(new java.awt.Font("Segoe UI", 1, 20));
        }
        return action;
    }
}
