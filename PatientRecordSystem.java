import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PatientRecordSystem{
    private ArrayList<Patient> patients;
    private ArrayList<ObservationType> observationTypes;
    private ArrayList<Observation> observations;

    public PatientRecordSystem(){
        patients = new ArrayList<Patient>();
        observationTypes = new ArrayList<ObservationType>();
        observations = new ArrayList<Observation>();
    }


    public void addPatient(String id, String name) throws Exception{
        if (findPatient(id) != null){
            throw new Exception("  ** Error: Patient with ID " + id + " aldready exists. **");
        }
        patients.add(new Patient(id, name));
    }

    public Patient findPatient(String id){
        for (Patient patient : patients){
            if (patient.getId().equals(id)){
                return patient;
            }
        }
        return null;
    }

    public void addMeasurementObservationType(String code, String name, String unit) throws Exception{
        if (findObservationType(code) != null){
            throw new Exception("  ** Error: Measurement observation type with code " + code + " already exists. **");
        }
        observationTypes.add(new MeasurementObservationType(code, name, unit));
    }

    public void addCategoryObservationType(String code, String name, String[] categories) throws Exception{
        if (findObservationType(code) != null){
            throw new Exception("  ** Error: Category observation type with code " + code + " already exists. **");
        }
        observationTypes.add(new CategoryObservationType(code, name, categories));
    }

    public ObservationType findObservationType(String code){
        for (ObservationType observationType : observationTypes){
            if (observationType.getCode().equals(code)){
                return observationType;
            }
        }
        return null;
    }

    public void addMeasurementObservation(String patientId, String observationTypeCode, double value) throws Exception{
        Patient patient = findPatient(patientId);
        if (patient == null){
            throw new Exception("  ** Error: Patient with ID " + patientId + " does not exist. **");
        }
        ObservationType observationType = findObservationType(observationTypeCode);
        if (observationType == null){
            throw new Exception("  ** Error: Measurement observation type with code " + observationTypeCode + " does not exist. **");
        }
        if (hasObservationOfType(observationType)){
            throw new Exception("  ** Error: Patient with ID " + patientId + " already has an observation of type " + observationTypeCode + ". **");
        }
        observations.add(new MeasurementObservation(patient, (MeasurementObservationType) observationType, value));

    }

    public boolean hasObservationOfType(ObservationType observationType){
        for (Observation observation : observations){
            if (observation.getObservationType().equals(observationType)){
                return true;
            }
        }
        return false;
    }

    public void addCategoryObservation(String patientId, String observationTypeCode, String value) throws Exception{
        Patient patient = findPatient(patientId);
        if (patient == null){
            throw new Exception("  ** Error: Patient with ID " + patientId + " does not exist. **");
        }
        ObservationType observationType = findObservationType(observationTypeCode);
        if (observationType == null){
            throw new Exception("  ** Error: Category observation type with code " + observationTypeCode + " does not exist. **");
        }
        if (findObservation(patientId, observationTypeCode) != null){
            throw new Exception("  ** Error: Patient with ID " + patientId + " already has an observation of type " + observationTypeCode + ". **");
        }
        CategoryObservationType categoryType = (CategoryObservationType) observationType;
        boolean validValue = false;
        for (String category : categoryType.getCategories()) {
            if (category.equals(value)) {
                validValue = true;
                break;
            }
        }
        if (!validValue) {
            throw new Exception("  ** Error: Invalid category value " + value + " for observation type " + observationTypeCode + ". **");
        }
        observations.add(new CategoryObservation(patient, categoryType, value));  
    }

    public Observation findObservation(String patientId, String string){
        for (Observation observation : observations){
            if (observation.getPatient().getId().equals(patientId) && observation.getObservationType().getCode().equals(string)){
                return observation;
            }
        }

        return null;
    }

    public void saveData() throws IOException{
        PrintWriter measurementObsTypesPrintWriter = new PrintWriter(new FileWriter("PRS-MeasurementObservationTypes.txt"));
        for (ObservationType obsType : observationTypes){
            if (obsType instanceof MeasurementObservationType){
                measurementObsTypesPrintWriter.println(obsType.getCode() + "; " + obsType.getName() + "; " + ((MeasurementObservationType) obsType).getUnit());
            }
        }
        measurementObsTypesPrintWriter.close(); 
        
        PrintWriter categoryObsTypesPrintWriter = new PrintWriter(new FileWriter("PRS-CategoryObservationTypes.txt"));
        for (ObservationType obsType : observationTypes){
            if (obsType instanceof CategoryObservationType){
                String categories = "";
                for (String category : ((CategoryObservationType) obsType).getCategories()){
                    categories += category + ", ";
                }
                if (categories.length() > 0) {
                    categories = categories.substring(0, categories.length() - 2);
                }
                categoryObsTypesPrintWriter.println(obsType.getCode() + "; " + obsType.getName() + "; " + categories);
            }
        }
        categoryObsTypesPrintWriter.close();

        

        PrintWriter patientsWriter = new PrintWriter(new FileWriter("PRS-Patients.txt"));
        for (Patient patient : patients){
            patientsWriter.println(patient.getId() + "; " + patient.getName());
        }
        patientsWriter.close();

        PrintWriter measureObsPrintWriter = new PrintWriter(new FileWriter("PRS-MeasurementObservations.txt"));
        for (Observation obs : observations){
            if (obs instanceof MeasurementObservation){
                measureObsPrintWriter.println(obs.getPatient().getId() + "; " + obs.getObservationType().getCode() + "; " + ((MeasurementObservation) obs).getValue());
            }
        }
        measureObsPrintWriter.close();

        PrintWriter categoryObsPrintWriter = new PrintWriter(new FileWriter("PRS-CategoryObservations.txt"));
        for (Observation obs : observations){
            if (obs instanceof CategoryObservation){
                categoryObsPrintWriter.println(obs.getPatient().getId() + "; " + obs.getObservationType().getCode() + "; " + ((CategoryObservation) obs).getValue());
            }
        }
        categoryObsPrintWriter.close();
    }


    public void loadData() throws Exception{
        BufferedReader measurementObsTypesReader = new BufferedReader(new FileReader("PRS-MeasurementObservationTypes.txt"));
        String line;
        while ((line = measurementObsTypesReader.readLine()) != null){
            StringTokenizer token = new StringTokenizer(line, ";");

            String code = token.nextToken().trim();
            String name = token.nextToken().trim();
            String unit = token.nextToken().trim();
            MeasurementObservationType type = new MeasurementObservationType(code, name, unit);
            observationTypes.add(type);
        }

        measurementObsTypesReader.close();

        BufferedReader categoryObsTypesReader = new BufferedReader(new FileReader("PRS-CategoryObservationTypes.txt"));
        while ((line = categoryObsTypesReader.readLine()) != null){
            StringTokenizer token = new StringTokenizer(line, ";");

            String code = token.nextToken().trim();
            String name = token.nextToken().trim();
            String categoryStr = token.nextToken().trim();
        
            String [] categories = categoryStr.split(",");
        
            for (int i = 0; i < categories.length; i++) {
                categories[i] = categories[i].trim();
            }
            CategoryObservationType type = new CategoryObservationType(code, name, categories);
            observationTypes.add(type);
        }

        categoryObsTypesReader.close();

        BufferedReader patientReader =  new BufferedReader(new FileReader("PRS-Patients.txt"));
        while ((line = patientReader.readLine()) != null){
            StringTokenizer token = new StringTokenizer(line, ";");

            String id = token.nextToken().trim();
            String name = token.nextToken().trim();
            Patient patient = new Patient(id, name);
            patients.add(patient);
        }

        patientReader.close();

        try (BufferedReader measurementObsReader = new BufferedReader(new FileReader("PRS-MeasurementObservations.txt"))) {
            
            while ((line = measurementObsReader.readLine()) != null){
                
                StringTokenizer token = new StringTokenizer(line, ";");
                String patientId = token.nextToken().trim();
                String observationTypeCode = token.nextToken().trim();
                double value = Double.parseDouble(token.nextToken());
                Patient patient = findPatient(patientId);
                MeasurementObservationType type = (MeasurementObservationType) findObservationType(observationTypeCode);
                MeasurementObservation obs = new MeasurementObservation(patient, type, value);
                observations.add(obs);
            }
            measurementObsReader.close();
        }


        try (BufferedReader categoryObsReader = new BufferedReader(new FileReader("PRS-CategoryObservations.txt"))) {
            while ((line = categoryObsReader.readLine()) != null){
                StringTokenizer token = new StringTokenizer(line, ";");

                String patientId = token.nextToken().trim();
                String observationTypeCode = token.nextToken().trim();
                String category = token.nextToken().trim();
                Patient patient = findPatient(patientId);
                CategoryObservationType type = (CategoryObservationType) findObservationType(observationTypeCode);
                CategoryObservation obs = new CategoryObservation(patient, type, category);
                observations.add(obs);
            }
        
            categoryObsReader.close();
        }
    }


    @Override
    public String toString() {
        String result = "";
        result += ("--------------------------\n");
        result += ("PATIENT RECORD SYSTEM DATA\n");
        result += ("--------------------------\n");
        result += ("\nOBSERVATION TYPES:\n");
        if (observationTypes.isEmpty()){
            result += "    No observation type.\n";
        }
        else{
            for (ObservationType observationType : observationTypes) {
                if (observationType instanceof MeasurementObservationType) {
                    MeasurementObservationType measurementType = (MeasurementObservationType) observationType;
                    result += "-- " + measurementType.toString() + "\n";
                }
                
                else if (observationType instanceof CategoryObservationType) {
                    CategoryObservationType categoryType = (CategoryObservationType) observationType;
    
                    result += "-- " + categoryType.toString();
                    
                }           
            }
        }

        result += "\nPATIENTS:\n";
        
        if (patients.isEmpty()){
            result += "    No patient added.\n";
        }
        for (Patient patient : patients) {
            result += patient.toString() + "\n";
            
        }
        result += "\nOBSERVATION:\n";
        if (observations.isEmpty()){
            result += "    No observation.\n";
        }
        for (Observation observation : observations) {
            if (observation instanceof MeasurementObservation) {
                MeasurementObservation measurementObservation = (MeasurementObservation) observation;
                result += "-- " + measurementObservation.toString() + "\n";
            }
        }
        for (Observation observation : observations) {
            if (observation instanceof CategoryObservation) {
                CategoryObservation categoryObservation = (CategoryObservation) observation;
                result += "-- " + categoryObservation.toString();
            }
        }
        return result;
    }    
}







    


