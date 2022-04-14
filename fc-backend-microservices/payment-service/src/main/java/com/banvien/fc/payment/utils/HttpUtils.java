package com.banvien.fc.payment.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtils {
    public static String callPostRequest(String uri, Map<String, String> headers, String body) {

        StringBuilder sBuilder = new StringBuilder();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        try {
            for(String key: headers.keySet()){
                post.setHeader(key, headers.get(key));
            }
            post.setEntity(new ByteArrayEntity(body.getBytes("UTF-8")));
            HttpResponse response = httpClient.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == HttpStatus.SC_OK){
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                    sBuilder.append(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sBuilder.toString();
    }
    private static TrustManager[ ] getTrustMgr() {
        TrustManager[ ] certs = new TrustManager[ ] {
                new X509TrustManager() {
                    public X509Certificate[ ] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
                    public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
                }
        };
        return certs;
    }
}
