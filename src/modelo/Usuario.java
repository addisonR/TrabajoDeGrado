
package modelo;

public class Usuario {
    private String usuario;
    private String clave;
    private String pregunta;
    private String respuesta;
    private int estatus;
    private int tipousu;

    public int getTipousu() {
        return tipousu;
    }
    public void setTipousu(int tipousu) {
        this.tipousu = tipousu;
    }
    

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }    

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    
    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
}
