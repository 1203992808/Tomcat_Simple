package com.servlet;

import com.http.Request;
import com.http.Response;
import com.http.Servlet;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

import java.io.IOException;

/**
 * @author syz
 * @date 2019-01-06 18:47
 */
public class SecondServlet  extends Servlet {
    public void doGet(Request request, Response response) {
        doPost(request,response);
    }

    public void doPost(Request request, Response response) {
        try {
            response.write("welcome to second page，"+"request method : "+request.getMethod()+" ,request path :  "+ request.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
