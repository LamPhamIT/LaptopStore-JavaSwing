package com.shinntl.laptopstore.controller.user;

import com.shinntl.laptopstore.security.Encryption;
import com.shinntl.laptopstore.constant.SystemConstant;
import com.shinntl.laptopstore.dao.ICustomerDAO;
import com.shinntl.laptopstore.dao.IUserDAO;
import com.shinntl.laptopstore.dao.impl.CustomerDAO;
import com.shinntl.laptopstore.dao.impl.UserDAO;
import com.shinntl.laptopstore.model.Customer;
import com.shinntl.laptopstore.model.Role;
import com.shinntl.laptopstore.model.User;
import com.shinntl.laptopstore.utils.HashTableUtil;
import com.shinntl.laptopstore.utils.StringUtil;
import com.shinntl.laptopstore.views.LoginJFrame;
import com.shinntl.laptopstore.views.user.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {

    private LoginJFrame loginJFrame;
    private IUserDAO userDAO;
    private ICustomerDAO customerDAO;

    public LoginController(LoginJFrame loginJFrame) {
        this.loginJFrame = loginJFrame;
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
    }

    public void actionLogin() {
        String userName = loginJFrame.getUserNameTf().getText();
        if (userName.equals("")) {
            JOptionPane.showMessageDialog(loginJFrame, "Enter username!");
            return;
        }
        String password = loginJFrame.getPasswordPwf().getText();
        if (password.equals("")) {
            JOptionPane.showMessageDialog(loginJFrame, "Enter password!");
            return;
        }
        User user = userDAO.findByUsername(userName);
        if (user == null) {
            JOptionPane.showMessageDialog(loginJFrame, "Username wrong!");
            return;
        } else {
            String encryptedPassword = Encryption.passwordEncryption(password);
            if (user.getPassword().equalsIgnoreCase(encryptedPassword)) {
                Customer customer = customerDAO.findByUserID(user.getUserID());
                HashTableUtil.newInstance().put(SystemConstant.CUSTOMER_MODEL, customer);
                loginJFrame.setVisible(false);
                loginJFrame.dispose();
                if (user.getRole().getName().equals(SystemConstant.ADMIN)) {
                    com.shinntl.laptopstore.views.admin.HomeJFrame home = new com.shinntl.laptopstore.views.admin.HomeJFrame();
                } else {
                    com.shinntl.laptopstore.views.user.HomeJFrame home = new com.shinntl.laptopstore.views.user.HomeJFrame();
                }

            } else {
                JOptionPane.showMessageDialog(loginJFrame, "Password wrong!");
            }
        }
    }

    public void actionSignUp() {
        Customer customer = new Customer();
        String fullName = loginJFrame.getFullnameTf().getText();
        if (fullName.equals("")) {
            JOptionPane.showMessageDialog(loginJFrame, "Full name must not empty");
            return;
        }
        customer.setName(fullName);
        String email = loginJFrame.getEmailTf().getText();

        if (!email.equals("")) {
            boolean result = StringUtil.newInstance().isValidEmail(email);
            if (result == false) {
                JOptionPane.showMessageDialog(loginJFrame, "Email invalidate");
                return;
            }
        }
        customer.setEmail(email);
        String address = loginJFrame.getAddressTf().getText();
        customer.setAddress(address);
        String userName = loginJFrame.getUserName2Tf().getText();
        if (userName.equals("")) {
            JOptionPane.showMessageDialog(loginJFrame, "UserName mustn't empty");
            return;
        }
        User user = new User();
        if (StringUtil.newInstance().isValidUserName(userName)) {
            user.setUserName(userName);
        } else {
            JOptionPane.showMessageDialog(loginJFrame, "Username invalidate");
            return;
        }
        String password = loginJFrame.getPassword2Pwf().getText();
        if (password.equals("")) {
            JOptionPane.showMessageDialog(loginJFrame, "Password mustn't empty");
            return;
        }
        if (StringUtil.newInstance().isValidPasswod(password)) {
            password = Encryption.passwordEncryption(password);
            user.setPassword(password);
        } else {
            JOptionPane.showMessageDialog(loginJFrame, "Password invalidate");
            return;
        }
        Role role = new Role();
        role.setRoleID(2L);
        user.setRole(role);
        customer.setUser(user);
        Long userId = userDAO.insert(user);
        user.setUserID(userId);
        customerDAO.insert(customer);
        JOptionPane.showMessageDialog(loginJFrame, "Signup  sucessful");
        HomeJFrame home = new HomeJFrame();
        loginJFrame.setVisible(false);
        loginJFrame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equalsIgnoreCase("Login")) {
            actionLogin();
        } else if (command.equalsIgnoreCase("Sign up")) {
            actionSignUp();
        }

    }

}
