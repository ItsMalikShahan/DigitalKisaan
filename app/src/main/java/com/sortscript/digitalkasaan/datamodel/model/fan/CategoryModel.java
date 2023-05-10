package com.sortscript.digitalkasaan.datamodel.model.fan;

import android.graphics.drawable.Drawable;

public class CategoryModel {

    String name;
    Drawable image;

    public CategoryModel(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
