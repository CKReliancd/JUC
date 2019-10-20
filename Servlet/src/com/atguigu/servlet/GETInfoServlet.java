package com.atguigu.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GETInfoServlet extends HttpServlet {
    private static final long serialVersionUID=1L;
    public GETInfoServlet(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应内容类型
        response.setContentType("text/html");

        response.getWriter().write("<h1>hahaaha</>");

    }

}
