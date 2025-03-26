
package modelo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.*;


public class inventario implements KeyListener{
    private producto produ;
    private bdatos con;
    private vistainventario inv;
    private datosbitacora dtbitacora;
    PreparedStatement cs;
    
/////////////////////////////contructor//////////////////////////////////

    public inventario(producto produ, bdatos con, vistainventario inv,datosbitacora dtbitacora) {
        this.produ = produ;
        this.dtbitacora=dtbitacora;
        this.con = con;
        this.inv = inv;
        this.inv.txtbusproduc.addKeyListener(this);
    }   
///////////////////////////////////////////////////////////////////////////
    public void consultamod(String descrip){
        Connection acceso=con.getCn();
        String sql="select codigobarra,descripcion,existencia,costo,iva,pvp from "
                + "inventario where descripcion='"+descrip+"'";
        Statement st;
        ResultSet rs=null;
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                produ.setCod_barra(rs.getString(1));
                produ.setDescripcion(rs.getString(2));
                produ.setExistencia(Integer.parseInt(rs.getString(3)));
                produ.setCosto(Float.parseFloat(rs.getString(4)));
                produ.setIva(Float.parseFloat(rs.getString(5)));
                produ.setPvp(Float.parseFloat(rs.getString(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }     
///////////////////////////////////ver inventario///////////////////////////////////////    
    public void consultarinventario(){
        Connection acceso=con.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel modelo=new DefaultTableModel();
        inv.tabinv.setModel(modelo);                
        String sql="SELECT codigobarra,descripcion,existencia,costo,iva,pvp FROM inventario";
        Statement st;
        ResultSet rs=null;
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData rsmd= rs.getMetaData();
            int cantidadcol= rsmd.getColumnCount();
            modelo.addColumn("Codigo de Barra");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Existencia");
            modelo.addColumn("Costo");
            modelo.addColumn("Iva");
            modelo.addColumn("PVP");
            while (rs.next()){ 
                Object[] fila= new Object[cantidadcol];
                for (int i = 0; i < cantidadcol; i++) {
                    fila[i]=rs.getObject(i+1);
                }
                int column;
                column=modelo.getColumnCount();
                for (int n = 0; n < column; n++) {
                    inv.tabinv.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
                modelo.addRow(fila);
            }            
        } 
        catch (SQLException ex) {
            System.out.println(ex.toString());              
        }
    }
        
///////////////////////////////////insertar producto///////////////////////////////////////
    public void insertproducto(){
        Connection acceso=con.getCn();        
        try {
            cs = acceso.prepareStatement("INSERT INTO inventario(codigobarra,descripcion,"
                    + "existencia,costo,iva,pvp)VALUES(?,?,?,?,?,?)");
            cs.setString(1,produ.getCod_barra());
            cs.setString(2,produ.getDescripcion());
            cs.setString(3,Integer.toString(produ.getExistencia()));
            cs.setString(4,Float.toString(produ.getCosto()));
            cs.setString(5,Float.toString(produ.getIva()));
            cs.setString(6,Float.toString(produ.getPvp()));
            int numf=cs.executeUpdate();
            if(numf>0){
                JOptionPane.showMessageDialog(null, "Producto nuevo Registrado");
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al registrar");
            }            
        } catch (SQLException ex){   
            System.err.println(ex);
        }
        dtbitacora.cargarbitacora("Registro un producto nuevo", "sistema");
    }  
//////////////////////////////////modificar producto/////////////////////////////////
    public void modificarproducto(){   
        Connection acceso=con.getCn();
        try {
            cs = acceso.prepareStatement("UPDATE inventario SET codigobarra=?"
                    + ",descripcion=?,existencia=?,costo=?,iva=?,pvp=?"
                    + "where codigobarra='"+produ.getCod_barra()+"'");
            cs.setString(1, produ.getCod_barra());
            cs.setString(2, produ.getDescripcion());
            cs.setString(3, Integer.toString(produ.getExistencia()));
            cs.setString(4,Float.toString(produ.getCosto()));
            cs.setString(5,Float.toString(produ.getIva()));
            cs.setString(6,Float.toString(produ.getPvp()));
            int numf=cs.executeUpdate();
            if(numf>0){
                JOptionPane.showMessageDialog(null, "Producto Actualizado");
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al registrado");
            }
            
        } catch (SQLException ex){   
            System.err.println(ex);
        }
    }

/////////////////////////////////////////Bucar producto////////////////////////////////////
    @Override
    public void keyPressed(KeyEvent e) {
        
    }   
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        String descrip=inv.txtbusproduc.getText();
        Connection acceso=con.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel modelo=new DefaultTableModel();
        inv.tabinv.setModel(modelo);
        modelo.setColumnIdentifiers(new Object[]{"Codigo de Barra","Descripcion","Existencia","Costo","Iva","PVP"});
        String sql="SELECT codigobarra,descripcion,existencia,costo,iva,pvp"
                + " FROM inventario where descripcion like '%"+descrip+"%'";
        Statement st;
        ResultSet rs=null;
        try {
            int filas=modelo.getRowCount();
            for (int n = 0; n < filas; n++) {
                modelo.removeRow(0);
            }
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                modelo.addRow(new Object[]{rs.getString("codigobarra"),rs.getString("descripcion"),
                rs.getString("existencia"),rs.getString("costo"),rs.getString("iva"),rs.getString("pvp")});
                int column;
                column=modelo.getColumnCount();
                for (int n = 0; n < column; n++) {
                    inv.tabinv.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            }
        } 
        catch (SQLException ex){   
            System.err.println(ex);
        }
    }
}
