package com.chubak.meygosar;

import java.util.List;

public class Vineyard {
    public String continent;
    public String country;
    public String provinceOrState;
    public String county;
    public String address;
    public String proprietor;
    public List<String> phoneNumbers;
    public String name;


    public Vineyard(String continent, String name, String country, String provinceOrState, String county, String address, String proprietor, List<String> phoneNumbers) {

        this.continent = continent;
        this.name = name;
        this.country = country;
        this.provinceOrState = provinceOrState;
        this.county = county;
        this.address = address;
        this.proprietor = proprietor;
        this.phoneNumbers = phoneNumbers;
    }
}
