package com.yatang.sc.facade.utils;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @description: mybatis 脚手架 数据库注释
 * @author: yinyuxin
 * @date: 2017/10/10 19:51
 * @version: v1.0
 */
public class CustomCommentGenerator implements CommentGenerator{

    private Properties properties;

    private Properties systemPro;

    private boolean supperssDate;

    private boolean supperssAllComments;

    private  String currentDateStr;



    public CustomCommentGenerator(){
        super();
        properties=new Properties();
        systemPro=System.getProperties();
        supperssDate = false;
        supperssAllComments = false;
        currentDateStr=(new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        supperssDate=isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        supperssAllComments=isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    protected void addJavadocTag(JavaElement javaElement,boolean markAsDoNotDelete){
        javaElement.addJavaDocLine(" *");
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(" * ");
        stringBuffer.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete){
            stringBuffer.append("do_not_delete_during_merge");
        }
        String dateString=getDateString();
        if (null != dateString){
            stringBuffer.append(" ");
            stringBuffer.append(dateString);
        }
        javaElement.addJavaDocLine(stringBuffer.toString());
    }

    protected String getDateString(){
        String result=null;
        if (!supperssDate){
            result=currentDateStr;
        }
        return null;
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {
        if (supperssAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }



    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (supperssAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }



    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

    }



    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (supperssAllComments){
            return;
        }
        innerClass.addJavaDocLine("/**");
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("/n");
        stringBuffer.append("* ");
        stringBuffer.append(introspectedTable.getFullyQualifiedTable());
        stringBuffer.append("/n");
        stringBuffer.append(getDateString());
        innerClass.addJavaDocLine(stringBuffer.toString());
        innerClass.addJavaDocLine("*/");
    }



    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }



    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (supperssAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append("/n");
        sb.append("* ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addEnumConstant("/n");
        innerEnum.addJavaDocLine("*/");
    }



    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {

    }



    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {

    }



    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }



    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }



    @Override
    public void addComment(XmlElement xmlElement) {

    }



    @Override
    public void addRootComment(XmlElement xmlElement) {

    }
}
