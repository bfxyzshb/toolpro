package base;

import com.ziroom.zrsc.mongo.util.ReflectBuildQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class MongodbBaseDao<T>{
  
    @Autowired
    protected MongoTemplate mongoTemplate;
  
    //保存一个对象到mongodb  
    public T save(T bean) {  
        mongoTemplate.save(bean);  
        return bean;  
    }  
  
    // 根据id删除对象  
    public void deleteById(T t) {  
        mongoTemplate.remove(t);  
    }  
  
    // 根据对象的属性删除  
    public void deleteByCondition(T t) {  
        Query query = ReflectBuildQueryUtils.buildBaseQuery(t);
        mongoTemplate.remove(query, getEntityClass());  
    }  
  
    // 通过条件查询更新数据  
    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, this.getEntityClass());  
    }  
  
    // 根据id进行更新  
    public void updateById(String id, T t) {  
        Query query = new Query();  
        query.addCriteria(Criteria.where("id").is(id));
        Update update = ReflectBuildQueryUtils.buildBaseUpdate(t);
        update(query, update);  
    }  
  
    // 通过条件查询实体(集合)  
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());  
    }  
  
    public List<T> findByCondition(T t) {  
        Query query = ReflectBuildQueryUtils.buildBaseQuery(t);
        return mongoTemplate.find(query, getEntityClass());  
    }  
  
    // 通过一定的条件查询一个实体
    //eg:Query query=new Query(Criteria.where("xxx").is(xxx));
    public T findOne(Query query) {  
        return mongoTemplate.findOne(query, this.getEntityClass());  
    }  
  
  
    // 通过ID获取记录  
    public T get(String id) {  
        return mongoTemplate.findById(id, this.getEntityClass());  
    }  
  
    // 通过ID获取记录,并且指定了集合名(表的意思)  
    public T get(String id, String collectionName) {  
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);  
    }


  
    // 获取需要操作的实体类class  
    @SuppressWarnings("unchecked")  
    protected Class<T> getEntityClass() {  
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }  
  
    public MongoTemplate getMongoTemplate() {  
        return mongoTemplate;  
    }  
} 