
package modelo;

import com.mysql.jdbc.StringUtils;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.*;

public class operaciones_compra implements KeyListener{
    private producto produ;
    private bdatos con;
    private Compra inv;
    private datosfacturacompra dtcompra;
    private datosbitacora dtbitacora;
    PreparedStatement cs;
    int cantidad=0,x=0,nproduc=0;
    double calcosto, caliva, calpvp;
    double totalc,totali,totalpvp;

    public operaciones_compra(producto produ, bdatos con, Compra inv,datosfacturacompra dtcompra,datosbitacora dtbitacora) {
        this.produ = produ;
        this.con = con;
        this.dtbitacora=dtbitacora;
        this.inv = inv;
        this.dtcompra=dtcompra;
        this.inv.txtbuspro.addKeyListener(this); 
        //////////////////////////////////////////////////////////////////////////
        borradato();       
}

    public void limpiar(){
        DefaultTableModel tb = (DefaultTableModel) inv.taborigen.getModel();
        int a = inv.taborigen.getRowCount()-1;
            for (int i = a; i >= 0; i--) {           
                tb.removeRow(tb.getRowCount()-1);
            }
    }
    public void agregarpro(){         
        String codigobarra,descripcion,existencia,costo,pvp,cant="1";
        int fila=inv.taborigen.getSelectedRow();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        int a=0;
        if(fila!=-1){                              
            codigobarra= (String) inv.taborigen.getValueAt(fila, 0);
            descripcion= (String) inv.taborigen.getValueAt(fila, 1);
            for (int i = 0; i < inv.tabdestino.getRowCount(); i++) {
                if(inv.tabdestino.getValueAt(i, 0).toString().equals(codigobarra)){
                    a=1;                                    
                }
            }
            if(a==1){
                JOptionPane.showMessageDialog(null, "Producto ya existe");
            }
            else{
            existencia= (String) inv.taborigen.getValueAt(fila, 2);
            costo= (String) inv.taborigen.getValueAt(fila, 3);
            pvp= (String) inv.taborigen.getValueAt(fila, 5);                
        ///////////////////////////////////////////////////////////////////////////            
            cantidad++;
            cant=String.valueOf(cantidad);
            DefaultTableModel destino = (DefaultTableModel) inv.tabdestino.getModel();
            String filaselement []={codigobarra,descripcion,cant,costo,pvp};
            int column;
                column=destino.getColumnCount();
                for (int n = 0; n < column; n++) {
                    inv.tabdestino.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            destino.addRow(filaselement);
            nproduc=inv.tabdestino.getRowCount();
            }
        }        
        else if(fila==-1){
            JOptionPane.showMessageDialog(null, "No selecciono un producto");
            }
        cantidad=0;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        String descrip=inv.txtbuspro.getText();
        Connection acceso=con.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel modelo=new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        inv.taborigen.setModel(modelo);
        modelo.setColumnIdentifiers(new Object[]{"Codigo de Barra","Descripcion","Existencia","Costo","Iva","PVP"});
        String sql="SELECT codigobarra,descripcion,existencia,costo,iva,pvp"
                + " FROM inventario where descripcion like '%"+descrip+"%'";
        Statement st;
        ResultSet rs;
        if(!StringUtils.isNullOrEmpty(descrip)){
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
                    inv.taborigen.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            }
        } 
        catch (SQLException ex){   
            System.err.println(ex);
        }
    }
        else{
            limpiar();            
        }        
    }
    @Override
    public void keyTyped(KeyEvent e) {
         
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    public void borradato(){
        inv.tabdestino.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int fila2;
            fila2=inv.tabdestino.getSelectedRow();
            if( e.getKeyCode() == KeyEvent.VK_DELETE){            
                DefaultTableModel modelo3 = (DefaultTableModel)inv.tabdestino.getModel(); 
                modelo3.removeRow(fila2);
            }
        }
    });
    } 
    public void calculos(){
        int fila= inv.tabdestino.getRowCount(); 
        for (int i = 0; i < fila; i++) {
            String costofinal =(String) inv.tabdestino.getValueAt(i, 2);
            String cantfinal =(String) inv.tabdestino.getValueAt(i, 3);
            double costototal=Double.parseDouble(costofinal)*Integer.parseInt(cantfinal);            
            totalc=totalc+costototal;
            totali=totalc*0.12;
            totalpvp=totalc+totali;
        }
        inv.txtcosto.setText(String.valueOf(totalc));
        inv.txtiva.setText(String.valueOf(totali)); 
        inv.txtpvp.setText(String.valueOf(totalpvp));        
        inv.btnguardar.setVisible(true);
            totalc=0;
            totali=0;
            totalpvp=0;        
    } 
    
    public void cargacb(){
        try {
            Connection acceso=con.getCn();
            Statement st=acceso.createStatement();
            ResultSet rs=st.executeQuery("Select nombre_proveedor from proveedores");
            while(rs.next()){
                inv.jcproveedor.addItem(rs.getString("nombre_proveedor"));
            }
        } catch (Exception e) {
        }
    } 
    public int getNproduc() {
        return nproduc;
    }
    public void insertcompra(String numfact){ 
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT numfact FROM compra WHERE numfact='"+numfact+"'");
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "La factura ya fue registrada");                              
            }
            else{
                cs = acceso.prepareStatement("INSERT INTO compra (numfact,fecha,cedulavend,nomuser,nombre_proveedor,"
                        + "nproductos,costo,iva,pvp)VALUES(?,?,?,?,?,?,?,?,?)");
                cs.setString(1,dtcompra.getNumerofact());
                cs.setString(2,dtcompra.getFecha().toString());
                cs.setString(3,dtcompra.getCedvendedor());
                cs.setString(4,dtcompra.getNombreusu());
                cs.setString(5,dtcompra.getNomproveedor());
                cs.setString(6,String.valueOf(dtcompra.getNumeroproduc()));
                cs.setString(7,String.valueOf(dtcompra.getCosto()));
                cs.setString(8,String.valueOf(dtcompra.getIva()));
                cs.setString(9,String.valueOf(dtcompra.getPvp()));                
                cs.executeUpdate();                
            }            
        } catch (SQLException ex){   
            System.err.println(ex);
            }
            dtbitacora.cargarbitacora("Ingreso de compra", "sistema");
    }
    public void insertcompradetalle(String numfact){ 
        Connection acceso=con.getCn();               
        try {                           
            cs = acceso.prepareStatement("INSERT INTO compradetalle (renglon,numfact,codigobarra,"
                    + "descripcion,cantidad,costo,pvp)VALUES(?,?,?,?,?,?,?)");
            cs.setString(1,String.valueOf(dtcompra.getI()));
            cs.setString(2,dtcompra.getNumerofact());
            cs.setString(3,dtcompra.getCodbarra());
            cs.setString(4,dtcompra.getDescripcion());
            cs.setString(5,String.valueOf(dtcompra.getCantidad()));
            cs.setString(6,String.valueOf(dtcompra.getCostounida()));
            cs.setString(7,String.valueOf(dtcompra.getPvpunidad()));                
            cs.executeUpdate();                                    
        } catch (SQLException ex){   
            System.err.println(ex);
        }
}     
    public void sumarinven(String codbarra){
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        String existencia="";
        int existencianueva;
        String sql="SELECT codigobarra,existencia"
                + " FROM inventario where codigobarra='"+codbarra+"'";
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                existencia=rs.getString("existencia");                
            }
            existencianueva=dtcompra.getCantidad()+Integer.parseInt(existencia);
            System.out.println("cantidad"+dtcompra.getCantidad());
            System.out.println("existencia"+existencia);
            System.out.println("eixtennueva"+existencianueva);
            cs=acceso.prepareStatement("UPDATE inventario set descripcion=?, existencia=?, costo=?, iva=? ,pvp=?"
                    + " where codigobarra='"+codbarra+"'");            
            Double iva=dtcompra.getCostounida()*0.12;
            cs.setString(1,String.valueOf(dtcompra.getDescripcion()));
            cs.setString(2,String.valueOf(existencianueva));
            cs.setString(3,String.valueOf(dtcompra.getCostounida()));
            cs.setString(4,String.valueOf(iva));
            cs.setString(5,String.valueOf(dtcompra.getPvpunidad()));
            cs.executeUpdate();
            System.out.println("existencia nueva "+existencianueva);
        } catch (SQLException e) {
            
        }
    }    
}

