package model;

public class Contact {
    private String name;
    private String email;

    public Contact (String name, String email) {
        setName(name);
        setEmail(email);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

}
