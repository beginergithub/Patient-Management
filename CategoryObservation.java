public class CategoryObservation extends Observation{
    private String value;


    public CategoryObservation(Patient patient, CategoryObservationType observationType, String value){
        super(patient, observationType);
        this.value = value;
    }

    public String getValue(){
        return value;
    }


    @Override
    public String toString() {

        return "CategoryObservation[observationType: " + getObservationType().toString().replaceAll("\\n", "") + ", value: " + value + "]\n";

    }
}
