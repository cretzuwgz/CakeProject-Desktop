package ro.cakeproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public abstract class ConnectionBase {
    
    HttpURLConnection connection;
    InputStream isr;
    OutputStreamWriter request;
    ByteArrayOutputStream baos;
    String response;
    
    public abstract String getResponseFromURL(String link) throws IOException;
    
    public abstract String getResponseFromURL(String link, String parameters) throws IOException;
}
