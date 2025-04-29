package com.zcqzwy.springframework.util;


import com.sun.xml.internal.ws.util.UtilException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * <p>作者： zcq</p>
 * <p>文件名称: XmlUtils </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2025/4/25 </p>
 *
 * @author <a href="mail to: 2928235428@qq.com" rel="nofollow">作者</a>
 * @version 1.0
 **/
public class XmlUtils {

  /**
   * 默认的DocumentBuilderFactory实现
   */
  private static String defaultDocumentBuilderFactory = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";


  /**
   * 是否打开命名空间支持
   */
  private static boolean namespaceAware = true;


  /**
   * 关闭XXE，避免漏洞攻击<br>
   * see: <a href="https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#JAXP_DocumentBuilderFactory.2C_SAXParserFactory_and_DOM4J">...</a>
   *
   * @param dbf DocumentBuilderFactory
   * @return DocumentBuilderFactory
   */
  private static DocumentBuilderFactory disableXXE(DocumentBuilderFactory dbf) {
    String feature;
    try {
      // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML entity attacks are prevented
      // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
      feature = "http://apache.org/xml/features/disallow-doctype-decl";
      dbf.setFeature(feature, true);
      // If you can't completely disable DTDs, then at least do the following:
      // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
      // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
      // JDK7+ - http://xml.org/sax/features/external-general-entities
      feature = "http://xml.org/sax/features/external-general-entities";
      dbf.setFeature(feature, false);
      // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
      // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
      // JDK7+ - http://xml.org/sax/features/external-parameter-entities
      feature = "http://xml.org/sax/features/external-parameter-entities";
      dbf.setFeature(feature, false);
      // Disable external DTDs as well
      feature = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
      dbf.setFeature(feature, false);
      // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
      dbf.setXIncludeAware(false);
      dbf.setExpandEntityReferences(false);
    } catch (ParserConfigurationException e) {
      // ignore
    }
    return dbf;
  }
  /**
   * 创建{@link DocumentBuilderFactory}
   * <p>
   * 默认使用"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"<br>
   * </p>
   *
   * @return {@link DocumentBuilderFactory}
   */
  public static DocumentBuilderFactory createDocumentBuilderFactory() {
    final DocumentBuilderFactory factory;
    if (StringUtils.isNotEmpty(defaultDocumentBuilderFactory)) {
      factory = DocumentBuilderFactory.newInstance(defaultDocumentBuilderFactory, null);
    } else {
      factory = DocumentBuilderFactory.newInstance();
    }
    // 默认打开NamespaceAware，getElementsByTagNameNS可以使用命名空间
    factory.setNamespaceAware(namespaceAware);
    return disableXXE(factory);
  }


  public static DocumentBuilder createDocumentBuilder() {
    DocumentBuilder builder;
    try {
      builder = createDocumentBuilderFactory().newDocumentBuilder();
    } catch (Exception e) {
      throw new UtilException(String.valueOf(e), "Create xml document error!");
    }
    return builder;
  }

  /**
   * 读取解析XML文件<br>
   * 编码在XML中定义
   *
   * @param inputStream XML流
   * @return XML文档对象
   * @throws UtilException IO异常或转换异常
   * @since 3.0.9
   */
  public static Document readXML(InputStream inputStream) throws UtilException {
    return readXML(new InputSource(inputStream));
  }


  /**
   * 读取解析XML文件<br>
   * 编码在XML中定义
   *
   * @param source {@link InputSource}
   * @return XML文档对象
   * @since 3.0.9
   */
  public static Document readXML(InputSource source) {
    final DocumentBuilder builder = createDocumentBuilder();
    try {
      return builder.parse(source);
    } catch (Exception e) {
      throw new UtilException(String.valueOf(e), "Parse XML from stream error!");
    }
  }


}
