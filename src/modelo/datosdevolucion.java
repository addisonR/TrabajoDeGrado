
package modelo;

import java.sql.Date;

public class datosdevolucion {
    String numerofactura,codigodebarra;
    String motivo;
    Date fecha;
    String descripcion;
    String cantidad;

    public String getCodigodebarra() {
        return codigodebarra;
    }

    public void setCodigodebarra(String codigodebarra) {
        this.codigodebarra = codigodebarra;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
}
