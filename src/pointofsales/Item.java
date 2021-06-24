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
public class Item {

    private int Id;
    private Transaction Transaction;
    private Product Product;
    private int Quantity;

    public Item(ResultSet rs) {
        try {
            this.Id = rs.getInt("Item.Id");
            this.Transaction = new Transaction(rs.getInt("Transaction"));
            this.Product = new Product(rs.getInt("Product"));
            this.Quantity = rs.getInt("Quantity");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object[] getTableObject() {
        String category_name = this.Product.getCategoty().getName();
        float product_price = this.Product.getPrice();
        return new Object[]{this.Id, this.Product.getDescription(), category_name, product_price, this.Quantity, product_price * this.Quantity};

    }

    public void delete() {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String update = "UPDATE Product SET Stock = Stock+" + this.Quantity + " WHERE Id = " + this.Product.getId() + ";";
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

    public void deposit(int quantity) {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String update = "UPDATE Item SET Quantity = Quantity+" + quantity + " WHERE Id = " + this.Id + ";";
            dbConn.executeSQL(update);
            this.Quantity = this.Quantity + quantity;
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void insert(Transaction transaction, Product product, int quantity) {
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            String insert = "INSERT INTO Item(Transaction, Product, Quantity) VALUES(" + transaction.getId() + "," + product.getId() + "," + quantity + ");";
            dbConn.executeSQL(insert);
            if (dbConn._getConnection() != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Item> getItems(Transaction transaction) {
        List<Item> items = new ArrayList<>();
        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            try {
                String query = "SELECT * FROM Item WHERE Transaction = " + transaction.getId() + ";";
                ResultSet rs = dbConn.getResultQuery(query);
                while (rs.next()) {
                    Item item = new Item(rs);
                    items.add(item);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

            }
            dbConn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return items;
    }

    public static Item getExistingItem(Product product, Transaction transaction) {

        try {
            DatabaseConnection dbConn = new DatabaseConnection();
            try {
                String query = "SELECT * FROM Item WHERE Item.Product = " + product.getId() + " AND Item.Transaction = " + transaction.getId() + ";";
                ResultSet rs = dbConn.getResultQuery(query);
                while (rs.next()) {
                    return new Item(rs);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

            }
            dbConn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return null;
    }

}
