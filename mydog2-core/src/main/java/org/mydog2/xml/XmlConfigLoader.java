package org.mydog2.xml;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mydog2.model.GeneratorModel;
import org.mydog2.model.JdbcConnection;
import org.mydog2.model.TableModel;
import org.mydog2.model.TableModel.ColumnOverride;
import org.mydog2.model.TableModel.ColumnSymbol;
import org.mydog2.model.TableModel.ColumnValue;
import org.mydog2.utils.JarTools;
import org.mydog2.utils.StringTools;
import org.w3c.dom.Element;

/**
 * @date 2017年6月8日 14:54:41
 * @desc 解析xml中的jdbc链接，生成表代码
 */
public class XmlConfigLoader {

	protected static XmlConfigLoader configLoader = new XmlConfigLoader();

	public static XmlConfigLoader getConfigLoader() {

		return configLoader;
	}

	private List<GeneratorModel> generators = new Vector<GeneratorModel>();
	protected JdbcConnection jdbcConnection;
	private String outputDirectory;
	private Map<String, TableModel> tableMap = new HashMap<String, TableModel>();

	public String xmlConfig;

	private XmlConfigLoader() {
	}

	public List<GeneratorModel> getGenerators() {
		return generators;
	}

	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public Map<String, TableModel> getTableMap() {
		return tableMap;
	}

	/**
	 * 加载读取xml文件
	 */
	public void load() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(xmlConfig);
			Element root = XmlUtil.getDocument(fis).getDocumentElement();
			/* 加载jdbc驱动包 */
			loadClassPathEntry(root);
			loadTable(root);
			loadJdbcConnection(root);
			loadGenerators(root);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	/**
	 * 加载ClassPath驱动包
	 */
	private void loadClassPathEntry(Element root) {
		Element element = XmlUtil.getElement(root, "classPathEntry");
		Map<String, String> properties = XmlUtil.loadAttribute(element);
		String location = properties.get("location");
		if (StringUtils.isNoneBlank(location)) {
			String[] splits = StringUtils.split(location, ',');
			for (String split : splits) {
				JarTools.loadJar(split, null);
			}
		}
	}

	private void loadGenerators(Element root) {
		Element e = XmlUtil.getElement(root, "generators");
		List<Element> elements = XmlUtil.getElements(e, "generator");
		for (Element element : elements) {
			Map<String, String> attributes = XmlUtil.loadAttribute(element);
			GeneratorModel generator = new GeneratorModel();
			generator.setRoot(attributes.get("root"));
			generator.setTargetPackage(attributes.get("targetPackage"));
			generator.setType(attributes.get("type"));
			generator.setTmplates(attributes.get("templates"));
			generators.add(generator);
		}
	}

	/**
	 * 加载jdbc数据量链接参数
	 */
	private void loadJdbcConnection(Element root) {
		Element e = XmlUtil.getElement(root, "jdbcConnection");
		jdbcConnection = new JdbcConnection();
		Map<String, String> attributes = XmlUtil.loadAttribute(e);
		jdbcConnection.setConnectionUrl(attributes.get("connectionUrl"));
		jdbcConnection.setDriverClass(attributes.get("driverClass"));
		jdbcConnection.setPassword(attributes.get("password"));
		jdbcConnection.setUsername(attributes.get("username"));
	}

	/**
	 * 加载表信息
	 */
	private void loadTable(Element root) {
		Element e = XmlUtil.getElement(root, "tables");
		List<Element> elements = XmlUtil.getElements(e, "table");
		for (Element element : elements) {
			TableModel tableModel = new TableModel();
			Map<String, String> attributes = XmlUtil.loadAttribute(element);
			String tableName = attributes.get("tableName");
			String domainObjectName = attributes.get("domainObjectName");
			String name = attributes.get("name");

			tableModel.setDomainObjectName(
					StringUtils.isBlank(domainObjectName) ? StringTools.toClassName(tableName) : domainObjectName);
			tableModel.setName(StringUtils.isBlank(name) ? tableName : name);
			tableModel.setTableName(tableName);

			List<Element> columnValues = XmlUtil.getElements(element, "columnValue");
			for (Element columnElement : columnValues) {
				String column = columnElement.getAttribute("column");
				List<Element> values = XmlUtil.getElements(element, "value");
				ColumnValue columnValue = new ColumnValue();
				columnValue.setName(column);
				for (Element valueElement : values) {
					columnValue.getMap().put(valueElement.getAttribute("key"), valueElement.getAttribute("value"));
				}
				tableModel.getColumnValue().put(column, columnValue);
			}
			
			/**
			 * 读取自定义的属性类型
			 * */
			List<Element> columnOverrides = XmlUtil.getElements(element, "columnOverride");
			for (Element columnElement : columnOverrides) {
				ColumnOverride columnOverride = new ColumnOverride();
				columnOverride.setColumn(columnElement.getAttribute("column"));
				columnOverride.setJavaType(columnElement.getAttribute("javaType"));
				columnOverride.setJdbcType(columnElement.getAttribute("jdbcType"));
				tableModel.getColumnOverride().put(columnOverride.getColumn(), columnOverride);
			}
			
			/**
			 * 读取查询链接符号
			 * */
			List<Element> columnSymbols = XmlUtil.getElements(element, "columnSymbol");
			for (Element columnElement : columnSymbols) {
				ColumnSymbol columnSymbol = new ColumnSymbol();
				columnSymbol.setColumn(columnElement.getAttribute("column"));
				columnSymbol.setSymbol(columnElement.getAttribute("symbol"));
				columnSymbol.setValue(columnElement.getAttribute("value"));
				tableModel.getColumnSymbols().put(columnSymbol.getColumn(), columnSymbol);
			}

			tableMap.put(tableName, tableModel);
		}
	}

	public XmlConfigLoader setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
		return this;
	}

	public XmlConfigLoader setXmlConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
		return this;
	}

}
