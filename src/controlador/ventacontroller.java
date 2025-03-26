
package controlador;


import com.mysql.jdbc.StringUtils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ventacontroller implements ActionListener{
    private vistaventa visventa;
    private vistacliente visclien; 
    private vistadevolucion visdevo;
    private cliente client;
    private operaciones_venta opventa;
    private datosfacturaventa dtventa;
    private empleado emp;
    private Usuario usu;
    private listaclientes lisclien;
    double totalventa= 0.00;
    int j=1;
    JButton facturar;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public ventacontroller(vistaventa visventa, vistacliente visclien, cliente client, operaciones_venta opventa
    ,datosfacturaventa dtventa,empleado emp,Usuario usu,vistadevolucion visdevo,listaclientes lisclien) {
        this.visventa = visventa;
        this.emp=emp;
        this.usu=usu;
        this.visdevo=visdevo;
        this.visclien = visclien;
        this.client = client;
        this.opventa = opventa;
        this.dtventa=dtventa;
        this.lisclien=lisclien;
        this.visventa.btnfacturar.addActionListener(this);
        this.visventa.btnsalir.addActionListener(this);
        this.visventa.btnagregar.addActionListener(this);
        this.visclien.btngenefacturar.addActionListener(this);
        this.visclien.btncancelar.addActionListener(this);
        this.visventa.jmenudevolucion.addActionListener(this);
        this.visclien.btnclientes.addActionListener(this);
        this.lisclien.btnelegir.addActionListener(this);
        facturar=new JButton("Facturar");
        facturar.setBounds(5, 5, 100, 25);
        visventa.jPanel1.setLayout(null);
        visventa.jPanel1.add(facturar);
        facturar.addActionListener(this);
        facturar.setVisible(true);
        mostartotal();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        int column;
        column=visventa.tabventa.getColumnCount();
                for (int n = 0; n < column; n++) {
                    visventa.tabventa.getColumnModel().getColumn(n).setCellRenderer(tcr);
                }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==visventa.btnfacturar){
            int fila2=visventa.tabventa.getRowCount();
            if(fila2>0){
            visclien.setVisible(true);
            java.sql.Date date2 = new java.sql.Date(date.getTime());
            dtventa.setFecha(date2);            
            dtventa.setCedvendedor(emp.getCedulavend());
            dtventa.setNombreusu(usu.getUsuario());
            dtventa.setNproductos(opventa.getNproduc());
            dtventa.setIva("12%");
            dtventa.setPvp(Float.valueOf(visventa.total.getText()));            
            double iva=Double.parseDouble(visventa.total.getText())*0.12;
            double costo=Double.parseDouble(visventa.total.getText())-iva;
            dtventa.setCosto(costo);
            opventa.existenci();       
        }
            else{
                JOptionPane.showMessageDialog(null, "no hay productos para facturar");
            }
        }
        if(e.getSource()==visventa.btnsalir){
            if(usu.getEstatus()==1){
                visventa.dispose();
                limpiar();
            }
            else if(usu.getEstatus()==2){
                System.exit(0);
                limpiar();
            }
        }
        if(e.getSource()==visventa.btnagregar){
            opventa.agregarproduc();
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        if(e.getSource()==visclien.btngenefacturar){
            if(Double.parseDouble(visclien.total.getText())==Double.parseDouble(visventa.total.getText())){
                if(!StringUtils.isNullOrEmpty(visclien.txtcedulacliente.getText())){
                    client.setTipodocumento(visclien.cbtpdoc.getSelectedItem().toString());
                    client.setCedulacliente(visclien.txtcedulacliente.getText());
                    opventa.registrarcliente(visclien.txtcedulacliente.getText(),visclien.cbtpdoc.getSelectedItem().toString());
                    client.setNombrecliente(visclien.nombrecliente.getText());
                    client.setApellidocliente(visclien.apellidoclient.getText());
                    client.setDireccioncliente(visclien.dirclient.getText());
                    dtventa.setEfectivo(Double.valueOf(visclien.totalefectivo.getText()));
                    dtventa.setDebito(Double.valueOf(visclien.totaldebito.getText()));
                    dtventa.setTransferencia(Double.valueOf(visclien.totaltransferencia.getText()));
                    opventa.insertventa();
                    if(visventa.tabventa.getRowCount()>0){
                        for (int i = 0; i < visventa.tabventa.getRowCount(); i++) {                    
                            dtventa.setDescripcion((String) visventa.tabventa.getValueAt(i, 0));
                            dtventa.setCantidad(Integer.parseInt((String) visventa.tabventa.getValueAt(i, 1)));
                            dtventa.setPvpu(Double.parseDouble((String) visventa.tabventa.getValueAt(i, 2)));
                            dtventa.setI(j++);   
                            opventa.insertventadetalle(dtventa.getDescripcion());
                            opventa.restarinven(dtventa.getCod_barra());
                        }                    
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "tabla vacia");
                    } 
                    limpiar();
                    visclien.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "No se ingresaron los datos del cliente");
                }
            }
            else if(Double.parseDouble(visclien.total.getText())>Double.parseDouble(visventa.total.getText())){
                JOptionPane.showMessageDialog(null, "No puede vender por encima del total");
            }
            else if(Double.parseDouble(visclien.total.getText())<Double.parseDouble(visventa.total.getText())){
                JOptionPane.showMessageDialog(null, "No puede vender en negativo");
            }
        }
        if(e.getSource()==visclien.btncancelar){
            visclien.dispose();
            limpiar2();
        }
        if(e.getSource()==visventa.jmenudevolucion){
            visdevo.setVisible(true);
        }
        if(e.getSource()==facturar){
            int fila2=visventa.tabinv.getRowCount();
            if(fila2>0){
            visclien.setVisible(true);
            java.sql.Date date2 = new java.sql.Date(date.getTime());
            dtventa.setFecha(date2);
            dtventa.setCedvendedor(emp.getCedulavend());
            dtventa.setNombreusu(usu.getUsuario());
            dtventa.setNproductos(opventa.getNproduc());
            dtventa.setIva("12%");
            dtventa.setPvp(Float.valueOf(visventa.total.getText()));            
            double iva=Double.parseDouble(visventa.total.getText())*0.12;
            double costo=Double.parseDouble(visventa.total.getText())-iva;
            dtventa.setCosto(costo);            
        }
            else{
                JOptionPane.showMessageDialog(null, "no hay productos para facturar");
            }
        }
        if(e.getSource()==visclien.btnclientes){
            lisclien.setVisible(true);
            opventa.listarclientes();
        }
        if(e.getSource()==lisclien.btnelegir){
            int filaseleccionada;
            filaseleccionada = lisclien.clientes.getSelectedRow();
            if(filaseleccionada!=-1){
            lisclien.dispose();            
            String ced = (String)lisclien.clientes.getValueAt(filaseleccionada, 0);            
            String[] parts = ced.split("-");
            String part1 = parts[0]; 
            String part2 = parts[1]; 
            visclien.cbtpdoc.setSelectedItem(part1);
            visclien.txtcedulacliente.setText(part2);
            visclien.nombrecliente.setText((String)lisclien.clientes.getValueAt(filaseleccionada, 1));
            visclien.apellidoclient.setText((String)lisclien.clientes.getValueAt(filaseleccionada, 2));
            visclien.dirclient.setText((String)lisclien.clientes.getValueAt(filaseleccionada, 3));                     
            }
            else{
                JOptionPane.showMessageDialog(null,"no selecciono ningun usurio");
            }
        }
    }
    public void limpiar(){
        visclien.txtcedulacliente.setText("");
        visclien.nombrecliente.setText("");
        visclien.apellidoclient.setText("");
        visclien.dirclient.setText("");
        visclien.totalefectivo.setText("0");
        visclien.totaldebito.setText("0");
        visclien.totaltransferencia.setText("0");
        visclien.total.setText("0.00");
        visventa.total.setText("");
        visventa.txtbus.setText("");        
        opventa.setNproduc(0);
        int fila=visventa.tabventa.getRowCount();
        DefaultTableModel modelo=(DefaultTableModel) visventa.tabventa.getModel();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
        int fila2=visventa.tabinv.getRowCount();
        DefaultTableModel modelo2=(DefaultTableModel) visventa.tabinv.getModel();
        for (int a = 0; a < fila; a++) {
            modelo2.removeRow(0);
        }
    }
    public void limpiar2(){
        visclien.txtcedulacliente.setText("");
        visclien.nombrecliente.setText("");
        visclien.apellidoclient.setText("");
        visclien.dirclient.setText("");
        visclien.totalefectivo.setText("0");
        visclien.totaldebito.setText("0");
        visclien.totaltransferencia.setText("0");
        visclien.total.setText("0.00");
        visventa.total.setText("");
        visventa.txtbus.setText("");        
        opventa.setNproduc(0);
    }
    
    public void mostartotal(){
        if(!StringUtils.isNullOrEmpty(visclien.totaldebito.getText())){        
            visclien.totaldebito.addKeyListener(new KeyListener() {               
                @Override
                public void keyTyped(KeyEvent e) { 
                }
                @Override
                public void keyPressed(KeyEvent e) {
                }
                @Override
                public void keyReleased(KeyEvent e) {                    
                    if(!StringUtils.isNullOrEmpty(visclien.totaldebito.getText())){                        
                        totalventa=Double.parseDouble(visclien.totaldebito.getText())+Double.parseDouble(visclien.totalefectivo.getText())
                                +Double.parseDouble(visclien.totaltransferencia.getText());
                        if(totalventa==Double.parseDouble(visventa.total.getText())){
                            visclien.total.setForeground(Color.GREEN);
                            visclien.total.setText(String.valueOf(totalventa));                         
                        }
                        else if(totalventa>Double.parseDouble(visventa.total.getText())){
                            visclien.total.setForeground(Color.ORANGE);
                            visclien.total.setText(String.valueOf("+"+totalventa));
                        }
                        else{                            
                            visclien.total.setForeground(Color.red);
                            visclien.total.setText(String.valueOf("-"+totalventa));
                        }
                    }
                    else{
                        visclien.total.setText("0.00");
                    }
                }               
            });
        }
        if(!StringUtils.isNullOrEmpty(visclien.totalefectivo.getText())){        
            visclien.totalefectivo.addKeyListener(new KeyListener() {               
                @Override
                public void keyTyped(KeyEvent e) { 

                }
                @Override
                public void keyPressed(KeyEvent e) {

                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if(!StringUtils.isNullOrEmpty(visclien.totalefectivo.getText())){
                    totalventa=Double.parseDouble(visclien.totaldebito.getText())+Double.parseDouble(visclien.totalefectivo.getText())
                            +Double.parseDouble(visclien.totaltransferencia.getText());
                    if(totalventa==Double.parseDouble(visventa.total.getText())){
                            visclien.total.setForeground(Color.GREEN);
                            visclien.total.setText(String.valueOf(totalventa));                         
                        }
                    else if(totalventa>Double.parseDouble(visventa.total.getText())){
                             visclien.total.setForeground(Color.ORANGE);
                            visclien.total.setText(String.valueOf("+"+totalventa));
                        }
                        else{                            
                            visclien.total.setForeground(Color.red);
                            visclien.total.setText(String.valueOf("-"+totalventa));
                        }
                    }
                    else{
                        visclien.total.setText("0.00");
                    }                    
                }
            });            
        }
        if(!StringUtils.isNullOrEmpty(visclien.totaltransferencia.getText())){        
            visclien.totaltransferencia.addKeyListener(new KeyListener() {               
                @Override
                public void keyTyped(KeyEvent e) { 

                }
                @Override
                public void keyPressed(KeyEvent e) {

                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if(!StringUtils.isNullOrEmpty(visclien.totaltransferencia.getText())){
                        totalventa=Double.parseDouble(visclien.totaldebito.getText())+Double.parseDouble(visclien.totalefectivo.getText())
                                +Double.parseDouble(visclien.totaltransferencia.getText());                    
                        if(totalventa==Double.parseDouble(visventa.total.getText())){
                                visclien.total.setForeground(Color.GREEN);
                                visclien.total.setText(String.valueOf(totalventa));                         
                            }
                        else if(totalventa>Double.parseDouble(visventa.total.getText())){
                                visclien.total.setForeground(Color.ORANGE);
                                visclien.total.setText(String.valueOf("+"+totalventa));
                            }
                        else{                            
                                visclien.total.setForeground(Color.red);
                                visclien.total.setText(String.valueOf("-"+totalventa));
                            }
                    }
                    else{
                        visclien.total.setText("0.00");
                    }
                }
            });            
        }
    }
}
