package ro.cakeproject.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtil extends ConnectionBase {
    
    @Override
    public String getResponseFromURL(String link) throws IOException {
    
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        isr = new BufferedInputStream(connection.getInputStream());
        byte[] bytes = new byte[10000];
        baos = new ByteArrayOutputStream(10000);
        while (true) {
            int numRead = isr.read(bytes);
            if (numRead == -1)
                break;
            baos.write(bytes, 0, numRead);
        }
        
        response = new String(baos.toByteArray(), "UTF-8");
        isr.close();
        connection.disconnect();
        
        return response;
    }
    
    @Override
    public String getResponseFromURL(String link, String parameters) throws IOException {
    
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        
        request = new OutputStreamWriter(connection.getOutputStream());
        request.write(parameters);
        request.flush();
        request.close();
        
        isr = new BufferedInputStream(connection.getInputStream());
        byte[] bytes = new byte[10000];
        baos = new ByteArrayOutputStream(10000);
        while (true) {
            int numRead = isr.read(bytes);
            if (numRead == -1)
                break;
            baos.write(bytes, 0, numRead);
        }
        
        response = new String(baos.toByteArray(), "UTF-8");
        isr.close();
        connection.disconnect();
        
        return response;
    }
    
}
