import java.util.Scanner;

public class RobustPatientSystemMenu {

    private static Scanner sc = new Scanner(System.in);
    private static PatientRecordSystem prs = new PatientRecordSystem();


    public static void main(String[] args) throws Exception {

        displayMenu();
    }

    public static void displayMenu() throws Exception {
        boolean exit = false;
        while (!exit) {
            System.out.println("=====================");
            System.out.println("Patient Record System");
            System.out.println("=====================");
            System.out.println("1. Add a measurement observation type");
            System.out.println("2. Add a category observation type");
            System.out.println("3. Add a patient");
            System.out.println("4. Add a measurement observation");
            System.out.println("5. Add a category observation");
            System.out.println("6. Display details of an observation type");
            System.out.println("7. Display a patient record by the patient id");
            System.out.println("8. Save data");
            System.out.println("9. Load data");
            System.out.println("D. Display all data for inspection");
            System.out.println("X. Exit");
            System.out.print("Please enter an option (1-9 or D or X): ");

            String choice = sc.nextLine().toUpperCase();

            switch (choice) {
                case "1":
                    addMeasurementObservationType();
                    break;
                case "2":
                    addCategoryObservationType();
                    break;
                case "3":
                    addPatient();
                    break;
                case "4":
                    addMeasurementObservation();
                    break;
                case "5":
                    addCategoryObservation();
                    break;
                case "6":
                    displayObservationType();
                    break;
                case "7":
                    displayPatientRecord();
                    break;
                case "8":
                    saveData();
                    break;
                case "9":
                    loadData();
                    break;
                case "D":
                    displayAllData();
                    break;
                case "X":
                    System.out.println("Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");

            }
        }
    }

    public static void addMeasurementObservationType() throws Exception {
        try {
            System.out.print("Enter observation type code: ");
            String code = sc.nextLine();
            System.out.print("Enter observation type name: ");
            String name = sc.nextLine();
            System.out.print("Enter measurement observation unit: ");
            String unit = sc.nextLine();
            prs.addMeasurementObservationType(code, name, unit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCategoryObservationType() throws Exception {
        try {
            System.out.print("Enter observation type code: ");
            String code = sc.nextLine();
            System.out.print("Enter observation type name: ");
            String name = sc.nextLine();
            System.out.print("Enter the number of categories: ");
            int numOfCategories = Integer.parseInt(sc.nextLine());
            String[] categories = new String[numOfCategories];
            for (int i = 0; i < numOfCategories; i++) {
                System.out.print("Enter category " + (i + 1) + ": ");
                categories[i] = sc.nextLine();
            }
            prs.addCategoryObservationType(code, name, categories);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addPatient() throws Exception {
        try {
            System.out.print("Enter patient ID: ");
            String id = sc.nextLine();
            System.out.print("Enter patient name: ");
            String name = sc.nextLine();
            prs.addPatient(id, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addMeasurementObservation() throws Exception {

        try {
            System.out.print("Enter patient ID: ");
            String patientId = sc.nextLine();
            System.out.print("Enter observation type code: ");
            String observationTypeCode = sc.nextLine();

            ObservationType observationType = prs.findObservationType(observationTypeCode);
            if (!(observationType instanceof MeasurementObservationType)) {
                throw new IllegalArgumentException(
                        "Observation type code does not correspond to a MeasurementObservationType");
            }

            System.out.print("Enter observation type value: ");
            double value = sc.nextDouble();
            sc.nextLine();

            prs.addMeasurementObservation(patientId, observationTypeCode, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCategoryObservation() throws Exception {
        try {
            System.out.print("Enter patient ID: ");
            String patientId = sc.nextLine();
            System.out.print("Enter observation type code: ");
            String observationTypeCode = sc.nextLine();
            ObservationType observationType = prs.findObservationType(observationTypeCode);
            if (!(observationType instanceof CategoryObservationType)) {
                throw new IllegalArgumentException(
                        "Observation type code does not correspond to a CategoryObservationType");
            }
            System.out.print("Enter observation type value: ");
            String value = sc.nextLine();
            prs.addCategoryObservation(patientId, observationTypeCode, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayObservationType() {
            System.out.print("Enter observation type code: ");
            String obsTypeCode = sc.nextLine();

            ObservationType type = prs.findObservationType(obsTypeCode);
            System.out.println("\nObservation type:");
            System.out.println("    - Code: " + type.getCode());
            System.out.println("    - Name: " + type.getName());

            System.out.print("\nPlease press the ENTER key to continue:");
            sc.nextLine();
    }

    public static void displayPatientRecord() {
        try {
            System.out.print("Enter patient ID: ");
            String patientId = sc.nextLine();
            System.out.print("Enter observation type code: ");
            String obsTypeCode = sc.nextLine();
            Patient patient = prs.findPatient(patientId);

            if (patient == null) {
                System.out.print("Patient with id" + patientId + " not found!");
            } else {
                System.out.println("\nPatient:");
                System.out.println("    - PatientID: " + patient.getId());
                System.out.println("    - Patient name: " + patient.getName());

                ObservationType type = prs.findObservationType(obsTypeCode);
                System.out.println("    - Observation type code: " + type.getCode());
                System.out.println("    - Observation type name: " + type.getName());

                if (type instanceof MeasurementObservationType) {
                    MeasurementObservationType mType = (MeasurementObservationType) type;

                    System.out.println("    - Measurement observation unit: " + mType.getUnit());
                    System.out.println("    - Measurement observation value: " + ((MeasurementObservation) prs.findObservation(patientId, type.getCode())).getValue());
                    System.out.print("\nPlease press the ENTER key to continue:");
                    sc.nextLine();

                } else if (type instanceof CategoryObservationType) {
                    CategoryObservationType cType = (CategoryObservationType) type;
                    System.out.print("    - Category observation categories: ");

                    String[] categories = cType.getCategories();
                    for (int i = 0; i < categories.length; i++) {
                        System.out.print(categories[i]);
                        if (i != categories.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                    System.out.println("    - Category observation value: " + ((CategoryObservation) prs.findObservation(patientId, type.getCode())).getValue());
                    System.out.print("\nPlease press the ENTER key to continue:");
                    sc.nextLine();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveData() throws Exception {
        try {
            prs.saveData();
            System.out.println("Data saved successfully!");
            System.out.print("Please press the ENTER key to continue:");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error saving data to file!");
        }
    }

    public static void loadData() throws Exception {
        try {
            prs.loadData();
            System.out.println("Data loaded successfully!");
            System.out.print("Please press the ENTER key to continue:");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error loading data from file!");
        }
    }

    public static void displayAllData() throws Exception {

        System.out.println(prs.toString());

        System.out.print("Please press the ENTER key to continue:");
        sc.nextLine();
    }
}