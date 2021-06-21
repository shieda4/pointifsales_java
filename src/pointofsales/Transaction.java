/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsales;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author shieda
 */
public class Transaction {

    private int Id;

    public Transaction() {
        try {

            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "CALL InsertSelectLastTransaction();";
            ResultSet rs = dbConn.getResultQuery(query);
            while (rs.next()) {
                this.Id = rs.getInt("Id");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getId() {
        return this.Id;
    }

    public void discard() {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String delete = "DELETE FROM Transaction WHERE Id = " + this.Id + ";";
            dbConn.executeSQL(delete);
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public float getTotal() {
        float total = 0;
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String stockQuery = "SELECT SUM(Item.Quantity * Product.Price) AS Total FROM Item "
                    + "JOIN Product ON Item.Product = Product.Id "
                    + "WHERE Item.Transaction = " + this.Id + ";";
            ResultSet rs = dbConn.getResultQuery(stockQuery);
            while (rs.next()) {
                total = rs.getFloat("Total");
            }
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }

}
