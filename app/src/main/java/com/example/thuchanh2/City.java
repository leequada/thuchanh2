package com.example.thuchanh2;

public class City  {
    public String id,name,citys,dates,amount;

    public City(String id, String name, String citys, String dates, String amount) {
        this.id = id;
        this.name = name;
        this.citys = citys;
        this.dates = dates;
        this.amount = amount;
    }

    public City() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCitys() {
        return citys;
    }

    public String getDates() {
        return dates;
    }

    public String getAmount() {
        return amount;
    }
}
