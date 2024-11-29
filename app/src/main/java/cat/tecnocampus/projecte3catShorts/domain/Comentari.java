package cat.tecnocampus.projecte3catShorts.domain;

public class Comentari {

    private String id;
    private String usuari;
    private String contingut;
    private String data;

    public Comentari(String id, String usuari, String contingut, String data) {
        this.id = id;
        this.usuari = usuari;
        this.contingut = contingut;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getUsuari() {
        return usuari;
    }

    public String getContingut() {
        return contingut;
    }

    public String getData() {
        return data;
    }

    public void setUsuari(String usuari) { this.usuari = usuari; }

    public void setText(String text) { this.contingut = text; }


}
