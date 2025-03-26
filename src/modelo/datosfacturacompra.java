
package modelo;

import java.sql.*;

public class datosfacturacompra {
    private String numerofact;
    private Date fecha;
    private String cedvendedor;
    private String nombreusu;
    private String nomproveedor;
    private int numeroproduc;
    private int i;
    private float costo;
    private float iva;
    private float pvp;
    ///////////////////////
    private int renglon;
    private String codbarra;
    private String descripcion;
    private int cantidad;
    private float costounida;
    private float pvpunidad;
    /////////////////////////////////////////////////////////////////////////

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getNombreusu() {
        return nombreusu;
    }

    public void setNombreusu(String nombreusu) {
        this.nombreusu = nombreusu;
    }

    public String getNumerofact() {
        return numerofact;
    }

    public void setNumerofact(String numerofact) {
        this.numerofact = numerofact;
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

    public String getNomproveedor() {
        return nomproveedor;
    }

    public void setNomproveedor(String nomproveedor) {
        this.nomproveedor = nomproveedor;
    }
    
    public int getNumeroproduc() {
        return numeroproduc;
    }

    public void setNumeroproduc(int numeroproduc) {
        this.numeroproduc = numeroproduc;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public String getCodbarra() {
        return codbarra;
    }

    public void setCodbarra(String codbarra) {
        this.codbarra = codbarra;
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

    public float getCostounida() {
        return costounida;
    }

    public void setCostounida(float costounida) {
        this.costounida = costounida;
    }

    public float getPvpunidad() {
        return pvpunidad;
    }

    public void setPvpunidad(float pvpunidad) {
        this.pvpunidad = pvpunidad;
    }
    
}
