package com.http;
/**
 * @author syz
 * @date 2019-01-06 18:46
 */
public abstract  class Servlet {
    public void servive (Request request,Response response){
        if (request.getMethod().equals("Get")) {
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }
    public abstract  void doGet(Request request,Response response);
    public abstract  void doPost(Request request,Response response);

}
