package test.com.yatang.sc.facade;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dto.system.UserDto;
import com.yatang.sc.facade.dubboservice.UserDubboService;

/**
 * @描述: 供应商只读服务测试类
 * @作者: huangjianjun
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestUserDubboService {
	@Autowired
	private UserDubboService service;
	
	@Test
	public void testQueryByUsername(){
		Response<UserDto> response = service.findByUsername("admin");
		UserDto dto = response.getResultObject();
		System.out.println(dto.toString());
	}
	
	@Test
	public void testFindOne(){
		Response<UserDetailDto> response = service.findOne(1L);
		UserDetailDto dto = response.getResultObject();
		System.out.println(dto.toString());
	}
	
	@Test
	public void testFindPage(){
		UserDetailDto record = new UserDetailDto();
		record.setUserName("admin");
		Response<PageResult<UserDetailDto>> response = service.findByPage(record, 1, 3);
		PageResult<UserDetailDto> page = response.getResultObject();
		System.out.println(page.getPageNum());
		System.out.println(page.getPageSize());
		System.out.println(page.getTotal());
		List<UserDetailDto> dtos = page.getData();
		for (UserDetailDto userDto : dtos) {
			System.out.println(userDto.toString());
		}
	}
	
	@Test
	public void testFindPermissions(){
		Response<Set<String>> response = service.findPermissions("zhangsan");
		Set<String> set = response.getResultObject();
		for (String s : set) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testFindUserRoles(){
		Response<Set<RoleDto>> response = service.findUserRoles("zhangsan");
		Set<RoleDto> set = response.getResultObject();
		for (RoleDto s : set) {
			System.out.println(s.toString());
		}
	}
	
	@Test
	public void testFindMenusPermissions(){
		Response<Set<ResourceDto>> response = service.findMenusPermissions("zhangsan");
		Set<ResourceDto> set = response.getResultObject();
		for (ResourceDto s : set) {
			System.out.println(s.toString());
		}
	}
	
	@Test
	public void testAuthorRoles(){
		Response<Boolean> res = service.authorRoles(5L, "1,2,6");
	}
	
	@Test
	public void testDeleteUserRoles(){
		Response<Boolean> res = service.deleteUserRoles(50L, "1,6");
	}
	
}
