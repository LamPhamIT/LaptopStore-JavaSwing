    package com.shinntl.laptopstore.main;

import com.shinntl.laptopstore.dao.impl.StatisticDAO;
import com.shinntl.laptopstore.model.Statistic;
import com.shinntl.laptopstore.views.*;
import com.shinntl.laptopstore.views.admin.HomeJFrame;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        HomeJFrame homeJFrame = new HomeJFrame();
//        List<Statistic> list = new StatisticDAO().findByBrand();
//        for (Statistic statistic : list) {
//            System.out.println("" + statistic.getBrandName() + " :" + statistic.getQuantity());
//        }
            LoginJFrame login = new LoginJFrame();
    }
}
