package com.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author syz
 * @date 2019-01-06 18:46
 */
public class Response {
    private OutputStream os;
    public Response(OutputStream os) {
        this.os = os;
    }
    public void write (String content) throws IOException {
        os.write(content.getBytes());
        os.flush();
    }
}
