
package controlador;

import com.mysql.jdbc.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import vista.*;
import modelo.*;

public class usucontroller implements ActionListener{
    private Registrousu view;
    private Usuario datos;
    private empleado emp;
    private usuarios viewusu;
    private registrarusuario guar;
    String nomuser;
    int filaseleccionada=0;

    public usucontroller(Registrousu view, Usuario datos, registrarusuario guar, empleado emp, usuarios viewusu) {
        this.view = view;
        this.datos = datos;
        this.guar = guar;
        this.emp = emp;
        this.viewusu= viewusu;
        this.view.btregistrar.addActionListener(this);
        this.view.btmodificar.addActionListener(this);
        this.view.btsalir.addActionListener(this);
        this.view.btsalir2.addActionListener(this);
        this.viewusu.btsalirusu.addActionListener(this);
        this.viewusu.btmodificar.addActionListener(this);
        solonumeros();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==view.btregistrar){            
            if(!StringUtils.isNullOrEmpty(view.cedemple.getText()) && !StringUtils.isNullOrEmpty(view.txtnomemp.getText())){
                if(!StringUtils.isNullOrEmpty(view.txtnomusu.getText()) && !StringUtils.isNullOrEmpty(view.txtcontraseña.getText())){
            ////////////////////////datos de usuario////////////////////////////////////////////////
            datos.setUsuario(view.txtnomusu.getText());
            datos.setClave(view.txtcontraseña.getText());
            datos.setPregunta(view.cbpregunta.getSelectedItem().toString());
            datos.setRespuesta(view.txtresp.getText());
            if(view.cbtpusuario.getSelectedItem().equals("Administrador")){
                datos.setTipousu(1);
            }
            else if(view.cbtpusuario.getSelectedItem().equals("Facturador")){
                datos.setTipousu(2);
            }
            else{
                JOptionPane.showMessageDialog(null, "Tipo de usuario no seleccionado");
            }
            //////////////////////datos del empleado////////////////////////////////////////////////
            emp.setTipoducumentoid(view.jctpdoc.getSelectedItem().toString());
            emp.setCedulavend(view.cedemple.getText());
            emp.setNombrevend(view.txtnomemp.getText());
            emp.setApellidovend(view.txtapeemple.getText());
            emp.setDireccionvend(view.txtdirecemple.getText());
            //////////////////////funciones////////////////////////////////////////////////////////
            guar.insertusuario(datos.getUsuario(),emp.getCedulavend());
            limpiar();
                }else{
                JOptionPane.showMessageDialog(null, "Campos vacios");
                } 
            }else{
                JOptionPane.showMessageDialog(null, "Campos vacios");
            }            
        }
        else if(e.getSource()==view.btsalir){            
            view.dispose();
            limpiar();
            view.jctpdoc.enable();
        }
        else if(e.getSource()==view.btsalir2){            
            view.dispose();   
            viewusu.setVisible(true);
            view.cedemple.enable();
            view.txtnomusu.enable();
            limpiar();
        }
        else if(e.getSource()==view.btmodificar){
             ////////////////////////datos de usuario////////////////////////////////////////////////
            datos.setUsuario(view.txtnomusu.getText());
            datos.setClave(view.txtcontraseña.getText());
            datos.setPregunta(view.cbpregunta.getSelectedItem().toString());
            datos.setRespuesta(view.txtresp.getText());
            if(view.cbtpusuario.getSelectedItem().equals("Administrador")){
                datos.setTipousu(1);
                guar.modificarusu();
            }
            else if(view.cbtpusuario.getSelectedItem().equals("Facturador")){
                datos.setTipousu(2);
                guar.modificarusu();
            }
            else{
                JOptionPane.showMessageDialog(null, "Tipo de usuario no seleccionado");
            }
            //////////////////////datos del empleado////////////////////////////////////////////////
            emp.setCedulavend(view.cedemple.getText());
            emp.setNombrevend(view.txtnomemp.getText());
            emp.setApellidovend(view.txtapeemple.getText());
            emp.setDireccionvend(view.txtdirecemple.getText());            
            //////////////////////funciones////////////////////////////////////////////////////////            
            guar.modificarvendedor();
            limpiar();
            viewusu.setVisible(true);
            view.dispose();     
            view.txtnomusu.enable();
            view.cedemple.enable();
            view.jctpdoc.enable();
        }
        ///////////////////////////////BOTONES DE VISTA USUARIOS//////////////////////////////////////////////////////
        else if(e.getSource()==viewusu.btsalirusu){
            viewusu.dispose();
            view.btregistrar.setVisible(true);
            view.btsalir.setVisible(true);
            view.btmodificar.setVisible(false); 
            view.btsalir2.setVisible(false);
            view.jctpdoc.enable();
        }
        else if(e.getSource()==viewusu.btmodificar){
            filaseleccionada = viewusu.tabusu.getSelectedRow();
            if(filaseleccionada!=-1){
            viewusu.dispose();
            view.setVisible(true);
            view.btregistrar.setVisible(false);
            view.btsalir.setVisible(false);
            view.btmodificar.setVisible(true); 
            view.btsalir2.setVisible(true);
            view.cedemple.disable();
            view.txtnomusu.disable();
            nomuser = (String)viewusu.tabusu.getValueAt(filaseleccionada, 2);
            guar.consultamod(nomuser);
            view.jctpdoc.setSelectedItem(emp.getTipoducumentoid());
            view.cedemple.setText(emp.getCedulavend());
            view.txtnomemp.setText(emp.getNombrevend());
            view.txtapeemple.setText(emp.getApellidovend());
            view.txtdirecemple.setText(emp.getDireccionvend());
            view.txtnomusu.setText(datos.getUsuario());
            view.txtcontraseña.setText(datos.getClave());
            view.cbpregunta.setSelectedItem(datos.getPregunta());
            view.txtresp.setText(datos.getRespuesta());
            if(datos.getTipousu()==1){
                view.cbtpusuario.setSelectedItem("Administrador");
            }
            else if(datos.getTipousu()==2){
                view.cbtpusuario.setSelectedItem("Facturador");
            }            
            view.jctpdoc.disable();
            view.setTitle("Actualizar datos de usuario");
            }
            else{
                JOptionPane.showMessageDialog(null,"no selecciono ningun usurio");
            }
        }        
    }
    public void solonumeros(){
        view.cedemple.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){
            char caracter = e.getKeyChar();      
            if(((caracter < '0') || (caracter > '9')) && (caracter != '\b')){
                e.consume();
            }
            int limite=8;
            if(view.cedemple.getText().length()>=limite){
                e.consume();
            }
        }
    });
        view.txtnomemp.addKeyListener(new KeyAdapter(){
    @Override
        public void keyTyped(KeyEvent e){
            char caracter = e.getKeyChar();      
            if(((caracter < 'a') && (caracter < 'A') || (caracter > 'z') && (caracter > 'Z')) && (caracter != '\b')){
                e.consume();
            }
            int limite=12;
            if(view.txtnomemp.getText().length()>=limite){
                e.consume();
            }
        }
    });
    }
    public void limpiar(){
        view.txtnomusu.setText("");
        view.txtcontraseña.setText("");
        view.txtresp.setText("");
        view.cedemple.setText("");
        view.txtnomemp.setText("");
        view.txtapeemple.setText("");
        view.txtdirecemple.setText("");
    }
}
