import com.ziroom.zrsc.mongo.exception.MongodbException;
import com.ziroom.zrsc.mongo.model.Article;
import com.ziroom.zrsc.mongo.repositories.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


//@ContextConfiguration(classes = MongoConfig.class)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)  
public class MongoDbTest {  

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 批量保存实体
     */
    @Test
    public void findOrderByName(){
        List<Article> artileList=new ArrayList<Article>();
        try {
            for(int i=0;i<10;i++){
                Article article=new Article();
                article.setId(""+i);
                article.setTitle("测试"+i);
                article.setContent("测试测试");
                artileList.add(article);
            }
            mongoTemplate.insert(artileList,Article.class);
        } catch (Exception e) {
            e.printStackTrace();
            new MongodbException("批量插入monogodb失败");
        } finally {
        }
    }


    @Test
    public void findOneArtile(){
        Criteria where=Criteria.where("id").is("1");
        Query query=Query.query(where);
        Article article=mongoTemplate.findOne(query,Article.class);
        System.out.println(article);

    }

    @Test
    public void findArtiles(){
        Criteria where=Criteria.where("title").is("shb");
        Query query=Query.query(where);
        List<Article> articles=mongoTemplate.find(query,Article.class);
        System.out.println(articles);

    }


    /**
     * 通过mongo原始查询语句查询
     */
    @Test
    public void findArtileByContent(){
        List<Article> articleList=articleRepository.findArticleByContent("测试");
        System.out.println(articleList);

    }


}  