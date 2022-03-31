package com.mto.topquizz.model;


public class User {

    private String firstName;

    public String getFirstName() {

        return firstName;

    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;

    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                '}';
    }
}
