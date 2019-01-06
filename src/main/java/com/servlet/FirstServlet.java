package com.servlet;

import com.http.Request;
import com.http.Response;
import com.http.Servlet;

import java.io.IOException;

/**
 * @author syz
 * @date 2019-01-06 18:47
 */
public class FirstServlet extends Servlet {
    public void doGet(Request request, Response response) {
        doPost(request,response);
    }

    public void doPost(Request request, Response response) {
        try {
            response.write("welcome to first page");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
