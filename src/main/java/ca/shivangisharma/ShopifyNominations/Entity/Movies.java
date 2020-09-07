package ca.shivangisharma.ShopifyNominations.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Search"
})
public class Movies {

    @JsonProperty("Search")
    private List<Search> search = new ArrayList<Search>();

    @JsonProperty("Search")
    public List<Search> getSearch() {
        return search;
    }

    @JsonProperty("Search")
    public void setSearch(List<Search> search) {
        this.search = search;
    }

    public Movies withSearch(List<Search> search) {
        this.search = search;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("search", search).toString();
    }


}
