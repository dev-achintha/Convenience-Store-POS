
package Controller;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRenderer extends DataVeryfier {

    Connection conn;
    Statement st;
    ResultSet rs;
    public DataRenderer(Connection conn) {
        this.conn = conn;
        
        
    }

    public String fetchUnit(int id) throws SQLException {
        st = conn.prepareStatement("SELECT * FROM tblproductunit WHERE unit_id='"+id+"'");
        rs = st.executeQuery("SELECT * FROM tblproductunit WHERE unit_id='"+id+"'");
        rs.next();
        String unit = rs.getString("unit_name");
        return unit;
    }
    
    public List fetchUser(int n) throws SQLException{
        String name,designation;
        List userData = new ArrayList();
        st = conn.prepareStatement("SELECT * FROM tbluser WHERE user_id='"+n+"'");
        rs = st.executeQuery("SELECT * FROM tbluser WHERE user_id='"+n+"'");
        rs.next();
        name = rs.getString("fullname");
        designation = rs.getString("designation");
        
        userData.add(name);
        userData.add(designation);
        return userData;
    }
    public List fetchSarchSource() throws SQLException {
        List searchList = new ArrayList();
        List searchPair = new ArrayList();
        st = conn.prepareStatement("SELECT produce_code,product_name FROM tblproduct");
        rs = st.executeQuery("SELECT produce_code,product_name FROM tblproduct");
        while(rs.next()) {
            String code = rs.getString("produce_coe");
            String name = rs.getString("product_name");
            searchPair.add(code);
            searchPair.add(name);
            searchList.add(searchPair);
        }
        return searchList;
    }
    }
