package com.shinntl.laptopstore.controller.admin;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.shinntl.laptopstore.constant.SystemConstant;
import com.shinntl.laptopstore.dao.IBrandDAO;
import com.shinntl.laptopstore.dao.ICartDAO;
import com.shinntl.laptopstore.dao.IOrderDAO;
import com.shinntl.laptopstore.dao.IProductDAO;
import com.shinntl.laptopstore.dao.impl.BrandDAO;
import com.shinntl.laptopstore.dao.impl.CartDAO;
import com.shinntl.laptopstore.dao.impl.OrderDAO;
import com.shinntl.laptopstore.dao.impl.ProductDAO;
import com.shinntl.laptopstore.model.Order;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.utils.HashTableUtil;
import com.shinntl.laptopstore.views.LoginJFrame;
import com.shinntl.laptopstore.views.admin.HomeJFrame;
import com.shinntl.laptopstore.views.components.ProductInputForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shinn
 */
public class HomeController implements ActionListener, MouseListener {

    private IProductDAO productDAO;
    private ICartDAO cartDAO;
    private IOrderDAO orderDAO;
    private IBrandDAO brandDAO;
    public HomeJFrame homeJFrame;

    public HomeController(HomeJFrame homeJFrame) {
        this.homeJFrame = homeJFrame;
        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
        brandDAO = new BrandDAO();
        setDataTable(productDAO.findAll());
    }

    public void setDataTable(List<Product> list) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getProductTable().setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Amount");
        model.addColumn("Brand");
        for (Product product : list) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getDescrip(),
                product.getPrice(), product.getAmount(), product.getBrand().getName()});
        }
    }

    public void setDataTable(Product product) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getProductTable().setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Amount");
        model.addColumn("Brand");
        model.addRow(new Object[]{product.getId(), product.getName(), product.getDescrip(),
            product.getPrice(), product.getAmount(), product.getBrand().getName()});
    }

    private void actionOfSearch() {
        String filterString = homeJFrame.getSearchTf().getText();
        List<Product> listResult = productDAO.findByNameOrDescrip(filterString);
        setDataTable(listResult);
    }

    private void actionOfDelete() {

        int selectedRow = homeJFrame.getProductTable().getSelectedRow();
        if (selectedRow != -1) {
            int result = JOptionPane.showConfirmDialog(homeJFrame, "Do you want to Log out? We will delete all Order and Cart");
            if (result == JOptionPane.YES_OPTION) {

                Long id = Long.parseLong(homeJFrame.getProductTable().getValueAt(selectedRow, 0).toString());
                cartDAO.deleteByProductID(id);
                orderDAO.deleteByProduct_ID(id);
                boolean result1 = productDAO.delete(id);
                if (result1 == false) {
                    JOptionPane.showMessageDialog(homeJFrame, "Delete fail");
                } else {
                    JOptionPane.showMessageDialog(homeJFrame, "Delete sucessful");
                    setDataTable(productDAO.findAll());
                }
            }

        } else {
            JOptionPane.showMessageDialog(homeJFrame, "Choose row to delete");
        }

    }

    private void setDataOrderTable(List<Order> list) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getOrderTable().setModel(model);
        model.addColumn("OrderID");
        model.addColumn("CustomerID");
        model.addColumn("ProductID");
        model.addColumn("Amount");
        model.addColumn("Total");
        model.addColumn("Status");

        for (Order order : list) {
            model.addRow(new Object[]{order.getOrderId(), order.getCustomer().getId(), order.getProduct().getId(),
                order.getAmount(), order.getAmount() * order.getProduct().getPrice(), order.getStatus().getName()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equalsIgnoreCase("Search")) {
            actionOfSearch();
        } else if (command.equalsIgnoreCase("Update")) {
            boolean result = putProductSelected();
            if (result == true) {
                ProductInputForm productInputForm = new ProductInputForm(SystemConstant.MODIFY);
            } else {
                JOptionPane.showMessageDialog(homeJFrame, "Choose product");
            }
        } else if (command.equalsIgnoreCase("Delete")) {
            actionOfDelete();
        } else if (command.equalsIgnoreCase("Refresh")) {
            setDataTable(productDAO.findAll());
        } else if (command.equalsIgnoreCase("Add product")) {
            ProductInputForm productInputForm = new ProductInputForm(SystemConstant.INSERT);
        } else if (command.equalsIgnoreCase("Print bill")) {
            actionOfPrint();
        }
    }

    public void actionOfLogout() {
        int result = JOptionPane.showConfirmDialog(homeJFrame, "Do you want to Log out?");
        if (result == JOptionPane.YES_OPTION) {
            HashTableUtil.newInstance().getHashMap().clear();
            LoginJFrame login = new LoginJFrame();
            homeJFrame.setVisible(false);
            homeJFrame.dispose();
        }
    }

    private boolean putProductSelected() {
        int selectedRow = homeJFrame.getProductTable().getSelectedRow();
        if (selectedRow != -1) {
            Long id = Long.parseLong(homeJFrame.getProductTable().getValueAt(selectedRow, 0).toString());
            HashTableUtil.newInstance().put("IDPRODUCT", id);
            return true;
        }
        return false;
    }

    private void actionOfPrint() {
        try {
            int selectedRow = homeJFrame.getOrderTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(homeJFrame, "Choose order to print bill");
                return;
            }
            Long id = Long.parseLong(homeJFrame.getOrderTable().getValueAt(selectedRow, 0).toString());
            Order order = orderDAO.findByOrderID(id);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(("Order" + order.getOrderId().toString()) + ".pdf"));
            document.open();
            document.add(new Paragraph("BILL"));
            document.add(new Paragraph("Customer: " + order.getCustomer().getName()));
            document.add(new Paragraph("Product: " + order.getProduct().getName()));
            document.add(new Paragraph("Amount: " + order.getAmount()));
            document.add(new Paragraph("Total: " + (order.getProduct().getPrice() * order.getAmount()) + "$"));
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph("Thank you very much! Have good day"));
            document.close();
            JOptionPane.showMessageDialog(homeJFrame, "Print sucessful");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // String cmd = me.
        if (me.getSource() == homeJFrame.getLogoutLabel()) {
            actionOfLogout();
        } else if (me.getSource() == homeJFrame.getOrderLabel()) {
            setDataOrderTable(orderDAO.findAll());
            homeJFrame.getProductPanel().setVisible(false);
            homeJFrame.getOrderPanel().setVisible(true);

        } else if (me.getSource() == homeJFrame.getHomeLabel()) {

            homeJFrame.getProductPanel().setVisible(true);
            homeJFrame.getOrderPanel().setVisible(false);
        } 
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}
