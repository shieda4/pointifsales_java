package pointofsales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author shieda
 */
public class Product {

    private int Id;
    private Category Category;
    private String Description;
    private float Price;
    private float Stock;

    public int getId() {
        return this.Id;
    }

    public String getDescription() {
        return this.Description;
    }

    public float getPrice() {
        return this.Price;
    }

    public float getStock() {
        return this.Stock;
    }

    public Category getCategoty() {
        return this.Category;
    }

    public Product(ResultSet rs) {
        try {
            this.Id = rs.getInt("Product.Id");
            this.Category = new Category(rs.getInt("Product.Category"));
            this.Description = rs.getString("Product.Description");
            this.Price = rs.getFloat("Product.Price");
            this.Stock = rs.getFloat("Product.Stock");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Product(int Id) {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT * FROM Product WHERE Product.Id = " + Id + ";";
            ResultSet rs = dbConn.getResultQuery(query);

            try {
                while (rs.next()) {
                    this.Id = rs.getInt("Id");
                    this.Category = new Category(rs.getInt("Category"));
                    this.Description = rs.getString("Description");
                    this.Price = rs.getFloat("Price");
                    this.Stock = rs.getFloat("Stock");
                }

            } catch (SQLException ex) {

            }
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void withdraw(int quantity) {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String update = "UPDATE Product SET Stock = Stock-" + quantity + " WHERE Id = " + this.getId() + ";";
            dbConn.executeSQL(update);
            this.Stock = this.Stock - quantity;
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object[] getTableObject() {
        return new Object[]{this.Id, this.Description, this.Category.getName(), this.Price, this.Stock};

    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            try {
                String query = "SELECT * FROM Product";
                ResultSet rs = dbConn.getResultQuery(query);
                while (rs.next()) {
                    Product item = new Product(rs);
                    products.add(item);
                }
            } catch (SQLException ex) {

            }
            dbConn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return products;
    }

    public static List<Product> getProducts(String text) {
        List<Product> products = new ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            try {
                String query = "SELECT Product.* FROM Product JOIN Category ON Product.Category = Category.Id "
                        + "WHERE Product.Description LIKE '%" + text + "%' OR Category.Name LIKE '%" + text + "%';";
                ResultSet rs = dbConn.getResultQuery(query);
                while (rs.next()) {
                    Product product = new Product(rs);
                    products.add(product);
                }
            } catch (SQLException ex) {

            }
            dbConn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return products;
    }

}
