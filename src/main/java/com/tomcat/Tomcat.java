package com.tomcat;

import com.http.Request;
import com.http.Response;
import com.http.Servlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author syz
 * @date 2019-01-06 18:46
 */
public class Tomcat {
    private ServerSocket serverSocket;
    private int port = 8080;
    private Properties webxml = new Properties();
    private Map<Pattern,Class<?>> servletMapping = new HashMap<Pattern, Class<?>>();
    private String web_inf = this.getClass().getResource("/").getPath();//path不以’/'开头时，默认是从此类所在的包下取资源； //path  以’/'开头时，则是从ClassPath根下获取；

    public Tomcat(){

    }
    public Tomcat(int port){
        this.port = port;

    }
    public void init() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(web_inf +"web.properties");
            webxml.load(fis);
            for (Object obj: webxml.keySet()) {
                String key = obj.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$","");
                    String className = webxml.getProperty(servletName + ".className");
                    String url = webxml.getProperty(key);
                    Class<?> servletClass = Class.forName(className);
                    Pattern pattern = Pattern.compile(url);
                    servletMapping.put(pattern,servletClass);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void process (Socket client) {
        InputStream is = null;
        OutputStream os = null;
       try {
            is = client.getInputStream();
            os = client.getOutputStream();
           Request request = new Request(is);
           Response response = new Response(os);
           boolean isPattern = false;
           String url = request.getUrl();
           for (Map.Entry<Pattern,Class<?>> entry:servletMapping.entrySet()) {
               if (entry.getKey().matcher(url).matches()) {
                   Servlet servlet = (Servlet)entry.getValue().newInstance();
                   servlet.servive(request,response);
                   isPattern = true;
                   break;
               }

           }
           if (!isPattern) {
               response.write("404 - Noy Found");
           }
       }catch (Exception e) {
           e.printStackTrace();
       }finally{
           try {
               is.close();
               os.close();
               client.close();
           }catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
    public void start (){
        init();
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Tomcat 启动，监听端口是 ： "+ this.port);
        }catch (Exception e) {
            System.out.println("Tomcat 启动失败" +e.getMessage());
            return ;
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                process(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Tomcat().start();
    }
}
