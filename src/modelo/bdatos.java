
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;

public class bdatos {
    Connection cn;
    String driver= "com.mysql.jdbc.Driver";
    String user="FARMANET";
    String password="UA0RUS0A";
    String url="jdbc:mysql://localhost:3308/taller";
    
    public bdatos() {
        cn = null;
        try {
            Class.forName(driver);
            cn= DriverManager.getConnection(url,user,password);
           /* if(cn !=null){
                JOptionPane.showMessageDialog(null,"conexion establecida.");
            }*/
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error al conectar"+ e);
        }
    }

    public Connection getCn() {
        return cn;
    }
    
    
}
