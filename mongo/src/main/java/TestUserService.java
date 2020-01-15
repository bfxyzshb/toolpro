import java.sql.Timestamp;
import java.util.List;

import com.ziroom.zrsc.mongo.model.UserInfo;
import com.ziroom.zrsc.mongo.service.UserService;
import com.ziroom.zrsc.mongo.util.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestUserService {

	@Autowired
	private UserService service;

	@Test
	public void save() {
		UserInfo user = new UserInfo();
		user.setName("王五");
		user.setAge(29);
		user.setBirth(new Timestamp(System.currentTimeMillis()));
		user.setProDesc("测试测试");
		service.save(user);
		System.out.println("已生成ID:" + user.getId());
	}

	@Test
	public void find() {
		UserInfo user = service.find("58edf1b26f033406394a8a61");
		System.out.println(user.getName());
	}

	@Test
	public void update() {
		UserInfo user = service.find("58edf1b26f033406394a8a61");
		user.setAge(18);
		service.update(user);
	}

	@Test
	public void delete() {
		service.delete("58edef886f03c7b0fdba51b9");
	}

	@Test
	public void findAll() {
		List<UserInfo> list = service.findAll("age desc");
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@Test
	public void findLikeByUserDesc() {
		UserInfo userInfo=new UserInfo();
		userInfo.setProDesc("测");
		List<UserInfo> list = service.findByLikeProps(userInfo);
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@Test
	public void findByProp() {
		List<UserInfo> list = service.findByProp("name", "张三");
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@Test
	public void findByProps() {
		List<UserInfo> list = service.findByProps(new String[] { "name", "age" }, new Object[] { "张三", 18 });
		for (UserInfo u : list) {
			System.out.println(u.getName());
		}
	}

	@Test
	public void pageAll() {
		PageModel<UserInfo> page = service.pageAll(1, 10);
		System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
		for (UserInfo u : page.getList()) {
			System.out.println(u.getName());
		}
	}

	@Test
	public void pageByProp() {
		PageModel<UserInfo> page = service.pageByProp(1, 10, "name", "张三");
		System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
		for (UserInfo u : page.getList()) {
			System.out.println(u.getName());
		}
	}

}
