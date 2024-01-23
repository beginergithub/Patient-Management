public class Patient{
    private String id;
    private String name;

    public Patient(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        
        return "-- Patient id: " + id + ", name: " + name;

}
}