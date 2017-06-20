package com.jzwy.zkx.common.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Xml解析器, 注意该类只能用于基于javax.xml的注解进行解析
 */
public final class XmlParser {

    private XmlParser() {
    }

    /**
     * 将Xml字符串内容解析成指定的对象
     * @param xmlString
     * @param objClazz
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static Object parseFromXmlString(String xmlString, Class objClazz) throws JAXBException, IOException {
        StringReader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objClazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xmlString);
            Object obj = unmarshaller.unmarshal(reader);
            return obj;
        } catch (Exception e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 将xml字节数组解析成指定的对象
     * @param xmlBytes
     * @param objClazz
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static Object parseFromXmlBytes(byte[] xmlBytes, Class objClazz) throws JAXBException, IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objClazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            byteArrayInputStream = new ByteArrayInputStream(xmlBytes);
            Object obj = unmarshaller.unmarshal(byteArrayInputStream);
            return obj;
        } catch (Exception e) {
            throw e;
        } finally {
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
        }
    }

    /**
     * 将Xml文件解析成指定的对象
     * @param xmlFilePath
     * @param objClazz
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static Object parseFromXmlFile(String xmlFilePath, Class objClazz) throws JAXBException, IOException {
        FileInputStream fileInputStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objClazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            fileInputStream = new FileInputStream(xmlFilePath);
            Object obj = unmarshaller.unmarshal(fileInputStream);
            return obj;
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    /**
     * 将对象解析成Xml字节数组
     * @param obj
     * @param charset
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static byte[] getXmlBytesFromObject(Object obj, String charset) throws JAXBException, IOException {
        ByteArrayOutputStream outputStream = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
            outputStream = new ByteArrayOutputStream();
            marshaller.marshal(obj, outputStream);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 将指定的对象生成Xml字符串
     * @param obj
     * @param charset
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static String generateXmlStringFromObject(Object obj, String charset) throws JAXBException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamWriter writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
            byteArrayOutputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(byteArrayOutputStream, charset);
            marshaller.marshal(obj, writer);
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

}
