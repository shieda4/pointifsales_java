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
public class CheckoutDialog extends javax.swing.JDialog {

    /**
     * Creates new form CheckoutDialog
     */
    private final Transaction transaction;

    public CheckoutDialog(java.awt.Frame parent, boolean modal, Transaction transaction) {
        super(parent, modal);
        initComponents();
        this.transaction = transaction;
        totalTextField.setText(String.valueOf(this.transaction.getTotal()));
        totalTextField.setEditable(false);
        amountTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void getdifference() {
                Runnable difference = new Runnable() {
                    @Override
                    public void run() {
                        _getDifference();
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
        amountTextField.requestFocus();

    }

    private void _finish() {
        try {
            Float amount = Float.parseFloat(amountTextField.getText());
            if (amount > 0) {
                if (amount >= this.transaction.getTotal()) {
                    this.setVisible(false);
                    this.dispose();
                    Main parentFrame = (Main) this.getParent();
                    parentFrame.closeMainPanel();
                } else {
                    amountTextField.setText("");
                    JOptionPane.showMessageDialog(null, "Not Enough To Cover Bill", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                amountTextField.setText("");
                JOptionPane.showMessageDialog(null, "Please Enter A Positive Number", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            amountTextField.setText("");
            JOptionPane.showMessageDialog(null, "Only Accepts Numeric Input", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void _getDifference() {
        try {
            float total = this.transaction.getTotal();
            float amount = Float.parseFloat(amountTextField.getText());
            if (amount > 0) {
                changeLabel.setText("CHANGE: " + (amount - total));
            } else {
                amountTextField.setText("");
                JOptionPane.showMessageDialog(null, "Please Enter A Positive Number", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            amountTextField.setText("");

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

        totalLabel = new javax.swing.JLabel();
        totalTextField = new javax.swing.JTextField();
        amountLabel = new javax.swing.JLabel();
        amountTextField = new javax.swing.JTextField();
        changeLabel = new javax.swing.JLabel();
        finishButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(6, 1));

        totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalLabel.setText("TOTAL");
        getContentPane().add(totalLabel);

        totalTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(totalTextField);

        amountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountLabel.setText("AMOUNT");
        getContentPane().add(amountLabel);

        amountTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amountTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                amountTextFieldFocusLost(evt);
            }
        });
        amountTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amountTextFieldKeyPressed(evt);
            }
        });
        getContentPane().add(amountTextField);

        changeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changeLabel.setText("CHANGE:");
        getContentPane().add(changeLabel);

        finishButton.setText("FINISH");
        finishButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                finishButtonMouseClicked(evt);
            }
        });
        getContentPane().add(finishButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void amountTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountTextFieldFocusLost
        amountTextField.requestFocus();
    }//GEN-LAST:event_amountTextFieldFocusLost

    private void finishButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_finishButtonMouseClicked
        _finish();
    }//GEN-LAST:event_finishButtonMouseClicked

    private void amountTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this._finish();
        }
    }//GEN-LAST:event_amountTextFieldKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amountLabel;
    private javax.swing.JTextField amountTextField;
    private javax.swing.JLabel changeLabel;
    private javax.swing.JButton finishButton;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JTextField totalTextField;
    // End of variables declaration//GEN-END:variables
}
