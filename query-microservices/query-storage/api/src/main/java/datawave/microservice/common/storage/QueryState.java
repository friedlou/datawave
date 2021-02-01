package datawave.microservice.common.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The query state represents the current state of a query
 */
@XmlRootElement
public class QueryState {
    private QueryKey queryKey;
    private Map<QueryTask.QUERY_ACTION,Integer> taskCounts;
    
    public QueryState(QueryKey queryKey, Map<QueryTask.QUERY_ACTION,Integer> taskCounts) {
        this(queryKey.getQueryId(), queryKey.getType(), taskCounts);
    }
    
    @JsonCreator
    public QueryState(@JsonProperty("queryId") UUID queryId, @JsonProperty("queryType") QueryType queryType,
                    @JsonProperty("taskCounts") Map<QueryTask.QUERY_ACTION,Integer> taskCounts) {
        this.queryKey = new QueryKey(queryType, queryId);
        this.taskCounts = Collections.unmodifiableMap(new HashMap<>(taskCounts));
    }
    
    @JsonIgnore
    public QueryKey getQueryKey() {
        return queryKey;
    }
    
    public UUID getQueryId() {
        return getQueryKey().getQueryId();
    }
    
    public QueryType getQueryType() {
        return getQueryKey().getType();
    }
    
    public Map<QueryTask.QUERY_ACTION,Integer> getTaskCounts() {
        return taskCounts;
    }
    
    @Override
    public String toString() {
        return getQueryKey() + ": " + getTaskCounts();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof QueryState) {
            QueryState other = (QueryState) o;
            return new EqualsBuilder().append(getQueryKey(), other.getQueryKey()).append(getTaskCounts(), other.getTaskCounts()).isEquals();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getQueryKey()).append(getTaskCounts()).toHashCode();
    }
}