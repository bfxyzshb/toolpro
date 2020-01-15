package repositories;

import com.ziroom.zrsc.mongo.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {
    @Query("{content:'?0'}")
    List<Article> findArticleByContent(String content);

}
