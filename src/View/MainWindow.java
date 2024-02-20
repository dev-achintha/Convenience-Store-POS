package View;

import Controller.Calculator;
import Controller.CounterTable;
import Controller.DataRenderer;
import Controller.NumberPad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.table.DefaultTableModel;
import View.Repository.TableActionCellEditor;
import View.Repository.TableActionCellRender;
import View.Repository.TableActionEvent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author skasa
 */
public final class MainWindow extends javax.swing.JFrame {
    private int  windowSizeStatusCheck = 0;
    final int MIN_WIDTH, MIN_HEIGHT;
    final int X_COORDINATE;
    final int Y_COORDINATE;
  
    static Connection conn;
    Statement st;
    ResultSet rs;
    int xx;
    int xy;
    
    Toolkit tk=Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    private DataRenderer dataRenderer;
    
    public MainWindow() {
        setUndecorated(true);
        initComponents();
        
        this.MIN_WIDTH = getWidth();
        this.MIN_HEIGHT = getHeight();
        this.X_COORDINATE = getX();
        this.Y_COORDINATE = getY();
        
        dataRenderer  = new DataRenderer(conn);
        quantityPanel.add(jPanel1);
        CounterTable ct = new CounterTable();
        ct.decorateTable();
        connectProductTable();
        
        try {
            connectCollectTable();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
              
        
        //Hexa Decimal values for buttons in number pad
        jButton_4.setName("0x64"); jButton_Dot.setName("0x6E");  jButton_5.setName("0x65");  
        jButton_7.setName("0x67");jButton_8.setName("0x68"); jButton_1.setName("0x61");   
        jButton_2.setName("0x62");   jButton_3.setName("0x63"); jButton_6.setName("0x66"); 
        jButton_9.setName("0x69"); jButton_0.setName("0x60");jButton_Enter.setName("0x0A"); jButton_BackSpace.setName("0x08");
        
        //setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jPanel_SideMenuView.setVisible(false);
        jPanel_CounterTab.setBackground(new java.awt.Color(181, 44, 90));
        saleItemTbl.setBackground(new Color(39, 40, 60,100));
        jPanel_StockPane.setVisible(false);
        jPanel_UserPane.setVisible(false);
        jPanel_ConfigurationPane.setVisible(false);
        jPanel_Transparent.setBackground(new Color(39, 40, 60,65));
        
        try {
            st = conn.prepareStatement("SELECT produce_code FROM tblproduct");
            rs = st.executeQuery("SELECT produce_code FROM tblproduct");
            while (rs.next()) {
                String item;
                item = rs.getString("produce_code");
                SearchBoxCombo.addItem(item);
            }
    
        }catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    public void winSizeSet() {
        if(windowSizeStatusCheck==1) {
            setShape(new RoundRectangle2D.Double(0, 0, screenSize.width, screenSize.height, 0, 0));
            setLocation(0,0);
            setSize(screenSize.width,screenSize.height);
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
            //jScrollPane5.setSize(materialTabbed1.getBoundsAt(2));
            System.out.println("materialTabbed1.getBoundsAt(1) "+materialTabbed1.getBoundsAt(1));
            windowSizeStatusCheck =0;
        }
        else if(windowSizeStatusCheck==0) {
            setLocation(X_COORDINATE,Y_COORDINATE);
            setSize(MIN_WIDTH,MIN_HEIGHT);
            //pack();
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
            System.out.println("materialTabbed1.getTabLayoutPolicy() "+materialTabbed1.getTabLayoutPolicy());
            System.out.println("materialTabbed1.getSize() "+materialTabbed1.getSize());
            windowSizeStatusCheck =1;
        }
    }
    
    public void connectProductTable() {
        ProductTable.setRowHeight(50);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                
            }

            @Override
            public void onDelete(int row) {
                if (ProductTable.isEditing()) {
                    ProductTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                
            }
        };
        ProductTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        ProductTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        try {
                        
            String sql = "SELECT product_id, produce_code, product_name, category_id, unit_in_stock, unit_price FROM tblproduct";
            
            st = conn.prepareStatement(sql);
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("product_id"));
                String code = String.valueOf(rs.getLong("produce_code"));
                String name = rs.getString("product_name");
                String category = String.valueOf(rs.getInt("category_id"));
                String instock = String.valueOf(rs.getFloat("unit_in_stock"));
                String price = String.valueOf((long) rs.getFloat("unit_price"));
                //String updateBtn = String.valueOf(rs.getString("update_button"));
                
                String data [] = {id,code,name,category,instock,price};
                DefaultTableModel productTbl =(DefaultTableModel) ProductTable.getModel();
                productTbl.addRow(data);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    
    
    public void connectCollectTable() throws SQLException {
        st = conn.prepareStatement("SELECT * FROM tblreceiveproduct");
        rs = st.executeQuery("SELECT * FROM tblreceiveproduct");
        while(rs.next()) {
            String rid = rs.getString(1);
            String id = rs.getString(2);
            String name = rs.getString(3);
            String  uid = rs.getString(4);
            String q = rs.getString(5);
            String up = rs.getString(6);
            String subt = rs.getString(7);
            String d = rs.getString(8);
            String sid = rs.getString(9);
            String rd = rs.getString(10);
            
            String data [] = {rid,id,name,uid,q,up,subt,d,sid,rd};
            DefaultTableModel Tbl =(DefaultTableModel) SupplyTable.getModel();
            Tbl.addRow(data);
            }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        quantityTf = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        quantityPanel = new javax.swing.JPopupMenu();
        jPanel_SideMenuView = new javax.swing.JPanel();
        jPanel_SideMenu = new javax.swing.JPanel();
        jPanel_Left_Top = new javax.swing.JPanel();
        jPanel_CounterTab = new javax.swing.JPanel();
        jLabel_Counter = new javax.swing.JLabel();
        jPanel_StockTab = new javax.swing.JPanel();
        jLabel_Stock = new javax.swing.JLabel();
        jPanel_StockTab1 = new javax.swing.JPanel();
        jLabel_Stock1 = new javax.swing.JLabel();
        jPanel_Left_Bottom = new javax.swing.JPanel();
        jPanel_UserTab = new javax.swing.JPanel();
        jLabel_User = new javax.swing.JLabel();
        jPanel_SettingTab = new javax.swing.JPanel();
        jLabel_Setting = new javax.swing.JLabel();
        jPanel_Transparent = new javax.swing.JPanel();
        jPanel_CounterPane = new javax.swing.JPanel();
        jPanel_TitleBarCounter = new javax.swing.JPanel();
        jLabel_MenuButtonCounter = new javax.swing.JLabel();
        jLabel_ExitCounter = new javax.swing.JLabel();
        jLabel_TitleCounter = new javax.swing.JLabel();
        jLabel_MaximizeCounter = new javax.swing.JLabel();
        jPanel_BodyCounter = new javax.swing.JPanel();
        jPanel_BodyCounterLeft = new javax.swing.JPanel();
        jPanel_ItemListCounter = new View.Repository.jPanel();
        SearchBox = new View.MyTextField();
        jButton_CashPay1 = new javax.swing.JButton();
        SearchBoxCombo = new javax.swing.JComboBox<>();
        jPanel_Spacer = new javax.swing.JPanel();
        jPanel_SearchContainerCounter = new View.Repository.jPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        saleItemTbl = new View.Repository.TableCustom();
        jPanel_Spacer1 = new javax.swing.JPanel();
        jPanel_BodyCounterRight = new javax.swing.JPanel();
        jPanel_TotalCounterRightTop = new javax.swing.JPanel();
        jPanel_TotalCounter = new View.Repository.jPanel();
        jPanel_ItemCountPanelCounter = new javax.swing.JPanel();
        jLabel_ItemCountLabelCounter = new javax.swing.JLabel();
        jLabel_ItemCountCounter = new javax.swing.JLabel();
        jPanel_SubtotalPanelCounter = new javax.swing.JPanel();
        jLabel_SubtotalLabelCounter = new javax.swing.JLabel();
        jLabel_SubtotalPriceCounter = new javax.swing.JLabel();
        jPanel_DiscountPanelCounter = new javax.swing.JPanel();
        jLabel_DiscountLabelCounter = new javax.swing.JLabel();
        jLabel_DiscountPriceCounter = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel_GrandTotalPanelCounter = new javax.swing.JPanel();
        jLabel_GrandTotalLabelCounter = new javax.swing.JLabel();
        jLabel_GrandTotalCounter = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel_PayButtonsPanelCounter = new javax.swing.JPanel();
        jButton_CashPay = new javax.swing.JButton();
        jButton_CardPay = new javax.swing.JButton();
        jButton_QRPay = new javax.swing.JButton();
        jPanel_TotalCounterRightMiddle = new javax.swing.JPanel();
        jPanel_TotalCounterRightBottom = new javax.swing.JPanel();
        jButton_7 = new javax.swing.JButton();
        jButton_8 = new javax.swing.JButton();
        jButton_9 = new javax.swing.JButton();
        jButton_Enter = new javax.swing.JButton();
        jButton_4 = new javax.swing.JButton();
        jButton_5 = new javax.swing.JButton();
        jButton_6 = new javax.swing.JButton();
        jButton_Calculator = new javax.swing.JButton();
        jButton_1 = new javax.swing.JButton();
        jButton_2 = new javax.swing.JButton();
        jButton_3 = new javax.swing.JButton();
        jButton_OSK = new javax.swing.JButton();
        jButton_Dot = new javax.swing.JButton();
        jButton_0 = new javax.swing.JButton();
        jButton_BackSpace = new javax.swing.JButton();
        jButton_Delete = new javax.swing.JButton();
        jPanel_StockPane = new javax.swing.JPanel();
        jPanel_TitleBarStock = new javax.swing.JPanel();
        jLabel_MenuButtonStock = new javax.swing.JLabel();
        jLabel_ExitStock = new javax.swing.JLabel();
        jLabel_TitleStock = new javax.swing.JLabel();
        jLabel_MaximizeStock = new javax.swing.JLabel();
        jPanel_BodyStock = new javax.swing.JPanel();
        materialTabbed1 = new View.Repository.TabbedPaneCustom();
        Product__StockPane = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        Supply__StockPane = new javax.swing.JLayeredPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        SupplyTable = new View.Repository.TableCustom();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel_UserPane = new javax.swing.JPanel();
        jPanel_TitleBarUser = new javax.swing.JPanel();
        jLabel_MenuButtonUser = new javax.swing.JLabel();
        jLabel_ExitUser = new javax.swing.JLabel();
        jLabel_TitleUser = new javax.swing.JLabel();
        jLabel_MaximizeUser = new javax.swing.JLabel();
        jPanel_BodyUser = new javax.swing.JPanel();
        jPanel_ConfigurationPane = new javax.swing.JPanel();
        jPanel_TitleBarConfiguration = new javax.swing.JPanel();
        jLabel_MenuButtonConfiguration = new javax.swing.JLabel();
        jLabel_ExitConfiguration = new javax.swing.JLabel();
        jLabel_TitleConfiguration = new javax.swing.JLabel();
        jLabel_MaximizeCnfiguration = new javax.swing.JLabel();
        jPanel_BodyConfiguration = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 85));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Quantity");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 20, 110, 40);

        quantityTf.setBackground(new java.awt.Color(255, 153, 153));
        quantityTf.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        quantityTf.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(quantityTf);
        quantityTf.setBounds(120, 20, 240, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_17609020071557740369_75px.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });
        jPanel1.add(jLabel3);
        jLabel3.setBounds(370, 0, 75, 80);

