package test.com.yatang.sc.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dubboservice.RoleDubboService;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestRoleDubboService {

	@Resource(name = "roleDubboService1")
	private RoleDubboService service;

	@Test
	public void createRole() {
		RoleDto roleDto = new RoleDto();
		roleDto.setUpdateUser("admin");
		roleDto.setCreateUser("admin");
		roleDto.setRole("test");
		roleDto.setAvailable(true);
		roleDto.setRoleCode("test");
		roleDto.setDescription("I am a role..");
		Response<Boolean> response = service.createRole(roleDto);
		int i = 0;
	}

	@Test
	public void updateRole() {
		RoleDto record = new RoleDto();
		record.setId(5L);
		record.setDescription("I am new description");
		record.setUpdateTime(new Date());
		record.setRoleCode("cold");
		record.setUpdateUser("user");
		Response<Boolean> response = service.updateRole(record);
		int i = 0;
	}

	@Test
	public void findOne() {
		Response<RoleDto> response = service.findOne(5L);
		int i = 0;
	}

	@Test
	public void deleteRole() {
		List<Long> ids = new ArrayList<>();
		ids.add(45L);
		ids.add(44L);
		Response<Boolean> response = service.deleteRole(ids);
		int i = 0;
	}

	@Test
	public void isRelatedWithUser() {
		Response<Boolean> response = service.isRelatedWithUser(4L);
		int i = 0;
	}

	@Test
	public void queryRelevantUsers() {
		Response<List<UserDto>> response =service.queryRelevantUsers(3L);
		int i = 0;
	}

	@Test
	public void queryRelevantRes() {
		Response<List<ResourceDto>> response = service.queryRelevantRes(3L);
		int i = 0;
	}

	@Test
	public void queryRoleByParam() {
		RoleDto record = new RoleDto();
		record.setRoleCode("d");
		record.setRole("理");
		record.setPageNum(1);
		record.setPageSize(10);
		Response<PageResult<RoleDto>> response = service.queryRoleByParam(record);
		int i = 0;
	}

	@Test
	public void addResToRole() {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(3L);
		Set<Long> set = new HashSet();
		set.add(52L);
		set.add(53L);
		roleDto.setResourceIds(set);
		Response<Boolean> response = service.addResToRole(roleDto);
		int i = 0;
	}

	@Test
	public void removeResFromRole() {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(3L);
		Set<Long> set = new HashSet();
		set.add(52L);
		set.add(53L);
		roleDto.setResourceIds(set);
		Response<Boolean> response = service.removeResFromRole(roleDto);
		int i = 0;
	}

	@Test
	public void queryRolesByLoginName() {
		Response<PageResult<RoleDto>> pageInfo = service.queryRolesByLoginName("admin", 10, 1);
		int i = 0;
	}

}
