package com.yatang.sc.shiro.filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.yatang.xc.mbd.biz.system.dto.MenuDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.AuthorityDubboService;

/**
 * 
 * <class description>shiro初始化时调用接口获取URL级别的权限控制
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月1日
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
	private Logger					log	= LoggerFactory.getLogger(ShiroPermissionFactory.class);
	@Autowired
	private AuthorityDubboService	authorityDubboService;

	// 通过server.properties属性注入
	@Value(value = "${shiro.gyl.code}")
	private String					gylCode;



	/**
	 * 设置过滤链
	 */
	@Override
	public void setFilterChainDefinitions(String definitions) {
		Map<String, String> chains = new LinkedHashMap<>();
		try {
			List<MenuDTO> menuList = authorityDubboService.queryPrivilegesDirectory(-1).getResultObject().getMenuList();
			// 顶级部门分类
			for (MenuDTO menuDTO : menuList) {
				if (gylCode.equals(menuDTO.getAuthorityCode())) {
					// 查询供应链下面的菜单
					List<MenuDTO> list = authorityDubboService.queryPrivilegesDirectory(menuDTO.getAuthorityId())
							.getResultObject().getMenuList();
					if (!StringUtils.isBlank(menuDTO.getMenuUrl())) {
						chains.put(menuDTO.getMenuUrl(), "perms[\"" + menuDTO.getAuthorityCode() + "\"]");
					}
					// 一级菜单
					for (MenuDTO menuDTO2 : list) {
						if (!StringUtils.isBlank(menuDTO2.getMenuUrl())) {
							chains.put(menuDTO2.getMenuUrl(), "perms[\"" + menuDTO2.getAuthorityCode() + "\"]");
						}
						// 二级菜单
						for (MenuDTO menuDTO3 : menuDTO2.getMenuList()) {
							if (!StringUtils.isBlank(menuDTO3.getMenuUrl())) {
								chains.put(menuDTO3.getMenuUrl(), "perms[\"" + menuDTO3.getAuthorityCode() + "\"]");
							}
							// 三级菜单
							for (MenuDTO menuDTO4 : menuDTO3.getMenuList()) {
								if (!StringUtils.isBlank(menuDTO4.getMenuUrl())) {
									chains.put(menuDTO4.getMenuUrl(), "perms[\"" + menuDTO4.getAuthorityCode() + "\"]");
								}
							}
						}
					}
				}
			}
			chains.put("/**", "authc");
		} catch (Exception e) {
			log.error("ShiroPermissionFactory has error!", e);
			throw new RuntimeException("拉取权限问题出错，严重！ at：com.yatang.sc.shiro.filter.ShiroPermissionFactory");
		}
		Ini ini = new Ini();
		// 设置配置的
		ini.load(definitions);
		Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		}
		// 设置获取的
		section.putAll(chains);
		setFilterChainDefinitionMap(section);
	}

}
