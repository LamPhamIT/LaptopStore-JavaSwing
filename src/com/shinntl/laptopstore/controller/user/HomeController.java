package com.shinntl.laptopstore.controller.user;

import com.shinntl.laptopstore.constant.SystemConstant;
import com.shinntl.laptopstore.dao.ICartDAO;
import com.shinntl.laptopstore.dao.IOrderDAO;
import com.shinntl.laptopstore.dao.IProductDAO;
import com.shinntl.laptopstore.dao.impl.CartDAO;
import com.shinntl.laptopstore.dao.impl.OrderDAO;
import com.shinntl.laptopstore.dao.impl.ProductDAO;
import com.shinntl.laptopstore.model.Cart;
import com.shinntl.laptopstore.model.Customer;
import com.shinntl.laptopstore.model.Order;
import com.shinntl.laptopstore.model.Product;
import com.shinntl.laptopstore.model.Status;
import com.shinntl.laptopstore.model.User;
import com.shinntl.laptopstore.utils.HashTableUtil;
import com.shinntl.laptopstore.views.LoginJFrame;
import com.shinntl.laptopstore.views.components.ProductForm;
import com.shinntl.laptopstore.views.user.HomeJFrame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shinn
 */
public class HomeController implements ActionListener, MouseListener {

    private HomeJFrame homeJFrame;
    private ProductForm productForm;
    private IProductDAO productDAO;
    private ICartDAO cartDAO;
    private IOrderDAO orderDAO;
    private List<Product> listProduct;
    private Integer currentPage = 1;
    private Integer maxPage = 1;
    private Integer startItem = 0;

    private Customer customer = ((Customer) HashTableUtil.newInstance().get(SystemConstant.CUSTOMER_MODEL));

    public HomeController(HomeJFrame homeJFrame) {
        this.homeJFrame = homeJFrame;
        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
        listProduct = productDAO.findAll();
        setMaxPage();
        setProduct();
    }

    private void setMaxPage() {
        maxPage = listProduct.size() / 4;
        if (listProduct.size() % 4 != 0) {
            maxPage++;
        }
    }

