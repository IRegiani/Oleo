/*
Responsável por conectar ao bnaco de dados e enviar
 */
package br.com.facamp;

import com.firebase.client.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader {
     
    private String baseURL;
    private String data;
    private int updateInterval;    
    private String user;
    private String password;
    private Reader rd;
     /**
     * @param updateInterval determines the upload frequency in the bank in minutes
     * @param user User account, if necessary to authenticate
     * @param password Password, if necessary to authenticate
     * @param baseURL The URL from the bank, or the path
     */    
    public Loader(int updateInterval, String user, String password, String baseURL) {
        this.updateInterval = updateInterval;
        this.user = user;
        this.password = password;
        this.baseURL = baseURL;
        try {
            checkIfBankIsValid();
        } catch (Exception ex) {
           System.out.println("Não foi possível conectar ao banco");
           System.exit(1);
        }
    }
     /**
     * @param updateInterval determines the upload frequency in the bank in minutes
     * @param user User account, if necessary to authenticate
     * @param password Password, if necessary to authenticate
     * @param baseURL The URL from the bank, or the path
     */
    public Loader(int updateInterval, String baseURL) {
        this.updateInterval = updateInterval;
        this.user = null;
        this.password = null;
        this.baseURL = baseURL;
        try {
            checkIfBankIsValid();
        } catch (Exception ex) {
           System.out.println("Não foi possível conectar ao banco");
           System.exit(2);
        }
    }
        
    //verificar conexão com banco, senão volta erro
    
    private void checkIfBankIsValid() throws Exception{
        
    }
    
    
    
    public String getPreviousUploadTime(){
        return null;
    }
    
    public void receiveReader(Reader rd) {
       this.rd = rd;
    }
    
    public void start(){
        new Thread() {
            @Override
            public void run() {
                try {
                    //https://javastreets.com/blog/mule4-firebase-connector-demo.html
                    //https://firebase.google.com/docs/admin/setup
                    //https://github.com/bane73/firebase4j

                    Thread.sleep(1000*60*updateInterval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    private void prepareJSON(){
        
    }
    
}
