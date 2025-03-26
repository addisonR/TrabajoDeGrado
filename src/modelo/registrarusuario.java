
package modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.*;

public class registrarusuario{
    
    private bdatos bd;
    private empleado emp;
    private Usuario usu;
    private usuarios usuarios;
    private datosbitacora dtbitacora;
    
    public registrarusuario(bdatos bd,empleado emp,Usuario usu,usuarios usuarios,datosbitacora dtbitacora) {
        this.bd=bd;
        this.dtbitacora=dtbitacora;
        this.emp=emp;        
        this.usu=usu;
        this.usuarios=usuarios;
    }

    PreparedStatement cs;
    ResultSet rs=null;
    Statement st;
    
    ///////////////////////////////////insertar usuario///////////////////////////////////////
    public void insertusuario(String usua, String ced){ 
        Connection acceso=bd.getCn();        
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT u.nomuser,v.cedulavend FROM vendedor v inner JOIN usuario u"
                    + " WHERE u.nomuser='"+usua+"' or v.cedulavend='"+ced+"'");
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Usuario o cedula ya existe");                              
            }
            else{
                cs = acceso.prepareStatement("INSERT INTO usuario (nomuser,contrasena,"
                        + "pregunsecreta,respsecreta,id_status,id_tipousu)VALUES(?,?,?,?,?,?)");
                cs.setString(1, usu.getUsuario());
                cs.setString(2, usu.getClave());
                cs.setString(3, usu.getPregunta());
                cs.setString(4, usu.getRespuesta());
                cs.setString(5, "1");
                cs.setString(6,String.valueOf(usu.getTipousu()));
                cs.executeUpdate();
                cs = acceso.prepareStatement("INSERT INTO vendedor(cedulavend,nomuser,nombre,"
                        + "apellido,direccion)VALUES(?,?,?,?,?)");
                cs.setString(1, emp.getTipoducumentoid()+"-"+emp.getCedulavend());
                cs.setString(2, usu.getUsuario());
                cs.setString(3, emp.getNombrevend());
                cs.setString(4, emp.getApellidovend());
                cs.setString(5, emp.getDireccionvend());
                int numf=cs.executeUpdate();
                if(numf>0){
                JOptionPane.showMessageDialog(null, "Usuario Registrado");
                }
                else{
                JOptionPane.showMessageDialog(null, "Error al registrado");
                }
            }            
        } catch (SQLException ex){   
            System.err.println(ex);
        }
        dtbitacora.cargarbitacora("Registro un usuario nuevo", "sistema");        
    }
    ///////////////////////////////////listar usuarios///////////////////////////////////////
    public void listausuarios(){
        Connection acceso=bd.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);        
        DefaultTableModel tab=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};
        usuarios.tabusu.setModel(tab);
        tab.setColumnIdentifiers(new Object[]{"NOMBRE","APELLIDO","USUARIO","STATUS"});        
        String sql="select v.nombre, v.apellido, u.nomuser, tp.descripcion from vendedor v"
                + " inner JOIN usuario u inner JOIN tipo_usuario tp on v.nomuser=u.nomuser and "
                + "u.id_tipousu=tp.id_tipousu";
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){                
                tab.addRow(new Object[]{rs.getString("nombre"),rs.getString("apellido"),
                rs.getString("nomuser"),rs.getString("descripcion")});
                int column;
                column=tab.getColumnCount();
                for (int n = 0; n < column; n++) {
                    usuarios.tabusu.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }    
    ///////////////////////////////////consulta para mandar los datos a modificar///////////////////////////////////////
    public void consultamod(String nomusu){
        Connection acceso=bd.getCn();
        String sql="select cedulavend, nombre, apellido, direccion,"
                + " v.nomuser, contrasena, pregunsecreta, respsecreta, id_tipousu from "
                + "vendedor v inner join usuario u where v.nomuser='"+nomusu+"' and u.nomuser='"+nomusu+"'";        
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                String ced = rs.getString(1);            
                String[] parts = ced.split("-");
                String part1 = parts[0]; 
                String part2 = parts[1]; 
                emp.setTipoducumentoid(part1);
                emp.setCedulavend(part2);
                emp.setNombrevend(rs.getString(2));
                emp.setApellidovend(rs.getString(3));
                emp.setDireccionvend(rs.getString(4));
            ////////////////////////////////////////////////////////////////////
                usu.setUsuario(rs.getString(5));
                usu.setClave(rs.getString(6));
                usu.setPregunta(rs.getString(7));
                usu.setRespuesta(rs.getString(8));
                usu.setTipousu(Integer.parseInt(rs.getString(9)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    } 
    ///////////////////////////////////modificar datos de usuario///////////////////////////
    public void modificarusu(){   
        Connection acceso=bd.getCn();
        try {
            cs = acceso.prepareStatement("UPDATE usuario SET nomuser=?"
                    + ",contrasena=?,pregunsecreta=?,respsecreta=?"
                    + ",id_status=?, id_tipousu=? where nomuser='"+usu.getUsuario()+"'");
            cs.setString(1, usu.getUsuario());
            cs.setString(2, usu.getClave());
            cs.setString(3, usu.getPregunta());
            cs.setString(4, usu.getRespuesta());
            cs.setString(5, "1");
            cs.setString(6, String.valueOf(usu.getTipousu()));            
            int numf=cs.executeUpdate();
            if(numf>0){
                JOptionPane.showMessageDialog(null, "Usuario Actualizado");                
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al registrado");
            }
            
        } catch (SQLException ex){   
            System.err.println(ex);
        }
        dtbitacora.cargarbitacora("Modifico datos de un usuario", "sistema");
    }
    ///////////////////////////////////modificar datos de vendedor///////////////////////////
    public void modificarvendedor(){   
        Connection acceso=bd.getCn();
        try {
            cs = acceso.prepareStatement("UPDATE vendedor SET cedulavend=?,"
                    + "nomuser=?,nombre=?,apellido=?,direccion=?"
                    + " where nomuser='"+usu.getUsuario()+"'");
            cs.setString(1, emp.getCedulavend());
            cs.setString(2, usu.getUsuario());
            cs.setString(3, emp.getNombrevend());
            cs.setString(4, emp.getApellidovend());
            cs.setString(5, emp.getDireccionvend());
            cs.executeUpdate();                       
        } catch (SQLException ex){   
            System.err.println(ex);
        }
    }
}

