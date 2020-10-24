package com.qf.web;

import com.qf.domain.Contact;
import com.qf.service.ContactService;
import com.qf.service.ContactServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
//修改
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private ContactService service=new ContactServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、接收数据
        Contact contact = new Contact();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            //request.getParameterMap();是接收的用户修改的参数，然后用parameterMap存储
            //再由contact寻找对应的属性返回给数据库
            BeanUtils.populate(contact,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2、处理数据
        service.update(contact);
        //3、响应数据
        response.sendRedirect("query_contact_page");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
