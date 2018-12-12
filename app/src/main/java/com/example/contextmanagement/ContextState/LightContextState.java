package com.example.contextmanagement.ContextState;

public class LightContextState {

    private String lightId;
    private int level;
    private String status;
    private int roomId;

    public LightContextState(String lightId, int level, String status, int roomId) {
        super();
        this.lightId = lightId;
        this.level = level;
        this.status = status;
        this.roomId = roomId;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLightId() {
        return lightId;
    }

    public void setLightId(String lightId) {
        this.lightId = lightId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
