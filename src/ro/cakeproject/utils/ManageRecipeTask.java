package ro.cakeproject.utils;

import java.io.IOException;

import javax.swing.SwingWorker;

import ro.cakeproject.MainApp;

public class ManageRecipeTask extends SwingWorker<String, Void> {
    
    private ConnectionUtil connection = new ConnectionUtil();
    private String url;
    private String params;
    private MainApp app;
    
    public ManageRecipeTask(String url, String params, MainApp app) {
    
        this.url = url;
        this.params = params;
        this.app = app;
    }
    
    @Override
    protected String doInBackground() throws Exception {
    
        try {
            String response = connection.getResponseFromURL(url, params);
            System.out.println(response);
        } catch (IOException e) {
            System.out.println("A aparut o eroare!");
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    protected void done() {
    
        app.updateData();
    }
}
