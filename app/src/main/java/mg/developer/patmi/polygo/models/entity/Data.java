package mg.developer.patmi.polygo.models.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by patmi on 12/02/2017.
 */
@DatabaseTable(tableName = "DATA")
public class Data {

    @DatabaseField(generatedId = true)
    protected Long id;

    @DatabaseField(columnName = "STATIONS")
    private String stations;

    @DatabaseField(columnName = "PV")
    private String pV;

    @DatabaseField(columnName = "hZ")
    private Double hZ;

    @DatabaseField(columnName = "V")
    private Double v;

    @DatabaseField(columnName = "DI")
    private Double di;

    public Data() {
    }

    public Data(String stations, String pV, Double hZ, Double v, Double di) {
        this.stations = stations;
        this.pV = pV;
        this.hZ = hZ;
        this.v = v;
        this.di = di;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public String getpV() {
        return pV;
    }

    public void setpV(String pV) {
        this.pV = pV;
    }

    public Double gethZ() {
        return hZ;
    }

    public void sethZ(Double hZ) {
        this.hZ = hZ;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public Double getDi() {
        return di;
    }

    public void setDi(Double di) {
        this.di = di;
    }
}
