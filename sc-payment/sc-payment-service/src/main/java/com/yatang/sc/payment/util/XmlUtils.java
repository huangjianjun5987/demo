package com.yatang.sc.payment.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/8.
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinPayUtils.class.getClass());

    public static String mapToXml(Map<String, String> pMap) {
        List<String> keys = new ArrayList<String>(pMap.keySet());
        Collections.sort(keys);
        String xmlStart = "<xml>";
        String xmlEnd = "</xml>";
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append("<" + key + ">");
            sb.append(pMap.get(key));
            sb.append("</" + key + ">");
        }
        return xmlStart + sb.toString() + xmlEnd;
    }

    public static Map<String, String> xml2Map(String xmlString, String rootElement) {
        try {
            Document doc = DocumentHelper.parseText(xmlString);
            Element body = (Element) doc.selectSingleNode("/" + rootElement);
            Map<String, String> vo = new HashMap<String, String>();
            if (body != null) {
                List<Element> elements = body.elements();
                for (Element element : elements) {
                    String key = element.getName();
                    String value = "";
                    if (StringUtils.isNotEmpty(key)) {
                        value = element.getText().trim();
                    }
                    vo.put(key, value);
                }
            }
            return vo;
        } catch (DocumentException e) {
            logger.error("Occur Exception xml to map", e);
        }
        return null;
    }
}
