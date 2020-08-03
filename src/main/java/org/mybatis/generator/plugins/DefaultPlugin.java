package org.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.MergeConstants;

/**
 * 
 * TODO 重写dao层接口以及XML中的生成方法
 * 
 * @author booyaka
 * @date 2019年12月11日 下午2:06:59
 */
public class DefaultPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	/**
	 * Model添加继承类
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * TODO " + introspectedTable.getRemarks() + "Model");
		topLevelClass.addJavaDocLine(" *");
		topLevelClass.addJavaDocLine(" * @author booyaka");
		topLevelClass.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		topLevelClass.addJavaDocLine(" */");
		topLevelClass.setSuperClass("BaseModel");
		topLevelClass.addImportedType("com.booyaka.commons.BaseModel");
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * 重写dao
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
		interfaze.getMethods().clear();
		interfaze.addJavaDocLine("/**");
		interfaze.addJavaDocLine(" * TODO " + introspectedTable.getRemarks() + "Dao");
		interfaze.addJavaDocLine(" *");
		interfaze.addJavaDocLine(" * @author booyaka");
		interfaze.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		interfaze.addJavaDocLine(" */");
		interfaze.addAnnotation("@Mapper");
		interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
		interfaze.addMethod(insertSelective(interfaze, introspectedTable));
		interfaze.addMethod(deleteByPrimaryKey(interfaze, introspectedTable));
		interfaze.addMethod(updateByPrimaryKeySelective(interfaze, introspectedTable));
		interfaze.addMethod(selectByPrimaryKey(interfaze, introspectedTable));
		interfaze.addMethod(selectSelective(interfaze, introspectedTable));
		return true;
	}

	private Method insertSelective(Interface interfaze, IntrospectedTable introspectedTable) {
		Method insertSelective = new Method("insertSelective");
		insertSelective.setReturnType(FullyQualifiedJavaType.getIntInstance());
		insertSelective.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()),
				fristLower(introspectedTable.getBaseRecordType())));
		insertSelective.addException(new FullyQualifiedJavaType("Exception"));
		insertSelective.setAbstract(true);
		context.getCommentGenerator().addGeneralMethodComment(insertSelective, introspectedTable);
		return insertSelective;
	}

	private Method deleteByPrimaryKey(Interface interfaze, IntrospectedTable introspectedTable) {
		Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
		deleteByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());
		deleteByPrimaryKey
				.addParameter(new Parameter(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType(),
						introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
		deleteByPrimaryKey.addException(new FullyQualifiedJavaType("Exception"));
		deleteByPrimaryKey.setAbstract(true);
		context.getCommentGenerator().addGeneralMethodComment(deleteByPrimaryKey, introspectedTable);
		return deleteByPrimaryKey;
	}

	private Method updateByPrimaryKeySelective(Interface interfaze, IntrospectedTable introspectedTable) {
		Method updateByPrimaryKeySelective = new Method("updateByPrimaryKeySelective");
		updateByPrimaryKeySelective.setReturnType(FullyQualifiedJavaType.getIntInstance());
		updateByPrimaryKeySelective
				.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()),
						fristLower(introspectedTable.getBaseRecordType())));
		updateByPrimaryKeySelective.addException(new FullyQualifiedJavaType("Exception"));
		updateByPrimaryKeySelective.setAbstract(true);
		context.getCommentGenerator().addGeneralMethodComment(updateByPrimaryKeySelective, introspectedTable);
		return updateByPrimaryKeySelective;
	}

	private Method selectByPrimaryKey(Interface interfaze, IntrospectedTable introspectedTable) {
		Method selectByPrimaryKey = new Method("selectByPrimaryKey");
		selectByPrimaryKey.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		selectByPrimaryKey
				.addParameter(new Parameter(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType(),
						introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
		selectByPrimaryKey.setAbstract(true);
		context.getCommentGenerator().addGeneralMethodComment(selectByPrimaryKey, introspectedTable);
		return selectByPrimaryKey;
	}

	private Method selectSelective(Interface interfaze, IntrospectedTable introspectedTable) {
		interfaze.addImportedType(FullyQualifiedJavaType.getNewListInstance());
		Method selectSelective = new Method("selectSelective");
		selectSelective
				.setReturnType(new FullyQualifiedJavaType("List<" + introspectedTable.getBaseRecordType() + ">"));
		selectSelective.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()),
				fristLower(introspectedTable.getBaseRecordType())));
		selectSelective.setAbstract(true);
		context.getCommentGenerator().addGeneralMethodComment(selectSelective, introspectedTable);
		return selectSelective;
	}

	/**
	 * 重写 mapper
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();
		List<VisitableElement> list = document.getRootElement().getElements();
		list.remove(7);
		list.remove(4);
		/* SQL_WHERE */
		XmlElement sql = new XmlElement("sql");
		sql.addElement(
				new TextElement("<!-- "+MergeConstants.NEW_ELEMENT_TAG+" This element is automatically generated by MyBatis Generator," + " do not modify.-->"));
		sql.addAttribute(new Attribute("id", "SQL_WHERE"));
		XmlElement where = new XmlElement("where");
		
		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
			XmlElement isNotNullElement = new XmlElement("if");
			sb.setLength(0);
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null");
//			sb.append(" &amp;&amp; ");
//			sb.append(introspectedColumn.getJavaProperty());
//			sb.append(" != ''");
			isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
			where.addElement(isNotNullElement);
			sb.setLength(0);
			sb.append(" and ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			isNotNullElement.addElement(new TextElement(sb.toString()));
		}
		sql.addElement(where);
		parentElement.addElement(sql);
		/* selectSelective */
		XmlElement select = new XmlElement("select");
		select.addElement(
				new TextElement("<!-- "+MergeConstants.NEW_ELEMENT_TAG+" This element is automatically generated by MyBatis Generator," + " do not modify.-->"));
		select.addAttribute(new Attribute("id", "selectSelective"));
		select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		select.addElement(new TextElement(" select * from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "SQL_WHERE"));
		select.addElement(include);
		parentElement.addElement(select);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private String fristLower(String str) {
		str = str.substring(str.lastIndexOf(".") + 1, str.length());
		char[] chars = str.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
}
