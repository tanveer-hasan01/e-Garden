package com.example.egarden;

public class Model_Device {

    String id,auto_watering,pump_status,last_watering;

    Model_Device()
    {

    }

    public Model_Device(String id, String auto_watering, String pump_status, String last_watering) {
        this.id = id;
        this.auto_watering = auto_watering;
        this.pump_status = pump_status;
        this.last_watering = last_watering;
    }

    public String getId() {
        return id;
    }

    public Model_Device setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuto_watering() {
        return auto_watering;
    }

    public Model_Device setAuto_watering(String auto_watering) {
        this.auto_watering = auto_watering;
        return this;
    }

    public String getPump_status() {
        return pump_status;
    }

    public Model_Device setPump_status(String pump_status) {
        this.pump_status = pump_status;
        return this;
    }

    public String getLast_watering() {
        return last_watering;
    }

    public Model_Device setLast_watering(String last_watering) {
        this.last_watering = last_watering;
        return this;
    }
}
