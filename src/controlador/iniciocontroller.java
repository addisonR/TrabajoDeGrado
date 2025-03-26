
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import modelo.*;
import vista.*;

public class iniciocontroller implements ActionListener{

    private inicio ini;
    private Registrousu reusu;
    private usuarios usu;
    private pronuevo pronue;
    private vistainventario inv;
    private Compra comp;
    private registrarusuario usuarios;
    private vistaproveedor vistaprovee;
    private vistaventa vistaventa;
    private listaproveedores lisprovee;
    private operaciones_proveedor opprovee;
    JButton exit;

    public iniciocontroller(inicio ini, Registrousu reusu, usuarios usu, pronuevo pronue,
            vistaproveedor vistaprovee,vistainventario inv, Compra comp,registrarusuario usuarios,
    vistaventa vistaventa,listaproveedores lisprovee,operaciones_proveedor opprovee) {
        this.ini = ini;
        this.reusu = reusu;
        this.usu = usu;
        this.pronue = pronue;
        this.inv = inv;
        this.comp = comp;
        this.usuarios = usuarios;
        this.vistaprovee=vistaprovee;
        this.vistaventa=vistaventa;
        this.lisprovee=lisprovee;
        this.opprovee=opprovee;
        this.ini.menucrear.addActionListener(this);
        this.ini.menuusers.addActionListener(this);
        this.ini.menusalir.addActionListener(this);
        this.ini.menuproducto.addActionListener(this);
        this.ini.menuproveedor.addActionListener(this);
        this.ini.menuinventario.addActionListener(this);
        this.ini.jbcompras.addActionListener(this);
        this.ini.jbventas.addActionListener(this);
        this.ini.menulisprovee.addActionListener(this);
        this.reusu.btmodificar.addActionListener(this);
        ////////////////ventana///////////////////
        ini.setTitle("Inicio");
        ini.setLocationRelativeTo(null);
        ini.setResizable(false); 
        ////////////////boton exit///////////////////
        exit=new JButton("Salir");
        exit.setBounds(55, 230, 80, 25);
        ImageIcon iconobtn = new ImageIcon("java/BlackSquareIconsX32/BlackSquareIconsX32/x32-salir.png");
        exit.setIcon(iconobtn);
        exit.setSize(130, 40);
        Font fuente = new Font("Arial", 1, 14);
        exit.setForeground(Color.WHITE);
        exit.setFont(fuente);
        exit.setBackground(Color.black);
        ini.jPanel1.setLayout(null);
        ini.jPanel1.add(exit);
        exit.addActionListener(this);
        exit.setVisible(true);
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ini.menucrear){
            reusu.setVisible(true);  
            reusu.setTitle("Registro de Usuarios");
            reusu.btmodificar.setVisible(false);
            reusu.btsalir2.setVisible(false);
        }        
        else if(e.getSource()==ini.menuusers){
            usu.setVisible(true);
            usuarios.listausuarios();
        }
        else if(e.getSource()==ini.menusalir){
            System.exit(0);
        }        
        else if(e.getSource()==ini.menuproducto){
            pronue.setVisible(true);
        }
        else if(e.getSource()==ini.menuproveedor){
            vistaprovee.setVisible(true);
        }
        else if(e.getSource()==ini.menuinventario){
            inv.setVisible(true);
        }
        else if(e.getSource()==ini.jbcompras){
            comp.setVisible(true);
        }
        else if(e.getSource()==ini.jbventas){
            vistaventa.setVisible(true);
        }        
        if(e.getSource()==exit){
            System.exit(0);
        }
        if(e.getSource()==ini.menulisprovee){
            lisprovee.setVisible(true);  
            opprovee.consultaproveedor();
        }
    }   
}