    private void setProduct() {

        if (currentPage == 1) {
            startItem = 0;
        } else {
            startItem = (currentPage - 1) * 4;
        }
        int count = 0;
        for (int i = startItem; i < listProduct.size(); i++) {
            if (count == 4) {
                break;
            }
            homeJFrame.getListPanel().get(count).setVisible(true);
            homeJFrame.getListName().get(count).setText(listProduct.get(i).getName());
            homeJFrame.getListPrice().get(count).setText(listProduct.get(i).getPrice().toString());

            BufferedImage image = null;
            InputStream in = new ByteArrayInputStream(listProduct.get(i).getImageData());
            try {
                image = ImageIO.read(in);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon imageIcon = new ImageIcon(image);
            Image imageI = imageIcon.getImage().getScaledInstance(homeJFrame.getListImage().get(count).getWidth(), homeJFrame.getListImage().get(count).getHeight(), Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(imageI);
            homeJFrame.getListImage().get(count).setIcon(icon);

            count++;

        }
    }

    private void actionOfPre() {
        if (currentPage == 1) {
            JOptionPane.showMessageDialog(homeJFrame, "Can't show this page!");
            return;
        }
        currentPage--;
        resetPanel();
        setProduct();
    }

    private void actionOfNext() {
        setMaxPage();
        if (currentPage == maxPage) {
            JOptionPane.showMessageDialog(homeJFrame, "Can't show this page");
            return;
        }
        currentPage++;
        resetPanel();
        setProduct();
    }

    private void resetPanel() {
        for (int i = 0; i < homeJFrame.getListPanel().size(); i++) {
            homeJFrame.getListPanel().get(i).setVisible(false);
        }
    }

    private void actionOfSearch() {
        String filter = homeJFrame.getSearchTf().getText();
        listProduct = productDAO.findByNameOrDescrip(filter);
        currentPage = 1;
        resetPanel();
        setMaxPage();
        setProduct();
    }

    private void setDataCartTable(List<Cart> list) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getCartTable().setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Amount");
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getProduct().getId(), list.get(i).getProduct().getName(), list.get(i).getProduct().getDescrip(),
                list.get(i).getProduct().getPrice(), list.get(i).getProduct().getAmount()});
        }
    }

    private void setDataCartTable(Cart cart) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getCartTable().setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Amount");
        model.addRow(new Object[]{cart.getProduct().getId(), cart.getProduct().getName(), cart.getProduct().getDescrip(),
            cart.getProduct().getPrice(), cart.getProduct().getAmount()});

    }

    private void actionOfAddToCart(User user, Product product) {
        //User user = ((Customer) MyHashTable.newInstance().getHashMap().get(SystemConstant.CUSTOMER_MODEL)).getUser();
        Cart cart = new Cart(user, product);
        cartDAO.insert(cart);
        JOptionPane.showMessageDialog(homeJFrame, "Add to cart sucessful");
    }

    public void actionOfRemove() {
        int selectedRow = homeJFrame.getCartTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(homeJFrame, "Choose product to remove");
            return;
        }
        Long id = Long.valueOf(homeJFrame.getCartTable().getValueAt(selectedRow, 0).toString());
        User user = customer.getUser();
        boolean result = cartDAO.deleteByProductIDAndUserID(id, user.getUserID());
        if (result == false) {
            JOptionPane.showMessageDialog(homeJFrame, "Remove fail");
            return;
        }
        JOptionPane.showMessageDialog(homeJFrame, "Remove sucessful");
        setDataCartTable(cartDAO.findByUserID(user.getUserID()));
    }

    private void actionOfBuyInCart() {
        int selectedRow = homeJFrame.getCartTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(homeJFrame, "Choose product to remove");
            return;
        }
        Integer amount = null;
        try {
            amount = Integer.parseInt(homeJFrame.getAmountInputTf().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(homeJFrame, "Amount mustn't empty");
            return;
        }

        if (amount <= 0) {
            JOptionPane.showMessageDialog(homeJFrame, "Amount invalid");
            return;
        }

        Long id = Long.valueOf(homeJFrame.getCartTable().getValueAt(selectedRow, 0).toString());
        Product product = productDAO.findByID(id);
        if (product.getAmount() < amount) {
            JOptionPane.showMessageDialog(homeJFrame, "So luong con lai khong du");
            return;
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        Status status = new Status();
        status.setId(2L);
        order.setAmount(amount);
        order.setStatus(status);
        orderDAO.insert(order);

        JOptionPane.showMessageDialog(homeJFrame, "Buy sucessful");
        cartDAO.deleteByProductIDAndUserID(id, customer.getUser().getUserID());
        product.setAmount(product.getAmount() - order.getAmount());
        productDAO.updateAmount(product);
        setDataCartTable(cartDAO.findByUserID(customer.getUser().getUserID()));

    }

    private void actionOfLogout() {
        int result = JOptionPane.showConfirmDialog(homeJFrame, "Do you want to Log out?");
        if (result == JOptionPane.YES_OPTION) {
            HashTableUtil.newInstance().getHashMap().clear();
            LoginJFrame login = new LoginJFrame();
            homeJFrame.setVisible(false);
            homeJFrame.dispose();
        }

    }

    private void setDataOrderTable(List<Order> list) {
        DefaultTableModel model = new DefaultTableModel();
        homeJFrame.getOrderTable().setModel(model);
        model.addColumn("OrderID");
        model.addColumn("Product");
        model.addColumn("Price");
        model.addColumn("Amount");
        model.addColumn("Total");
        for (Order order : list) {
            model.addRow(new Object[]{order.getOrderId(), order.getProduct().getName(),
                order.getProduct().getPrice(), order.getAmount(), (order.getProduct().getPrice() * order.getAmount())});
        }
    }

    private void actionOfRemoveOrder() {
        int selectedRow = homeJFrame.getOrderTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(homeJFrame, "Choose order");
            return;
        }
        Long id = Long.parseLong(homeJFrame.getOrderTable().getValueAt(selectedRow, 0).toString());
        Order order = orderDAO.findByOrderID(id);
        Product product = productDAO.findByID(order.getProduct().getId());
        product.setAmount(product.getAmount() + order.getAmount());
        orderDAO.deleteByOrderID(id);
        JOptionPane.showMessageDialog(homeJFrame, "Delete sucessful");
        setDataOrderTable(orderDAO.findByCustomerID(customer.getId()));
    }

    private void actionOfUpdateOrder() {
        int selectedRow = homeJFrame.getOrderTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(homeJFrame, "Choose order");
            return;
        }
        Long id = Long.parseLong(homeJFrame.getOrderTable().getValueAt(selectedRow, 0).toString());
        String amountStr = homeJFrame.getAmountOrderTf().getText();
        if (amountStr.equals("")) {
            JOptionPane.showMessageDialog(homeJFrame, "Amount mustn't empty");
            return;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(homeJFrame, "Amount must be number");
            return;
        }

        Order orderUpdate = orderDAO.findByOrderID(id);
        Product productOrder = productDAO.findByID(orderUpdate.getProduct().getId());
        if (productOrder.getAmount() < amount) {
            JOptionPane.showMessageDialog(homeJFrame, "Amount khong du");
            return;
        }
        productOrder.setAmount(productOrder.getAmount() - amount);
        productDAO.updateAmount(productOrder);
        Order order = new Order();
        order.setAmount(amount);
        order.setOrderId(id);
        orderDAO.updateByOrderID(order);
        JOptionPane.showMessageDialog(homeJFrame, "Update sucessful");
        setDataOrderTable(orderDAO.findByCustomerID(customer.getId()));

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equalsIgnoreCase("Previous")) {
            actionOfPre();
        } else if (command.equalsIgnoreCase("Next")) {
            actionOfNext();
        } else if (command.equalsIgnoreCase("Search")) {
            actionOfSearch();
        } else if (command.equalsIgnoreCase("More")) {
            if (ae.getSource() == homeJFrame.getListMoreInfor().get(0)) {
                productForm = new ProductForm(listProduct.get((currentPage - 1) * 4));
            } else if (ae.getSource() == homeJFrame.getListMoreInfor().get(1)) {
                productForm = new ProductForm(listProduct.get((currentPage - 1) * 4 + 1));
            } else if (ae.getSource() == homeJFrame.getListMoreInfor().get(2)) {
                productForm = new ProductForm(listProduct.get((currentPage - 1) * 4 + 2));
            } else if (ae.getSource() == homeJFrame.getListMoreInfor().get(3)) {
                productForm = new ProductForm(listProduct.get((currentPage - 1) * 4 + 3));
            }
        } else if (command.equalsIgnoreCase("Add to cart")) {

            setDataCartTable(cartDAO.findByUserID(customer.getUser().getUserID()));
            if (ae.getSource() == homeJFrame.getListAddToCartBtn().get(0)) {
                actionOfAddToCart(customer.getUser(), listProduct.get((currentPage - 1) * 4));

            } else if (ae.getSource() == homeJFrame.getListAddToCartBtn().get(1)) {
                actionOfAddToCart(customer.getUser(), listProduct.get((currentPage - 1) * 4 + 1));
            } else if (ae.getSource() == homeJFrame.getListAddToCartBtn().get(2)) {
                actionOfAddToCart(customer.getUser(), listProduct.get((currentPage - 1) * 4 + 2));
            } else if (ae.getSource() == homeJFrame.getListAddToCartBtn().get(3)) {
                actionOfAddToCart(customer.getUser(), listProduct.get((currentPage - 1) * 4 + 3));
            }
        } else if (ae.getSource() == homeJFrame.getRemoveCartBtn()) {
            actionOfRemove();
        } else if (command.equals("Buy ")) {
            actionOfBuyInCart();
        } else if (ae.getSource() == homeJFrame.getRemoveOrderBtn()) {
            actionOfRemoveOrder();
        } else if (ae.getSource() == homeJFrame.getUpdateOrderBtn()) {
            actionOfUpdateOrder();
        }

    }

    public void actionOfTable() {
        int selectedRow = homeJFrame.getCartTable().getSelectedRow();
        Long id;
        if (selectedRow != -1) {
            id = Long.valueOf(homeJFrame.getCartTable().getValueAt(selectedRow, 0).toString());

        } else {
            id = Long.valueOf(homeJFrame.getCartTable().getValueAt(0, 0).toString());
        }
        Product product = productDAO.findByID(id);
        homeJFrame.getNameCartLabel().setText(product.getName());
        homeJFrame.getDescripCartTextArea().setText(product.getDescrip());
        homeJFrame.getPriceCartLabel().setText(product.getPrice().toString());
        homeJFrame.getBrandCartLabel().setText(product.getBrand().getName());
        homeJFrame.getAmountCartLabel().setText(product.getAmount().toString());
        BufferedImage image = null;
        InputStream in = new ByteArrayInputStream(product.getImageData());
        try {
            image = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon imageIcon = new ImageIcon(image);
        Image imageI = imageIcon.getImage().getScaledInstance(homeJFrame.getImgCartLabel().getWidth(), homeJFrame.getImgCartLabel().getHeight(), Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(imageI);
        homeJFrame.getImgCartLabel().setIcon(icon);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == homeJFrame.getCartTable()) {
            actionOfTable();
        } else if (me.getSource() == homeJFrame.getLogoutLabel()) {
            actionOfLogout();
        } else if (me.getSource() == homeJFrame.getCartLabel()) {
            setDataCartTable(cartDAO.findByUserID(customer.getUser().getUserID()));
            homeJFrame.getCartPanel().setVisible(true);
            homeJFrame.getHomePanel().setVisible(false);
            homeJFrame.getPurchasedPanel().setVisible(false);
        } else if (me.getSource() == homeJFrame.getHomeLabel()) {
            homeJFrame.getCartPanel().setVisible(false);
            homeJFrame.getHomePanel().setVisible(true);
            homeJFrame.getPurchasedPanel().setVisible(false);
        } else if (me.getSource() == homeJFrame.getOrderLabel()) {
            setDataOrderTable(orderDAO.findByCustomerID(customer.getId()));
            homeJFrame.getPurchasedPanel().setVisible(true);
            homeJFrame.getCartPanel().setVisible(false);
            homeJFrame.getHomePanel().setVisible(false);
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
