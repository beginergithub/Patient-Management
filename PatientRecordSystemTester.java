public class PatientRecordSystemTester {
    public static void main(String[] args) throws Exception {
        testInit();
        testAddObservationTypes();
        testAddPatients();
        testAddObservations();

        testSaveData();
        testLoadData();

    }

    public static void testInit() {
        String test = "TEST: Create new patient record system and display it";
        System.out.println(test);
        PatientRecordSystem prs = new PatientRecordSystem();
        System.out.println(prs);
    }

    public static void testAddObservationTypes() throws Exception {
        PatientRecordSystem prs = new PatientRecordSystem();
        // add a measurement observation type
        prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
        System.out.println(prs);
        // add a measurement observation type with invalid code
        try {
            prs.addMeasurementObservationType("T100", "Height", "cm");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
        // add a category observation type
        String[] categories = { "Group A", "Group B1", "Group B2" };
        prs.addCategoryObservationType("T200", "blood type", categories);
        System.out.println(prs);
    }

    public static void testAddPatients() throws Exception {
        PatientRecordSystem prs = new PatientRecordSystem();
        // add a new patient
        prs.addPatient("P100", "Smith");
        System.out.println(prs);
        // add another patient
        prs.addPatient("P200", "Adams");
        System.out.println(prs);
        // invalid request
        try {
            prs.addPatient("P200", "Blake");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
    }

    public static void testAddObservations() throws Exception {
        // Create PatientRecordSystem and add patients
        PatientRecordSystem prs = new PatientRecordSystem();
        // add observation types
        prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
        String[] categories = { "Group A", "Group B1", "Group B2" };
        prs.addCategoryObservationType("T200", "blood type", categories);
        // add patients
        prs.addPatient("P100", "Smith");
        prs.addPatient("P200", "Adams");
        System.out.println(prs);
        // add a measurement observation to Smith’s records
        prs.addMeasurementObservation("P100", "T100", 120);
        System.out.println(prs);
        // add a category observation to Smith’s records
        prs.addCategoryObservation("P100", "T200", "Group A");
        System.out.println(prs);
        // invalid request: patient already has observation of the type
        try {
            prs.addMeasurementObservation("P100", "T100", 140);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
        // invalid request: invalid category value
        try {
            prs.addCategoryObservation("P200", "T200", "Group D");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
    }

    public static void testSaveData() throws Exception {
        // Create PatientRecordSystem
        // Add observation types , patients and observations
        PatientRecordSystem prs = new PatientRecordSystem();
        prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
        String[] categories = { "Group A", "Group B1", "Group B2" };
        prs.addCategoryObservationType("T200", "blood type", categories);
        String[] temp = { "low", "Medium", "high" };
        categories = temp;
        prs.addCategoryObservationType("T300", "stress level", categories);
        prs.addMeasurementObservationType("T400", "height", "cm");
        prs.addPatient("P100", "Smith");
        prs.addPatient("P200", "Adams");
        prs.addMeasurementObservation("P100", "T100", 120);
        prs.addCategoryObservation("P100", "T200", "Group A");
        // save data to file
        prs.saveData();
    }

    public static void testLoadData() throws Exception {
        testSaveData();
        PatientRecordSystem prs = new PatientRecordSystem();
        prs.loadData();
        System.out.println(prs);
    }
}
