
package comienzo;

// import com.jtattoo.plaf.mint.MintLookAndFeel;
import reportes.verreportes;
import vista.*;
import modelo.*;
import controlador.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
//import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
//
public class Comienzo {

    public static void main(String[] args) {
        NoireLookAndFeel xoi = new NoireLookAndFeel();
       
        try{
                JFrame.setDefaultLookAndFeelDecorated(true);
                UIManager.setLookAndFeel(xoi);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        ////////////////////////////VISTAS/////////////////////////////////////////////////////////
        vista.inicio_sesion view=new inicio_sesion();
        vista.inicio co=new inicio();
        vista.Recontraseña rc=new Recontraseña();
        vista.Registrousu reusu=new Registrousu();
        vista.usuarios usuarios=new usuarios();
        vista.pronuevo pro=new pronuevo();
        vista.vistainventario inv=new vista.vistainventario();
        vista.Compra comp=new Compra();
        vista.vistaproveedor viewprovee=new vistaproveedor();
        vista.datosmodproducto datosproduc=new datosmodproducto();
        vista.vistaventa vistaventa=new vistaventa();
        vista.vistacliente vistacli=new vistacliente();
        vista.vistadevolucion visdevo=new vistadevolucion();   
        vista.listaclientes lisclient=new listaclientes();
        vista.reporte reportcal=new reporte();
        vista.vistareporteporusuario visreporusu=new vistareporteporusuario();
        vista.listaproveedores lisprovee=new listaproveedores();
        
        //////////////////////////////MODELOS//////////////////////////////////////////////////////
        modelo.bdatos bd=new bdatos();
        modelo.Usuario usu=new Usuario();
        modelo.empleado emple=new empleado();
        modelo.datosbitacora dtbitacora=new datosbitacora(bd, usu);
        modelo.coniniciosesion modini=new coniniciosesion(usu, bd, co, view, emple, dtbitacora, vistaventa);
        modelo.registrarusuario regisusu=new registrarusuario(bd, emple, usu, usuarios, dtbitacora);
        modelo.producto produc=new producto();
        modelo.inventario inventario=new modelo.inventario(produc, bd, inv, dtbitacora);
        modelo.proveedor proveedormodelo=new proveedor();
        modelo.operaciones_proveedor proveedor=new operaciones_proveedor(bd, proveedormodelo, dtbitacora, lisprovee);
        modelo.datosfacturacompra dtcompra=new datosfacturacompra();
        modelo.operaciones_compra opcompras=new operaciones_compra(produc, bd, comp, dtcompra, dtbitacora);
        modelo.cliente client=new cliente();
        modelo.datosfacturaventa dtventas=new datosfacturaventa();
        modelo.operaciones_venta opventa=new operaciones_venta(vistaventa, vistacli, client, dtventas, bd, dtbitacora, lisclient);
        modelo.datosdevolucion dtdevolucion=new datosdevolucion();
        modelo.operaciones_devolucion opdevolucion=new operaciones_devolucion(visdevo, dtdevolucion, bd, dtbitacora);
        
        /////////////////////////////////CONTROLADORES/////////////////////////////////////////////
        controlador.iniciosesion conini=new iniciosesion(usu, view, co, rc, modini);
        controlador.iniciocontroller conin=new iniciocontroller(co, reusu, usuarios, pro, viewprovee, inv, comp, regisusu, vistaventa, lisprovee, proveedor);
        controlador.usucontroller usucon=new usucontroller(reusu, usu, regisusu, emple, usuarios);
        controlador.inventariocontroller invcontrol=new inventariocontroller(inv, inventario, pro, produc, datosproduc, dtbitacora);
        controlador.proveedorcontroller proveecontrol=new proveedorcontroller(viewprovee, proveedor, proveedormodelo, lisprovee);
        controlador.compracontroller compracontrol=new compracontroller(comp, opcompras, dtcompra, usu, emple);
        controlador.ventacontroller ventacontrol=new ventacontroller(vistaventa, vistacli, client, opventa, dtventas, emple, usu, visdevo, lisclient);
        controlador.devolucioncontroller devolucioncontrol=new devolucioncontroller(visdevo, dtdevolucion, opdevolucion);
        reportes.verreportes report=new verreportes(co, bd, visreporusu, reportcal);
        ////////////////////////////////////////////Reportes/////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////////////////////////////////////////////
        view.setVisible(true);
        view.setTitle("Inicio Sesion");
        view.setLocationRelativeTo(null);
        
    }
    
}
