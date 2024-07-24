package org.example.service.dto;

import org.example.domain.Role;
import org.example.domain.User;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private int id;
    private String firstName;
    private String email;
    private String password;

    public UserDTO(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO id(int id){
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserDTO firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO email(String email){
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO password(String password){
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        return id == ((UserDTO)o).id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
