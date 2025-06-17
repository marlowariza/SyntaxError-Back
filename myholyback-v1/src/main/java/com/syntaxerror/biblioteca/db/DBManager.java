package com.syntaxerror.biblioteca.db;

import com.syntaxerror.biblioteca.db.util.Cifrado;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";
   
    private Connection conexion;
    private String driver;
    private String tipo_de_driver;
    private String base_de_datos;
    private String nombre_de_host;
    private String puerto;
    private String usuario;
    private String contraseña;
    private static DBManager dbManager = null;
    
    private DBManager(){
        //para no usar new fuera de la clase
    }
    
    public static DBManager getInstance(){
        if (DBManager.dbManager == null){
            DBManager.createInstance();
        }
        return DBManager.dbManager;
    }
    
    private static void createInstance() {
        if (DBManager.dbManager == null){
            DBManager.dbManager = new DBManager();
            DBManager.dbManager.leer_archivo_de_propiedades();
        }
    }


    private void leer_archivo_de_propiedades() {
        try {
            Properties properties = new Properties();
            String nmArchivoConf = "/" + ARCHIVO_CONFIGURACION;
            properties.load(this.getClass().getResourceAsStream(nmArchivoConf));
            this.driver = properties.getProperty("driver");
            this.tipo_de_driver = properties.getProperty("tipo_de_driver");
            this.base_de_datos = properties.getProperty("base_de_datos");
            this.nombre_de_host = properties.getProperty("nombre_de_host");
            this.puerto = properties.getProperty("puerto");
            this.usuario = properties.getProperty("usuario");
            this.contraseña = properties.getProperty("contrasenha");
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection(){
        try {
            Class.forName(this.driver);
            this.conexion = DriverManager.getConnection(this.getURL(), this.usuario, Cifrado.descifrarMD5(this.contraseña));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conexion;
    }
    
    private String getURL(){
        String url = this.tipo_de_driver.concat("://");
        url = url.concat(this.nombre_de_host);
        url = url.concat(":");
        url = url.concat(this.puerto);
        url = url.concat("/");
        url = url.concat(this.base_de_datos);
        url = url.concat("?useSSL=false");
        return url;
    }
}
