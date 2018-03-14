/**
 * 
 */
package com.yatang.sc.web.common.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContextAware;

/**
 * @author 邓东山
 *
 */
public interface IExcelExportService extends ApplicationContextAware{
	void excelExport(String name, List exportData, HttpServletResponse response) throws Exception;
}
