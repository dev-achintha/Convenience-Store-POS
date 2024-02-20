/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import View.MainWindow;
import static View.MainWindow.saleItemTbl;
import View.Repository.TableActionCellEditor;
import View.Repository.TableActionCellRender;
import View.Repository.TableActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author skasa
 */
public class CounterTable {
    
    Statement st;
    ResultSet rs;
    Connection conn;
    
    public CounterTable() {
        try {
            conn = Model.ConnectDB.connect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CounterTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CounterTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void decorateTable() {
        saleItemTbl.setRowHeight(50);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                
            }

            @Override
            public void onDelete(int row) {
                if (saleItemTbl.isEditing()) {
                    saleItemTbl.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) saleItemTbl.getModel();
                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                
            }
        };
        saleItemTbl.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        saleItemTbl.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
           }
    
}
