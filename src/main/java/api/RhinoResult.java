package api;

/**
 * Created by danie on 10/19/2016.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
public class RhinoResult {
    private long id;

    @Length(max = 3)
    private String content;

    public RhinoResult() {
        // Jackson deserialization
    }

    public RhinoResult(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
