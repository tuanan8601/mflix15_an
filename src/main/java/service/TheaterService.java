package service;

import DAO.MongoDB.AbsDAO;
import DAO.MongoDB.TheaterDAO;
import com.mongodb.client.FindIterable;
import model.Feature;
import model.Properties;
import model.GeoJSON;
import model.Theater;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;



@Path("/theater")
public class TheaterService extends AbsDAO {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/geojson")
    public GeoJSON getGeoJSON() {
        GeoJSON geoJson = new GeoJSON();
        geoJson.setType("FeatureCollection");
        geoJson.setFeatures(new ArrayList<>());
        FindIterable<Theater> list = new TheaterDAO().getListTheater();
        list.forEach(theater -> {
            Feature feature = new Feature();
            feature.setType("Feature");
            feature.setGeometry(theater.getLocation().getGeo());
            feature.setProperties(new Properties());
            feature.getProperties().setAddress(theater.getLocation().getAddress().getStreet1() + ", " + theater.getLocation().getAddress().getCity());
            geoJson.getFeatures().add(feature);
        });
        return geoJson;
    }
}