package org.example.service.dto;

import org.example.domain.Role;
import org.example.domain.RoleName;
import org.example.domain.User;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class RoleDTO implements Serializable {

    private int id;
    private RoleName name;

    public RoleDTO(){ }

    public int getId() {
        return id;
    }

    public RoleDTO id(int id){
        this.id = id;
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public RoleDTO name(RoleName name){
        this.name = name;
        return this;
    }

    public void setName(RoleName name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
       if (this == o){
           return true;
       }
       if (!(o instanceof RoleDTO)){
           return false;
       }
       return id == ((RoleDTO)o).id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
