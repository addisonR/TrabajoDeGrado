
package modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;

public class operaciones_devolucion {
    private vistadevolucion visdevolucion;
    private datosdevolucion dtdevolucion;    
    private datosbitacora dtbitacora;
    private bdatos con;
    int valor=0;
    PreparedStatement cs; 

    public operaciones_devolucion(vistadevolucion visdevolucion, datosdevolucion dtdevolucion, bdatos con,datosbitacora dtbitacora) {
        this.visdevolucion = visdevolucion;
        this.dtbitacora=dtbitacora;
        this.dtdevolucion = dtdevolucion;
        this.con = con;        
    }
    
    public void hacerdevolucion(){
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        String existencia="";
        int existencianueva;
        int count=0;
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT estatus_venta FROM venta WHERE numfact='"+visdevolucion.numfact.getText()+"'");
            if(rs.next()){
                String estado=rs.getString(1);
                if(estado.equals("v")){
                    valor=0;
                    cs=acceso.prepareStatement("UPDATE venta set estatus_venta=? "
                        + "where numfact='"+visdevolucion.numfact.getText()+"'");
                    cs.setString(1,String.valueOf("d"));
                    cs.executeUpdate();
                    int filas=visdevolucion.datos.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        st = acceso.createStatement();
                        rs = st.executeQuery("SELECT codigobarra,existencia FROM inventario where"
                            + " codigobarra='"+visdevolucion.datos.getValueAt(i, 0)+"'");
                        if(rs.next()){
                            existencia=rs.getString("existencia");                            
                        }                        
                        existencianueva=Integer.parseInt((String) visdevolucion.datos.getValueAt(i, 2))+Integer.parseInt(existencia);
                        cs=acceso.prepareStatement("UPDATE inventario set existencia=? "
                        + "where codigobarra='"+visdevolucion.datos.getValueAt(i, 0)+"'");
                        cs.setString(1,String.valueOf(existencianueva));
                        count=cs.executeUpdate();
                    }
                    if(count>0){
                                JOptionPane.showMessageDialog(null, "Devolucion Cargada");
                                limpiar();
                            }
                }
                else{
                    valor=1;
                    JOptionPane.showMessageDialog(null, "ya existe devolucion de esta factura");
                    visdevolucion.dispose();
                    limpiar();
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        dtbitacora.cargarbitacora("Realizo una devolucion", "sistema");        
    }
    public void limpiar(){
        DefaultTableModel tb = (DefaultTableModel) visdevolucion.datos.getModel();
        int a = visdevolucion.datos.getRowCount()-1;
            for (int i = a; i >= 0; i--) {           
                tb.removeRow(tb.getRowCount()-1);
            }
    }
    public void insertardevolucion(){
        Connection acceso=con.getCn();
        if(valor==0){
        try {
            cs = acceso.prepareStatement("INSERT INTO devoluciones(numfact,motivo,fecha"
                + ")VALUES(?,?,?)");
            cs.setString(1, dtdevolucion.getNumerofactura());
            cs.setString(2, dtdevolucion.getMotivo());
            cs.setString(3, String.valueOf(dtdevolucion.getFecha()));            
            cs.executeUpdate();
            visdevolucion.dispose();
        } catch (SQLException e) {
            System.err.println(e);
        }
        }
        else{}
    }
    
    public void consultardetalleventa(String numfact){
        Connection acceso=con.getCn();
        Statement st;
        ResultSet rs;
        String cant = null;
        String descripcion = null;
        try {
            st = acceso.createStatement();
            rs= st.executeQuery("SELECT codigobarra,descripcion,cantidad FROM"
                    + " ventadetalle WHERE numfact='"+visdevolucion.numfact.getText()+"'");
            DefaultTableModel destino = (DefaultTableModel) visdevolucion.datos.getModel();            
            while(rs.next()){
                dtdevolucion.setCodigodebarra(rs.getString(1));
                destino.addRow(new Object[]{rs.getString("codigobarra"),rs.getString("descripcion"),rs.getString("cantidad")});
            }
        }
        catch(SQLException e){
            
        }
    }
}
