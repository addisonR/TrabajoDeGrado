
package modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class datosbitacora { 
    private bdatos bd;
    private Usuario usu;    
    String usuario;
    PreparedStatement cs; 
    Date date = new Date();
    Date hora = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");

    public datosbitacora(bdatos bd, Usuario usu) {
        this.bd = bd;
        this.usu = usu;
    }    
    
    public void cargarbitacora(String movimiento,String razon){
        Connection acceso=bd.getCn();
        java.sql.Date date2 = new java.sql.Date(date.getTime());
        java.sql.Time hora2 = new java.sql.Time(hora.getTime());
        usuario=usu.getUsuario();
        try {            
            cs = acceso.prepareStatement("INSERT INTO bitacora(nomuser,movimiento,fecha,hora,razon)VALUES(?,?,?,?,?)");
            cs.setString(1,usuario);
            cs.setString(2,movimiento);
            cs.setString(3,String.valueOf(date2));
            cs.setString(4,String.valueOf(hora2));
            cs.setString(5,razon);
            cs.executeUpdate();   
        } 
        catch (SQLException ex){   
            System.err.println(ex);
        }
    }
}
