/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.controller.user;

import com.shinntl.laptopstore.constant.SystemConstant;
import com.shinntl.laptopstore.dao.ICartDAO;
import com.shinntl.laptopstore.dao.impl.CartDAO;
import com.shinntl.laptopstore.model.Cart;
import com.shinntl.laptopstore.model.Customer;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.utils.HashTableUtil;
import com.shinntl.laptopstore.views.components.ProductForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author shinn
 */
public class ProductController implements ActionListener{

    private ProductForm productForm;
    private ICartDAO cartDAO;
    public ProductController(ProductForm productForm) {
        this.productForm = productForm;
        cartDAO = new CartDAO();
    }
    private void actionOfAddToCart() {
        Product product = productForm.getProduct();
        Customer customer = (Customer) HashTableUtil.newInstance().get(SystemConstant.CUSTOMER_MODEL);
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(customer.getUser());
        cartDAO.insert(cart);
        JOptionPane.showMessageDialog(productForm, "Add to cart sucessful");
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if(command.equalsIgnoreCase("Add to cart")) {
            actionOfAddToCart();
        }
    }
    
}
