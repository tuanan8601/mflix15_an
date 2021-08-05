package DAO;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieDAO extends AbsDAO {
    public List<Movie> getMovies(int limit) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        List<Movie> list = new ArrayList<>();
        movies.find().limit(limit).forEach(d -> list.add(d));
        return list;
    }

    public Movie getMovieByID(String id) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        Movie movie = movies.find(eq("_id", new ObjectId(id))).first();
        return movie;
    }
    public DistinctIterable<String> getGenres() {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        DistinctIterable<String> genres = movies.distinct("genres", String.class);
        return genres;
    }
    public AggregateIterable<Document> getTopGenres(int limit) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        AggregateIterable<Document> result = movies.aggregate(Arrays.asList(new Document("$unwind", "$genres"),
                new Document("$group", new Document("_id", "$genres").append("numOfMovies",new Document("$sum", 1L))),
                new Document("$sort", new Document("numOfMovies", -1L)),
                new Document("$limit",limit)));
        return result;
    }
    public long getMoviesNumber(Document filter) {
        MongoCollection<Document> movies = getDB().getCollection("movies");
        return movies.countDocuments(filter);
    }
    public List<Movie> searchMovies(Document filter, Document sort, int limit, int skip) {
        MongoCollection<Movie> movies = getDB().getCollection("movies", Movie.class);
        List<Movie> list = new ArrayList<>();
        movies.find(filter).sort(sort).limit(limit).skip(skip).forEach(d -> list.add(d));
        return list;
    }
}
