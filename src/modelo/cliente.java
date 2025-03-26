
package modelo;

public class cliente {
    private String tipodocumento;
    private String cedulacliente;
    private String nombrecliente;
    private String apellidocliente;
    private String direccioncliente;

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getCedulacliente() {
        return cedulacliente;
    }
    public void setCedulacliente(String cedulacliente) {
        this.cedulacliente = cedulacliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }
    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getApellidocliente() {
        return apellidocliente;
    }
    public void setApellidocliente(String apellidocliente) {
        this.apellidocliente = apellidocliente;
    }

    public String getDireccioncliente() {
        return direccioncliente;
    }
    public void setDireccioncliente(String direccioncliente) {
        this.direccioncliente = direccioncliente;
    }
    
}
