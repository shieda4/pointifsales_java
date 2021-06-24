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
public class Category {

    private int Id;
    private String Name;

    public Category(int Id) {
        this.Id = Id;
        try {

            DatabaseConnection dbConn = new DatabaseConnection();
            String query = "SELECT Name FROM Category WHERE Category.Id = " + this.Id + ";";
            ResultSet rs = dbConn.getResultQuery(query);

            try {
                while (rs.next()) {
                    this.Name = rs.getString("Name");
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

    public String getName() {
        return this.Name;
    }
}
