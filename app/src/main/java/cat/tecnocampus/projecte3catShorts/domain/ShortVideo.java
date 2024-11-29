package cat.tecnocampus.projecte3catShorts.domain;

import java.util.List;

public class ShortVideo {
    private int id;
    private String titol;
    private String descripcio;
    private int likes;
    private int visualitzacions;
    private String urlSerie; // URL de la serie original

    private boolean liked;

    private List<Comentari> comentaris;

    public ShortVideo(int id, String titol, String descripcio, int likes, int visualitzacions, String urlSerie, boolean liked) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.likes = likes;
        this.visualitzacions = visualitzacions;
        this.urlSerie = urlSerie;
        this.liked = liked;
    }

    public String getTitol() {
        return titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public int getVisualitzacions() {
        return visualitzacions;
    }

    public String getUrlSerie() {
        return urlSerie;
    }

    public boolean isLiked() {
        return liked;
    }

    public List<Comentari> getComentaris() {
        return comentaris;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComentaris(List<Comentari> comentaris) {
        this.comentaris = comentaris;
    }

    public int getId() {
        return id;
    }

    public void alterLiked() {
        liked = !liked;
    }
}

