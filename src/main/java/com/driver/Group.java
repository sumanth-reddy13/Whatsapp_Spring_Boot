package com.driver;

public class Group {
    private String name;
    private int numberOfParticipants;

    public Group(String name, int numberOfParticipants) {
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Group group = (Group) obj;

        return this.getName().equals(group.getName()) && this.getNumberOfParticipants() == group.getNumberOfParticipants();
    }
}