        quantityPanel.setFocusable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 900));
        setName("MainWindowFrame"); // NOI18N
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        jPanel_SideMenuView.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel_SideMenuView.setMinimumSize(new java.awt.Dimension(1440, 900));
        jPanel_SideMenuView.setOpaque(false);
        jPanel_SideMenuView.setPreferredSize(new java.awt.Dimension(1440, 900));
        jPanel_SideMenuView.setLayout(new javax.swing.BoxLayout(jPanel_SideMenuView, javax.swing.BoxLayout.LINE_AXIS));

        jPanel_SideMenu.setBackground(new java.awt.Color(59, 77, 117));
        jPanel_SideMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel_SideMenu.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel_SideMenu.setPreferredSize(new java.awt.Dimension(300, 774));
        jPanel_SideMenu.setLayout(new javax.swing.BoxLayout(jPanel_SideMenu, javax.swing.BoxLayout.Y_AXIS));

        jPanel_Left_Top.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_Left_Top.setMaximumSize(new java.awt.Dimension(300, 345466));
        jPanel_Left_Top.setPreferredSize(new java.awt.Dimension(300, 6565));
        jPanel_Left_Top.setLayout(new javax.swing.BoxLayout(jPanel_Left_Top, javax.swing.BoxLayout.Y_AXIS));

        jPanel_CounterTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_CounterTab.setToolTipText("Counter");
        jPanel_CounterTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_CounterTab.setMaximumSize(new java.awt.Dimension(300, 70));
        jPanel_CounterTab.setMinimumSize(new java.awt.Dimension(70, 70));
        jPanel_CounterTab.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel_CounterTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_CounterTabMousePressed(evt);
            }
        });
        jPanel_CounterTab.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));

        jLabel_Counter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_Counter.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Counter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Counter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_shopping_cart_64px.png"))); // NOI18N
        jLabel_Counter.setLabelFor(jPanel_CounterTab);
        jLabel_Counter.setText("Counter");
        jLabel_Counter.setToolTipText("Counter");
        jLabel_Counter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Counter.setIconTextGap(20);
        jLabel_Counter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_CounterMousePressed(evt);
            }
        });
        jPanel_CounterTab.add(jLabel_Counter);

        jPanel_Left_Top.add(jPanel_CounterTab);

        jPanel_StockTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_StockTab.setToolTipText("Manage the Items");
        jPanel_StockTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_StockTab.setMaximumSize(new java.awt.Dimension(300, 70));
        jPanel_StockTab.setMinimumSize(new java.awt.Dimension(70, 70));
        jPanel_StockTab.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel_StockTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_StockTabMousePressed(evt);
            }
        });
        jPanel_StockTab.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));

        jLabel_Stock.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_Stock.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Stock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_stacking_64px_1.png"))); // NOI18N
        jLabel_Stock.setLabelFor(jPanel_StockTab);
        jLabel_Stock.setText("Manage Stock");
        jLabel_Stock.setToolTipText("Manage the Items");
        jLabel_Stock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Stock.setIconTextGap(20);
        jLabel_Stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_StockMousePressed(evt);
            }
        });
        jPanel_StockTab.add(jLabel_Stock);

        jPanel_Left_Top.add(jPanel_StockTab);

        jPanel_StockTab1.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_StockTab1.setToolTipText("Manage the Items");
        jPanel_StockTab1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_StockTab1.setMaximumSize(new java.awt.Dimension(300, 70));
        jPanel_StockTab1.setMinimumSize(new java.awt.Dimension(70, 70));
        jPanel_StockTab1.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel_StockTab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_StockTab1MousePressed(evt);
            }
        });
        jPanel_StockTab1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));

        jLabel_Stock1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_Stock1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Stock1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Stock1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_stacking_64px_1.png"))); // NOI18N
        jLabel_Stock1.setLabelFor(jPanel_StockTab);
        jLabel_Stock1.setText("Extra");
        jLabel_Stock1.setToolTipText("Manage the Items");
        jLabel_Stock1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Stock1.setIconTextGap(20);
        jLabel_Stock1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_Stock1MousePressed(evt);
            }
        });
        jPanel_StockTab1.add(jLabel_Stock1);

        jPanel_Left_Top.add(jPanel_StockTab1);

        jPanel_SideMenu.add(jPanel_Left_Top);

        jPanel_Left_Bottom.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_Left_Bottom.setAlignmentY(0.0F);
        jPanel_Left_Bottom.setMaximumSize(new java.awt.Dimension(300, 140));
        jPanel_Left_Bottom.setPreferredSize(new java.awt.Dimension(300, 140));
        jPanel_Left_Bottom.setLayout(new javax.swing.BoxLayout(jPanel_Left_Bottom, javax.swing.BoxLayout.Y_AXIS));

        jPanel_UserTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_UserTab.setToolTipText("User Profile");
        jPanel_UserTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_UserTab.setMaximumSize(new java.awt.Dimension(300, 70));
        jPanel_UserTab.setMinimumSize(new java.awt.Dimension(70, 70));
        jPanel_UserTab.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel_UserTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_UserTabMousePressed(evt);
            }
        });
        jPanel_UserTab.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));

        jLabel_User.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_User.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_User.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_User.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_male_user_64px.png"))); // NOI18N
        jLabel_User.setLabelFor(jPanel_CounterTab);
        jLabel_User.setText("User");
        jLabel_User.setToolTipText("User Profile");
        jLabel_User.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_User.setIconTextGap(20);
        jLabel_User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_UserMousePressed(evt);
            }
        });
        jPanel_UserTab.add(jLabel_User);

        jPanel_Left_Bottom.add(jPanel_UserTab);

        jPanel_SettingTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_SettingTab.setToolTipText("Configuration Settings");
        jPanel_SettingTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_SettingTab.setMaximumSize(new java.awt.Dimension(300, 70));
        jPanel_SettingTab.setMinimumSize(new java.awt.Dimension(70, 70));
        jPanel_SettingTab.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel_SettingTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_SettingTabMousePressed(evt);
            }
        });
        jPanel_SettingTab.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10));

        jLabel_Setting.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_Setting.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Setting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Setting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_settings_64px.png"))); // NOI18N
        jLabel_Setting.setLabelFor(jPanel_CounterTab);
        jLabel_Setting.setText("Configuration");
        jLabel_Setting.setToolTipText("Configuration Settings");
        jLabel_Setting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Setting.setIconTextGap(20);
        jLabel_Setting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_SettingMousePressed(evt);
            }
        });
        jPanel_SettingTab.add(jLabel_Setting);

        jPanel_Left_Bottom.add(jPanel_SettingTab);

        jPanel_SideMenu.add(jPanel_Left_Bottom);

        jPanel_SideMenuView.add(jPanel_SideMenu);

        jPanel_Transparent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_TransparentMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TransparentLayout = new javax.swing.GroupLayout(jPanel_Transparent);
        jPanel_Transparent.setLayout(jPanel_TransparentLayout);
        jPanel_TransparentLayout.setHorizontalGroup(
            jPanel_TransparentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1140, Short.MAX_VALUE)
        );
        jPanel_TransparentLayout.setVerticalGroup(
            jPanel_TransparentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );

        jPanel_SideMenuView.add(jPanel_Transparent);

        getContentPane().add(jPanel_SideMenuView);

        jPanel_CounterPane.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_CounterPane.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel_CounterPane.setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel_TitleBarCounter.setBackground(new java.awt.Color(102, 102, 102));
        jPanel_TitleBarCounter.setForeground(new java.awt.Color(60, 63, 65));
        jPanel_TitleBarCounter.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel_TitleBarCounter.setMinimumSize(new java.awt.Dimension(0, 70));
        jPanel_TitleBarCounter.setPreferredSize(new java.awt.Dimension(1458, 70));
        jPanel_TitleBarCounter.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarCounterMouseDragged(evt);
            }
        });
        jPanel_TitleBarCounter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarCounterMousePressed(evt);
            }
        });

        jLabel_MenuButtonCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_rectangle-list_50px.png"))); // NOI18N
        jLabel_MenuButtonCounter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MenuButtonCounter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MenuButtonCounterMousePressed(evt);
            }
        });

        jLabel_ExitCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_close_32px.png"))); // NOI18N
        jLabel_ExitCounter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ExitCounter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_ExitCounterMousePressed(evt);
            }
        });

        jLabel_TitleCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_TitleCounter.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TitleCounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TitleCounter.setText("COUNTER");

        jLabel_MaximizeCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_window-maximize_50px.png"))); // NOI18N
        jLabel_MaximizeCounter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MaximizeCounter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MaximizeCounterMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TitleBarCounterLayout = new javax.swing.GroupLayout(jPanel_TitleBarCounter);
        jPanel_TitleBarCounter.setLayout(jPanel_TitleBarCounterLayout);
        jPanel_TitleBarCounterLayout.setHorizontalGroup(
            jPanel_TitleBarCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarCounterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_MenuButtonCounter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_MaximizeCounter)
                .addGap(18, 18, 18)
                .addComponent(jLabel_ExitCounter)
                .addContainerGap())
            .addGroup(jPanel_TitleBarCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarCounterLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleCounter)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_TitleBarCounterLayout.setVerticalGroup(
            jPanel_TitleBarCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarCounterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel_TitleBarCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_MaximizeCounter)
                    .addComponent(jLabel_MenuButtonCounter)
                    .addComponent(jLabel_ExitCounter))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_TitleBarCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarCounterLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleCounter)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel_BodyCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_BodyCounter.setLayout(new javax.swing.BoxLayout(jPanel_BodyCounter, javax.swing.BoxLayout.LINE_AXIS));

        jPanel_BodyCounterLeft.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_BodyCounterLeft.setLayout(new javax.swing.BoxLayout(jPanel_BodyCounterLeft, javax.swing.BoxLayout.Y_AXIS));

        jPanel_ItemListCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_ItemListCounter.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel_ItemListCounter.setMinimumSize(new java.awt.Dimension(0, 70));
        jPanel_ItemListCounter.setPreferredSize(new java.awt.Dimension(958, 70));
        jPanel_ItemListCounter.setLayout(null);

        SearchBox.setBackground(new java.awt.Color(255, 254, 249));
        SearchBox.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        SearchBox.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_search_in_list_32px.png"))); // NOI18N
        SearchBox.setSelectionColor(new java.awt.Color(255, 153, 102));
        SearchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBoxActionPerformed(evt);
            }
        });
        SearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SearchBoxKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchBoxKeyReleased(evt);
            }
        });
        jPanel_ItemListCounter.add(SearchBox);
        SearchBox.setBounds(14, 6, 400, 60);

        jButton_CashPay1.setBackground(new java.awt.Color(255, 255, 255));
        jButton_CashPay1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_CashPay1.setForeground(new java.awt.Color(102, 102, 102));
        jButton_CashPay1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_add_new_45px.png"))); // NOI18N
        jButton_CashPay1.setText("ADD");
        jButton_CashPay1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_CashPay1.setFocusable(false);
        jButton_CashPay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CashPay1ActionPerformed(evt);
            }
        });
        jPanel_ItemListCounter.add(jButton_CashPay1);
        jButton_CashPay1.setBounds(790, 10, 150, 50);

        SearchBoxCombo.setBackground(new java.awt.Color(255, 255, 255));
        SearchBoxCombo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        SearchBoxCombo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPanel_ItemListCounter.add(SearchBoxCombo);
        SearchBoxCombo.setBounds(420, 10, 360, 50);

        jPanel_BodyCounterLeft.add(jPanel_ItemListCounter);

        jPanel_Spacer.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Spacer.setMaximumSize(new java.awt.Dimension(32767, 10));
        jPanel_Spacer.setPreferredSize(new java.awt.Dimension(958, 10));

        javax.swing.GroupLayout jPanel_SpacerLayout = new javax.swing.GroupLayout(jPanel_Spacer);
        jPanel_Spacer.setLayout(jPanel_SpacerLayout);
        jPanel_SpacerLayout.setHorizontalGroup(
            jPanel_SpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        jPanel_SpacerLayout.setVerticalGroup(
            jPanel_SpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel_BodyCounterLeft.add(jPanel_Spacer);

        jPanel_SearchContainerCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_SearchContainerCounter.setLayout(new javax.swing.BoxLayout(jPanel_SearchContainerCounter, javax.swing.BoxLayout.LINE_AXIS));

        saleItemTbl.setBackground(new java.awt.Color(255, 255, 255));
        saleItemTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ITEM", "PRICE", "QUANTITY", "TOTAL", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(saleItemTbl);

        jPanel_SearchContainerCounter.add(jScrollPane1);

        jPanel_BodyCounterLeft.add(jPanel_SearchContainerCounter);

        jPanel_BodyCounter.add(jPanel_BodyCounterLeft);

        jPanel_Spacer1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Spacer1.setMaximumSize(new java.awt.Dimension(10, 32767));
        jPanel_Spacer1.setPreferredSize(new java.awt.Dimension(10, 32767));

        javax.swing.GroupLayout jPanel_Spacer1Layout = new javax.swing.GroupLayout(jPanel_Spacer1);
        jPanel_Spacer1.setLayout(jPanel_Spacer1Layout);
        jPanel_Spacer1Layout.setHorizontalGroup(
            jPanel_Spacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanel_Spacer1Layout.setVerticalGroup(
            jPanel_Spacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );

        jPanel_BodyCounter.add(jPanel_Spacer1);

        jPanel_BodyCounterRight.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_BodyCounterRight.setMaximumSize(new java.awt.Dimension(470, 32767));
        jPanel_BodyCounterRight.setPreferredSize(new java.awt.Dimension(470, 827));
        jPanel_BodyCounterRight.setLayout(new java.awt.GridLayout(2, 1));

        jPanel_TotalCounterRightTop.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_TotalCounterRightTop.setLayout(new javax.swing.BoxLayout(jPanel_TotalCounterRightTop, javax.swing.BoxLayout.Y_AXIS));

        jPanel_TotalCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_TotalCounter.setLayout(new javax.swing.BoxLayout(jPanel_TotalCounter, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel_ItemCountPanelCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_ItemCountPanelCounter.setEnabled(false);
        jPanel_ItemCountPanelCounter.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel_ItemCountPanelCounter.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel_ItemCountPanelCounter.setOpaque(false);
        jPanel_ItemCountPanelCounter.setPreferredSize(new java.awt.Dimension(400, 80));

        jLabel_ItemCountLabelCounter.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_ItemCountLabelCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_ItemCountLabelCounter.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_ItemCountLabelCounter.setText("Item Count");
        jLabel_ItemCountLabelCounter.setMaximumSize(new java.awt.Dimension(96, 30));
        jLabel_ItemCountLabelCounter.setMinimumSize(new java.awt.Dimension(96, 30));
        jLabel_ItemCountLabelCounter.setPreferredSize(new java.awt.Dimension(96, 30));

        jLabel_ItemCountCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel_ItemCountPanelCounterLayout = new javax.swing.GroupLayout(jPanel_ItemCountPanelCounter);
        jPanel_ItemCountPanelCounter.setLayout(jPanel_ItemCountPanelCounterLayout);
        jPanel_ItemCountPanelCounterLayout.setHorizontalGroup(
            jPanel_ItemCountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ItemCountPanelCounterLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel_ItemCountLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_ItemCountCounter)
                .addGap(48, 48, 48))
        );
        jPanel_ItemCountPanelCounterLayout.setVerticalGroup(
            jPanel_ItemCountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ItemCountPanelCounterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_ItemCountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ItemCountLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ItemCountCounter))
                .addGap(22, 22, 22))
        );

        jPanel_TotalCounter.add(jPanel_ItemCountPanelCounter);

        jPanel_SubtotalPanelCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_SubtotalPanelCounter.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel_SubtotalPanelCounter.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel_SubtotalPanelCounter.setOpaque(false);
        jPanel_SubtotalPanelCounter.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel_SubtotalLabelCounter.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_SubtotalLabelCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_SubtotalLabelCounter.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_SubtotalLabelCounter.setText("Subtotal");
        jLabel_SubtotalLabelCounter.setMaximumSize(new java.awt.Dimension(96, 30));
        jLabel_SubtotalLabelCounter.setMinimumSize(new java.awt.Dimension(96, 30));
        jLabel_SubtotalLabelCounter.setPreferredSize(new java.awt.Dimension(96, 30));

        jLabel_SubtotalPriceCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_SubtotalPriceCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_us_dollar_24px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel_SubtotalPanelCounterLayout = new javax.swing.GroupLayout(jPanel_SubtotalPanelCounter);
        jPanel_SubtotalPanelCounter.setLayout(jPanel_SubtotalPanelCounterLayout);
        jPanel_SubtotalPanelCounterLayout.setHorizontalGroup(
            jPanel_SubtotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SubtotalPanelCounterLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel_SubtotalLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                .addComponent(jLabel_SubtotalPriceCounter)
                .addGap(48, 48, 48))
        );
        jPanel_SubtotalPanelCounterLayout.setVerticalGroup(
            jPanel_SubtotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SubtotalPanelCounterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_SubtotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_SubtotalLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_SubtotalPriceCounter))
                .addGap(22, 22, 22))
        );

        jPanel_TotalCounter.add(jPanel_SubtotalPanelCounter);

        jPanel_DiscountPanelCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_DiscountPanelCounter.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel_DiscountPanelCounter.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel_DiscountPanelCounter.setOpaque(false);
        jPanel_DiscountPanelCounter.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel_DiscountLabelCounter.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_DiscountLabelCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_DiscountLabelCounter.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_DiscountLabelCounter.setText("Discount");
        jLabel_DiscountLabelCounter.setMaximumSize(new java.awt.Dimension(96, 30));
        jLabel_DiscountLabelCounter.setMinimumSize(new java.awt.Dimension(96, 30));
        jLabel_DiscountLabelCounter.setPreferredSize(new java.awt.Dimension(96, 30));

        jLabel_DiscountPriceCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_DiscountPriceCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_us_dollar_24px.png"))); // NOI18N

        jSeparator1.setBackground(new java.awt.Color(196, 196, 196));
        jSeparator1.setForeground(new java.awt.Color(196, 196, 196));

        javax.swing.GroupLayout jPanel_DiscountPanelCounterLayout = new javax.swing.GroupLayout(jPanel_DiscountPanelCounter);
        jPanel_DiscountPanelCounter.setLayout(jPanel_DiscountPanelCounterLayout);
        jPanel_DiscountPanelCounterLayout.setHorizontalGroup(
            jPanel_DiscountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DiscountPanelCounterLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel_DiscountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel_DiscountPanelCounterLayout.createSequentialGroup()
                        .addComponent(jLabel_DiscountLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                        .addComponent(jLabel_DiscountPriceCounter)))
                .addGap(48, 48, 48))
        );
        jPanel_DiscountPanelCounterLayout.setVerticalGroup(
            jPanel_DiscountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DiscountPanelCounterLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_DiscountPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_DiscountLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_DiscountPriceCounter))
                .addGap(22, 22, 22))
        );

        jPanel_TotalCounter.add(jPanel_DiscountPanelCounter);

        jPanel_GrandTotalPanelCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_GrandTotalPanelCounter.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel_GrandTotalPanelCounter.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel_GrandTotalPanelCounter.setOpaque(false);
        jPanel_GrandTotalPanelCounter.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel_GrandTotalLabelCounter.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_GrandTotalLabelCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_GrandTotalLabelCounter.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_GrandTotalLabelCounter.setText("Grand Total");
        jLabel_GrandTotalLabelCounter.setMaximumSize(new java.awt.Dimension(96, 30));
        jLabel_GrandTotalLabelCounter.setMinimumSize(new java.awt.Dimension(96, 30));
        jLabel_GrandTotalLabelCounter.setPreferredSize(new java.awt.Dimension(96, 30));

        jLabel_GrandTotalCounter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_GrandTotalCounter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_us_dollar_24px.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(196, 196, 196));
        jSeparator3.setForeground(new java.awt.Color(196, 196, 196));

        javax.swing.GroupLayout jPanel_GrandTotalPanelCounterLayout = new javax.swing.GroupLayout(jPanel_GrandTotalPanelCounter);
        jPanel_GrandTotalPanelCounter.setLayout(jPanel_GrandTotalPanelCounterLayout);
        jPanel_GrandTotalPanelCounterLayout.setHorizontalGroup(
            jPanel_GrandTotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_GrandTotalPanelCounterLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel_GrandTotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel_GrandTotalPanelCounterLayout.createSequentialGroup()
                        .addComponent(jLabel_GrandTotalLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_GrandTotalCounter)))
                .addGap(48, 48, 48))
        );
        jPanel_GrandTotalPanelCounterLayout.setVerticalGroup(
            jPanel_GrandTotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_GrandTotalPanelCounterLayout.createSequentialGroup()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_GrandTotalPanelCounterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_GrandTotalLabelCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_GrandTotalCounter))
                .addGap(22, 22, 22))
        );

        jPanel_TotalCounter.add(jPanel_GrandTotalPanelCounter);

        jPanel_PayButtonsPanelCounter.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_PayButtonsPanelCounter.setMaximumSize(new java.awt.Dimension(32767, 80));
        jPanel_PayButtonsPanelCounter.setMinimumSize(new java.awt.Dimension(0, 80));
        jPanel_PayButtonsPanelCounter.setOpaque(false);
        jPanel_PayButtonsPanelCounter.setPreferredSize(new java.awt.Dimension(400, 80));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 20);
        flowLayout2.setAlignOnBaseline(true);
        jPanel_PayButtonsPanelCounter.setLayout(flowLayout2);

        jButton_CashPay.setBackground(new java.awt.Color(255, 255, 255));
        jButton_CashPay.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_CashPay.setForeground(new java.awt.Color(102, 102, 102));
        jButton_CashPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/money.png"))); // NOI18N
        jButton_CashPay.setText("Cash");
        jButton_CashPay.setFocusable(false);
        jButton_CashPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CashPayActionPerformed(evt);
            }
        });
        jPanel_PayButtonsPanelCounter.add(jButton_CashPay);

        jButton_CardPay.setBackground(new java.awt.Color(255, 255, 255));
        jButton_CardPay.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_CardPay.setForeground(new java.awt.Color(102, 102, 102));
        jButton_CardPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/credit-card.png"))); // NOI18N
        jButton_CardPay.setText("Card");
        jButton_CardPay.setFocusable(false);
        jPanel_PayButtonsPanelCounter.add(jButton_CardPay);

        jButton_QRPay.setBackground(new java.awt.Color(255, 255, 255));
        jButton_QRPay.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_QRPay.setForeground(new java.awt.Color(102, 102, 102));
        jButton_QRPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/qr-code.png"))); // NOI18N
        jButton_QRPay.setText("QR");
        jButton_QRPay.setFocusable(false);
        jButton_QRPay.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel_PayButtonsPanelCounter.add(jButton_QRPay);

        jPanel_TotalCounter.add(jPanel_PayButtonsPanelCounter);

        jPanel_TotalCounterRightTop.add(jPanel_TotalCounter);

        jPanel_TotalCounterRightMiddle.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel_TotalCounterRightMiddleLayout = new javax.swing.GroupLayout(jPanel_TotalCounterRightMiddle);
        jPanel_TotalCounterRightMiddle.setLayout(jPanel_TotalCounterRightMiddleLayout);
        jPanel_TotalCounterRightMiddleLayout.setHorizontalGroup(
            jPanel_TotalCounterRightMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
        jPanel_TotalCounterRightMiddleLayout.setVerticalGroup(
            jPanel_TotalCounterRightMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
        );

        jPanel_TotalCounterRightTop.add(jPanel_TotalCounterRightMiddle);

        jPanel_BodyCounterRight.add(jPanel_TotalCounterRightTop);

        jPanel_TotalCounterRightBottom.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_TotalCounterRightBottom.setLayout(new java.awt.GridLayout(4, 3));

        jButton_7.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_7.setForeground(new java.awt.Color(102, 102, 102));
        jButton_7.setText("7");
        jButton_7.setFocusable(false);
        jButton_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_7ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_7);

        jButton_8.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_8.setForeground(new java.awt.Color(102, 102, 102));
        jButton_8.setText("8");
        jButton_8.setFocusable(false);
        jButton_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_8ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_8);

        jButton_9.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_9.setForeground(new java.awt.Color(102, 102, 102));
        jButton_9.setText("9");
        jButton_9.setFocusable(false);
        jButton_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_9ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_9);

        jButton_Enter.setBackground(new java.awt.Color(51, 102, 255));
        jButton_Enter.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Enter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_enter_key_60px_1.png"))); // NOI18N
        jButton_Enter.setFocusable(false);
        jButton_Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EnterActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_Enter);

        jButton_4.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_4.setForeground(new java.awt.Color(102, 102, 102));
        jButton_4.setText("4");
        jButton_4.setFocusable(false);
        jButton_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_4ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_4);

        jButton_5.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_5.setForeground(new java.awt.Color(102, 102, 102));
        jButton_5.setText("5");
        jButton_5.setFocusable(false);
        jButton_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_5ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_5);

        jButton_6.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_6.setForeground(new java.awt.Color(102, 102, 102));
        jButton_6.setText("6");
        jButton_6.setFocusable(false);
        jButton_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_6ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_6);

        jButton_Calculator.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Calculator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_Calculator_64px.png"))); // NOI18N
        jButton_Calculator.setFocusable(false);
        jButton_Calculator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CalculatorActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_Calculator);

        jButton_1.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_1.setForeground(new java.awt.Color(102, 102, 102));
        jButton_1.setText("1");
        jButton_1.setFocusable(false);
        jButton_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_1ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_1);

        jButton_2.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_2.setForeground(new java.awt.Color(102, 102, 102));
        jButton_2.setText("2");
        jButton_2.setFocusable(false);
        jButton_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_2ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_2);

        jButton_3.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_3.setForeground(new java.awt.Color(102, 102, 102));
        jButton_3.setText("3");
        jButton_3.setFocusable(false);
        jButton_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_3ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_3);

        jButton_OSK.setBackground(new java.awt.Color(51, 51, 51));
        jButton_OSK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_keyboard_52px.png"))); // NOI18N
        jButton_OSK.setFocusable(false);
        jButton_OSK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OSKActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_OSK);

        jButton_Dot.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_Dot.setForeground(new java.awt.Color(0, 0, 0));
        jButton_Dot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_new_moon_24px_2.png"))); // NOI18N
        jButton_Dot.setFocusable(false);
        jButton_Dot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DotActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_Dot);

        jButton_0.setFont(new java.awt.Font("Segoe UI", 1, 55)); // NOI18N
        jButton_0.setForeground(new java.awt.Color(102, 102, 102));
        jButton_0.setText("0");
        jButton_0.setFocusable(false);
        jButton_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_0ActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_0);

        jButton_BackSpace.setBackground(new java.awt.Color(51, 51, 51));
        jButton_BackSpace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_delete-left_50px_2.png"))); // NOI18N
        jButton_BackSpace.setFocusable(false);
        jButton_BackSpace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BackSpaceActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_BackSpace);

        jButton_Delete.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_delete_row_60px.png"))); // NOI18N
        jButton_Delete.setToolTipText("Cancel Sell");
        jButton_Delete.setFocusable(false);
        jButton_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteActionPerformed(evt);
            }
        });
        jPanel_TotalCounterRightBottom.add(jButton_Delete);

        jPanel_BodyCounterRight.add(jPanel_TotalCounterRightBottom);

        jPanel_BodyCounter.add(jPanel_BodyCounterRight);

        javax.swing.GroupLayout jPanel_CounterPaneLayout = new javax.swing.GroupLayout(jPanel_CounterPane);
        jPanel_CounterPane.setLayout(jPanel_CounterPaneLayout);
        jPanel_CounterPaneLayout.setHorizontalGroup(
            jPanel_CounterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CounterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_BodyCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 1428, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel_TitleBarCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
        );
        jPanel_CounterPaneLayout.setVerticalGroup(
            jPanel_CounterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CounterPaneLayout.createSequentialGroup()
                .addComponent(jPanel_TitleBarCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_BodyCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel_CounterPane);

        jPanel_StockPane.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_StockPane.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel_StockPane.setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel_TitleBarStock.setBackground(new java.awt.Color(102, 102, 102));
        jPanel_TitleBarStock.setForeground(new java.awt.Color(60, 63, 65));
        jPanel_TitleBarStock.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel_TitleBarStock.setMinimumSize(new java.awt.Dimension(0, 70));
        jPanel_TitleBarStock.setPreferredSize(new java.awt.Dimension(1458, 70));
        jPanel_TitleBarStock.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarStockMouseDragged(evt);
            }
        });
        jPanel_TitleBarStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarStockMousePressed(evt);
            }
        });

        jLabel_MenuButtonStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_rectangle-list_50px.png"))); // NOI18N
        jLabel_MenuButtonStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MenuButtonStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MenuButtonStockMousePressed(evt);
            }
        });

        jLabel_ExitStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_close_32px.png"))); // NOI18N
        jLabel_ExitStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ExitStock.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel_ExitStockFocusGained(evt);
            }
        });
        jLabel_ExitStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_ExitStockMousePressed(evt);
            }
        });

        jLabel_TitleStock.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_TitleStock.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TitleStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TitleStock.setText("MANAGE STOCK");

        jLabel_MaximizeStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_window-maximize_50px.png"))); // NOI18N
        jLabel_MaximizeStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MaximizeStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MaximizeStockMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TitleBarStockLayout = new javax.swing.GroupLayout(jPanel_TitleBarStock);
        jPanel_TitleBarStock.setLayout(jPanel_TitleBarStockLayout);
        jPanel_TitleBarStockLayout.setHorizontalGroup(
            jPanel_TitleBarStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_MenuButtonStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_MaximizeStock)
                .addGap(18, 18, 18)
                .addComponent(jLabel_ExitStock)
                .addContainerGap())
            .addGroup(jPanel_TitleBarStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarStockLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleStock)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_TitleBarStockLayout.setVerticalGroup(
            jPanel_TitleBarStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarStockLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel_TitleBarStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_MaximizeStock)
                    .addComponent(jLabel_MenuButtonStock)
                    .addComponent(jLabel_ExitStock))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_TitleBarStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarStockLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleStock)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel_BodyStock.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_BodyStock.setLayout(new javax.swing.BoxLayout(jPanel_BodyStock, javax.swing.BoxLayout.LINE_AXIS));

        materialTabbed1.setBackground(new java.awt.Color(255, 255, 255));
        materialTabbed1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        materialTabbed1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        Product__StockPane.setBackground(new java.awt.Color(255, 255, 255));
        Product__StockPane.setLayout(new javax.swing.BoxLayout(Product__StockPane, javax.swing.BoxLayout.LINE_AXIS));

        ProductTable.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "CODE", "NAME", "CATEGORY", "IN STOCK", "UNIT PRICE", ""
            }
        ));
        ProductTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(ProductTable);

        Product__StockPane.add(jScrollPane4);

        materialTabbed1.addTab("Product", Product__StockPane);
        materialTabbed1.addTab("PRODUCT", Product__StockPane);

        Supply__StockPane.setLayout(new javax.swing.OverlayLayout(Supply__StockPane));

        SupplyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RECORD ID", "PRODUCT ID", "NAME", "UNIT NAME", "QUANTITY", "UNIT PRICE", "SUB TOTAL", "DISCOUNTS", "SUPPLIER", "DATE RECEIVED"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SupplyTable.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        SupplyTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(SupplyTable);

        Supply__StockPane.add(jScrollPane3);

        jPanel3.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/icons8_17609020071557740369_75px.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel1);
        jLabel1.setBounds(1218, 741, 75, 75);

        Supply__StockPane.add(jPanel3);

        materialTabbed1.addTab("COLLECT", Supply__StockPane);
        Supply__StockPane.add(jLabel1,JLayeredPane.DEFAULT_LAYER,0);
        Supply__StockPane.add(jScrollPane3,JLayeredPane.DEFAULT_LAYER,1);
        Supply__StockPane.add(jPanel3,JLayeredPane.DEFAULT_LAYER,2);

        jPanel_BodyStock.add(materialTabbed1);

        javax.swing.GroupLayout jPanel_StockPaneLayout = new javax.swing.GroupLayout(jPanel_StockPane);
        jPanel_StockPane.setLayout(jPanel_StockPaneLayout);
        jPanel_StockPaneLayout.setHorizontalGroup(
            jPanel_StockPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_TitleBarStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
            .addGroup(jPanel_StockPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_BodyStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_StockPaneLayout.setVerticalGroup(
            jPanel_StockPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StockPaneLayout.createSequentialGroup()
                .addComponent(jPanel_TitleBarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_BodyStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel_StockPane);

        jPanel_UserPane.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_UserPane.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel_UserPane.setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel_TitleBarUser.setBackground(new java.awt.Color(102, 102, 102));
        jPanel_TitleBarUser.setForeground(new java.awt.Color(60, 63, 65));
        jPanel_TitleBarUser.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel_TitleBarUser.setMinimumSize(new java.awt.Dimension(0, 70));
        jPanel_TitleBarUser.setPreferredSize(new java.awt.Dimension(1458, 70));
        jPanel_TitleBarUser.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarUserMouseDragged(evt);
            }
        });
        jPanel_TitleBarUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarUserMousePressed(evt);
            }
        });

        jLabel_MenuButtonUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_rectangle-list_50px.png"))); // NOI18N
        jLabel_MenuButtonUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MenuButtonUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MenuButtonUserMousePressed(evt);
            }
        });

        jLabel_ExitUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_close_32px.png"))); // NOI18N
        jLabel_ExitUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ExitUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel_ExitUserFocusGained(evt);
            }
        });
        jLabel_ExitUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_ExitUserMousePressed(evt);
            }
        });

        jLabel_TitleUser.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_TitleUser.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TitleUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TitleUser.setText("USER PROFILE");

        jLabel_MaximizeUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_window-maximize_50px.png"))); // NOI18N
        jLabel_MaximizeUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MaximizeUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MaximizeUserMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TitleBarUserLayout = new javax.swing.GroupLayout(jPanel_TitleBarUser);
        jPanel_TitleBarUser.setLayout(jPanel_TitleBarUserLayout);
        jPanel_TitleBarUserLayout.setHorizontalGroup(
            jPanel_TitleBarUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_MenuButtonUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1260, Short.MAX_VALUE)
                .addComponent(jLabel_MaximizeUser)
                .addGap(18, 18, 18)
                .addComponent(jLabel_ExitUser)
                .addContainerGap())
            .addGroup(jPanel_TitleBarUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarUserLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleUser)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_TitleBarUserLayout.setVerticalGroup(
            jPanel_TitleBarUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarUserLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel_TitleBarUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_MaximizeUser)
                    .addComponent(jLabel_MenuButtonUser)
                    .addComponent(jLabel_ExitUser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_TitleBarUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarUserLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleUser)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel_BodyUser.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel_BodyUserLayout = new javax.swing.GroupLayout(jPanel_BodyUser);
        jPanel_BodyUser.setLayout(jPanel_BodyUserLayout);
        jPanel_BodyUserLayout.setHorizontalGroup(
            jPanel_BodyUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1428, Short.MAX_VALUE)
        );
        jPanel_BodyUserLayout.setVerticalGroup(
            jPanel_BodyUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 827, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_UserPaneLayout = new javax.swing.GroupLayout(jPanel_UserPane);
        jPanel_UserPane.setLayout(jPanel_UserPaneLayout);
        jPanel_UserPaneLayout.setHorizontalGroup(
            jPanel_UserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_TitleBarUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
            .addGroup(jPanel_UserPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_BodyUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_UserPaneLayout.setVerticalGroup(
            jPanel_UserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_UserPaneLayout.createSequentialGroup()
                .addComponent(jPanel_TitleBarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_BodyUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel_UserPane);

        jPanel_ConfigurationPane.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_ConfigurationPane.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel_ConfigurationPane.setMinimumSize(new java.awt.Dimension(1440, 900));

        jPanel_TitleBarConfiguration.setBackground(new java.awt.Color(102, 102, 102));
        jPanel_TitleBarConfiguration.setForeground(new java.awt.Color(60, 63, 65));
        jPanel_TitleBarConfiguration.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel_TitleBarConfiguration.setMinimumSize(new java.awt.Dimension(0, 70));
        jPanel_TitleBarConfiguration.setPreferredSize(new java.awt.Dimension(1458, 70));
        jPanel_TitleBarConfiguration.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarConfigurationMouseDragged(evt);
            }
        });
        jPanel_TitleBarConfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel_TitleBarConfigurationMousePressed(evt);
            }
        });

        jLabel_MenuButtonConfiguration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_rectangle-list_50px.png"))); // NOI18N
        jLabel_MenuButtonConfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MenuButtonConfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MenuButtonConfigurationMousePressed(evt);
            }
        });

        jLabel_ExitConfiguration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_close_32px.png"))); // NOI18N
        jLabel_ExitConfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ExitConfiguration.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel_ExitConfigurationFocusGained(evt);
            }
        });
        jLabel_ExitConfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_ExitConfigurationMousePressed(evt);
            }
        });

        jLabel_TitleConfiguration.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_TitleConfiguration.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TitleConfiguration.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TitleConfiguration.setText("CONFIGURATION");

        jLabel_MaximizeCnfiguration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icon/Active/icons8_window-maximize_50px.png"))); // NOI18N
        jLabel_MaximizeCnfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_MaximizeCnfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_MaximizeCnfigurationMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TitleBarConfigurationLayout = new javax.swing.GroupLayout(jPanel_TitleBarConfiguration);
        jPanel_TitleBarConfiguration.setLayout(jPanel_TitleBarConfigurationLayout);
        jPanel_TitleBarConfigurationLayout.setHorizontalGroup(
            jPanel_TitleBarConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarConfigurationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_MenuButtonConfiguration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1260, Short.MAX_VALUE)
                .addComponent(jLabel_MaximizeCnfiguration)
                .addGap(18, 18, 18)
                .addComponent(jLabel_ExitConfiguration)
                .addContainerGap())
            .addGroup(jPanel_TitleBarConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarConfigurationLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleConfiguration)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_TitleBarConfigurationLayout.setVerticalGroup(
            jPanel_TitleBarConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TitleBarConfigurationLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel_TitleBarConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_MaximizeCnfiguration)
                    .addComponent(jLabel_MenuButtonConfiguration)
                    .addComponent(jLabel_ExitConfiguration))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_TitleBarConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_TitleBarConfigurationLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel_TitleConfiguration)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel_BodyConfiguration.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel_BodyConfigurationLayout = new javax.swing.GroupLayout(jPanel_BodyConfiguration);
        jPanel_BodyConfiguration.setLayout(jPanel_BodyConfigurationLayout);
        jPanel_BodyConfigurationLayout.setHorizontalGroup(
            jPanel_BodyConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1428, Short.MAX_VALUE)
        );
        jPanel_BodyConfigurationLayout.setVerticalGroup(
            jPanel_BodyConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 827, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_ConfigurationPaneLayout = new javax.swing.GroupLayout(jPanel_ConfigurationPane);
        jPanel_ConfigurationPane.setLayout(jPanel_ConfigurationPaneLayout);
        jPanel_ConfigurationPaneLayout.setHorizontalGroup(
            jPanel_ConfigurationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_TitleBarConfiguration, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
            .addGroup(jPanel_ConfigurationPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_BodyConfiguration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_ConfigurationPaneLayout.setVerticalGroup(
            jPanel_ConfigurationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ConfigurationPaneLayout.createSequentialGroup()
                .addComponent(jPanel_TitleBarConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_BodyConfiguration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel_ConfigurationPane);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel_CounterTabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_CounterTabMousePressed
        
        jPanel_CounterTab.setBackground(new java.awt.Color(181, 44, 90));
        jPanel_StockTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_UserTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_SettingTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_CounterPane.setVisible(true);
        jPanel_StockPane.setVisible(false);
        jPanel_UserPane.setVisible(false);
        jPanel_ConfigurationPane.setVisible(false);
    }//GEN-LAST:event_jPanel_CounterTabMousePressed

    private void jPanel_StockTabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_StockTabMousePressed
        
        jPanel_CounterTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_StockTab.setBackground(new java.awt.Color(181, 44, 90));
        jPanel_UserTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_SettingTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_CounterPane.setVisible(false);
        jPanel_StockPane.setVisible(true);
        jPanel_UserPane.setVisible(false);
        jPanel_ConfigurationPane.setVisible(false);
    }//GEN-LAST:event_jPanel_StockTabMousePressed

    private void jLabel_CounterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CounterMousePressed
        
        jPanel_CounterTabMousePressed(evt);
    }//GEN-LAST:event_jLabel_CounterMousePressed

    private void jLabel_StockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_StockMousePressed
        
        jPanel_StockTabMousePressed(evt);
    }//GEN-LAST:event_jLabel_StockMousePressed

    private void jPanel_UserTabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_UserTabMousePressed
        
        jPanel_CounterTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_StockTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_UserTab.setBackground(new java.awt.Color(181, 44, 90));
        jPanel_SettingTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_CounterPane.setVisible(false);
        jPanel_StockPane.setVisible(false);
        jPanel_UserPane.setVisible(true);
        jPanel_ConfigurationPane.setVisible(false);
    }//GEN-LAST:event_jPanel_UserTabMousePressed

    private void jPanel_SettingTabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_SettingTabMousePressed
        
        jPanel_CounterTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_StockTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_UserTab.setBackground(new java.awt.Color(39, 40, 60));
        jPanel_SettingTab.setBackground(new java.awt.Color(181, 44, 90));
        jPanel_CounterPane.setVisible(false);
        jPanel_StockPane.setVisible(false);
        jPanel_UserPane.setVisible(false);
        jPanel_ConfigurationPane.setVisible(true);
    }//GEN-LAST:event_jPanel_SettingTabMousePressed

    private void jLabel_UserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_UserMousePressed
        
        jPanel_UserTabMousePressed(evt);
    }//GEN-LAST:event_jLabel_UserMousePressed

    private void jLabel_SettingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_SettingMousePressed
        
        jPanel_SettingTabMousePressed(evt);
    }//GEN-LAST:event_jLabel_SettingMousePressed

    private void jLabel_MenuButtonStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MenuButtonStockMousePressed
        
        jPanel_SideMenuView.setVisible(true);
    }//GEN-LAST:event_jLabel_MenuButtonStockMousePressed

    private void jLabel_ExitStockFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel_ExitStockFocusGained
        
    }//GEN-LAST:event_jLabel_ExitStockFocusGained

    private void jLabel_ExitStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitStockMousePressed
        
        jLabel_ExitCounterMousePressed(evt);
    }//GEN-LAST:event_jLabel_ExitStockMousePressed

    private void jLabel_MenuButtonUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MenuButtonUserMousePressed
        
        jPanel_SideMenuView.setVisible(true);
    }//GEN-LAST:event_jLabel_MenuButtonUserMousePressed

    private void jLabel_ExitUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel_ExitUserFocusGained
        
    }//GEN-LAST:event_jLabel_ExitUserFocusGained

    private void jLabel_ExitUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitUserMousePressed
        
        jLabel_ExitCounterMousePressed(evt);
    }//GEN-LAST:event_jLabel_ExitUserMousePressed

    private void jLabel_MenuButtonConfigurationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MenuButtonConfigurationMousePressed
        
        jPanel_SideMenuView.setVisible(true);
    }//GEN-LAST:event_jLabel_MenuButtonConfigurationMousePressed

    private void jLabel_ExitConfigurationFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel_ExitConfigurationFocusGained
        
    }//GEN-LAST:event_jLabel_ExitConfigurationFocusGained

    private void jLabel_ExitConfigurationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitConfigurationMousePressed
        
        jLabel_ExitCounterMousePressed(evt);
    }//GEN-LAST:event_jLabel_ExitConfigurationMousePressed

    private void jPanel_TransparentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TransparentMousePressed
        
        jPanel_SideMenuView.setVisible(false);
    }//GEN-LAST:event_jPanel_TransparentMousePressed

    private void jLabel_Stock1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Stock1MousePressed
        
    }//GEN-LAST:event_jLabel_Stock1MousePressed

    private void jPanel_StockTab1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_StockTab1MousePressed
        
    }//GEN-LAST:event_jPanel_StockTab1MousePressed

    private void jLabel_MaximizeStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MaximizeStockMousePressed
        
        winSizeSet();
    }//GEN-LAST:event_jLabel_MaximizeStockMousePressed

    private void jLabel_MaximizeUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MaximizeUserMousePressed
        
        winSizeSet();
    }//GEN-LAST:event_jLabel_MaximizeUserMousePressed

    private void jLabel_MaximizeCnfigurationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MaximizeCnfigurationMousePressed
        
        winSizeSet();
    }//GEN-LAST:event_jLabel_MaximizeCnfigurationMousePressed

    private void jLabel_MenuButtonCounterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MenuButtonCounterMousePressed

        jPanel_SideMenuView.setVisible(true);
    }//GEN-LAST:event_jLabel_MenuButtonCounterMousePressed

    private void jLabel_ExitCounterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ExitCounterMousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel_ExitCounterMousePressed

    private void jLabel_MaximizeCounterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MaximizeCounterMousePressed

        winSizeSet();
    }//GEN-LAST:event_jLabel_MaximizeCounterMousePressed

    private void jButton_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_7ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_7ActionPerformed

    private void jButton_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_8ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_8ActionPerformed

    private void jButton_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_9ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_9ActionPerformed

    private void jButton_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EnterActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_EnterActionPerformed

    private void jButton_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_4ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_4ActionPerformed

    private void jButton_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_5ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_5ActionPerformed

    private void jButton_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_6ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_6ActionPerformed

    private void jButton_CalculatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CalculatorActionPerformed
        Calculator calc = new Calculator();

    }//GEN-LAST:event_jButton_CalculatorActionPerformed

    private void jButton_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_1ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_1ActionPerformed

    private void jButton_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_2ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_2ActionPerformed

    private void jButton_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_3ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_3ActionPerformed

    private void jButton_OSKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OSKActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /c osk");
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_OSKActionPerformed

    private void jButton_DotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DotActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_DotActionPerformed

    private void jButton_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_0ActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_0ActionPerformed

    private void jButton_BackSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BackSpaceActionPerformed

        JButton key = (JButton)evt.getSource();
        int keycode = Integer.decode(key.getName());
        NumberPad.typeKey(keycode);
    }//GEN-LAST:event_jButton_BackSpaceActionPerformed

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DeleteActionPerformed
        SearchBox.setText("");
    }//GEN-LAST:event_jButton_DeleteActionPerformed

    private void SearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchBoxActionPerformed

    private void jButton_CashPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CashPayActionPerformed
        
        
    }//GEN-LAST:event_jButton_CashPayActionPerformed

    private void jPanel_TitleBarCounterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarCounterMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel_TitleBarCounterMousePressed

    private void jPanel_TitleBarCounterMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarCounterMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-xy);
    }//GEN-LAST:event_jPanel_TitleBarCounterMouseDragged

    private void jPanel_TitleBarStockMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarStockMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-xy);
    }//GEN-LAST:event_jPanel_TitleBarStockMouseDragged

    private void jPanel_TitleBarUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarUserMousePressed
       xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel_TitleBarUserMousePressed

    private void jPanel_TitleBarUserMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarUserMouseDragged
    int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-xy);       // TODO add your handling code here:
    }//GEN-LAST:event_jPanel_TitleBarUserMouseDragged

    private void jPanel_TitleBarConfigurationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarConfigurationMousePressed
    xx = evt.getX();
        xy = evt.getY();         // TODO add your handling code here:
    }//GEN-LAST:event_jPanel_TitleBarConfigurationMousePressed

    private void jPanel_TitleBarConfigurationMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarConfigurationMouseDragged
    int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-xy);      // TODO add your handling code here:
    }//GEN-LAST:event_jPanel_TitleBarConfigurationMouseDragged

    private void jPanel_TitleBarStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TitleBarStockMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel_TitleBarStockMousePressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        AddProduct ap = new AddProduct();
        ap.setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void SearchBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchBoxKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_0:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_4:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_5:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_6:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD7:
            case KeyEvent.VK_7:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_8:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD9:
            case KeyEvent.VK_9:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_PERIOD:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_BACK_SPACE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_DELETE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            SearchBox.setText("");
            break;
            case KeyEvent.VK_ENTER:
            
                quantityPanel.setVisible(true);
            quantityPanel.show(SearchBox, 0, SearchBox.getHeight());
            
            SearchBox.setText("");
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            SearchBox.setText("");
            break;
            default:
            break;
        }
    }//GEN-LAST:event_SearchBoxKeyPressed

    private void SearchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchBoxKeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_0:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_4:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_5:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_6:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD7:
            case KeyEvent.VK_7:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_8:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD9:
            case KeyEvent.VK_9:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_PERIOD:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_BACK_SPACE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_DELETE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            SearchBox.setText("");
            break;
            case KeyEvent.VK_ENTER:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            
            
            break;
            default:
            break;
        }
    }//GEN-LAST:event_SearchBoxKeyReleased

    private void jButton_CashPay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CashPay1ActionPerformed
        String s = (String) SearchBoxCombo.getSelectedItem();
        int q = Integer.parseInt(quantityTf.getText());
        try {
            st = conn.prepareStatement("SELECT product_id,product_name,unit_price FROM tblproduct WHERE produce_code='"+s+"'");
            rs = st.executeQuery("SELECT product_id,product_name,unit_price FROM tblproduct WHERE produce_code='"+s+"'");
            rs.next();
            float qq = q*rs.getFloat("unit_price");
            st = conn.prepareStatement("INSERT INTO tblsales (product_id,product_name,quantity,sub_total) VALUES ('"+rs.getString("product_id")+"','"+rs.getString("product_name")+"','"+q+"','"+qq+"')");
            st.executeUpdate("INSERT INTO tblsales (product_id,product_name,quantity,sub_total) VALUES ('"+rs.getString("product_id")+"','"+rs.getString("product_name")+"','"+q+"','"+q*rs.getFloat("unit_price")+"')");
            
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_CashPay1ActionPerformed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        quantityPanel.setVisible(false);
    }//GEN-LAST:event_jLabel3MousePressed

    void enterPressed1 () {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
            */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            
            conn = Model.ConnectDB.connect();
            
            //</editor-fold>
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainWindow().setVisible(true);
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ProductTable;
    private javax.swing.JPanel Product__StockPane;
    public static View.MyTextField SearchBox;
    private javax.swing.JComboBox<String> SearchBoxCombo;
    private View.Repository.TableCustom SupplyTable;
    private javax.swing.JLayeredPane Supply__StockPane;
    public static javax.swing.JButton jButton_0;
    public static javax.swing.JButton jButton_1;
    public static javax.swing.JButton jButton_2;
    public static javax.swing.JButton jButton_3;
    public static javax.swing.JButton jButton_4;
    public static javax.swing.JButton jButton_5;
    public static javax.swing.JButton jButton_6;
    public static javax.swing.JButton jButton_7;
    public static javax.swing.JButton jButton_8;
    public static javax.swing.JButton jButton_9;
    public static javax.swing.JButton jButton_BackSpace;
    public static javax.swing.JButton jButton_Calculator;
    private javax.swing.JButton jButton_CardPay;
    private javax.swing.JButton jButton_CashPay;
    private javax.swing.JButton jButton_CashPay1;
    public static javax.swing.JButton jButton_Delete;
    public static javax.swing.JButton jButton_Dot;
    public static javax.swing.JButton jButton_Enter;
    public javax.swing.JButton jButton_OSK;
    private javax.swing.JButton jButton_QRPay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Counter;
    private javax.swing.JLabel jLabel_DiscountLabelCounter;
    private javax.swing.JLabel jLabel_DiscountPriceCounter;
    private javax.swing.JLabel jLabel_ExitConfiguration;
    private javax.swing.JLabel jLabel_ExitCounter;
    private javax.swing.JLabel jLabel_ExitStock;
    private javax.swing.JLabel jLabel_ExitUser;
    private javax.swing.JLabel jLabel_GrandTotalCounter;
    private javax.swing.JLabel jLabel_GrandTotalLabelCounter;
    private javax.swing.JLabel jLabel_ItemCountCounter;
    private javax.swing.JLabel jLabel_ItemCountLabelCounter;
    private javax.swing.JLabel jLabel_MaximizeCnfiguration;
    private javax.swing.JLabel jLabel_MaximizeCounter;
    private javax.swing.JLabel jLabel_MaximizeStock;
    private javax.swing.JLabel jLabel_MaximizeUser;
    private javax.swing.JLabel jLabel_MenuButtonConfiguration;
    private javax.swing.JLabel jLabel_MenuButtonCounter;
    private javax.swing.JLabel jLabel_MenuButtonStock;
    private javax.swing.JLabel jLabel_MenuButtonUser;
    private javax.swing.JLabel jLabel_Setting;
    private javax.swing.JLabel jLabel_Stock;
    private javax.swing.JLabel jLabel_Stock1;
    private javax.swing.JLabel jLabel_SubtotalLabelCounter;
    private javax.swing.JLabel jLabel_SubtotalPriceCounter;
    private javax.swing.JLabel jLabel_TitleConfiguration;
    private javax.swing.JLabel jLabel_TitleCounter;
    private javax.swing.JLabel jLabel_TitleStock;
    private javax.swing.JLabel jLabel_TitleUser;
    private javax.swing.JLabel jLabel_User;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_BodyConfiguration;
    private javax.swing.JPanel jPanel_BodyCounter;
    private javax.swing.JPanel jPanel_BodyCounterLeft;
    private javax.swing.JPanel jPanel_BodyCounterRight;
    private javax.swing.JPanel jPanel_BodyStock;
    private javax.swing.JPanel jPanel_BodyUser;
    private javax.swing.JPanel jPanel_ConfigurationPane;
    private javax.swing.JPanel jPanel_CounterPane;
    private javax.swing.JPanel jPanel_CounterTab;
    private javax.swing.JPanel jPanel_DiscountPanelCounter;
    private javax.swing.JPanel jPanel_GrandTotalPanelCounter;
    private javax.swing.JPanel jPanel_ItemCountPanelCounter;
    private View.Repository.jPanel jPanel_ItemListCounter;
    private javax.swing.JPanel jPanel_Left_Bottom;
    private javax.swing.JPanel jPanel_Left_Top;
    private javax.swing.JPanel jPanel_PayButtonsPanelCounter;
    private View.Repository.jPanel jPanel_SearchContainerCounter;
    private javax.swing.JPanel jPanel_SettingTab;
    private javax.swing.JPanel jPanel_SideMenu;
    private javax.swing.JPanel jPanel_SideMenuView;
    private javax.swing.JPanel jPanel_Spacer;
    private javax.swing.JPanel jPanel_Spacer1;
    private javax.swing.JPanel jPanel_StockPane;
    private javax.swing.JPanel jPanel_StockTab;
    private javax.swing.JPanel jPanel_StockTab1;
    private javax.swing.JPanel jPanel_SubtotalPanelCounter;
    private javax.swing.JPanel jPanel_TitleBarConfiguration;
    private javax.swing.JPanel jPanel_TitleBarCounter;
    private javax.swing.JPanel jPanel_TitleBarStock;
    private javax.swing.JPanel jPanel_TitleBarUser;
    private View.Repository.jPanel jPanel_TotalCounter;
    private javax.swing.JPanel jPanel_TotalCounterRightBottom;
    private javax.swing.JPanel jPanel_TotalCounterRightMiddle;
    private javax.swing.JPanel jPanel_TotalCounterRightTop;
    private javax.swing.JPanel jPanel_Transparent;
    private javax.swing.JPanel jPanel_UserPane;
    private javax.swing.JPanel jPanel_UserTab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private View.Repository.TabbedPaneCustom materialTabbed1;
    private javax.swing.JPopupMenu quantityPanel;
    private javax.swing.JTextField quantityTf;
    public static View.Repository.TableCustom saleItemTbl;
    // End of variables declaration//GEN-END:variables


}
