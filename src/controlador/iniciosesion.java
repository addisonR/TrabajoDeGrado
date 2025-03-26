
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.*;
import vista.*;

public class iniciosesion implements ActionListener {
    private Usuario usu;
    private inicio_sesion in;
    private inicio co;
    private Recontraseña rc;
    private coniniciosesion ini;    

    public iniciosesion(Usuario usu, inicio_sesion in, inicio co, Recontraseña rc, coniniciosesion ini) {
        this.usu = usu;        
        this.in = in;
        this.co = co;
        this.rc = rc;
        this.ini = ini;
        this.in.btentrar.addActionListener(this);
        click();                
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {  
        if(e.getSource()==in.btentrar){            
            usu.setUsuario(in.txtnombre.getText());
            usu.setClave(in.txtpass.getText());
            ini.iniciosesion();      
        }        
    }
   
    public void click(){
        in.recontraseña.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                rc.setVisible(true);
                in.dispose();                
            }
        });
    }
    
}
