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
public class Item {

    int Id;
    int Transaction;
    int Product;
    int Quantity;

    public Item(ResultSet rs) {
        try {
            this.Id = rs.getInt("Item.Id");
            this.Transaction = rs.getInt("Transaction");
            this.Product = rs.getInt("Product");
            this.Quantity = rs.getInt("Quantity");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object[] getTableObject() {
        String description = "";
        String category = "";
        float price = 0;

        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT Product.Description , Category.Name, Product.Price "
                    + "FROM Category INNER JOIN Product ON Category.Id = Product.Category "
                    + "JOIN Item ON Item.Product = Product.Id "
                    + "JOIN Transaction ON Transaction.Id = Item.Transaction "
                    + "WHERE Transaction.Id = " + this.Transaction
                    + " AND Product.Id = " + this.Product + ";";
            ResultSet rs = dbConn.getResultQuery(query);

            try {
                while (rs.next()) {
                    description = rs.getString("Product.Description");
                    category = rs.getString("Category.Name");
                    price = rs.getFloat("Product.Price");
                }

            } catch (SQLException ex) {

            }
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return new Object[]{this.Id, description, category, price, this.Quantity, price * this.Quantity};

    }

    public void discard() {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String update = "UPDATE Product SET Stock = Stock+" + this.Quantity + " WHERE Id = " + this.Product + ";";
            String delete = "DELETE FROM Item WHERE Id = " + this.Id + ";";
            dbConn.executeSQL(update);
            dbConn.executeSQL(delete);
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
