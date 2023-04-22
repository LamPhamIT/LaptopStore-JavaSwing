/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.controller.admin;

import com.shinntl.laptopstore.constant.SystemConstant;
import com.shinntl.laptopstore.dao.IBrandDAO;
import com.shinntl.laptopstore.dao.IProductDAO;
import com.shinntl.laptopstore.dao.impl.BrandDAO;
import com.shinntl.laptopstore.dao.impl.ProductDAO;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.utils.HashTableUtil;
import com.shinntl.laptopstore.views.components.ProductInputForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author shinn
 */
public class ProductController implements ActionListener {

    private ProductInputForm productInputForm;
    private IProductDAO productDAO;
    private IBrandDAO brandDAO;

    public ProductController(ProductInputForm productInputForm) {
        this.productInputForm = productInputForm;
        productDAO = new ProductDAO();
        brandDAO = new BrandDAO();
        displayProduct();
    }

    private void displayProduct() {
        if (productInputForm.getActionOfForm().equals(SystemConstant.MODIFY)) {
            Long id = (Long) HashTableUtil.newInstance().get("IDPRODUCT");
            Product product = productDAO.findByID(id);
            productInputForm.getNameTf().setText(product.getName());
            productInputForm.getDescripTf().setText(product.getDescrip());
            productInputForm.getPriceTf().setText(product.getPrice().toString());
            productInputForm.getAmountTf().setText(product.getAmount().toString());

            HashTableUtil.newInstance().put("FileToUpdate", product.getImageData());
        }
    }

    private void actionOfChoose() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("image", "jpg", "png");
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setMultiSelectionEnabled(false);

        int result = fileChooser.showDialog(productInputForm, "Choose");
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            HashTableUtil.newInstance().put("FileToSave", file);
        }
    }

    private Product wrapProduct() {
        if (productInputForm != null) {
            Product product = new Product();
            String name = productInputForm.getNameTf().getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(productInputForm, "Name must not empty");
                return null;
            }
            product.setName(name);
            String descrip = productInputForm.getDescripTf().getText();
            product.setDescrip(descrip);
            Long price;
            try {
                price = Long.parseLong(productInputForm.getPriceTf().getText());
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productInputForm, "Price error");
                return null;
            }
            product.setPrice(price);
            Integer amount;
            try {
                amount = Integer.parseInt(productInputForm.getAmountTf().getText());
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productInputForm, "Amount error");
                return null;
            }
            product.setAmount(amount);
            String nameBrand = (String) productInputForm.getBrandCb().getSelectedItem();
            product.setBrand(brandDAO.findByName(nameBrand));
            File file = (File) HashTableUtil.newInstance().get("FileToSave");
            byte[] imageData;

            if (productInputForm.getActionOfForm().equalsIgnoreCase(SystemConstant.INSERT)) {
                imageData = new byte[(int) file.length()];
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(file);
                    inputStream.read(imageData);
                } catch (Exception e) {
                    System.out.println(" cast File to Byte");
                    return null;
                } finally {
                    HashTableUtil.newInstance().remove("FileToSave");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(ProductController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                }
                product.setImageData(imageData);
                return product;
            }
            imageData = (byte[]) HashTableUtil.newInstance().get("FileToUpdate");
            product.setImageData(imageData);
            return product;
        }
        return null;
    }

    private void actionOfConfirm() {
        Product product = wrapProduct();
        if (product != null) {
            if (productInputForm.getActionOfForm().equals(SystemConstant.INSERT)) {
    
                Long id = productDAO.insert(product);
                if(id!= null) {
                    JOptionPane.showMessageDialog(productInputForm, "Insert sucessful");
                }
            } else {
                Long id = (Long) HashTableUtil.newInstance().get("IDPRODUCT");
                product.setId(id);
                boolean result = productDAO.update(product);
                if (result == false) {
                    JOptionPane.showMessageDialog(productInputForm, "Update fail");
                } else {
                    JOptionPane.showMessageDialog(productInputForm, "Update sucessful");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equalsIgnoreCase("Choose")) {
            actionOfChoose();
        } else if (command.equalsIgnoreCase("Confirm")) {
            actionOfConfirm();
        } else if (command.equalsIgnoreCase("Back to home")) {
            productInputForm.setVisible(false);
            productInputForm.dispose();
        }
    }

}
