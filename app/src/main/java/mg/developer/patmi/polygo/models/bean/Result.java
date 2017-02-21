package mg.developer.patmi.polygo.models.bean;

import mg.developer.patmi.polygo.models.entity.Data;

/**
 * Created by developer on 2/21/17.
 */

public class Result {

    private Double dH;
    private Double gisement;
    private Double xM;
    private Double yM;
    private Data data;

    public Double getdH() {
        return dH;
    }

    public void setdH(Double dH) {
        this.dH = dH;
    }

    public Double getGisement() {
        return gisement;
    }

    public void setGisement(Double gisement) {
        this.gisement = gisement;
    }

    public Double getxM() {
        return xM;
    }

    public void setxM(Double xM) {
        this.xM = xM;
    }

    public Double getyM() {
        return yM;
    }

    public void setyM(Double yM) {
        this.yM = yM;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
