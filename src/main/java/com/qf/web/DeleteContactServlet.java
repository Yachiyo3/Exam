package com.qf.web;

import com.qf.service.ContactService;
import com.qf.service.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//删除
@WebServlet("/query_delete")
public class DeleteContactServlet extends HttpServlet {
    private ContactService service=new ContactServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1、接受数据
        String contactId = request.getParameter("id");
        //2、处理数据
        service.deleteById(contactId);
        // 3. 响应数据

        // 告诉用户删除成功 && 让用户看一下

        // 跳转到query_contact
        // 使用哪种跳转技术？
        // 优先考虑重定向，如果重定向搞不定，再使用转发

        // 在使用转发时，必须写斜线
        // 在使用重定向时，必须不能写斜线
        response.sendRedirect("query_contact_page");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
