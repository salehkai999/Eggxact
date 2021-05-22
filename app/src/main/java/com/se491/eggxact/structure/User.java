package com.se491.eggxact.structure;

public class User {
    public String fullname, phone, email, password;

    public User() {

    }

    public User(String fullname, String phone, String email, String password) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
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

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ",\n email=" + email +
                ",\n phone=" + phone +
                ",\n password=" + password +
                '}';
    }

}
