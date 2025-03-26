
package modelo;
import com.mysql.jdbc.StringUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.*;

public class operaciones_venta implements KeyListener{
    private vistaventa visventa;
    private vistacliente viscliente;
    private cliente client;
    private datosfacturaventa dtventas;
    private datosbitacora dtbitacora;
    private bdatos con;
    private listaclientes lisclien;
    int cantidad,nproduc=0;
    double total;
    PreparedStatement cs;

    public operaciones_venta(vistaventa visventa, vistacliente viscliente, cliente client, datosfacturaventa dtventas
    ,bdatos con,datosbitacora dtbitacora,listaclientes lisclien) {
        this.con=con;
        this.dtbitacora=dtbitacora;
        this.visventa = visventa;
        this.viscliente = viscliente;
        this.client = client;
        this.dtventas = dtventas;
        this.lisclien=lisclien;
        filtroclientes();
        this.visventa.txtbus.addKeyListener(this);
        borradato();
        visventa.tabventa.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {            
            if( e.getKeyCode() == KeyEvent.VK_ENTER){            
                 calculo();                
            }
        }
    });
    }
    
    public void agregarproduc(){         
        String descripcion,pvp,cant="1";
        int fila=visventa.tabinv.getSelectedRow();
        int a=0;        
        if(fila!=-1){                           
            descripcion= (String) visventa.tabinv.getValueAt(fila, 0);
            for (int i = 0; i < visventa.tabventa.getRowCount(); i++) {
                if(visventa.tabventa.getValueAt(i, 0).toString().equals(descripcion)){
                    a=1;                                    
                }
            }
            if(a==1){
                JOptionPane.showMessageDialog(null, "Producto ya existe");                
            }
            else{                
                pvp= (String) visventa.tabinv.getValueAt(fila, 3);                
        ///////////////////////////////////////////////////////////////////////////            
            cantidad++;
            cant=String.valueOf(cantidad);
            DefaultTableModel destino = (DefaultTableModel) visventa.tabventa.getModel();
            String filaselement []={descripcion,cant,pvp};
            destino.addRow(filaselement);
            nproduc++; 
            calculo();
            }
        }        
        else if(fila==-1){
            JOptionPane.showMessageDialog(null, "No selecciono un producto");
            }
        cantidad=0;
    }
    
    public void limpiar(){
        DefaultTableModel tb = (DefaultTableModel) visventa.tabinv.getModel();
        int a = visventa.tabinv.getRowCount()-1;
            for (int i = a; i >= 0; i--) {           
                tb.removeRow(tb.getRowCount()-1);
            }
    }
    public void existenci(){
        int existencia,fila;
        String descrip;
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        fila=visventa.tabventa.getRowCount();
        for (int i = 0; i < fila; i++) {
            descrip=String.valueOf(visventa.tabventa.getValueAt(i, 0));
            try {
                st = acceso.createStatement();
                rs = st.executeQuery("select existencia FROM inventario where descripcion='"+descrip+"'"); 
                if(rs.next()){
                    existencia=Integer.parseInt(rs.getString(1));
                    if(existencia<Integer.parseInt((String) visventa.tabventa.getValueAt(i, 1))){
                        JOptionPane.showMessageDialog(null, "Cantidad de producto supera su existencia");
                        viscliente.setVisible(false);
                    }
                }
            } catch (Exception e) {
            }
            
        }
        
    }
    public void calculo(){
        DefaultTableModel destino = (DefaultTableModel) visventa.tabventa.getModel();
        int fila=destino.getRowCount();
        int cantida;
        double pvp;
        if(fila!=-1){
            for (int i = 0; i < fila; i++) {
                    cantida=Integer.parseInt(destino.getValueAt(i, 1).toString());
                    pvp=Double.parseDouble(destino.getValueAt(i, 2).toString());
                    total=total+(cantida*pvp);
                    visventa.total.setText(String.valueOf(total));
                }
                total=0;
                cantida=0;
                pvp=0;                        
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
        String descrip=visventa.txtbus.getText();
        Connection acceso=con.getCn();
        DefaultTableModel modelo=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};
        visventa.tabinv.setModel(modelo);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        modelo.setColumnIdentifiers(new Object[]{"Descripcion","Existencia","Costo","PVP"});
        String sql="SELECT descripcion,existencia,costo,pvp"
                + " FROM inventario where descripcion like '%"+descrip+"%'";
        Statement st;
        ResultSet rs;
        int column;
        if(!StringUtils.isNullOrEmpty(descrip)){
        try {
            int filas=modelo.getRowCount();
            for (int n = 0; n < filas; n++) {
                modelo.removeRow(0);
            }
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                modelo.addRow(new Object[]{rs.getString("descripcion"),rs.getString("existencia"),
                    rs.getString("costo"),rs.getString("pvp")});
                column=modelo.getColumnCount();
                for (int n = 0; n < column; n++) {
                    visventa.tabinv.getColumnModel().getColumn(n).setCellRenderer(tcr);
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
    public int getNproduc() {
        return nproduc;
    }

    public void setNproduc(int nproduc) {
        this.nproduc = nproduc;
    }
    
    public void insertventa(){ 
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        int numerofact;
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES "
                    + "WHERE TABLE_SCHEMA = 'taller' AND TABLE_NAME = 'venta'");
            if(rs.next()){                
                numerofact=Integer.parseInt(rs.getString(1));
                System.out.println("dato: "+numerofact);                
                dtventas.setNumfact(String.valueOf(numerofact));
                cs = acceso.prepareStatement("INSERT INTO venta (fecha,cedulacli,cedulavend,nomuser,nproductos,"
                        + "efectivo,debito,transferencia,costo,iva,total,estatus_venta)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                cs.setString(1,dtventas.getFecha().toString());
                cs.setString(2,client.getTipodocumento()+"-"+client.getCedulacliente());
                cs.setString(3,dtventas.getCedvendedor());
                cs.setString(4,dtventas.getNombreusu());
                cs.setString(5,String.valueOf(dtventas.getNproductos()));
                cs.setString(6,String.valueOf(dtventas.getEfectivo()));
                cs.setString(7,String.valueOf(dtventas.getDebito()));
                cs.setString(8,String.valueOf(dtventas.getTransferencia()));
                cs.setString(9,String.valueOf(dtventas.getCosto()));
                cs.setString(10,String.valueOf(dtventas.getIva()));
                cs.setString(11,String.valueOf(dtventas.getPvp()));    
                cs.setString(12,String.valueOf("v")); 
                cs.executeUpdate();             
            }            
        } catch (SQLException ex){   
            System.err.println(ex);
            }
            dtbitacora.cargarbitacora("Realizo una venta", "sistema");
    }
    public void insertventadetalle(String descripcion){ 
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT codigobarra,costo,iva FROM inventario "
                    + "WHERE descripcion='"+descripcion+"'");
            if(rs.next()){
                dtventas.setCod_barra(rs.getString(1));
                dtventas.setCostou(Double.parseDouble(rs.getString(2)));
                dtventas.setIvaunidad(Double.parseDouble(rs.getString(3)));
                cs = acceso.prepareStatement("INSERT INTO ventadetalle (renglon,numfact,codigobarra,"
                    + "descripcion,cantidad,costo,iva,pvp)VALUES(?,?,?,?,?,?,?,?)");
                cs.setString(1,String.valueOf(dtventas.getI()));
                cs.setString(2,dtventas.getNumfact());
                cs.setString(3,dtventas.getCod_barra());
                cs.setString(4,dtventas.getDescripcion());
                cs.setString(5,String.valueOf(dtventas.getCantidad()));
                cs.setString(6,String.valueOf(dtventas.getCostou()));
                cs.setString(7,String.valueOf(dtventas.getIvaunidad()));
                cs.setString(8,String.valueOf(dtventas.getPvpu()));
                cs.executeUpdate(); 
            }
                                                
        } catch (SQLException ex){   
            System.err.println(ex);
        }
}     
    public void restarinven(String codbarra){
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
            existencianueva=Integer.parseInt(existencia)-dtventas.getCantidad();            
            cs=acceso.prepareStatement("UPDATE inventario set existencia=? where codigobarra='"+codbarra+"'");
            cs.setString(1,String.valueOf(existencianueva));
            cs.executeUpdate();
            
        } catch (SQLException e) {
            
        }
    }
