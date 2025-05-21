package com.example.project;

import java.io.Serializable;

public class HotelModel implements Serializable {
    private int id;
    private String name;
    private String address;
    private String description;
    private int roomCount;

    public HotelModel(int id, String name, String address, String description, int roomCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.roomCount = roomCount;
    }

    public HotelModel(String name, String address, String description, int roomCount) {
        this(-1, name, address, description, roomCount);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRoomCount() { return roomCount; }
    public void setRoomCount(int roomCount) { this.roomCount = roomCount; }
}

