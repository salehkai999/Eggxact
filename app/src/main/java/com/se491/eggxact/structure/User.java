package com.se491.eggxact.structure;

public class User {
    public String fullname, phone, email;

    public User() {

    }

    public User(String fullname, String phone, String email) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getFullname(){
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }



    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ",\n email=" + email +
                ",\n phone=" + phone +
                '}';
    }

}
