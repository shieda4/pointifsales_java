/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsales;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shieda
 */
public class MainPanel extends javax.swing.JPanel {

    /**
     * Creates new form MainPanel
     */
    private Transaction transaction;

    DefaultTableModel itemTableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Category", "Price", "Quantity", "Total"}, 0);
    DefaultTableModel productTableModel = new DefaultTableModel(new Object[]{"Product ID", "Desciption", "Category", "Price", "Stock"}, 0);

    JTable itemTable;
    JTable productTable;

    public MainPanel() {
        this.productTable = new JTable(productTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.itemTable = new JTable(itemTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        initComponents();

        itemTable.setRowHeight(30);
        itemTable.getTableHeader().setResizingAllowed(false);
        itemTable.getTableHeader().setEnabled(false);
        itemTable.setRowSelectionAllowed(false);
        centerLeftPanel.add(new JScrollPane(itemTable));

        productTable.setRowHeight(30);
        productTable.getTableHeader().setResizingAllowed(false);
        productTable.getTableHeader().setEnabled(false);
        productTable.setRowSelectionAllowed(true);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerRightCenterPanel.add(new JScrollPane(productTable));

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                _buildProductTable(Product.getProducts(searchTextField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                _buildProductTable(Product.getProducts(searchTextField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                _buildProductTable(Product.getProducts(searchTextField.getText()));
            }

        });

    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        transactionLabel.setText("Transaction ID: " + this.transaction.getId());
        refresh();

    }

    private void _buildItemTable(List<Item> items) {

        while (itemTableModel.getRowCount() > 0) {
            itemTableModel.removeRow(0);
        }

        for (int i = 0; i < items.size(); i++) {
            Item transactionItem = items.get(i);
            itemTableModel.addRow(transactionItem.getTableObject());
        }

    }

    private void _buildProductTable(List<Product> products) {

        while (productTableModel.getRowCount() > 0) {
            productTableModel.removeRow(0);
        }
        for (int i = 0; i < products.size(); i++) {
            Product item = products.get(i);
            productTableModel.addRow(item.getTableObject());
        }

    }

    private void _addItem() {
        int row = productTable.getSelectedRow();
        Product product = new Product(Integer.parseInt(productTableModel.getValueAt(row, 0).toString()));
        
        JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddItemDialog addItem = new AddItemDialog(top, true, this.transaction, product);
        
        addItem.setTitle("ADD ITEM");
        addItem.setSize(400, 300);
        addItem.setLocationRelativeTo(null);
        addItem.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                refresh();
            }
        });
        addItem.setVisible(true);
    }

    private void _checkout() {
        if (this.transaction.getTotal() > 0) {
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            CheckoutDialog checkoutDialog = new CheckoutDialog(top, true, this.transaction);
            checkoutDialog.setTitle("CHECKOUT");
            checkoutDialog.setSize(400, 300);
            checkoutDialog.setLocationRelativeTo(null);
            checkoutDialog.setVisible(true);
        }

    }

    public void refresh() {
        _buildProductTable(Product.getProducts());
        _buildItemTable(Item.getItems(this.transaction));
        totalLabel.setText("TOTAL: " + this.transaction.getTotal());
    }

