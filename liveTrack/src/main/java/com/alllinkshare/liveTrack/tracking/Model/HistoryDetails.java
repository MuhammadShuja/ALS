package com.alllinkshare.liveTrack.tracking.Model;

public class HistoryDetails {


    private String orderDate;
    private String orderTme;
    private String status;
    private String pickupAddress;
    private String dropoffAddress;

    public HistoryDetails() {
    }


    public HistoryDetails(String orderDate, String orderTme, String status, String pickupAddress, String dropoffAddress) {
        this.orderDate = orderDate;
        this.orderTme = orderTme;
        this.status = status;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTme() {
        return orderTme;
    }

    public void setOrderTme(String orderTme) {
        this.orderTme = orderTme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDropoffAddress() {
        return dropoffAddress;
    }

    public void setDropoffAddress(String dropoffAddress) {
        this.dropoffAddress = dropoffAddress;
    }


    private static int value = 0;

//    public static ArrayList<HistoryDetails> createOrderList(int numContacts) {
//        ArrayList<HistoryDetails> historyDetails = new ArrayList<HistoryDetails>();
//
//        for (int i = 1; i <= 20; i++) {
//            //contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2));
//            historyDetails.add(new HistoryDetails("22/07/2020", "5:10", "Delivered", "Sector I-9 Store B 2 Islamabad Pakistan", "Sector H-9 House 2 Islamabad Pakistan"));
//            historyDetails.add(new HistoryDetails("22/07/2020", "5:10", "Delivered", "Sector I-9 Store B 2 Islamabad Pakistan", "Sector H-9 House 2 Islamabad Pakistan"));
//            historyDetails.add(new HistoryDetails("22/07/2020", "5:10", "Delivered", "Sector I-9 Store B 2 Islamabad Pakistan", "Sector H-9 House 2 Islamabad Pakistan"));
//            historyDetails.add(new HistoryDetails("22/07/2020", "5:10", "Delivered", "Sector I-9 Store B 2 Islamabad Pakistan", "Sector H-9 House 2 Islamabad Pakistan"));
//
//
//        }

//        return historyDetails;
//    }


}
