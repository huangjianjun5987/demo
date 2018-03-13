package test.com.yatang.sc.junit;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class TestUserService {

	protected final Log	log	= LogFactory.getLog(this.getClass());

	@Autowired
	private UserService	service;



	@Before
	public void setUp() throws Exception {
	}



	@Test
	public void testfindByUsername() {
		UserPo po = service.findByUsername("admin");
		System.out.println(po.toString());
	}



	@Test
	public void testfindOne() {
		UserPo po = service.findOne(1L);
		System.out.println(po.toString());
	}



	@Test
	public void testCreateUser() {
		UserPo userPo = new UserPo();
		userPo.setUserName("zhaoliu");
		userPo.setFullName("赵六");
		userPo.setMobile("18523122252");
		userPo.setPassword("123456");
		Set<Long> roleIds = new HashSet<>();
		roleIds.add(2L);
		roleIds.add(3L);
		userPo.setRoleIds(roleIds);
		boolean flag = service.createUser(userPo);
		System.out.println(flag);
	}



	@Test
	public void testUpdateUser() {
		UserPo userPo = new UserPo();
		userPo.setId(5L);
		userPo.setUserName("zhaoliu");
		userPo.setFullName("赵六");
		userPo.setMobile("18523152553");
		userPo.setPassword("123123");
		Set<Long> roleIds = new HashSet<>();
		roleIds.add(1L);
		roleIds.add(2L);
		roleIds.add(3L);
		roleIds.add(4L);
		userPo.setRoleIds(roleIds);
		boolean flag = service.updateUser(userPo);
		System.out.println(flag);
	}



	@Test
	public void testFindRoles() {
		Set<RolePo> roles = service.findRoles("zhaoliu");
		for (RolePo role : roles) {
			System.out.println(role.toString());
		}
	}



	@Test
	public void testFindPermissions() {
		Set<String> permissions = service.findPermissions("zhangsan");
		for (String permission : permissions) {
			System.out.println("permission:" + permission);
		}
	}



	@Test
	public void testFindMenusPermissions() {
		Set<ResourcePo> permissions = service.findMenusPermissions("zhangsan");
		for (ResourcePo po : permissions) {
			System.out.println("DATA:" + po.toString());
		}
	}



	@Test
	public void testFindByPage() {
		UserPo record = new UserPo();
		record.setMobile("185");
		;
		PageInfo<UserPo> pageInfo = service.findByPage(record, 1, 3);
		for (UserPo po : pageInfo.getList()) {
			System.out.println(po.toString());
		}
	}

}
