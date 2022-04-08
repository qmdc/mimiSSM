package com.konan.Listener;

import com.konan.pojo.ProductType;
import com.konan.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("进入监听器");
        //手工从Spring容器中取出productTypeServiceImpl的对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeService");
        List<ProductType> allType = productTypeService.getAllType();
        System.out.println(allType);
        //放入全局应用作用域中，供新增页面，修改页面，前台的查询功能提供全部商品类别集合
        sce.getServletContext().setAttribute("typeList",allType);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
