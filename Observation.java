public class Observation{
    private Patient patient;
    private ObservationType observationType;

    public Observation(Patient patient, ObservationType observationType){
        this.patient = patient;
        this.observationType = observationType;
    }

    public Patient getPatient(){
        return patient;
    }

    public ObservationType getObservationType(){
        return observationType;
    }
    
}