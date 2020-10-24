package com.qf.web;

import com.qf.domain.Contact;
import com.qf.service.ContactService;
import com.qf.service.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
//分页查找
@WebServlet("/query_contact_page")
public class QueryContactPageServlet extends HttpServlet {
    private ContactService service = new ContactServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1、接受数据
        int currentPage=1;
        String strCurrentPage = request.getParameter("currentPage");
        if(strCurrentPage!=null){
            currentPage=Integer.parseInt(strCurrentPage);
        }
        int pageSize=10;
        String strpageSize = request.getParameter("pageSize");
        if(strpageSize!=null){
            pageSize = Integer.parseInt(strpageSize);

        }
        //2、处理数据

        List<Contact> contacts = service.queryAll(currentPage, pageSize);
        int pageCount = service.queryPageCount(pageSize);
        // 如果当前页码为1-5的话，10个按钮当中和第1个，应该就是1
        // 如果当前页码为: pageCount ~ pageCount-4的话，最后一个按钮，应该是pageCount
        // 其余的情况，第1个按钮 = currentPage - 5
        //           最后按钮 = current + 4

        // 1. 如果当前页码为1-5的情况，那么首页就为1
        int begin = 1;
        if (currentPage > 5) {
            // 2. 如果当前页码大于5的情况，那么首页应该: 当前页 - 5
            begin = currentPage - 5;
        }

        // 3. 如果当前页码为总页码 ~ 总页码-4的情况，那么末页就为总页码
        int end = pageCount;
        if (currentPage < pageCount - 4) {
            // 4. 如果末页为3以外的情况，那么是当前页码+4
            end = currentPage + 4;
        }

        // 5. 如果首页为5以内，并且begin ~ end不足10个，那么end=begin+9
        if (begin <= 5 && end - begin < 10) {
            end = begin + 9;

            // 6. 如果end超出范围，那么end就该为总页码
            if (end > pageCount) {
                end = pageCount;
            }
        }

        // 7. 如果末页为最后5页以内，并且begin ~ end不足10个，那么begin = end -9
        if (end == pageCount && end - begin < 10) {
            begin = end - 9;

            // 8. 如果计算后的begin比1小的情况，那么再把begin设置为1
            if (begin < 1) {
                begin = 1;
            }
        }
        //3、响应数据
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageSize",pageSize);
        request.setAttribute("begin",begin);
        request.setAttribute("end",end);
        request.setAttribute("abc",contacts);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
