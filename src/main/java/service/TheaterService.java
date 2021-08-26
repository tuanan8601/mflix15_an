package service;

import DAO.MongoDB.AbsDAO;
import DAO.MongoDB.TheaterDAO;
import com.mongodb.client.FindIterable;
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
    public ArrayList<Theater> getListTheater() {
        FindIterable<Theater> list = new TheaterDAO().getListTheater();
        return list.into(new ArrayList<>());
    }
}