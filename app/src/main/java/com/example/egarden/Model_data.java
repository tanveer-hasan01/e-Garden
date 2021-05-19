package com.example.egarden;

public class Model_data {

    String id,name,Moisture;

    Model_data(){

    }

    public Model_data(String id, String name, String Moisture) {
        this.id = id;
        this.name = name;
        this.Moisture = Moisture;
    }

    public String getId() {
        return id;
    }

    public Model_data setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Model_data setName(String name) {
        this.name = name;
        return this;
    }

    public String getMoisture() {
        return Moisture;
    }

    public Model_data setMoisture(String moisture) {
        Moisture = moisture;
        return this;
    }
}
