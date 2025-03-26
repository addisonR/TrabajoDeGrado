
package modelo;

public class proveedor {
    private String rifproveedor;
    private String nombreproveedor;
    private String direccion;
    private String telefono;
    private String contacto;
    private String telcontacto;

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelcontacto() {
        return telcontacto;
    }

    public void setTelcontacto(String telcontacto) {
        this.telcontacto = telcontacto;
    }   
    
    public String getRifproveedor() {
        return rifproveedor;
    }
    public void setRifproveedor(String rifproveedor) {
        this.rifproveedor = rifproveedor;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }
    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
