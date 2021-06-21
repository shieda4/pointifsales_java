/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author shieda
 */
public class DatabaseConnection {

    private Connection conn;

    public DatabaseConnection() {

        try {
            Connection connect;
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pointofsales?zeroDateTimeBehavior=convertToNull", "shieda", "Alliahsosmena1!");
            connect.setAutoCommit(true);
            _setConnection(connect);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void _setConnection(Connection conn) {
        this.conn = conn;
    }

    protected Connection _getConnection() {
        return conn;
    }

    public ResultSet getResultQuery(String query) {
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return null;
    }

    public void executeSQL(String sql) {
        try {
            Statement st = this.conn.createStatement();
            st.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void close() throws SQLException {
        this.conn.close();
    }

}
