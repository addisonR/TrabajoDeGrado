
package modelo;

import java.sql.Date;

public class datosfacturaventa {
    String numfact,iva;
    Date fecha;    
    String cedvendedor;
    String nombreusu;
    int nproductos;
    double costo,pvp;
    double efectivo,debito,transferencia;
    ////////////////////////////////////////////////////////////////////////////////
    String cod_barra;
    String descripcion;
    int cantidad,i;
    double costou, ivaunidad ,pvpu;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    
    
    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public double getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(double transferencia) {
        this.transferencia = transferencia;
    }
    
    
    public String getNumfact() {
        return numfact;
    }

    public void setNumfact(String numfact) {
        this.numfact = numfact;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
   
    public String getCedvendedor() {
        return cedvendedor;
    }

    public void setCedvendedor(String cedvendedor) {
        this.cedvendedor = cedvendedor;
    }

    public String getNombreusu() {
        return nombreusu;
    }

    public void setNombreusu(String nombreusu) {
        this.nombreusu = nombreusu;
    }

    public int getNproductos() {
        return nproductos;
    }

    public void setNproductos(int nproductos) {
        this.nproductos = nproductos;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCostou() {
        return costou;
    }

    public void setCostou(double costou) {
        this.costou = costou;
    }

    public double getIvaunidad() {
        return ivaunidad;
    }

    public void setIvaunidad(double ivaunidad) {
        this.ivaunidad = ivaunidad;
    }

    public double getPvpu() {
        return pvpu;
    }

    public void setPvpu(double pvpu) {
        this.pvpu = pvpu;
    }
}
