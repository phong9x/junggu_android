package com.trams.joonggu_nubigo.objects;

import java.util.List;

/**
 * Created by Dang on 11/11/2015.
 */
public class AreaStoreDetailPage2Obj {

    String name;
    List<ImageObj> imageObjs;

    public AreaStoreDetailPage2Obj() {
    }

    public AreaStoreDetailPage2Obj(String name, List<ImageObj> imageObjs) {
        this.name = name;
        this.imageObjs = imageObjs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageObj> getImageObjs() {
        return imageObjs;
    }

    public void setImageObjs(List<ImageObj> imageObjs) {
        this.imageObjs = imageObjs;
    }
}