////////////////////////falta registrar el cliente///////////////////////
    public void registrarcliente(String cedclient,String tipodoc){
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        String existencia="";
        int existencianueva;
        String sql="SELECT cedulacli,nombre,apellido,direccion"
                + " FROM cliente where cedulacli='"+tipodoc+"-"+cedclient+"'";
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                viscliente.txtcedulacliente.disable();
                viscliente.nombrecliente.setText(rs.getString(2));
                viscliente.apellidoclient.setText(rs.getString(3));
                viscliente.dirclient.setText(rs.getString(4));
                cs=acceso.prepareStatement("UPDATE cliente set nombre=?,apellido=?,direccion=? "
                        + "where cedulacli='"+tipodoc+cedclient+"'");
                cs.setString(1,viscliente.nombrecliente.getText());
                cs.setString(2,viscliente.apellidoclient.getText());
                cs.setString(3,viscliente.dirclient.getText());
                cs.executeUpdate(); 
            }
            else{
                cs = acceso.prepareStatement("INSERT INTO cliente (cedulacli,nombre,apellido,direccion)"
                        + "VALUES(?,?,?,?)");
                cs.setString(1,viscliente.cbtpdoc.getSelectedItem().toString()+"-"+viscliente.txtcedulacliente.getText());
                cs.setString(2,viscliente.nombrecliente.getText());
                cs.setString(3,viscliente.apellidoclient.getText());
                cs.setString(4,viscliente.dirclient.getText());
                cs.executeUpdate(); 
                dtbitacora.cargarbitacora("Registro cliente nuevo", "sistema");
            }
        } catch (SQLException e) {
            
        }
    }    
    public void listarclientes(){
        Statement st;
        ResultSet rs;
        Connection acceso=con.getCn();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel lista=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};
        lisclien.clientes.setModel(lista);
        lista.setColumnIdentifiers(new Object[]{"Cedula-Rif","Nombre","Apellido","Direccion"});        
        String sql="select cedulacli,nombre,apellido,direccion from cliente";
        try {
            st = acceso.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){                
                lista.addRow(new Object[]{rs.getString("cedulacli"),rs.getString("nombre"),
                rs.getString("apellido"),rs.getString("direccion")});
                int column;
                column=lista.getColumnCount();
                for (int n = 0; n < column; n++) {
                    lisclien.clientes.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public void borradato(){
        visventa.tabventa.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int fila2;
            fila2=visventa.tabventa.getSelectedRow();
            if( e.getKeyCode() == KeyEvent.VK_DELETE){            
                DefaultTableModel modelo3 = (DefaultTableModel)visventa.tabventa.getModel(); 
                modelo3.removeRow(fila2);
            }
            calculo();
        }
    });
    } 
    public void filtroclientes(){
        lisclien.busced.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {            
            String descrip=lisclien.busced.getText();
            Connection acceso=con.getCn();
            DefaultTableModel modelo=new DefaultTableModel(){
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};            
            lisclien.clientes.setModel(modelo);
            modelo.setColumnIdentifiers(new Object[]{"Cedula-Rif","Nombre","Apellido","Direccion"});
            String sql="select cedulacli,nombre,apellido,direccion from cliente"
                    + " where cedulacli like '%"+descrip+"%'";
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
                    modelo.addRow(new Object[]{rs.getString("cedulacli"),rs.getString("nombre"),
                    rs.getString("apellido"),rs.getString("direccion")});
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
        });
    }
}
