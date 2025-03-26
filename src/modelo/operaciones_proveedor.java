
package modelo;

import com.mysql.jdbc.StringUtils;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.*;

public class operaciones_proveedor implements KeyListener{
    private bdatos bd;
    private proveedor provee;
    private datosbitacora dtbitacora;
    private listaproveedores lisprovee;

    public operaciones_proveedor(bdatos bd, proveedor provee,datosbitacora dtbitacora,listaproveedores lisprovee) {
        this.bd = bd;
        this.dtbitacora=dtbitacora;
        this.provee = provee;
        this.lisprovee=lisprovee;        
        this.lisprovee.filtroproveedores.addKeyListener(this);
    }
    
    PreparedStatement cs;
    ///////////////////////////////////insertar usuario///////////////////////////////////////
    public void insertproveedor(String proveedor){
        Connection acceso=bd.getCn();
        String sql="SELECT rif_proveedor,nombre_proveedor,dir_proveedor,tel_proveedor,contacto,tel_contacto"
                + " FROM proveedores where rif_proveedor like '%"+proveedor+"%'";
        Statement st;
        ResultSet rs;
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                modificarproveedor(proveedor);
            }
            else{
            cs = acceso.prepareStatement("INSERT INTO proveedores(rif_proveedor,nombre_proveedor,"
                    + "dir_proveedor,tel_proveedor,contacto,tel_contacto,status_proveedor)VALUES(?,?,?,?,?,?,?)");
            cs.setString(1,provee.getRifproveedor());
            cs.setString(2,provee.getNombreproveedor());
            cs.setString(3,provee.getDireccion());
            cs.setString(4,provee.getTelefono());
            cs.setString(5,provee.getContacto());
            cs.setString(6,provee.getTelcontacto());
            cs.setString(7, "1");
            int numf=cs.executeUpdate();
            if(numf>0){
                JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al registrar");
            }
            dtbitacora.cargarbitacora("Registro un proveedor", "sistema");
            }
        }
        catch (SQLException ex){   
            System.err.println(ex);
        }
    }
    
    public void consultaproveedor(){
        Connection acceso=bd.getCn();
        Statement st;
        ResultSet rs;
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel modelo=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};
        lisprovee.tabproveedores.setModel(modelo);
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT rif_proveedor,nombre_proveedor,dir_proveedor,"
                    + "tel_proveedor,contacto,tel_contacto FROM proveedores");          
            ResultSetMetaData rsmd= rs.getMetaData();
            int cantidadcol= rsmd.getColumnCount();
            modelo.addColumn("Rif");
            modelo.addColumn("Nombre");
            modelo.addColumn("Direccion");
            modelo.addColumn("Telefono");
            modelo.addColumn("Contacto");
            modelo.addColumn("Telefono contacto");
            while (rs.next()){ 
                Object[] fila= new Object[cantidadcol];
                for (int i = 0; i < cantidadcol; i++) {
                    fila[i]=rs.getObject(i+1);
                }
                int column;
                column=modelo.getColumnCount();
                for (int n = 0; n < column; n++) {
                    lisprovee.tabproveedores.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
                modelo.addRow(fila);
            }            
        } catch (Exception e) {
        }
    }
    
    public void modificarproveedor(String proveedor){
        Connection acceso=bd.getCn();
        try {
            cs = acceso.prepareStatement("UPDATE proveedores SET rif_proveedor=?"
                    + ",nombre_proveedor=?,dir_proveedor=?,tel_proveedor=?,contacto=?,tel_contacto=?"
                    + "where rif_proveedor='"+proveedor+"'");
            cs.setString(1,provee.getRifproveedor());
            cs.setString(2,provee.getNombreproveedor());
            cs.setString(3,provee.getDireccion());
            cs.setString(4,provee.getTelefono());
            cs.setString(5,provee.getContacto());
            cs.setString(6,provee.getTelcontacto());
            int numf=cs.executeUpdate();
            if(numf>0){
                JOptionPane.showMessageDialog(null, "Proveedor Actualizado");
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
            
        } catch (SQLException ex){   
            System.err.println(ex);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        String descrip=lisprovee.filtroproveedores.getText();
        Connection acceso=bd.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel modelo=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};        
        lisprovee.tabproveedores.setModel(modelo);
        modelo.setColumnIdentifiers(new Object[]{"Rif","Nombre","Direccion","Telefono","Contacto","Telefono contacto"});
        String sql="SELECT rif_proveedor,nombre_proveedor,dir_proveedor,tel_proveedor,contacto,tel_contacto"
                + " FROM proveedores where nombre_proveedor like '%"+descrip+"%'";
        Statement st;
        ResultSet rs;
        try {
            int filas=modelo.getRowCount();
            for (int n = 0; n < filas; n++) {
                modelo.removeRow(0);
            }
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                modelo.addRow(new Object[]{rs.getString("rif_proveedor"),rs.getString("nombre_proveedor"),
                    rs.getString("dir_proveedor"),rs.getString("tel_proveedor"),rs.getString("contacto"),
                    rs.getString("tel_contacto")});
                int column;
                column=modelo.getColumnCount();
                for (int n = 0; n < column; n++) {
                    lisprovee.tabproveedores.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            }
        } 
        catch (SQLException ex){   
            System.err.println(ex);
        }    
        }        
}
