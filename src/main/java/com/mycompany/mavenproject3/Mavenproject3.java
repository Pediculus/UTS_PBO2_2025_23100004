package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mavenproject3 extends JFrame implements Runnable {
    private List<Product> products; // Holds the list of products
    private String bannerText = ""; // Text to scroll across the screen
    private int x; // Current X position of the scrolling text
    private int width; // Width of the window
    private BannerPanel bannerPanel; // Custom JPanel to paint scrolling text
    private JButton addProductButton;

    public Mavenproject3() {
        // Initialize the product list
        products = new ArrayList<>();
        products.add(new Product(1, "P001", "Americano", "Coffee", 18000, 10));
        products.add(new Product(2, "P002", "Pandan Latte", "Coffee", 15000, 8));

        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and add the banner panel
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Set initial banner text based on products
        updateBannerText();

        // Create and add the "Kelola Produk" button
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        bottomPanel.add(addProductButton);
        add(bottomPanel, BorderLayout.SOUTH);


        addProductButton.addActionListener(e -> {
            new ProductForm(products, this).setVisible(true); 
        });

        setVisible(true);

        
        Thread thread = new Thread(this);
        thread.start();
    }

    // update text banner
    public void updateBannerText() {
        StringBuilder sb = new StringBuilder("Menu yang tersedia: ");
        for (Product p : products) {
            sb.append(p.getName()).append(" | ");
        }
        bannerText = sb.toString();
        bannerPanel.repaint(); //refresh
    }

    // Panel for custom text painting
    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(bannerText, x, getHeight() / 2);
        }
    }

    //movement
    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(bannerText);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100); //speed
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        // Start the main window with product list and banner update
        new Mavenproject3();
    }
}
