
package controlador;
import com.mysql.jdbc.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class compracontroller implements ActionListener{
    private Compra compras;
    private operaciones_compra opcompras;
    private datosfacturacompra datoscompra;
    private Usuario usu;
    private empleado emple;
    int j=1;
    int x=0;
    
    public compracontroller(Compra compras, operaciones_compra opcompras, datosfacturacompra datoscompra,
            Usuario usu,empleado emple) {
        this.compras = compras;
        this.opcompras = opcompras;
        this.datoscompra = datoscompra;
        this.emple=emple;
        this.usu=usu;
        this.compras.btnsalir.addActionListener(this);
        this.compras.agrepro.addActionListener(this);
        this.compras.calculos.addActionListener(this);
        this.compras.btnguardar.addActionListener(this);
        compras.btnguardar.setVisible(false);
        compras.txtcosto.setEditable(false);
        compras.txtiva.setEditable(false);
        compras.txtpvp.setEditable(false);
        this.opcompras.cargacb();
        solonumeros();               
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==compras.btnsalir){
            compras.dispose();
            compras.btnguardar.setVisible(false);
            compras.calculos.setVisible(true);
            limpiar();      
            x=0;
        }
        if(e.getSource()==compras.btnguardar){
            int fila;
            if(!StringUtils.isNullOrEmpty(compras.txtnumfact.getText())){
                if(!StringUtils.isNullOrEmpty(compras.jcproveedor.getSelectedItem().toString())){                
                datoscompra.setNumerofact(compras.txtnumfact.getText());
                datoscompra.setNomproveedor((String) compras.jcproveedor.getSelectedItem());
                if(compras.calendario.getDate()!=null){
                    int año= compras.calendario.getCalendar().get(Calendar.YEAR);
                    int mes= compras.calendario.getCalendar().get(Calendar.MARCH);
                    int dia= compras.calendario.getCalendar().get(Calendar.DAY_OF_MONTH);
                    int mest= mes+1;
                    String fech= año+"-"+mest+"-"+dia;            
                    datoscompra.setFecha(java.sql.Date.valueOf(fech));
                    ///////////////////////////////////////////////////////////////////////            
                    datoscompra.setCosto(Float.valueOf(compras.txtcosto.getText()));
                    datoscompra.setIva(Float.valueOf(compras.txtiva.getText()));
                    datoscompra.setPvp(Float.valueOf(compras.txtpvp.getText()));           
                    datoscompra.setNumeroproduc(opcompras.getNproduc());
                    datoscompra.setCedvendedor(emple.getCedulavend());
                    datoscompra.setNombreusu(usu.getUsuario());
                    opcompras.insertcompra(compras.txtnumfact.getText());
            }//claendario
                else{
                    JOptionPane.showMessageDialog(null, "No a eleguido una fecha");                    
                }
            }///proveedor
                else{
                    JOptionPane.showMessageDialog(null, "Proveedor no seleccionado");                    
                }
                if(compras.tabdestino.getRowCount()>0){
                    for (int i = 0; i < compras.tabdestino.getRowCount(); i++) {
                        datoscompra.setCodbarra((String) compras.tabdestino.getValueAt(i, 0));
                        datoscompra.setDescripcion((String) compras.tabdestino.getValueAt(i, 1));
                        datoscompra.setCantidad(Integer.parseInt((String) compras.tabdestino.getValueAt(i, 2)));
                        datoscompra.setCostounida(Float.parseFloat((String) compras.tabdestino.getValueAt(i, 3)));
                        datoscompra.setPvpunidad(Float.parseFloat((String) compras.tabdestino.getValueAt(i, 4)));
                        datoscompra.setI(j++);
                        opcompras.insertcompradetalle(compras.txtnumfact.getText());
                        opcompras.sumarinven(datoscompra.getCodbarra());
                        limpiar();
                    }
                }///tabla
                else{
                    JOptionPane.showMessageDialog(null, "tabla vacia");
                }
                limpiar();
            }//numero de factura
            else{
                JOptionPane.showMessageDialog(null, "Ingrese numero de factura y campos obligatorios");                
            }
            compras.calculos.setVisible(true);            
            x=0;            
            compras.btnguardar.setVisible(false);
        }//////////boton
        if(e.getSource()==compras.agrepro){
            opcompras.agregarpro();
        }
        if(e.getSource()==compras.calculos){
            int eleccion = JOptionPane.showConfirmDialog(null, "Se realizaron todos los cambios", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(eleccion==JOptionPane.YES_OPTION){
                if(compras.tabdestino.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "La tabla producto esta vacia");                
                }            
                else{                
                    compras.calculos.setVisible(false);
                    if(x==0){
                    compras.taborigen.disable();
                    compras.tabdestino.disable();
                    opcompras.calculos();
                    x++;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "los calculos ya se realizaron");
                    }
                } 
            } 
        }
    } 
    public void limpiar(){
        compras.txtbuspro.setText("");
        compras.txtnumfact.setText("");
        compras.txtcosto.setText("");
        compras.txtiva.setText("");
        compras.txtpvp.setText("");
        int fila=compras.tabdestino.getRowCount();
        DefaultTableModel modelo=(DefaultTableModel) compras.tabdestino.getModel();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }
    
    public void solonumeros(){
        compras.txtnumfact.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){
            char caracter = e.getKeyChar();      
            if(((caracter < '0') || (caracter > '9')) && (caracter != '\b')){
                e.consume();
            }
            int limite=10;
            if(compras.txtnumfact.getText().length()>=limite){
                e.consume();
            }
        }
    });
        compras.txtbuspro.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){
            char caracter = e.getKeyChar();      
            if(((caracter < 'a') && (caracter < 'A') || (caracter > 'z') && (caracter > 'Z')) && (caracter != '\b')){
                e.consume();
            }
            int limite=12;
            if(compras.txtbuspro.getText().length()>=limite){
                e.consume();
            }
        }
    });
    }
}
