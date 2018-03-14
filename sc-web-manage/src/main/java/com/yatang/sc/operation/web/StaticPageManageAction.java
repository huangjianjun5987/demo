package com.yatang.sc.operation.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.web.config.MvcConfig;
import com.yatang.xc.mbd.biz.system.dto.UserDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.StaticPageInfoDto;
import com.yatang.sc.facade.dto.StaticPageQueyDto;
import com.yatang.sc.facade.dubboservice.StaticPageQueryDubboService;
import com.yatang.sc.facade.dubboservice.StaticPageWriteDubboService;
import com.yatang.sc.operation.vo.StaticPageInfoVo;
import com.yatang.sc.operation.vo.StaticPageQueyVo;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

import lombok.extern.log4j.Log4j;

/**
 * @描述:静态页管理action
 * @类名:StaticPageManageAction
 * @作者: lvheping
 * @创建时间: 2017/6/8 15:55
 * @版本: v1.0
 */
@Log4j
@RestController
@RequestMapping("sc/staticPage")
public class StaticPageManageAction extends BaseAction {
	@Autowired
	private StaticPageQueryDubboService	staticPageQueryDubboService;
	@Autowired
	private StaticPageWriteDubboService	staticPageWriteDubboService;
	@Autowired
	private UserDubboService userDubboService;

	// 获取Server.properties中的键值对
	@Value("${html.url.ip}")
	private String						htmlIP;
	@Value("${html.username}")
	private String						htmlUserName;
	@Value("${html.password}")
	private String						htmlPassword;
	@Value("${html.port}")
	private String						htmlPort;
	@Value("${html.path}")
	private String						htmlPath;

