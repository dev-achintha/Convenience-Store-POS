/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import static View.MainWindow.conn;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author skasa
 */
public class AddProduct extends javax.swing.JFrame {

    int user=1;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
    int productid;
    boolean addProductCBox = false;
    boolean expCBox = true;
    MainWindow mw = new MainWindow();
    
    public AddProduct() {
        initComponents();
        
        setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
            generateProductID();
            st = conn.prepareStatement("SELECT category_name FROM tblproductcategory");
            rs = st.executeQuery("SELECT category_name FROM tblproductcategory");
            while (rs.next()) {
                String item = rs.getString("category_name");
                categoryCB.addItem(item);
                
            }
            st = conn.prepareStatement("SELECT * FROM tblproductunit");
            rs = st.executeQuery("SELECT * FROM tblproductunit");
            while(rs.next()) {
                String unit = rs.getString("unit_name");
                unitCB.addItem(unit);
            }
            st = conn.prepareStatement("SELECT * FROM tblsupplier");
            rs = st.executeQuery("SELECT * FROM tblsupplier");
            while(rs.next()){
                String sup = rs.getString("supplier_code");
                supplierCB.addItem(sup);
            }
            st = conn.prepareStatement("SELECT produce_code,product_name FROM tblproduct");
            rs = st.executeQuery("SELECT produce_code,product_name FROM tblproduct");
            while(rs.next()) {
                String code = rs.getString("product_name");
                codeCB.addItem(code);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AutoCompleteDecorator.decorate(categoryCB);
        AutoCompleteDecorator.decorate(supplierCB);
        AutoCompleteDecorator.decorate(codeCB);
        AutoCompleteDecorator.decorate(codeCB);
        
        yearLbl.setVisible(false);
        yearTf.setVisible(false);
        monthLbl.setVisible(false);
        monthTf.setVisible(false);
        dayLbl.setVisible(false);
        dayTf.setVisible(false);
        addNextBtn.setBackground(Color.yellow);
        finishBtn.setBackground(Color.PINK);
        cancelBtn.setBackground(Color.DARK_GRAY);
        addNewCodeTf.setFont(new java.awt.Font("Segoe UI", 2, 20));
        addNewCodeTf.setForeground(new java.awt.Color(51, 51, 51));
        addNewCodeTf.setBackground(new java.awt.Color(255,255,255));
        addNewCodeTf.setText(" Code will be generated automatically");
        addNewCodeTf.setEditable(false);
        addNewCodeTf.setEnabled(false);
        disableComponent(false);
        
        }
    
    Integer generateProductID() {
    List idList = new ArrayList();
    String getID = "SELECT product_id FROM tblproduct";
    try {
        pst = conn.prepareStatement(getID);
        rs = pst.executeQuery(getID);
        while (rs.next()) {
            int id = rs.getInt("product_id");
            idList.add(id);
        }

    } catch (SQLException ex) {
        Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
    }
    Collections.sort(idList);
    int size = idList.size();
    int  lastElement = (int) idList.get(size-1);
    productid = lastElement+1;
    return productid;
        
    }
    
    void insertNewItem() throws SQLException {
        categoryCB.setSelectedIndex(3);
        supplierCB.setSelectedIndex(4);
        nameTf.setText("Test1");
        unitCB.setSelectedIndex(3);
        unitTf.setText("50");
        pPricePUnitTf.setText("20.00");
        sPricePUnitTf.setText("30.00");
        discountTf.setText("0.00");
        reOderTf.setText("10");
        yearTf.setText("2023");
        monthTf.setText("03");
        dayTf.setText("12");
        
        String pid = String.valueOf(generateProductID());
        String getUID = "SELECT unit_id FROM tblproductunit WHERE unit_name='"+unitCB.getSelectedItem()+"'";
        st = conn.prepareStatement(getUID);
        rs = st.executeQuery(getUID);
        rs.next();
        int uid = rs.getInt(1);
        st = conn.prepareCall("SELECT SYSDATE()");
        rs = st.executeQuery("SELECT SYSDATE()");
        rs.next();
        String date = rs.getString(1);
        
        insertIntoProductTable(pid,uid,date);
        insertIntoProductReceiveTable(pid, uid, date);
        
    }
    
    void insertIntoProductTable(String productId, int uid, String date) throws SQLException {
        String getCatID = "(SELECT category_id FROM tblproductcategory WHERE category_name='"+categoryCB.getSelectedItem()+"')";
        st = conn.prepareStatement(getCatID);
        rs = st.executeQuery(getCatID);
        rs.next();
        int cid = rs.getInt(1);
        
        st = conn.prepareStatement("SELECT NOW()+0");
        rs = st.executeQuery("SELECT NOW()+0");
        rs.next();
        String co = String.valueOf(rs.getLong(1));
        
        String insertTblPro = "INSERT INTO tblproduct (product_id, produce_code, product_name, category_id, unit_in_stock, unit_price, expire_date, added_date, unit_id) VALUES ('"+productId+"', '"+co+"', '"+nameTf.getText()+"', '"+cid+"','0.00' ,'"+sPricePUnitTf.getText()+"','"+yearTf.getText()+"-"+monthTf.getText()+"-"+dayTf.getText()+"','"+date+"','"+uid+"');";
        st = conn.prepareStatement(insertTblPro);
        st.executeUpdate(insertTblPro);
    }
    
    void insertIntoProductReceiveTable(String productId,int unitId, String date) throws SQLException {
        float subT ;
        subT = Float.parseFloat(unitTf.getText())*Float.parseFloat(pPricePUnitTf.getText());
        
        st = conn.prepareStatement("SELECT supplier_id FROM tblsupplier WHERE supplier_code='"+supplierCB.getSelectedItem()+"'");
        rs = st.executeQuery("SELECT supplier_id FROM tblsupplier WHERE supplier_code='"+supplierCB.getSelectedItem()+"'");
        rs.next();
        String supplierId = rs.getString(1);
        
        String insertTblR = "INSERT INTO tblreceiveproduct (product_id,product_name,unit_id,quantity, unit_price, sub_total, discount,supplier_id,received_date) VALUES ('"+productId+"','"+nameTf.getText()+"','"+unitId+"','"+unitTf.getText()+"', '"+pPricePUnitTf.getText()+"', '"+subT+"', '"+discountTf.getText()+"','"+supplierId+"','"+date+"');";
        st = conn.prepareCall(insertTblR);
        st.executeUpdate(insertTblR);
    }
    
//    String produceCode() throws SQLException {
//        List listCode = new ArrayList();
//        String code=null;
//        String getC = "SELECT supplier_code FROM tblsupplier";
//        st = conn.prepareStatement(getC);
//        rs = st.executeQuery(getC);
//        while(rs.next()) {
//            int x = rs.getInt("supplier_code");
//            listCode.add(x);
//        }
//        for(Object i:listCode) {
//            String setCode="UPDATE tblsupplier SET supplier_code= (LPAD("+i+",6,0)) WHERE supplier_id='"+i+"'";
//            st = conn.prepareStatement(setCode);
//            st.executeUpdate(setCode);
//        }
//        return code;
//    }
    
    void loadProduct() {
        
    }
    void enableComponent(Boolean s) {
        codeCB.setEnabled(false);
        jLabel5.setForeground(new java.awt.Color(0,0,0));
        addCategoryBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_add_new_45px.png")));
        jLabel2.setForeground(new java.awt.Color(0,0,0));
        addSupplierBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_add_new_45px.png")));
        jLabel17.setForeground(new java.awt.Color(0,0,0));
        addNewCodeTf.setBackground(new java.awt.Color(232, 244, 250));
        jLabel3.setForeground(new java.awt.Color(0,0,0));
        nameTf.setBackground(new java.awt.Color(232, 244, 250));
        jLabel4.setForeground(new java.awt.Color(0,0,0));
        jLabel6.setForeground(new java.awt.Color(0,0,0));
        jLabel7.setForeground(new java.awt.Color(0,0,0));
        jLabel8.setForeground(new java.awt.Color(0,0,0));
        jLabel9.setForeground(new java.awt.Color(0,0,0));
        jLabel18.setForeground(new java.awt.Color(0,0,0));
        jLabel19.setForeground(new java.awt.Color(0,0,0));
        jLabel12.setForeground(new java.awt.Color(0,0,0));
        unitTf.setBackground(new java.awt.Color(232, 244, 250));
        pPricePUnitTf.setBackground(new java.awt.Color(232, 244, 250));
        sPricePUnitTf.setBackground(new java.awt.Color(232, 244, 250));
        discountTf.setBackground(new java.awt.Color(232, 244, 250));
        reOderTf.setBackground(new java.awt.Color(232, 244, 250));
        unitCB.setBorder(new javax.swing.border.MatteBorder(null));
        unitCB.setBackground(new java.awt.Color(232, 244, 250));
        unitCB.setForeground(new java.awt.Color(0,0,0));
        supplierCB.setBorder(new javax.swing.border.MatteBorder(null));
        supplierCB.setBackground(new java.awt.Color(232, 244, 250));
        supplierCB.setForeground(new java.awt.Color(0,0,0));
        categoryCB.setBorder(new javax.swing.border.MatteBorder(null));
        categoryCB.setBackground(new java.awt.Color(232, 244, 250));
        categoryCB.setForeground(new java.awt.Color(0,0,0));
        yearLbl.setForeground(new java.awt.Color(0,0,0));
        yearTf.setBackground(new java.awt.Color(232,244,250));
        monthLbl.setForeground(new java.awt.Color(0,0,0));
        monthTf.setBackground(new java.awt.Color(232,244,250));
        dayLbl.setForeground(new java.awt.Color(0,0,0));
        dayTf.setBackground(new java.awt.Color(232,244,250));
        codeCB.setBorder(new javax.swing.border.MatteBorder(null));
        codeCB.setBackground(new java.awt.Color(214,214,214));
        addNewCodeTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        nameTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        unitTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        pPricePUnitTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        sPricePUnitTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        discountTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        reOderTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        yearTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        monthTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        dayTf.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 5, 1, new java.awt.Color(161, 208, 255)));
        unitCB.setEnabled(true);
        pPricePUnitTf.setEnabled(true);
        reOderTf.setEnabled(true);
        discountTf.setEnabled(true);
        unitTf.setEnabled(true);
        categoryCB.setEnabled(true);
        sPricePUnitTf.setEnabled(true);
        supplierCB.setEnabled(true);
        nameTf.setEnabled(true);
        expCheckBox.setEnabled(true);
        yearTf.setEnabled(true);
        monthTf.setEnabled(true);
        dayTf.setEnabled(true);
        jLabel10.setForeground(new java.awt.Color(214,214,214));
        
    }
    
    void disableComponent(Boolean s) {
        jLabel5.setForeground(new java.awt.Color(214,214,214));
        addCategoryBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Inactive/icons8_add_new_45px_1.png")));
        jLabel2.setForeground(new java.awt.Color(214,214,214));
        addSupplierBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Inactive/icons8_add_new_45px_1.png")));
        jLabel17.setForeground(new java.awt.Color(214,214,214));
        addNewCodeTf.setBackground(new java.awt.Color(214,214,214));
        jLabel3.setForeground(new java.awt.Color(214,214,214));
        nameTf.setBackground(new java.awt.Color(214,214,214));
        jLabel4.setForeground(new java.awt.Color(214,214,214));
        jLabel6.setForeground(new java.awt.Color(214,214,214));
        jLabel7.setForeground(new java.awt.Color(214,214,214));
        jLabel8.setForeground(new java.awt.Color(214,214,214));
        jLabel9.setForeground(new java.awt.Color(214,214,214));
        jLabel18.setForeground(new java.awt.Color(214,214,214));
        jLabel19.setForeground(new java.awt.Color(214,214,214));
        jLabel12.setForeground(new java.awt.Color(214,214,214));
        unitTf.setBackground(new java.awt.Color(214,214,214));
        pPricePUnitTf.setBackground(new java.awt.Color(214,214,214));
        sPricePUnitTf.setBackground(new java.awt.Color(214,214,214));
        discountTf.setBackground(new java.awt.Color(214,214,214));
        reOderTf.setBackground(new java.awt.Color(214,214,214));
        unitCB.setBorder(new javax.swing.border.MatteBorder(null));
        unitCB.setBackground(new java.awt.Color(214,214,214));
        unitCB.setForeground(new java.awt.Color(214,214,214));
        supplierCB.setBorder(new javax.swing.border.MatteBorder(null));
        supplierCB.setBackground(new java.awt.Color(214,214,214));
        supplierCB.setForeground(new java.awt.Color(214,214,214));
        categoryCB.setBorder(new javax.swing.border.MatteBorder(null));
        categoryCB.setBackground(new java.awt.Color(214,214,214));
        categoryCB.setForeground(new java.awt.Color(214,214,214));
        yearLbl.setForeground(new java.awt.Color(214,214,214));
        yearTf.setBackground(new java.awt.Color(214,214,214));
        monthLbl.setForeground(new java.awt.Color(214,214,214));
        monthTf.setBackground(new java.awt.Color(214,214,214));
        dayLbl.setForeground(new java.awt.Color(214,214,214));
        dayTf.setBackground(new java.awt.Color(214,214,214));
        codeCB.setBackground(new java.awt.Color(232,244,250));
        addNewCodeTf.setBorder(new javax.swing.border.MatteBorder(null));
        nameTf.setBorder(new javax.swing.border.MatteBorder(null));
        unitTf.setBorder(new javax.swing.border.MatteBorder(null));
        pPricePUnitTf.setBorder(new javax.swing.border.MatteBorder(null));
        sPricePUnitTf.setBorder(new javax.swing.border.MatteBorder(null));
        discountTf.setBorder(new javax.swing.border.MatteBorder(null));
        reOderTf.setBorder(new javax.swing.border.MatteBorder(null));
        yearTf.setBorder(new javax.swing.border.MatteBorder(null));
        monthTf.setBorder(new javax.swing.border.MatteBorder(null));
        dayTf.setBorder(new javax.swing.border.MatteBorder(null));
        unitCB.setEnabled(false);
        pPricePUnitTf.setEnabled(false);
        reOderTf.setEnabled(false);
        discountTf.setEnabled(false);
        unitTf.setEnabled(false);
        categoryCB.setEnabled(false);
        sPricePUnitTf.setEnabled(false);
        supplierCB.setEnabled(false);
        nameTf.setEnabled(false);
        expCheckBox.setEnabled(false);
        yearTf.setEnabled(false);
        monthTf.setEnabled(false);
        dayTf.setEnabled(false);
        codeCB.setEnabled(true);
        jLabel10.setForeground(Color.BLACK);
        
    }
    
    float getStock() throws SQLException {
        float currentStock;
        float newStock;
        String getStock = "SELECT product_id,unit_in_stock FROM tblproduct WHERE product_id = '"+generateProductID()+"';";
        st = conn.prepareStatement(getStock);
        rs = st.executeQuery(getStock);
        currentStock = rs.getFloat("unit_in_stock");
        newStock = currentStock+Float.parseFloat(unitTf.getText());
        return newStock;
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nameTf = new javax.swing.JTextField();
        unitTf = new javax.swing.JTextField();
        discountTf = new javax.swing.JTextField();
        reOderTf = new javax.swing.JTextField();
        userNameLbl = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        finishBtn = new javax.swing.JButton();
        addNextBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        yearTf = new javax.swing.JTextField();
        monthTf = new javax.swing.JTextField();
        dayTf = new javax.swing.JTextField();
        yearLbl = new javax.swing.JLabel();
        monthLbl = new javax.swing.JLabel();
        dayLbl = new javax.swing.JLabel();
        addCategoryBtnLbl = new javax.swing.JLabel();
        pPricePUnitTf = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        sPricePUnitTf = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        addNewCodeTf = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        addSupplierBtnLbl = new javax.swing.JLabel();
        supplierCB = new javax.swing.JComboBox<>();
        codeCB = new javax.swing.JComboBox<>();
        unitCB = new javax.swing.JComboBox<>();
        categoryCB = new javax.swing.JComboBox<>();
        addProductCheckBox = new javax.swing.JLabel();
        expCheckBox = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Collect Product");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(780, 910));
        setMinimumSize(new java.awt.Dimension(780, 910));
        setName("UpdateProductFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(780, 910));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(null);
        jPanel2.setMinimumSize(new java.awt.Dimension(780, 890));
        jPanel2.setPreferredSize(new java.awt.Dimension(780, 890));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("SUPPLIER:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("ITEM NAME:");
        jLabel3.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("UNIT TYPE:");
        jLabel4.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("CATEGORY:");
        jLabel5.setFocusable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("NUMBER OF UNITS:");
        jLabel6.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("DISCOUNT:");
        jLabel7.setFocusable(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("REORDER LEVEL:");
        jLabel8.setFocusable(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("ADDED BY:");
        jLabel9.setFocusable(false);

        nameTf.setBackground(new java.awt.Color(255, 255, 204));
        nameTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        nameTf.setForeground(new java.awt.Color(0, 0, 0));
        nameTf.setBorder(new javax.swing.border.MatteBorder(null));
        nameTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTfActionPerformed(evt);
            }
        });

        unitTf.setBackground(new java.awt.Color(255, 255, 204));
        unitTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        unitTf.setForeground(new java.awt.Color(0, 0, 0));
        unitTf.setBorder(new javax.swing.border.MatteBorder(null));
        unitTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitTfActionPerformed(evt);
            }
        });

        discountTf.setBackground(new java.awt.Color(255, 255, 204));
        discountTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        discountTf.setForeground(new java.awt.Color(0, 0, 0));
        discountTf.setBorder(new javax.swing.border.MatteBorder(null));
        discountTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountTfActionPerformed(evt);
            }
        });

        reOderTf.setBackground(new java.awt.Color(255, 255, 204));
        reOderTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        reOderTf.setForeground(new java.awt.Color(0, 0, 0));
        reOderTf.setBorder(new javax.swing.border.MatteBorder(null));
        reOderTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reOderTfActionPerformed(evt);
            }
        });

        userNameLbl.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        userNameLbl.setForeground(new java.awt.Color(51, 51, 51));
        userNameLbl.setFocusable(false);

        cancelBtn.setBackground(new java.awt.Color(255, 255, 255));
        cancelBtn.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        cancelBtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelBtn.setText("CANCEL");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        finishBtn.setBackground(new java.awt.Color(255, 255, 255));
        finishBtn.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        finishBtn.setForeground(new java.awt.Color(51, 51, 51));
        finishBtn.setText("DONE");
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });

        addNextBtn.setBackground(new java.awt.Color(255, 255, 255));
        addNextBtn.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        addNextBtn.setForeground(new java.awt.Color(51, 51, 51));
        addNextBtn.setText("ADD NEXT");
        addNextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNextBtnActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("EXPIRABLE?");
        jLabel12.setFocusable(false);

        yearTf.setBackground(new java.awt.Color(255, 255, 204));
        yearTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        yearTf.setForeground(new java.awt.Color(0, 0, 0));
        yearTf.setBorder(new javax.swing.border.MatteBorder(null));
        yearTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearTfActionPerformed(evt);
            }
        });

        monthTf.setBackground(new java.awt.Color(255, 255, 204));
        monthTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        monthTf.setForeground(new java.awt.Color(0, 0, 0));
        monthTf.setBorder(new javax.swing.border.MatteBorder(null));
        monthTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthTfActionPerformed(evt);
            }
        });

        dayTf.setBackground(new java.awt.Color(255, 255, 204));
        dayTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        dayTf.setForeground(new java.awt.Color(0, 0, 0));
        dayTf.setBorder(new javax.swing.border.MatteBorder(null));
        dayTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayTfActionPerformed(evt);
            }
        });

        yearLbl.setText("YEAR:");

        monthLbl.setText("MONTH:");

        dayLbl.setText("DAY:");

        addCategoryBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_add_new_45px.png"))); // NOI18N
        addCategoryBtnLbl.setToolTipText("Add a new category");
        addCategoryBtnLbl.setAlignmentY(0.0F);
        addCategoryBtnLbl.setIconTextGap(0);

        pPricePUnitTf.setBackground(new java.awt.Color(255, 255, 204));
        pPricePUnitTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        pPricePUnitTf.setForeground(new java.awt.Color(0, 0, 0));
        pPricePUnitTf.setBorder(new javax.swing.border.MatteBorder(null));
        pPricePUnitTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pPricePUnitTfActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("PURCHASE PRICE PER UNIT:");
        jLabel18.setFocusable(false);

        sPricePUnitTf.setBackground(new java.awt.Color(255, 255, 204));
        sPricePUnitTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        sPricePUnitTf.setForeground(new java.awt.Color(0, 0, 0));
        sPricePUnitTf.setBorder(new javax.swing.border.MatteBorder(null));
        sPricePUnitTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sPricePUnitTfActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("SELLING PRICE PER UNIT:");
        jLabel19.setFocusable(false);

        addNewCodeTf.setBackground(new java.awt.Color(255, 255, 204));
        addNewCodeTf.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        addNewCodeTf.setForeground(new java.awt.Color(0, 0, 0));
        addNewCodeTf.setBorder(new javax.swing.border.MatteBorder(null));
        addNewCodeTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCodeTfActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("ITEM CODE:");
        jLabel17.setFocusable(false);

        addSupplierBtnLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_add_new_45px.png"))); // NOI18N
        addSupplierBtnLbl.setToolTipText("Add a new category");
        addSupplierBtnLbl.setAlignmentY(0.0F);
        addSupplierBtnLbl.setIconTextGap(0);

        supplierCB.setBackground(new java.awt.Color(255, 255, 255));
        supplierCB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        supplierCB.setMaximumRowCount(100);
        supplierCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ""}));
        supplierCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        supplierCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        supplierCB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                supplierCBKeyTyped(evt);
            }
        });

        codeCB.setBackground(new java.awt.Color(255, 255, 204));
        codeCB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        codeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        codeCB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        codeCB.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        codeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeCBActionPerformed(evt);
            }
        });

        unitCB.setBackground(new java.awt.Color(255, 255, 255));
        unitCB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        unitCB.setMaximumRowCount(100);
        unitCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        unitCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        unitCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        categoryCB.setBackground(new java.awt.Color(255, 255, 255));
        categoryCB.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        categoryCB.setMaximumRowCount(100);
        categoryCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        categoryCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        categoryCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        addProductCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        addProductCheckBox.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        addProductCheckBox.setForeground(new java.awt.Color(51, 51, 51));
        addProductCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px_1.png"))); // NOI18N
        addProductCheckBox.setText("New Product");
        addProductCheckBox.setToolTipText("Add a new product");
        addProductCheckBox.setAlignmentY(0.0F);
        addProductCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProductCheckBox.setIconTextGap(5);
        addProductCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addProductCheckBoxMousePressed(evt);
            }
        });

        expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px.png"))); // NOI18N
        expCheckBox.setToolTipText("Add a new category");
        expCheckBox.setAlignmentY(0.0F);
        expCheckBox.setIconTextGap(0);
        expCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                expCheckBoxMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("PRODUCT NAME:");
        jLabel10.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(codeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(addProductCheckBox)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(149, 149, 149)
                                .addComponent(unitCB, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(97, 97, 97)
                                .addComponent(unitTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(pPricePUnitTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sPricePUnitTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(149, 149, 149)
                                .addComponent(discountTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(reOderTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(supplierCB, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addSupplierBtnLbl))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addNewCodeTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameTf, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(149, 149, 149)
                                    .addComponent(categoryCB, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(addCategoryBtnLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6))
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(expCheckBox)
                                .addGap(6, 6, 6)
                                .addComponent(yearLbl)
                                .addGap(6, 6, 6)
                                .addComponent(yearTf, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(monthLbl)
                                .addGap(6, 6, 6)
                                .addComponent(monthTf, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(dayLbl)
                                .addGap(6, 6, 6)
                                .addComponent(dayTf, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(337, 337, 337)
                            .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(finishBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(userNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(253, 253, 253)
                                    .addComponent(addNextBtn))))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addProductCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addCategoryBtnLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(categoryCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(supplierCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(addSupplierBtnLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(addNewCodeTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3))
                    .addComponent(nameTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4))
                    .addComponent(unitCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel6))
                    .addComponent(unitTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel18))
                    .addComponent(pPricePUnitTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel19))
                    .addComponent(sPricePUnitTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7))
                    .addComponent(discountTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel8))
                    .addComponent(reOderTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(expCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(monthLbl)
                                .addComponent(yearTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(monthTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dayLbl)
                                .addComponent(yearLbl))
                            .addComponent(dayTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel9))
                    .addComponent(userNameLbl)
                    .addComponent(addNextBtn))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(finishBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        getContentPane().add(jPanel2);

        setSize(new java.awt.Dimension(787, 1025));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nameTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTfActionPerformed

    private void unitTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unitTfActionPerformed

    private void discountTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discountTfActionPerformed

    private void reOderTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reOderTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reOderTfActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void yearTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearTfActionPerformed

    private void monthTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthTfActionPerformed

    private void dayTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayTfActionPerformed

    private void pPricePUnitTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pPricePUnitTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pPricePUnitTfActionPerformed

    private void sPricePUnitTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sPricePUnitTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sPricePUnitTfActionPerformed

    private void addNewCodeTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewCodeTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addNewCodeTfActionPerformed

    private void codeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codeCBActionPerformed

    private void expCheckBoxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expCheckBoxMousePressed
        if (expCBox==true) {
            expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/blue-ok_25px_2.png"))); // NOI18N
            yearLbl.setVisible(true);
            yearTf.setVisible(true);
            monthLbl.setVisible(true);
            monthTf.setVisible(true);
            dayLbl.setVisible(true);
            dayTf.setVisible(true);
            expCBox=false;
        } else {
            if (addProductCBox==false) {
                expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px.png"))); // NOI18N
            }else{
                expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px_1.png"))); // NOI18N
            }
            yearLbl.setVisible(false);
            yearTf.setVisible(false);
            monthLbl.setVisible(false);
            monthTf.setVisible(false);
            dayLbl.setVisible(false);
            dayTf.setVisible(false);
            expCBox=true;
            
        }
    }//GEN-LAST:event_expCheckBoxMousePressed

    private void addProductCheckBoxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProductCheckBoxMousePressed
        if (addProductCBox==true) {
            disableComponent(true);
            addProductCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px_1.png"))); // NOI18N
            expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px.png"))); // NOI18N
            codeCB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            addProductCBox=false;
        }else {
            enableComponent(true);
            addProductCBox=true;
            addProductCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/blue-ok_25px_2.png"))); // NOI18N
            expCheckBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_circle_25px_1.png"))); // NOI18N
        }
    }//GEN-LAST:event_addProductCheckBoxMousePressed

    private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
        try {
            insertNewItem();
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        mw.connectProductTable();
        dispose();
        
    }//GEN-LAST:event_finishBtnActionPerformed

    private void supplierCBKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierCBKeyTyped
        // TODO add your handling code here:
        String txt = (String) supplierCB.getSelectedItem();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                addNewCodeTf.setText(txt);
            }
    }//GEN-LAST:event_supplierCBKeyTyped

    private void addNextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNextBtnActionPerformed
        try {
            insertNewItem();
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        mw.connectProductTable();
        
    }//GEN-LAST:event_addNextBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            /* Set the Nimbus look and feel */
            
            conn = Model.ConnectDB.connect();
                       
                       
            //</editor-fold>
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addCategoryBtnLbl;
    private javax.swing.JTextField addNewCodeTf;
    private javax.swing.JButton addNextBtn;
    private javax.swing.JLabel addProductCheckBox;
    private javax.swing.JLabel addSupplierBtnLbl;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox<String> categoryCB;
    private javax.swing.JComboBox<String> codeCB;
    private javax.swing.JLabel dayLbl;
    private javax.swing.JTextField dayTf;
    private javax.swing.JTextField discountTf;
    private javax.swing.JLabel expCheckBox;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel monthLbl;
    private javax.swing.JTextField monthTf;
    private javax.swing.JTextField nameTf;
    private javax.swing.JTextField pPricePUnitTf;
    private javax.swing.JTextField reOderTf;
    private javax.swing.JTextField sPricePUnitTf;
    private javax.swing.JComboBox<String> supplierCB;
    private javax.swing.JComboBox<String> unitCB;
    private javax.swing.JTextField unitTf;
    private javax.swing.JLabel userNameLbl;
    private javax.swing.JLabel yearLbl;
    private javax.swing.JTextField yearTf;
    // End of variables declaration//GEN-END:variables
}
