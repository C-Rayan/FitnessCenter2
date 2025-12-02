package apps;

public class Person {
    private String email;
    private String name;
    private String gender;

    public Person(String email, String name, String gender){
        this.email = email;
        this.name = name;
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
