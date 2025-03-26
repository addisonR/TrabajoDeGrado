
package reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import vista.*;
import modelo.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class verreportes implements ActionListener{
    private inicio ini;
    private bdatos bd;
    private vistareporteporusuario visreporusu;
    private reporte reportcal;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public verreportes(inicio ini, bdatos bd,vistareporteporusuario visreporusu,reporte reportcal) {
        this.ini = ini;
        this.bd = bd;
        this.reportcal= reportcal;
        this.visreporusu=visreporusu;
        this.ini.reporventa.addActionListener(this);
        this.ini.reporcompra.addActionListener(this);
        this.ini.reporinv.addActionListener(this);
        this.ini.menuporusu.addActionListener(this);
        this.ini.menureporte.addActionListener(this);
        ///////////////////////////botones de generar reporte//////////////////////////////////////////
        this.visreporusu.btnreport.addActionListener(this);
        this.reportcal.crear.addActionListener(this);
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ini.reporventa){
            try {
                JasperReport jr = JasperCompileManager.compileReport("ventas.jrxml");                
                JasperPrint j= JasperFillManager.fillReport(jr,null,bd.getCn());
                JasperViewer jv= new JasperViewer(j,false);
                jv.setTitle("Ventas");
                jv.setVisible(true);
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "erro al ver reporte");
            }
        }
        if(e.getSource()==ini.reporcompra){
            try {
                JasperReport jr = JasperCompileManager.compileReport("compras02.jrxml");                
                JasperPrint j= JasperFillManager.fillReport(jr,null,bd.getCn());
                JasperViewer jv= new JasperViewer(j,false);
                jv.setTitle("Compras");
                jv.setVisible(true);
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "erro al ver reporte");
            }
        }
        if(e.getSource()==ini.reporinv){
            try {
                JasperReport jr = JasperCompileManager.compileReport("inventario.jrxml");                
                JasperPrint j= JasperFillManager.fillReport(jr,null,bd.getCn());
                JasperViewer jv= new JasperViewer(j,false);
                jv.setTitle("Inventario");
                jv.setVisible(true);
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "erro al ver reporte");
            }
        }
        ////////////////////////////////////criterios/////////////////////////////////////////////////
        if(e.getSource()==ini.menuporusu){
            visreporusu.setVisible(true);
        }
        if(e.getSource()==ini.menureporte){
            reportcal.setVisible(true);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////
        if(e.getSource()==visreporusu.btnreport){
            String usuario=visreporusu.bususuario.getText();
            try {
                Map parametro = new HashMap();
                parametro.clear();
                parametro.put("usuario",usuario);
                JasperReport jr = JasperCompileManager.compileReport("porusuario.jrxml");                
                JasperPrint j= JasperFillManager.fillReport(jr,parametro,bd.getCn());
                JasperViewer jv= new JasperViewer(j,false);
                jv.setTitle("Ventas por usuario");
                jv.setVisible(true);
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "erro al ver reporte");
            }
        }
        if(e.getSource()==reportcal.crear){
            if(reportcal.fechainicio.getDate()!=null && reportcal.fechafinal.getDate()!=null){
                int año= reportcal.fechainicio.getCalendar().get(Calendar.YEAR);
                int mes= reportcal.fechainicio.getCalendar().get(Calendar.MARCH);
                int dia= reportcal.fechainicio.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mest= mes+1;
                String fech= año+"-"+mest+"-"+dia;
                System.out.println("valor "+fech);
                int año2= reportcal.fechafinal.getCalendar().get(Calendar.YEAR);
                int mes2= reportcal.fechafinal.getCalendar().get(Calendar.MARCH);
                int dia2= reportcal.fechafinal.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mest2= mes+1;
                String fech2= año2+"-"+mest2+"-"+dia2;
                System.out.println("valor "+fech2); 
                try {
                    Map parametro = new HashMap();
                    parametro.clear();
                    parametro.put("fecha",fech);
                    parametro.put("fecha2", fech2);
                    JasperReport jr = JasperCompileManager.compileReport("ventasporfecha.jrxml");                
                    JasperPrint j= JasperFillManager.fillReport(jr,parametro,bd.getCn());
                    JasperViewer jv= new JasperViewer(j,false);
                    jv.setTitle("Ventas por Fecha");
                    jv.setVisible(true);
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "erro al ver reporte");
                }
            }
            else if(reportcal.fechainicio.getDate()==null && reportcal.fechafinal.getDate()!=null){
                JOptionPane.showMessageDialog(null, "erro al ver reporte, no ingreso fecha de inicio");
            }
            else if(reportcal.fechainicio.getDate()!=null && reportcal.fechafinal.getDate()==null){
                JOptionPane.showMessageDialog(null, "erro al ver reporte, no ingreso fecha final");
            }
            else{
                JOptionPane.showMessageDialog(null, "erro al ver reporte");            
            }
        }
    }    
}
