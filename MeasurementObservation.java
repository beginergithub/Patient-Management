public class MeasurementObservation extends Observation{
    private double value;

    public MeasurementObservation(Patient patient, MeasurementObservationType observationType, double value){
        super(patient, observationType);
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "MeasurementObservation[observationType: " + getObservationType() + ", value: " + value + "]";
    }

}