/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsales;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author shieda
 */
public class AddItemDialog extends javax.swing.JDialog {

    /**
     * Creates new form AddItemDialog
     */
    Transaction transaction;
    Product product;

    public AddItemDialog(java.awt.Frame parent, boolean modal, Transaction transaction, Product product) {
        super(parent, modal);
        initComponents();
        this.transaction = transaction;
        this.product = product;
        itemTextField.setText(this.product.getDescription());
        itemTextField.setEditable(false);
        quantityTextField.requestFocus();

        quantityTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void getdifference() {
                Runnable difference = new Runnable() {
                    @Override
                    public void run() {
                        numberCheck();
                    }
                };
                SwingUtilities.invokeLater(difference);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                getdifference();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getdifference();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getdifference();
            }
        });
    }

    private void numberCheck() {
        try {
            float quantity = Float.parseFloat(quantityTextField.getText());
        } catch (NumberFormatException e) {
            quantityTextField.setText(quantityTextField.getText().substring(0, quantityTextField.getText().length() - 1));
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
        jLabel1 = new javax.swing.JLabel();
        itemTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        quantityTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PRODUCT");
        jPanel1.add(jLabel1);

        itemTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(itemTextField);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("QUANTITY");
        jPanel1.add(jLabel3);

        quantityTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantityTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                quantityTextFieldFocusLost(evt);
            }
        });
        quantityTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                quantityTextFieldKeyPressed(evt);
            }
        });
        jPanel1.add(quantityTextField);

        addButton.setText("ADD");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        jPanel1.add(addButton);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        this._submit();
    }//GEN-LAST:event_addButtonMouseClicked

    private void quantityTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this._submit();
        }
    }//GEN-LAST:event_quantityTextFieldKeyPressed

    private void quantityTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTextFieldFocusLost
        quantityTextField.requestFocus();
    }//GEN-LAST:event_quantityTextFieldFocusLost

    private void _submit() {
        try {
            int quantity = Integer.parseInt(quantityTextField.getText());
            if (quantity > 0) {
                if (this.product.getStock() >= quantity) {
                    this.product.withdraw(quantity);
                    Item item = Item.getExistingItem(product, transaction);
                    if (item != null) {
                        item.deposit(quantity);
                    } else {
                        Item.insert(transaction, product, quantity);
                    }

                    this.setVisible(false);
                    this.dispose();

                } else {
                    quantityTextField.setText("");
                    JOptionPane.showMessageDialog(null, "Not Enough Stock", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                quantityTextField.setText("");
                JOptionPane.showMessageDialog(null, "Please Enter A Positive Number", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            quantityTextField.setText("");
            JOptionPane.showMessageDialog(null, "Only Accepts Numeric Input", "ERROR", JOptionPane.ERROR_MESSAGE);

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField itemTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField quantityTextField;
    // End of variables declaration//GEN-END:variables
}
