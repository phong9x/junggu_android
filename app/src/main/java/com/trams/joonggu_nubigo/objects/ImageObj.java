package com.trams.joonggu_nubigo.objects;

/**
 * Created by zin9x on 11/10/2015.
 */
public class ImageObj {
    private String id;
    private String urlImage;

    public ImageObj() {
        super();
    }

    public ImageObj(String id, String urlImage) {
        this.id = id;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