	@Autowired
	private ApplicationContext context;
	private ObjectMapper objectMapper;

	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = context.getBean(ObjectMapper.class);
		}
		return objectMapper;
	}

	/**
	 * 根据传入条件查询静态页列表
	 * 
	 * @param staticPageQueyVo
	 * @return
	 */
	@RequestMapping(value = "/findStaticPageList", method = RequestMethod.GET)
	public Response findStaticPageList(StaticPageQueyVo staticPageQueyVo) throws IOException {
		staticPageQueyVo = getObjectMapper().readValue(getObjectMapper().writeValueAsString(staticPageQueyVo), StaticPageQueyVo.class);
		if (staticPageQueyVo.getPageNum() == null && staticPageQueyVo.getPageSize() == null) {
			staticPageQueyVo.setPageNum(Integer.parseInt(CommonsEnum.RESPONSE_PAGE_NUM.getCode()));
			staticPageQueyVo.setPageSize(Integer.parseInt(CommonsEnum.RESPONSE_PAGE_SIZE.getCode()));
		}
		StaticPageQueyDto staticPageQueyDto = BeanConvertUtils.convert(staticPageQueyVo, StaticPageQueyDto.class);
		Response<PageResult<StaticPageInfoDto>> staticPageList = staticPageQueryDubboService
				.findStaticPageList(staticPageQueyDto);

		if (!staticPageList.isSuccess()) {
			return BeanConvertUtils.convert(staticPageList, Response.class);
		} else {
			PageResult<StaticPageInfoDto> resultObject = staticPageList.getResultObject();
			List<StaticPageInfoDto> data = resultObject.getData();
			List<StaticPageInfoVo> pageInfoVos = BeanConvertUtils.convertList(data, StaticPageInfoVo.class);

			for (StaticPageInfoVo staticPageInfoVo:pageInfoVos){
                if (staticPageInfoVo.getCreateUserId()!=null){
					Response<UserDTO> userById = userDubboService.findUserById(Integer.parseInt(staticPageInfoVo.getCreateUserId()));
                   if (userById.getResultObject()!=null){
					   staticPageInfoVo.setCreateUser(userById.getResultObject().getEmployeeName());//设置创建人名称
				   }
				}
				if (staticPageInfoVo.getUpdateUserId()!=null){
					Response<UserDTO> userById = userDubboService.findUserById(Integer.parseInt(staticPageInfoVo.getUpdateUserId()));
					if (userById.getResultObject()!=null){
						staticPageInfoVo.setUpdateuser(userById.getResultObject().getEmployeeName());//设置修改人名称
					}
				}
			}
			PageResult convert = BeanConvertUtils.convert(resultObject, PageResult.class);
			convert.setData(pageInfoVos);
			Response response = BeanConvertUtils.convert(staticPageList, Response.class);
			response.setResultObject(convert);
			return response;
		}

	}



	/**
	 * 添加静态页
	 * 
	 * @param staticPageInfoVo
	 * @return
	 */
	@RequestMapping(value = "/insertStaticPage", method = RequestMethod.POST)
	public Response<Boolean> insertStaticPage(@RequestBody StaticPageInfoVo staticPageInfoVo, HttpSession session) {
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (attribute == null) {
			Response response = new Response();
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		staticPageInfoVo.setCreateUserId(attribute.getUserId());
		StaticPageInfoDto convert = BeanConvertUtils.convert(staticPageInfoVo, StaticPageInfoDto.class);
		Response<Boolean> response = staticPageWriteDubboService.InsertStaticPage(convert);
		return response;
	}



	/**
	 * 修改静态页基本信息
	 * 
	 * @param staticPageInfoVo
	 * @return
	 */
	@RequestMapping(value = "/updateStaticPageBase", method = RequestMethod.POST)
	public Response<Boolean> updateStaticPageBase(@RequestBody StaticPageInfoVo staticPageInfoVo, HttpSession session) {
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (staticPageInfoVo.getId() == null || attribute == null) {
			Response<Boolean> response = new Response<>();
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			return response;
		}
		staticPageInfoVo.setUpdateUserId(attribute.getUserId());
		StaticPageInfoDto convert = BeanConvertUtils.convert(staticPageInfoVo, StaticPageInfoDto.class);
		Response<Boolean> response = staticPageWriteDubboService.updateStaticPageBase(convert);
		return response;
	}



	/**
	 * 修改静态页编辑内容并上传到文件服务器
	 * 
	 * @param staticPageInfoVo
	 * @return
	 */
	@RequestMapping(value = "/updateStaticPageUpload", method = RequestMethod.POST)
	public Response<Boolean> updateStaticPageUpload(@RequestBody StaticPageInfoVo staticPageInfoVo,
			HttpSession session) {
		String conten = staticPageInfoVo.getPageContent().trim();
		LoginInfoVO attribute = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (staticPageInfoVo.getId() == null || conten == null || conten.contains("<script") || attribute == null) {
			Response<Boolean> response = new Response<>();
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			return response;
		}
		staticPageInfoVo.setUpdateUserId(attribute.getUserId());
		//staticPageInfoVo.setUpdateUserId("10027");
		int htmlName = (int) (System.currentTimeMillis() / 100);
		staticPageInfoVo.setLinkUrl("/sc/html/" + htmlName + ".html");
		generateHTMLFile(staticPageInfoVo.getPageContent(), htmlName + "");
		StaticPageInfoDto convert = BeanConvertUtils.convert(staticPageInfoVo, StaticPageInfoDto.class);
		Response<Boolean> response = staticPageWriteDubboService.updateStaticPageBase(convert);
		return response;
	}



	/**
	 * 跳转到静态页修改页面根据ID查询信息回显数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUpdateStaticPage", method = RequestMethod.GET)
	public Response<StaticPageInfoVo> toUpdateStaticPage(Integer id) {
		Response<StaticPageInfoVo> response = new Response<>();
		Response<StaticPageInfoDto> staticPageInfoDto = staticPageQueryDubboService.getStaticPageById(id);

		if (!staticPageInfoDto.isSuccess()) {
			return BeanConvertUtils.convert(staticPageInfoDto, Response.class);
		}
		StaticPageInfoDto resultObject = staticPageInfoDto.getResultObject();
		StaticPageInfoVo convert = BeanConvertUtils.convert(resultObject, StaticPageInfoVo.class);
		response.setResultObject(convert);
		response.setSuccess(staticPageInfoDto.isSuccess());
		return response;
	}



	private void generateHTMLFile(String content, String name) {
		try {
			log.debug("html content :" + content);
			InputStream input = new ByteArrayInputStream(content.getBytes("utf-8"));
			boolean flag = uploadFile(htmlIP, Integer.parseInt(htmlPort), htmlUserName, htmlPassword, htmlPath,
					name + ".html", input);
			log.debug("result:" + flag + "---------");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}



	public static boolean uploadFile(String url, int port, String username, String password, String path,
			String filename, InputStream input) {
		log.debug("enter uploadFile---------");
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {

			log.info("---enter uploadFile url=" + url + " ,port=" + port + " ,username=" + username + " ,password="
					+ password + " ,path=" + path + " ,filename=" + filename + " ,input=" + input);

			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			String encoding = System.getProperty("file.encoding");
			log.info("file.encoding:" + encoding);
			ftp.setControlEncoding(encoding);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("port is close ------ftp disconnect url:" + url);
				return false;
			}
			ftp.enterLocalPassiveMode();
			log.info("connection is success");
			ftp.changeWorkingDirectory(path);
			log.info("start store file:" + path + filename);

			// 上传文件

			// Scanner scanner = new Scanner(input);
			// while (scanner.hasNext()) {
			// log.info("scanner.next-->" + scanner.next());
			// }
			// scanner.close();
			if (!ftp.storeFile(filename, input)) {
				log.info("fail store file:" + path + filename);
				return false;
			}
			log.info("end store file:" + filename);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					log.debug("connection is disconnection");
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error(ExceptionUtils.getFullStackTrace(ioe));
				}
			}
		}
		return success;
	}

}
