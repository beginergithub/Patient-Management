public class ObservationType{
    private String code;
    private String name;

    public ObservationType(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "ObservationType[code: " + code + ", name: " + name + "]";
    }

}