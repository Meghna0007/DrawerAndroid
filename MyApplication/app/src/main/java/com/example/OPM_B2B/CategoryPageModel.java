package com.example.OPM_B2B;

import java.util.List;

public class CategoryPageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;


    private int type;
    //////////////////////////////Banner Slider
    private List<SliderModel> sliderModelList;

    public CategoryPageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    ////////Banner Slider
    //////////StripSlider
    private int resource;
    private String backgroundColor;

    public CategoryPageModel(int type, int resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    //////////StripSlider
    //////////////////Horizon @Grid_Product_layout
    private String title;
    private List<HorizonantleProductScrollModel> horizonantleProductScrollModelList;

    public CategoryPageModel(int type, String title, List<HorizonantleProductScrollModel> horizonantleProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizonantleProductScrollModel> getHorizonantleProductScrollModelList() {
        return horizonantleProductScrollModelList;
    }

    public void setHorizonantleProductScrollModelList(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList) {
        this.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
    }
////////////////Horizon
}
