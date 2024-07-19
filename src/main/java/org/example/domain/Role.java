package org.example.domain;

import java.util.Objects;
import java.util.Set;


/**
 * The Role class represents a role in the system.
 * A role has a unique ID, a name, and a set of users associated with it.
 */
public class Role {

    private int id;
    private RoleName name;
    private Set<User> users;

    public Role(){ }

    public int getId() {
        return id;
    }

    public Role id(int id){
        this.id = id;
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public Role name(RoleName name){
        this.name = name;
        return this;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null){
            for (User user : this.users){
                user.setRole(null);
            }
        }
        if (users != null){
            for (User user : users){
                user.setRole(this);
            }
        }
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
