/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.util.ArrayList;
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
    private int idCounter = 2;

    public ProductForm() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "P001", "Americano", "Coffee", 18000, 10));
        products.add(new Product(2, "P002", "Pandan Latte", "Coffee", 15000, 8));
        
        
        setTitle("WK. Cuan | Stok Barang");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columnNames = {"Code", "Nama", "Kategori", "Harga", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable drinkTable = new JTable (tableModel);
        
        // Panel form pemesanan
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
        formPanel.add(saveButton);
        
        removeButton = new JButton("Hapus");
        formPanel.add(removeButton);
        
        editButton = new JButton("Edit");
        formPanel.add(editButton);
       


        
        drinkTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedCode = drinkTable.getValueAt(selectedRow, 0).toString();
                String selectedName = drinkTable.getValueAt(selectedRow, 1).toString();
                String selectedCategory = drinkTable.getValueAt(selectedRow, 2).toString();
                String selectedPrice = drinkTable.getValueAt(selectedRow, 3).toString();
                String selectedStock = drinkTable.getValueAt(selectedRow, 4).toString();
                codeField.setText(selectedCode);
                nameField.setText(selectedName);
                categoryField.setSelectedItem(selectedCategory);
                priceField.setText(selectedPrice);
                stockField.setText(selectedStock);
                
            }
        });
        
        saveButton.addActionListener (e ->{
            
            //Try catch untuk mencegah misinput pada harga
            try{
            
            String code = codeField.getText();
            String name = nameField.getText();
            String category = categoryField.getSelectedItem().toString();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            

            //Menambah nama dan harga produk
            Product product = new Product(idCounter++, code, name, category, price, stock);  
            products.add(product);
            System.out.println(idCounter);
            tableModel.addRow(new Object[]{code, name, category, price, stock});
            codeField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
            }
            //exception handling ketika harga berupa string
            catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(drinkTable, "Harga & Stock harus berupa angka!");
            }
        });
        
        removeButton.addActionListener (e ->{
            int selectedRow = drinkTable.getSelectedRow();
            System.out.println("Selected row: " + selectedRow);
            if (selectedRow != -1){
                products.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
            }else {
                JOptionPane.showMessageDialog(drinkTable, "tidak ada yang dipilih");
            }
        });
        
        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow(); //Menggunakan sistem yang mirip dengan remove
            if (selectedRow != -1) {
            String newCode = codeField.getText();
            String newName = nameField.getText(); 
            String newCategory = categoryField.getSelectedItem().toString();
            double newPrice = Double.parseDouble(priceField.getText());
            int newStock = Integer.parseInt(stockField.getText());

            try {
                newPrice = Double.parseDouble(priceField.getText()); // Mencegah error ketika harga bukan berupa angka
            } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(drinkTable, "Harga harus berupa angka!");
            return;
        }

        // Update data di bagian ArrayList dan menggunakan get set di class product
        products.get(selectedRow).setCode(newCode);
        products.get(selectedRow).setName(newName);
        products.get(selectedRow).setCategory(newCategory);
        products.get(selectedRow).setPrice(newPrice);
        products.get(selectedRow).setStock(newStock);
        

        // Update data di bagian Table Model 
        tableModel.setValueAt(newCode, selectedRow, 0);  // Update code
        tableModel.setValueAt(newName, selectedRow, 1);  // Update name
        tableModel.setValueAt(newCategory, selectedRow,2);  // Update category
        tableModel.setValueAt(newPrice, selectedRow, 3);  // Update price
        tableModel.setValueAt(newStock, selectedRow, 4);  // Update stock
        

        // Clear input
        codeField.setText("");
        nameField.setText("");
        categoryField.setSelectedIndex(0);
        priceField.setText("");
        stockField.setText("");
        } else {
            JOptionPane.showMessageDialog(drinkTable, "Pilih produk yang ingin diubah!");
        }
        });
        
        add(new JScrollPane(drinkTable), BorderLayout.CENTER);

        add(formPanel, BorderLayout.SOUTH);
        loadProductData(products);
    }
    private void loadProductData(List<Product> productList) {
        for (Product products : productList) {
            tableModel.addRow(new Object[]{
                products.getCode(), products.getName(), products.getCategory(), products.getPrice(), products.getStock()
            });
        }
    }
}
