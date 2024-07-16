package org.example.response;


import io.vertx.core.json.JsonObject;
import org.example.entity.Entites;

import java.util.ArrayList;
import java.util.List;

public class entitesResponse {


    private  List<Entites> employeesList = new ArrayList<>();

    public JsonObject toJsonObject(){
        return JsonObject.mapFrom(this);
    }

    public  List<Entites> getEmployeesList() {
        return employeesList;
    }

    public  void  setEmployeesList(List<Entites> employeesList) {
        this.employeesList = employeesList;
    }
}
