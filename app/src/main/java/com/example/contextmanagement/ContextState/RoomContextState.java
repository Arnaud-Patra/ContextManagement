package com.example.contextmanagement.ContextState;

public class RoomContextState {



    private String roomId;
    private String name;
    private int floor;
    private String buildingId;

    public RoomContextState(String roomId, String name, int floor, String buildingId) {
        super();
        this.roomId = roomId;
        this.name = name;
        this.floor = floor;
        this.buildingId = buildingId;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }


}
