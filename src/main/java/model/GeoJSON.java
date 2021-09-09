package model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GeoJSON {
    private String type;
    private ArrayList<Feature> features;
}