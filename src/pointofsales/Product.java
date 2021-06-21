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
public class Product {

    private int Id;
    private int Category;
    private String Description;
    private float Price;
    private float Stock;

    public Product(int Id, String Description, int Category, float Price, float Stock) {
        this.Id = Id;
        this.Description = Description;
        this.Category = Category;
        this.Price = Price;
        this.Stock = Stock;
    }

    public Product(ResultSet rs) {
        try {
            this.Id = rs.getInt("Product.Id");
            this.Category = rs.getInt("Product.Category");
            this.Description = rs.getString("Product.Description");
            this.Price = rs.getFloat("Product.Price");
            this.Stock = rs.getFloat("Product.Stock");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Product() {

    }

    public Object[] getTableObject() {
        String categoryName = "";
        float price = 0;

        try {

            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT Name, Price FROM Category INNER JOIN Product ON Category.Id = Product.Category WHERE Product.Id = " + this.Id + ";";
            ResultSet rs = dbConn.getResultQuery(query);

            try {
                while (rs.next()) {
                    categoryName = rs.getString("Name");
                    price = rs.getFloat("Price");
                }

            } catch (SQLException ex) {

            }
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return new Object[]{this.Id, this.Description, categoryName, price, this.Stock};

    }

}