    public void itemSearchRequestFocus() {
        searchTextField.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        transactionLabel = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        centerLeftPanel = new javax.swing.JPanel();
        centerRightPanel = new javax.swing.JPanel();
        centerRightTopPanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        centerRightCenterPanel = new javax.swing.JPanel();
        centerRightBottomPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        totalLabel = new javax.swing.JLabel();
        finishDiscardPanel = new javax.swing.JPanel();
        finishButton = new javax.swing.JButton();
        discardButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(java.awt.Color.lightGray);
        topPanel.setMinimumSize(new java.awt.Dimension(98, 30));
        topPanel.setLayout(new java.awt.GridLayout(1, 2));

        transactionLabel.setBackground(java.awt.Color.white);
        transactionLabel.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        transactionLabel.setText("jLabel1");
        topPanel.add(transactionLabel);

        add(topPanel, java.awt.BorderLayout.PAGE_START);

        centerPanel.setBackground(java.awt.Color.cyan);
        centerPanel.setLayout(new java.awt.GridLayout(1, 2));

        centerLeftPanel.setLayout(new java.awt.GridLayout(1, 0));
        centerPanel.add(centerLeftPanel);

        centerRightPanel.setLayout(new java.awt.BorderLayout());

        centerRightTopPanel.setLayout(new java.awt.BorderLayout());

        searchTextField.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyPressed(evt);
            }
        });
        centerRightTopPanel.add(searchTextField, java.awt.BorderLayout.CENTER);

        centerRightPanel.add(centerRightTopPanel, java.awt.BorderLayout.PAGE_START);

        centerRightCenterPanel.setLayout(new java.awt.GridLayout(1, 0));
        centerRightPanel.add(centerRightCenterPanel, java.awt.BorderLayout.CENTER);

        centerRightBottomPanel.setLayout(new java.awt.BorderLayout());

        addButton.setBackground(java.awt.Color.cyan);
        addButton.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        addButton.setText("ADD");
        addButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        centerRightBottomPanel.add(addButton, java.awt.BorderLayout.CENTER);

        centerRightPanel.add(centerRightBottomPanel, java.awt.BorderLayout.PAGE_END);

        centerPanel.add(centerRightPanel);

        add(centerPanel, java.awt.BorderLayout.CENTER);

        bottomPanel.setBackground(java.awt.Color.lightGray);
        bottomPanel.setLayout(new java.awt.GridLayout(1, 0));

        totalLabel.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        totalLabel.setText("jLabel1");
        bottomPanel.add(totalLabel);

        finishDiscardPanel.setBackground(java.awt.Color.lightGray);
        finishDiscardPanel.setLayout(new java.awt.GridLayout(1, 0));

        finishButton.setBackground(java.awt.Color.green);
        finishButton.setText("Finish");
        finishButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                finishButtonMouseClicked(evt);
            }
        });
        finishDiscardPanel.add(finishButton);

        discardButton.setBackground(java.awt.Color.red);
        discardButton.setText("Discard");
        discardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                discardButtonMouseClicked(evt);
            }
        });
        finishDiscardPanel.add(discardButton);

        bottomPanel.add(finishDiscardPanel);

        add(bottomPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void discardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_discardButtonMouseClicked
        //Return Stock to Product
        //Delete Item
        Item.getItems(this.transaction).forEach((item) -> item.delete());
        this.transaction.delete();
        setVisible(false);
    }//GEN-LAST:event_discardButtonMouseClicked

    private void finishButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_finishButtonMouseClicked
        _checkout();
    }//GEN-LAST:event_finishButtonMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        _addItem();
    }//GEN-LAST:event_addButtonMouseClicked

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusLost
        searchTextField.requestFocus();
    }//GEN-LAST:event_searchTextFieldFocusLost

    private void searchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            int row = productTable.getSelectedRow();
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    if (row < productTable.getRowCount() - 1) {
                        productTable.setRowSelectionInterval(row + 1, row + 1);
                    }
                    break;
                case KeyEvent.VK_UP:

                    if (row > 0) {
                        productTable.setRowSelectionInterval(row - 1, row - 1);
                    }
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            _addItem();
        }


    }//GEN-LAST:event_searchTextFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel centerLeftPanel;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JPanel centerRightBottomPanel;
    private javax.swing.JPanel centerRightCenterPanel;
    private javax.swing.JPanel centerRightPanel;
    private javax.swing.JPanel centerRightTopPanel;
    private javax.swing.JButton discardButton;
    private javax.swing.JButton finishButton;
    private javax.swing.JPanel finishDiscardPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel transactionLabel;
    // End of variables declaration//GEN-END:variables
}
