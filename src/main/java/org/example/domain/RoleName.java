package org.example.domain;

/**
 * The RoleName enum represents the roles available in the system.
 * Each role has a name associated with it.
 */
public enum RoleName {
    ADMINISTRATOR ("ADMINISTRATOR"),
    USER ("USER");

    private final String name;

    RoleName(String name){
        this.name = name;
    }

    public String getUserRole(){
        return name;
    }

    @Override
    public String toString() {
        return "RoleName{" +
                "name='" + name + '\'' +
                '}';
    }
}
