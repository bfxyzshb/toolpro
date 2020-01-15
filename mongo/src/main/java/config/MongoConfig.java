package config;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.ziroom.zrsc.mongo.repositories")
public class MongoConfig {


  
    /** 
     *创建mongo数据库实例：localhost指定数据库服务地址//也可以指定端口号，默认是27017 
     */  
    @Bean
    public MongoFactoryBean mongo(){  
        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setHost("localhost");  
        return mongo;  
    }  
  
    /** 
     *设置当前数据库名称为OrdersDB, MongoOperations的功能很强大 
     *能够实现数据库的绝大部分操作， 但是没有用到他的功能 
     */  
    @Bean  
    public MongoOperations mongoTemplate(Mongo mongo){
        return new MongoTemplate(mongo, "shb");
    }  
} 