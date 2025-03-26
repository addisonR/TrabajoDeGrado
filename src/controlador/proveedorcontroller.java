
package controlador;
import com.mysql.jdbc.StringUtils;
import java.awt.event.*;
import javax.swing.JOptionPane;
import vista.*;
import modelo.*;

public class proveedorcontroller implements ActionListener{
    private proveedor provee;
    private operaciones_proveedor op_provee;
    private vistaproveedor viewprovee;
    private listaproveedores lisprovee;
    
    public proveedorcontroller(vistaproveedor viewprovee,operaciones_proveedor op_provee,proveedor provee,
            listaproveedores lisprovee) {
        this.viewprovee=viewprovee;
        this.op_provee=op_provee;
        this.provee=provee;
        this.lisprovee=lisprovee;
        this.viewprovee.btnguardar.addActionListener(this);
        this.viewprovee.salir.addActionListener(this);
        this.lisprovee.btnsalir.addActionListener(this);
        this.lisprovee.btnmodicicar.addActionListener(this);
        op_provee.consultaproveedor();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewprovee.btnguardar){
            if(!StringUtils.isNullOrEmpty(viewprovee.txtrifprovee.getText())
                && !StringUtils.isNullOrEmpty(viewprovee.txtnombreprovee.getText())){
            provee.setRifproveedor(viewprovee.txtrifprovee.getText());
            provee.setNombreproveedor(viewprovee.txtnombreprovee.getText());
            provee.setDireccion(viewprovee.txtdirprovee.getText());
            provee.setTelefono(viewprovee.txttelprovee.getText());
            provee.setContacto(viewprovee.txtcontactoprov.getText());
            provee.setTelcontacto(viewprovee.txttelcontacto.getText());
            op_provee.insertproveedor(viewprovee.txtrifprovee.getText());
            limpiar();
            }
            else{
            JOptionPane.showMessageDialog(null, "Campos obligatorios vacios");
        }
    }        
        if(e.getSource()==viewprovee.salir){
            viewprovee.dispose();
            limpiar();
            
        }
        /////////////////////////////////////////////////////////////////////////////
        if(e.getSource()==lisprovee.btnsalir){
            lisprovee.dispose();            
        }
        if(e.getSource()==lisprovee.btnmodicicar){
            int filaseleccionada;
            filaseleccionada = lisprovee.tabproveedores.getSelectedRow();
            if(filaseleccionada!=-1){
            lisprovee.dispose();
            viewprovee.setVisible(true);            
            String descrip = (String)lisprovee.tabproveedores.getValueAt(filaseleccionada, 1);
            // op_provee.modificarproveedor(descrip);
            viewprovee.txtrifprovee.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 0));
            viewprovee.txtnombreprovee.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 1));
            viewprovee.txtdirprovee.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 2));
            viewprovee.txttelprovee.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 3));
            viewprovee.txtcontactoprov.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 4));
            viewprovee.txttelcontacto.setText((String) lisprovee.tabproveedores.getValueAt(filaseleccionada, 5));            
/*llenar tabla record porducto aqui*/            
            }
            else{
                JOptionPane.showMessageDialog(null,"no selecciono ningun producto");
            }
        }        
    }
    
    public void limpiar(){
        viewprovee.txtrifprovee.setText("");
        viewprovee.txtnombreprovee.setText("");
        viewprovee.txtdirprovee.setText("");
        viewprovee.txttelprovee.setText("");
        viewprovee.txtcontactoprov.setText("");
        viewprovee.txttelcontacto.setText("");
    }
 
}
