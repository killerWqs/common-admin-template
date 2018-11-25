package com.killer.demo.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO...
 * 匹配根目录都是""
 * @author wqs
 * @date 2018-11-15 14:22
 */
//@WebServlet(urlPatterns = "")
public class MyServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("exec");
        PrintWriter writer = resp.getWriter();
        writer.println("wa kao");
    }
}
