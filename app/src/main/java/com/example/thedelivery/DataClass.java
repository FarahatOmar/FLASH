package com.example.thedelivery;

public class DataClass {
    private String dataName;
    private String dataAddress;
    private String dataPickup;
    private String packageType;
    private String packageStatus;
    private String estimatedPickupTime;
    private String estimatedDeliveryTime;
    private boolean isPickedUp;



    private String key;



    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDataName() {
        return dataName;
    }
    public String getDataAddress() {
        return dataAddress;
    }
    public String getDataPickup() {
        return dataPickup;
    }
    public String getPackageType() {
        return packageType;
    }
    public String getPackageStatus() {
        return packageStatus;
    }

    public String getEstimatedPickupTime() {
        return estimatedPickupTime;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }
    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }
    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }


    public DataClass(String dataName, String dataAddress, String dataPickup, String packageType,String packageStatus,String estimatedPickupTime,String estimatedDeliveryTime) {
        this.dataName = dataName;
        this.dataAddress = dataAddress;
        this.dataPickup = dataPickup;
        this.packageType = packageType;
        this.packageStatus=packageStatus;
        this.estimatedPickupTime=estimatedPickupTime;
        this.estimatedDeliveryTime=estimatedDeliveryTime;
    }
    public DataClass(){
    }
}