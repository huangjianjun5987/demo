package com.yatang.sc.operation.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.Constants;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ZiptoftpInfoDto;
import com.yatang.sc.facade.dubboservice.ZiptoftpInfoDubboService;
import com.yatang.sc.operation.util.ImageDomainJoinUrlUtil;
import com.yatang.sc.operation.vo.Base64DataVo;
import com.yatang.sc.operation.vo.SingleUploadResult;
import com.yatang.sc.operation.vo.ZiptoftpInfoResultVo;
import com.yatang.sc.operation.vo.ZiptoftpInfoVo;
import com.yatang.xc.mbd.biz.image.dubboservice.ImageDubboService;

import lombok.RequiredArgsConstructor;

/**
 * @描述: 上传文件的通用action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/23 20:47
 * @版本: v1.0
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/commonUploadFile")
public class CommonUploadFileAction extends BaseAction {
	private static final Logger				log	= LoggerFactory.getLogger(CommonUploadFileAction.class);
	// 获取Server.properties中的键值对
	@Value("${html.url.ip}")
	private String							url;
	@Value("${html.username}")
	private String							username;
	@Value("${html.password}")
	private String							password;
	@Value("${html.port}")
	private String							port;
	@Value("${html.view.url}")
	private String							viewUrl;
	private final ZiptoftpInfoDubboService	ziptoftpInfoDubboService;
	private final ImageDubboService			imageDubboService;

	private final ImageDomainJoinUrlUtil	imageDomainJoinUrlUtil;										// 图片路径



	/**
	 * 图片类型的文件上传
	 *
	 * @param file
	 * @return
	 * @author yangshuang
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public Response<SingleUploadResult> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
		Response<SingleUploadResult> responseResult = new Response<>();
		SingleUploadResult result = new SingleUploadResult();
		try {
			// 获取文件名和文件流
			InputStream inputStream = file.getInputStream();
			byte[] body = new byte[inputStream.available()];
			inputStream.read(body);
			Response<String> response = imageDubboService.uploadFile(body, "png");
			if (!CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())) {
				return BeanConvertUtils.convert(response, Response.class);
			}
			// 解析url
			if (null == response.getResultObject()) {
				// 解析异常
				log.error("上传图片文件出错,无法获取服务端资源");
				responseResult.setCode(CommonsEnum.RESPONSE_10006.getCode());
				responseResult.setSuccess(false);
				responseResult.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				return responseResult;
			}
			result.setSuffixUrl(response.getResultObject());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseResult.setCode(CommonsEnum.RESPONSE_500.getCode());
			responseResult.setSuccess(false);
			responseResult.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			return responseResult;

		}
		result.setImageDomain(imageDomainJoinUrlUtil.getImageViewDomain());// 图片服务器地址
		responseResult.setResultObject(result);
		responseResult.setCode(CommonsEnum.RESPONSE_200.getCode());
		responseResult.setSuccess(true);
		responseResult.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return responseResult;
	}



	/**
	 * 图片类型的文件上传批量
	 *
	 * @param file
	 * @return
	 * @author yangshuang
	 */
	@RequestMapping(value = "/uploadImageFiles", method = RequestMethod.POST)
	public Response<List<SingleUploadResult>> uploadImageFiles(
			@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		Response<List<SingleUploadResult>> responseResult = new Response<>();

		List<SingleUploadResult> singleUploadResults = new ArrayList<>();
		try {
			for (MultipartFile multipartFile : file) {

				// 获取文件名和文件流
				InputStream inputStream = multipartFile.getInputStream();
				byte[] body = new byte[inputStream.available()];
				inputStream.read(body);
				Response<String> response = imageDubboService.uploadFile(body, "png");
				if (!CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())) {
					return BeanConvertUtils.convert(response, Response.class);
				}
				// 解析url
				if (null == response.getResultObject()) {
					// 解析异常
					log.error("上传图片文件出错,无法获取服务端资源");
					responseResult.setCode(CommonsEnum.RESPONSE_10006.getCode());
					responseResult.setSuccess(false);
					responseResult.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
					return responseResult;
				}
				SingleUploadResult singleUploadResult = new SingleUploadResult();
				singleUploadResult.setSuffixUrl(response.getResultObject());
				singleUploadResult.setImageDomain(imageDomainJoinUrlUtil.getImageViewDomain());
				singleUploadResults.add(singleUploadResult);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseResult.setCode(CommonsEnum.RESPONSE_500.getCode());
			responseResult.setSuccess(false);
			responseResult.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			return responseResult;

		}
		responseResult.setResultObject(singleUploadResults);
		responseResult.setCode(CommonsEnum.RESPONSE_200.getCode());
		responseResult.setSuccess(true);
		responseResult.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return responseResult;
	}



	/**
	 * 图片类型Base64上传
	 *
	 * @param base64Data
	 *            base64Data图片流
	 * @return
	 * @author: yangshuang
	 */

	@RequestMapping(value = "/uploadImageBase64Data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Response<SingleUploadResult> uploadImageBase64Data(@RequestBody Base64DataVo base64Data) {
		Response<SingleUploadResult> responseResult = new Response<>();
		SingleUploadResult result = new SingleUploadResult();
		String base64Content = base64Data.getBase64Content();// 接收数据内容
		try {
			log.debug("上传文件的数据：" + base64Content);
			String dataPrix = null;
			String data = null;
			if (base64Content == null || "".equals(base64Content)) {
				responseResult.setSuccess(false);
				responseResult.setErrorMessage(CommonsEnum.RESPONSE_10003.getName());
				responseResult.setCode(CommonsEnum.RESPONSE_10003.getCode());
				log.error("上传失败，上传图片数据为空");
				return responseResult;
			} else {
				String[] d = base64Content.split("base64,");// 切分数据
				if (d != null && d.length == 2) {
					dataPrix = d[0];
					data = d[1];
				} else {
					responseResult.setSuccess(false);
					responseResult.setCode(CommonsEnum.RESPONSE_10004.getCode());
					responseResult.setErrorMessage(CommonsEnum.RESPONSE_10004.getName());
					return responseResult;
				}
			}
			log.debug("对数据进行解析，获取文件名和流数据");
			String suffix = "";
			if (Constants.BASE64_IMAGE_JPEG.equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
				suffix = "jpg";
			} else if (Constants.BASE64_IMAGE_X_ICON.equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
				suffix = ".ico";
			} else if (Constants.BASE64_IMAGE_GIF.equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
				suffix = "gif";
			} else if (Constants.BASE64_IMAGE_PNG.equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
				suffix = "png";
			} else {
				responseResult.setSuccess(false);
				responseResult.setCode(CommonsEnum.RESPONSE_10005.getCode());
				responseResult.setErrorMessage(CommonsEnum.RESPONSE_10005.getName());
				return responseResult;
			}
			// 解析base64位bytes类型
			byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(data);
			Response<String> response = imageDubboService.uploadFile(bytes, suffix);
			// 判断是否响应出错
			if (!CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())) {
				return BeanConvertUtils.convert(response, Response.class);
			}
			result.setSuffixUrl(response.getResultObject());// 相对地址
			result.setImageDomain(imageDomainJoinUrlUtil.getImageViewDomain());// 图片服务器地址
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseResult.setCode(CommonsEnum.RESPONSE_500.getCode());
			responseResult.setSuccess(false);
			responseResult.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			return responseResult;
		}

		responseResult.setResultObject(result);
		responseResult.setSuccess(true);
		responseResult.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return responseResult;
	}



	/**
	 * 上传图片并返回绝对地址
	 *
	 * @param file
	 * @return
	 * @author lvheping
	 */
	@RequestMapping(value = "/uploadImageReturnUrl", method = RequestMethod.POST)
	public Response<SingleUploadResult> uploadImageReturnUrl(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Response<SingleUploadResult> responseResult = new Response<>();
		SingleUploadResult result = new SingleUploadResult();
		try {
			// 获取文件名和文件流
			InputStream inputStream = file.getInputStream();
			byte[] body = new byte[inputStream.available()];
			inputStream.read(body);
			Response<String> response = imageDubboService.uploadFile(body, "png");
			if (!CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())) {
				return BeanConvertUtils.convert(response, Response.class);
			}
			// 解析url
			if (null == response.getResultObject()) {
				// 解析异常
				log.error("上传图片文件出错,无法获取服务端资源");
				responseResult.setCode(CommonsEnum.RESPONSE_10006.getCode());
				responseResult.setSuccess(false);
				responseResult.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				return responseResult;
			}
			result.setFileOnServerUrl(response.getResultObject());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseResult.setCode(CommonsEnum.RESPONSE_500.getCode());
			responseResult.setSuccess(false);
			responseResult.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			return responseResult;

		}
		result.setFileOnServerUrl(imageDomainJoinUrlUtil.getImageViewDomain() + "/" + result.getFileOnServerUrl());
		responseResult.setResultObject(result);
		responseResult.setCode(CommonsEnum.RESPONSE_200.getCode());
		responseResult.setSuccess(true);
		responseResult.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return responseResult;
	}



	@RequestMapping(value = "/uploadZip", method = RequestMethod.POST)
	public Response<Integer> uploadZipAndUnzipToFTP(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request, @Valid ZiptoftpInfoVo ziptoftpInfoVo) {
		log.info("/sc/commonUploadFile/uploadZip收到请求，上传文件名：{}", file.getOriginalFilename());
		Response<Integer> result;

		if (file.isEmpty()) {
			log.info("上传文件为空，返回500");
			return getFailResponse();
		}
		// 生成文件夹名称
		String path = "/" + file.getOriginalFilename().replace(".zip", "");
		dealWithZip(file, request, path);

		// 保存文件信息
		ZiptoftpInfoDto ziptoftpInfoDto = BeanConvertUtils.convert(ziptoftpInfoVo, ZiptoftpInfoDto.class);
		ziptoftpInfoDto.setRootPath(viewUrl + "/html" + path);
		ziptoftpInfoDto.setZipName(file.getOriginalFilename());
		if (null != ziptoftpInfoVo && null != ziptoftpInfoVo.getId()) {
			// 更新
			result = ziptoftpInfoDubboService.updateIfAbsent(ziptoftpInfoDto);
		} else {
			// 新增
			result = ziptoftpInfoDubboService.insert(ziptoftpInfoDto);
		}

		return result;
	}



	@RequestMapping(value = "/deleteZipById", method = RequestMethod.GET)
	public Response<Boolean> deleteZipById(@Valid @NotBlank Integer id) {
		log.info("/sc/commonUploadFile/deleteZipById收到请求，删除ID：{}", id);
		Response<ZiptoftpInfoDto> result = ziptoftpInfoDubboService.selectByPrimaryKey(id);
		if (CommonsEnum.RESPONSE_10006.getCode().equals(result.getCode())) {
			return getSuccessResponse();
		}
		ZiptoftpInfoDto ziptoftpInfoDto = result.getResultObject();
		String rootPath = ziptoftpInfoDto.getRootPath();
		FTPClient ftpClient = null;
		boolean delete = false;
		try {
			ftpClient = getFTPClient();
			delete = deleteDirectory(rootPath.replace(viewUrl + "/html", ""), ftpClient);
		} catch (Exception e) {
			log.info("删除FTP文件夹失败，id：{}", id);
		} finally {
			try {
				if (null != ftpClient) {
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!delete) {
			return getFailResponse();
		}
		Response<Boolean> ok = ziptoftpInfoDubboService.deleteByPrimaryKey(id);
		if ("500".equals(ok.getCode())) {
			return getFailResponse();
		}
		return getSuccessResponse(true);
	}



	public boolean deleteDirectory(String remote, FTPClient ftpClient) throws Exception {
		ftpClient.changeWorkingDirectory(remote);
		FTPFile[] files = ftpClient.listFiles();
		for (FTPFile f : files) {
			if (f.isFile()) {
				if (ftpClient.deleteFile(remote.equals("/") ? remote + f.getName() : remote + "/" + f.getName())) {
				} else {
					return false;
				}
			} else {
				deleteDirectory(remote.equals("/") ? remote + f.getName() : remote + "/" + f.getName(), ftpClient);
			}
		}
		if (!ftpClient.removeDirectory(remote)) {
			return false;
		}
		return true;
	}



	@RequestMapping(value = "/selectZiptoftpInfoList", method = RequestMethod.GET)
	public Response<PageResult<ZiptoftpInfoDto>> selectZiptoftpInfoList(Integer pageNum, Integer pageSize) {
		if (null == pageNum) {
			pageNum = 1;
		}
		if (null == pageSize) {
			pageSize = 15;
		}
		log.info("/sc/commonUploadFile/selectZiptoftpInfoList收到请求，pageNum={},pageSize={}", pageNum, pageSize);
		Response<PageResult<ZiptoftpInfoDto>> result = ziptoftpInfoDubboService
				.selectZiptoftpInfoList(new ZiptoftpInfoDto(), pageNum, pageSize);
		if ("500".equals(result.getCode())) {
			return getFailResponse();
		}
		return result;
	}



	@RequestMapping(value = "/selectPath", method = RequestMethod.GET)
	public Response<ZiptoftpInfoResultVo> selectPath(@Valid @NotBlank Integer id) {
		log.info("/sc/commonUploadFile/selectPath收到请求，查询ID：{}", id);
		Response<ZiptoftpInfoDto> result = ziptoftpInfoDubboService.selectByPrimaryKey(id);
		if ("500".equals(result.getCode())) {
			return getFailResponse();
		}
		ZiptoftpInfoResultVo ziptoftpInfoResultVo = BeanConvertUtils.convert(result.getResultObject(),
				ZiptoftpInfoResultVo.class);
		return getSuccessResponse(ziptoftpInfoResultVo);
	}



	private void dealWithZip(MultipartFile file, HttpServletRequest request, String path) {

		String realPath = request.getServletContext().getRealPath("ziptemp");
		File ziptemp = null;
		ZipFile zipFile = null;
		InputStream in = null;
		File upload = null;
		FTPClient ftp = null;
		try {
			// 创建上传临时目录
			ziptemp = new File(realPath);
			if (!ziptemp.exists()) {
				ziptemp.mkdirs();
			}
			// 上传的真实文件名
			String originalFilename = file.getOriginalFilename();
			upload = new File(realPath, originalFilename);
			FileUtils.copyInputStreamToFile(file.getInputStream(), upload);
			log.info("ZIP文件上传成功，临时文件路径：{}", upload.getAbsolutePath());

			zipFile = new ZipFile(upload, Charset.forName("GBK"));
			// 连接FTP,创建根目录
			ftp = getFTPClient(path);

			for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String fileName = new String(entry.getName().getBytes("GBK"), "iso-8859-1");
				if (entry.isDirectory()) {
					log.info("当前为文件夹，在FTP上创建文件夹：{}", path + "/" + fileName);
					ftp.makeDirectory(path + "/" + fileName);
					continue;
				}
				in = zipFile.getInputStream(entry);

				String outPath = (path + "/" + fileName).replaceAll("\\*", "/");
				if (!ftp.storeFile(outPath, in)) {
					zipFile.close();
					throw new RuntimeException(
							"文件" + (path + "/" + entry.getName()).replaceAll("\\*", "/") + "上传FTP失败，结束上传。");
				}
				log.info("上传文件到FTP成功：{}", (path + "/" + entry.getName()).replaceAll("\\*", "/"));
				in.close();

			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException("上传过程出错");
		} finally {
			try {
				if (null != zipFile) {
					log.info("关闭压缩文件流:{}", zipFile.getName());
					zipFile.close();
				}
				if (null != in) {
					in.close();
				}
				if (null != ziptemp) {
					boolean delete1 = upload.delete();
					// boolean delete2 = ziptemp.delete();
					log.info("删除临时文件：{},{}", upload.getAbsolutePath(), delete1);

				}
				if (null != ftp) {
					ftp.disconnect();
				}
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
	}



	private FTPClient getFTPClient(String path) throws Exception {
		FTPClient ftp = new FTPClient();
		int reply;
		ftp.connect(url, Integer.parseInt(port));
		ftp.login(username, password);
		String encoding = System.getProperty("file.encoding");
		ftp.setControlEncoding(encoding);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();

		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new RuntimeException();
		}
		ftp.enterLocalPassiveMode();
		ftp.makeDirectory(path);
		return ftp;
	}



	private FTPClient getFTPClient() throws Exception {
		FTPClient ftp = new FTPClient();
		int reply;
		ftp.connect(url, Integer.parseInt(port));
		ftp.login(username, password);
		String encoding = System.getProperty("file.encoding");
		ftp.setControlEncoding(encoding);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();

		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new RuntimeException();
		}
		ftp.enterLocalPassiveMode();
		return ftp;
	}
}
