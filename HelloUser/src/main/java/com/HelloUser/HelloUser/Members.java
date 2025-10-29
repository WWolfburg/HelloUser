package com.HelloUser.HelloUser;

import java.util.UUID;

public class Members {

    private UUID id;
    private String name;
    private String Lname;
    private String email;
    private String PhoneNumber;
    private String FnLname;

    
    public Members() {
    }

    public Members(String name, String Lname, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.Lname = Lname;
        this.FnLname = name + " " + Lname;
        PhoneNumber = phoneNumber;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

     public String getLname() {
        return Lname;
    }

    public void setLname(String Lname) {
        this.Lname = Lname;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getFnLname() {
        return FnLname;
    }

    public void setFnLname(String fnLname) {
        this.FnLname = fnLname;
    }
}
