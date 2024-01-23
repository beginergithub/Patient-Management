public class MeasurementObservationType extends ObservationType{
    private String unit;

    public MeasurementObservationType(String code, String name, String unit){
        super(code, name);
        this.unit = unit;
    }

    public String getUnit(){
        return  unit;
    }

    @Override
    public String toString() {

        return "MeasurementObservationType[code: " + getCode() + ", name: " + getName() + ", unit: " + unit + "]";
    }

}