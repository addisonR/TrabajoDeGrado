
package modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import vista.*;

public class coniniciosesion{
    private Usuario usu;
    private bdatos bd;
    private inicio co;
    private inicio_sesion in;
    private empleado emp;
    private datosbitacora dtbitacora;
    private vistaventa visventa;
    
    public coniniciosesion(Usuario usu, bdatos bd,inicio co,inicio_sesion in,empleado emp,datosbitacora dtbitacora
    ,vistaventa visventa){
        this.dtbitacora=dtbitacora;
        this.usu=usu; 
        this.visventa=visventa;
        this.bd = bd;  
        this.co = co;
        this.in = in;
        this.emp=emp;
    }
    
    public void iniciosesion(){
        String usuario=usu.getUsuario();
        String clave=usu.getClave();        
        String sql="select u.nomuser,u.contrasena,v.cedulavend,id_tipousu from usuario u inner join"
                + " vendedor v where u.nomuser='"+usuario+"'";
        Connection cn=bd.getCn();
        ResultSet rs;
        Statement st;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                String nusu=rs.getString(1);
                String contra=rs.getString(2);
                String cedusu=rs.getString(3);
                int tpusu=Integer.parseInt(rs.getString(4));
                if(nusu.equals(usuario) && contra.equals(clave)){
                    emp.setCedulavend(cedusu);
                    usu.setEstatus(tpusu);
                    JOptionPane.showMessageDialog(null, "Bienvenido");
                    if(tpusu==1){
                        co.setVisible(true);
                        in.dispose();
                        dtbitacora.cargarbitacora("ingreso al sistema", "sistema");
                        visventa.jPanel1.setVisible(false);
                    }
                    else if(tpusu==2){
                        visventa.setVisible(true);
                        in.dispose();
                        visventa.btnsalir.setVisible(false);
                        visventa.btnfacturar.setVisible(false);
                        dtbitacora.cargarbitacora("ingreso al sistema", "sistema");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña erronea");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuario no existe");
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
}