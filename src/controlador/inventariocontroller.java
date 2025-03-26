
package controlador;
import com.mysql.jdbc.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import modelo.*;
import vista.*;

public class inventariocontroller implements ActionListener {
    private vistainventario inve;
    private inventario inventario;
    private pronuevo produnuevo;
    private producto product;
    private datosmodproducto datosproduc;
    private datosbitacora dtbitacora;
    int existencia;
    

    public inventariocontroller(vistainventario inve, inventario inv,pronuevo produnuevo,producto product,
            datosmodproducto datosproduc,datosbitacora dtbitacora) {
        this.inve=inve;
        this.dtbitacora=dtbitacora;
        this.inventario=inv;
        this.produnuevo=produnuevo;
        this.product=product;
        this.datosproduc=datosproduc;
        this.produnuevo.btnguardar.addActionListener(this);
        this.produnuevo.btnsalir.addActionListener(this);
        inv.consultarinventario();
        this.inve.btnsalir.addActionListener(this);
        this.inve.btnproducnuevo.addActionListener(this);
        this.inve.btnmodproduc.addActionListener(this);
        this.datosproduc.btnactualizar.addActionListener(this);
        this.datosproduc.btncancelar.addActionListener(this);
        solonumeros();
        produnuevo.txtivaproduc.disable();
        produnuevo.txtpvpproduc.disable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
 /////////////////////////////////////Bones de producto nuevo////////////////////////////////////////////////       
        if(e.getSource()==produnuevo.btnguardar){
            if(!StringUtils.isNullOrEmpty(produnuevo.txtcodbarra.getText()) && 
                !StringUtils.isNullOrEmpty(produnuevo.txtnombrepro.getText()) &&
                !StringUtils.isNullOrEmpty(produnuevo.txtcostoproduc.getText())){
            product.setCod_barra(produnuevo.txtcodbarra.getText());
            product.setDescripcion(produnuevo.txtnombrepro.getText());
            product.setExistencia(Integer.parseInt(produnuevo.txtcantproduc.getText()));            
            product.setCosto(Float.valueOf(produnuevo.txtcostoproduc.getText()));
            double iva=Double.parseDouble(produnuevo.txtcostoproduc.getText())*0.12;
            double pvp=Double.parseDouble(produnuevo.txtcostoproduc.getText())+iva;
            produnuevo.txtivaproduc.setText(String.valueOf(iva));
            produnuevo.txtpvpproduc.setText(String.valueOf(pvp));
            product.setIva(Float.valueOf(produnuevo.txtivaproduc.getText()));
            product.setPvp(Float.valueOf(produnuevo.txtpvpproduc.getText()));
            inventario.insertproducto(); 
            limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "Campos obligatorios vacios");
            }
        }
        if(e.getSource()==produnuevo.btnsalir){
            produnuevo.dispose(); 
            limpiar();
        }
        /////////////////////////////////Botones de iventario//////////////////////////////////////////
        if(e.getSource()==inve.btnmodproduc){
            int filaseleccionada;
            filaseleccionada = inve.tabinv.getSelectedRow();
            if(filaseleccionada!=-1){
            inve.dispose();
            datosproduc.setVisible(true);            
            String descrip = (String)inve.tabinv.getValueAt(filaseleccionada, 1);
            inventario.consultamod(descrip);
            datosproduc.txtcodbarra.setText(product.getCod_barra());
            datosproduc.txtdescripcion.setText(product.getDescripcion());
            datosproduc.txtexistencia.setText(String.valueOf(product.getExistencia()));
            datosproduc.txtexistencia.disable();
            datosproduc.txtcosto.setText(String.valueOf(product.getCosto()));
            datosproduc.txtiva.setText(String.valueOf(product.getIva()));
            datosproduc.txtpvp.setText(String.valueOf(product.getPvp()));
/*llenar tabla record porducto aqui*/            
            }
            else{
                JOptionPane.showMessageDialog(null,"no selecciono ningun producto");
            }
        }       
        if(e.getSource()==inve.btnproducnuevo){
            produnuevo.setVisible(true);
        }        
        if(e.getSource()==inve.btnsalir){
            inve.dispose();                  
        }
        ////////////////////////////////botones de modificar productos///////////////////////////////////
        if(e.getSource()==datosproduc.btnactualizar){
            String cantidadmas=datosproduc.txtsumar.getText();
            String cantidadmenos=datosproduc.txtrestar.getText();
            int valor=0;
            existencia=Integer.parseInt(datosproduc.txtexistencia.getText());
            if(!StringUtils.isNullOrEmpty(datosproduc.txtrazon.getText())){                
            if(!StringUtils.isNullOrEmpty(cantidadmas) && StringUtils.isNullOrEmpty(cantidadmenos)){
                existencia+=Integer.parseInt(cantidadmas);
                valor=1;
                product.setExistencia(existencia);
            }
            else if(StringUtils.isNullOrEmpty(cantidadmas) && !StringUtils.isNullOrEmpty(cantidadmenos)){
                if(existencia<Integer.parseInt(cantidadmenos)){
                    JOptionPane.showMessageDialog(null, "Error al descargar");
                }
                else{
                    existencia-=Integer.parseInt(cantidadmenos);                    
                    valor=1;
                    product.setExistencia(existencia);                    
                }
            }
            else if(cantidadmas=="" && cantidadmenos==""){                
                valor=1;
            }
            ////////////////////////////////////////////////////////////////////
            if(valor==1){
                inventario.modificarproducto();
                limpiar();
                dtbitacora.cargarbitacora("actualizo un producto", datosproduc.txtrazon.getText());
            }            
        }
            else{
                JOptionPane.showMessageDialog(null, "campo razon vacio\n"+"no se puede realizar la actualizacion");
            }
        }
        
        if(e.getSource()==datosproduc.btncancelar){
            datosproduc.dispose();    
            limpiar();
        }
    }

    public void solonumeros(){
        produnuevo.txtcostoproduc.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){
            char caracter = e.getKeyChar();      
            if(((caracter < '0') || (caracter > '9')) && (caracter != '\b')){
                e.consume();
            }
            int limite=12;
            if(produnuevo.txtcostoproduc.getText().length()>=limite){
                e.consume();
            }
            /*DecimalFormat df = new DecimalFormat("###,###.##");
            if(produnuevo.txtcostoproduc.getText().length()>=1){
                produnuevo.txtcostoproduc.setText( df.format(Double.parseDouble(produnuevo.txtcostoproduc.getText().replace(".", "").replace(",", ""))));
            }*/
        }
    });
        produnuevo.txtcodbarra.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){            
            int limite=12;
            if(produnuevo.txtcodbarra.getText().length()>=limite){
                e.consume();
            }
            char caracter = e.getKeyChar();      
            if(((caracter < '0') || (caracter > '9')) && (caracter != '\b')){
                e.consume();
            }
        }
    }); 
        produnuevo.txtcantproduc.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){            
            int limite=12;
            if(produnuevo.txtcantproduc.getText().length()>=limite){
                e.consume();
            }
            char caracter = e.getKeyChar();      
            if(((caracter < '0' ) || (caracter > '9')) && (caracter != '\b')){
                e.consume();
            }
        }
    });          
    }
    public void limpiar(){
        produnuevo.txtcodbarra.setText("");
        produnuevo.txtnombrepro.setText("");
        produnuevo.txtcantproduc.setText("0");
        produnuevo.txtcostoproduc.setText("");
        produnuevo.txtivaproduc.setText("");
        produnuevo.txtpvpproduc.setText("");
    }
}
