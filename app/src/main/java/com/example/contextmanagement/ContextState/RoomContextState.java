package com.example.contextmanagement.ContextState;

public class RoomContextState {

    private String room;
    private String status;
    private int light;
    private float noise;

    public RoomContextState(String room, String status, int light, float noise) {
        super();
        this.room = room;
        this.status = status;
        this.light = light;
        this.noise = noise;
    }

    public RoomContextState(String room, int light, String status) {
        super();
        this.room = room;
        this.status = status;
        this.light = light;
    }

    public String getRoom() {
        return this.room;
    }

    public String getStatus() {
        return this.status;
    }

    public int getLight() {
        return this.light;
    }

    public float getNoise() {
        return this.noise;
    }
}
