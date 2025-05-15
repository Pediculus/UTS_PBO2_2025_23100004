package com.mycompany.mavenproject3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton refreshBannerButton; // refresh banner
    private int idCounter = 3; 

    private List<Product> products;
    private Mavenproject3 mainWindow; //for main

    // Accept product list and reference to main window
    public ProductForm(List<Product> products, Mavenproject3 mainWindow) {
        this.products = products;
        this.mainWindow = mainWindow; // also for main

        setTitle("WK. Cuan | Stok Barang");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Code", "Nama", "Kategori", "Harga", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        drinkTable = new JTable(tableModel);

        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField(10);
        formPanel.add(codeField);

        formPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField(10);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(10);
        formPanel.add(priceField);

        formPanel.add(new JLabel("Stok Tersedia:"));
        stockField = new JTextField(10);
        formPanel.add(stockField);

        saveButton = new JButton("Simpan");
        removeButton = new JButton("Hapus");
        editButton = new JButton("Edit");
        refreshBannerButton = new JButton("Refresh Banner");
        formPanel.add(saveButton);
        formPanel.add(removeButton);
        formPanel.add(editButton);
        formPanel.add(refreshBannerButton);

        // Table selection listener
        drinkTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                codeField.setText(drinkTable.getValueAt(selectedRow, 0).toString());
                nameField.setText(drinkTable.getValueAt(selectedRow, 1).toString());
                categoryField.setSelectedItem(drinkTable.getValueAt(selectedRow, 2).toString());
                priceField.setText(drinkTable.getValueAt(selectedRow, 3).toString());
                stockField.setText(drinkTable.getValueAt(selectedRow, 4).toString());
            }
        });

        // Add product
        saveButton.addActionListener(e -> {
            try {
                String code = codeField.getText();
                String name = nameField.getText();
                String category = categoryField.getSelectedItem().toString();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                Product product = new Product(idCounter++, code, name, category, price, stock);
                products.add(product);
                tableModel.addRow(new Object[]{code, name, category, price, stock});
                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga & Stock harus berupa angka!");
            }
        });

        // Remove product
        removeButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                products.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus!");
            }
        });

        // Edit product
        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    String newCode = codeField.getText();
                    String newName = nameField.getText();
                    String newCategory = categoryField.getSelectedItem().toString();
                    double newPrice = Double.parseDouble(priceField.getText());
                    int newStock = Integer.parseInt(stockField.getText());

                    Product selectedProduct = products.get(selectedRow);
                    selectedProduct.setCode(newCode);
                    selectedProduct.setName(newName);
                    selectedProduct.setCategory(newCategory);
                    selectedProduct.setPrice(newPrice);
                    selectedProduct.setStock(newStock);

                    // Update table row
                    tableModel.setValueAt(newCode, selectedRow, 0);
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newCategory, selectedRow, 2);
                    tableModel.setValueAt(newPrice, selectedRow, 3);
                    tableModel.setValueAt(newStock, selectedRow, 4);

                    codeField.setText("");
                    nameField.setText("");
                    categoryField.setSelectedIndex(0);
                    priceField.setText("");
                    stockField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Harga & Stock harus berupa angka!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diubah!");
            }
        });

        // Refresh banner manually
        refreshBannerButton.addActionListener(e -> {
            mainWindow.updateBannerText(); 
        });

        add(new JScrollPane(drinkTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        loadProductData(); // Load products into table at start
    }

    private void loadProductData() {
        for (Product p : products) {
            tableModel.addRow(new Object[]{
                p.getCode(), p.getName(), p.getCategory(), p.getPrice(), p.getStock()
            });
        }
    }
}
