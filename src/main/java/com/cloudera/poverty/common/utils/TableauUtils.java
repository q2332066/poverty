package com.cloudera.poverty.common.utils;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TableauUtils {

    //获取票证逻辑
    public static String getTrustedTicket(String wgserver, String user) throws ServletException {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            StringBuilder reqUrl = new StringBuilder();
            reqUrl.append(URLEncoder.encode("username", "UTF-8"));
            reqUrl.append("=");
            reqUrl.append(URLEncoder.encode(user, "UTF-8"));
            reqUrl.append("&target_site=").append(URLEncoder.encode("development", "UTF-8"));
            URL url = new URL(wgserver + "/trusted");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(reqUrl.toString());
            out.flush();

            StringBuilder rsp = new StringBuilder();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                rsp.append(line);
            }
            return rsp.toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {

            }
        }
    }
}
