package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.ziroom.zrsc.mongo.annotation.QueryField;
import com.ziroom.zrsc.mongo.util.QueryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户实体类
 * <p>
 * ClassName: UserInfo
 * </p>
 * <p>
 * Description:本类用来展示MongoDB实体类映射的使用
 * </p>
 * <p>
 * Copyright: (c)2017 Jastar·Wang,All rights reserved.
 * </p>
 * 
 * @author Jastar·Wang
 * @date 2017年4月12日
 */
@Document(collection = "user_info")
public class UserInfo implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	// 主键使用此注解
	@Id
	private String id;

	// 字段使用此注解
	@Field
	private String name;

	// 字段还可以用自定义名称
	@Field("myage")
	private int age;

	//生成索引
	@Indexed(name = "index_birth", direction = IndexDirection.DESCENDING)
	@Field
	private Date birth;
	@Field("desc")
	@QueryField(type= QueryType.LIKE,attribute ="desc")
	private String proDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}



	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}
}
