package DAO.MovieDB.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryMovie {

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("results")
    private List<ResultsItem> results;

    @JsonProperty("total_results")
    private int totalResults;

    public int getPage(){
        return page;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public List<ResultsItem> getResults(){
        return results;
    }

    public int getTotalResults(){
        return totalResults;
    }
}