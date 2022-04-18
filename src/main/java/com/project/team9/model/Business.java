package com.project.team9.model;

import com.project.team9.model.buissness.Pointlist;
import com.project.team9.model.buissness.SiteFee;
import com.project.team9.model.buissness.UserCategory;

import java.util.ArrayList;
import java.util.List;

public class Business {

    List<UserCategory> userCategories;
    SiteFee siteFee;
    Pointlist clientPointlist;
    Pointlist vendorPointlist;

    public Business() {
        userCategories = new ArrayList<UserCategory>();
    }

    public void addUserCategory(UserCategory userCategory) {
        userCategories.add(userCategory);
    }

    public void removeUserCategory(UserCategory userCategory) {
        userCategories.remove(userCategory);
    }

    public List<UserCategory> getUserCategories() {
        return userCategories;
    }

    public SiteFee getSiteFee() {
        return siteFee;
    }

    public void setSiteFee(SiteFee siteFee) {
        this.siteFee = siteFee;
    }

    public Pointlist getClientPointlist() {
        return clientPointlist;
    }

    public void setClientPointlist(Pointlist clientPointlist) {
        this.clientPointlist = clientPointlist;
    }

    public Pointlist getVendorPointlist() {
        return vendorPointlist;
    }

    public void setVendorPointlist(Pointlist vendorPointlist) {
        this.vendorPointlist = vendorPointlist;
    }
}
