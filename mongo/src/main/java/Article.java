import com.ziroom.zrsc.mongo.util.QueryType;
import com.ziroom.zrsc.mongo.annotation.QueryField;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Article {
    @Id
    private String id;  
  

    private String title;  
  
    @QueryField(type = QueryType.LIKE, attribute = "content")
    private String content;

    @QueryField(type = QueryType.IN, attribute = "title")
    private List<String> queryTitles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getQueryTitles() {
        return queryTitles;
    }

    public void setQueryTitles(List<String> queryTitles) {
        this.queryTitles = queryTitles;
    }
}