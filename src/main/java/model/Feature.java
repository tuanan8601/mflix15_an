package model;

import lombok.Data;

@Data
public class Feature {
    private String type;
    private Geo geometry;
    private Properties properties;
}
