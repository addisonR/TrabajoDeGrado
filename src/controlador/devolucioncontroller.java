
package controlador;
import com.mysql.jdbc.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;
public class devolucioncontroller implements ActionListener{
    private vistadevolucion visdevolucion;
    private datosdevolucion dtdevolucion;
    private operaciones_devolucion opdevolucion;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    int num=0;

    public devolucioncontroller(final vistadevolucion visdevolucion, datosdevolucion dtdevolucion, final operaciones_devolucion opdevolucion) {
        this.visdevolucion = visdevolucion;
        this.dtdevolucion = dtdevolucion;
        this.opdevolucion = opdevolucion;
        visdevolucion.numfact.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {            
            if( e.getKeyCode() == KeyEvent.VK_ENTER){                
                if(num==0){
                opdevolucion.consultardetalleventa(visdevolucion.numfact.getText());
                num++;                
                }
                else{
                    JOptionPane.showMessageDialog(null, "La factura esta cargada");                    
                    num=0;
                }
            }            
        }
    });
        visdevolucion.btndevolver.addActionListener(this);        
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==visdevolucion.btndevolver){
            if(StringUtils.isNullOrEmpty(visdevolucion.motivo.getText())){
                JOptionPane.showMessageDialog(null, "Campo motivo vacio");
                num=0;
            }
            else{
                dtdevolucion.setNumerofactura(visdevolucion.numfact.getText());
                dtdevolucion.setMotivo(visdevolucion.motivo.getText());
                java.sql.Date date2 = new java.sql.Date(date.getTime());
                dtdevolucion.setFecha(date2);
                opdevolucion.hacerdevolucion();
                opdevolucion.insertardevolucion();   
            }
        }
        limpiar();
    }
    public void limpiar(){
        visdevolucion.numfact.setText("");
        visdevolucion.motivo.setText("");        
        int fila=visdevolucion.datos.getRowCount();
        DefaultTableModel modelo=(DefaultTableModel) visdevolucion.datos.getModel();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }
}
