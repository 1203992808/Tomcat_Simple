package com.http;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author syz
 * @date 2019-01-06 18:46
 */
public class Request {
    private String method;
    private String url;
    public Request(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            String  content = "";
            int len = 0;
            if ((len = is.read(buffer)) > 0) {
                content = new String(buffer,0,len);
            }
            System.out.println(content);
            String line = content.split("\\n")[0];
            String[] strs = line.split("\\s");
            String method = strs[0];
            String url = strs[1].split("\\?")[0];
            this.method = method;
            this.url = url;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
