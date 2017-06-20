package com.jzwy.zkx.common.email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.*;

/**
 * 邮件 EmailClient
 */
public class EmailClient {

    /**
     * 邮件发送对象
     */
    private JavaMailSender javaMailSender;

    /**
     * 文件保存路径
     */
    private String attachmentSavePath;

    /**
     * 临时文件列表
     */
    private List<String> tempFileList = new ArrayList<>();

    private JavaMailSender getJavaMailSender() {
        return this.javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void setAttachmentSavePath(String attachmentSavePath) {
        this.attachmentSavePath = attachmentSavePath;
    }

    /**
     * 发送邮件
     *
     * @param toAddresses   收件地址列表
     * @param ccAddresses   抄送地址列表
     * @param subject       邮件主题
     * @param content       邮件内容
     * @param linkImageMap  图片链接列表(内容中嵌套的图片)
     * @param attachmentMap 附件列表
     */
    public void sendEmail(String from, String[] toAddresses, String[] ccAddresses, String subject, String content, Map<String, String> linkImageMap, Map<String, String> attachmentMap) {
        // 生成邮件消息对象
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = generatorMessage(from, filterAddresses(toAddresses), filterAddresses(ccAddresses), subject, content, linkImageMap, attachmentMap);
        } catch (Exception e) {
            throw new RuntimeException("发送邮件时生成邮件消息对象出现异常", e);
        }

        // 发送邮件
        getJavaMailSender().send(mimeMessage);

        // 删除临时文件
        removeFile();
    }

    /**
     * 生成邮件消息对象
     */
    private MimeMessage generatorMessage(String from, String[] toAddresses, String[] ccAddresses, String subject, String content, Map<String, String> linkImageMap, Map<String, String> attachmentMap) throws Exception {
        // 创建邮件消息对象
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        // 设置发送地址
        mimeMessageHelper.setFrom(from);
        // 设置接收方地址
        mimeMessageHelper.setTo(toAddresses);
        // 设置抄送地址
        if (ccAddresses != null) {
            mimeMessageHelper.setCc(ccAddresses);
        }
        // 设置邮件主题
        mimeMessageHelper.setSubject(subject);
        // 设置邮件内容
        mimeMessageHelper.setText(content, true);

        // 添加嵌套图片
        if (linkImageMap != null && linkImageMap.size() > 0) {
            Set<String> fileNames = linkImageMap.keySet();
            Iterator<String> iterator = fileNames.iterator();
            while (iterator.hasNext()) {
                // 保存文件
                String fileName = iterator.next();
                byte[] bytes = readFile(linkImageMap.get(fileName));
                String filepath = attachmentSavePath + generatorFileName(fileName);
                saveFile(filepath, bytes);
                // 添加图片
                FileSystemResource imgResource = new FileSystemResource(new File(filepath));
                mimeMessageHelper.addInline(MimeUtility.encodeText(fileName), imgResource);
                tempFileList.add(filepath);
            }
        }

        // 添加附件
        if (attachmentMap != null && attachmentMap.size() > 0) {
            Set<String> fileNames = attachmentMap.keySet();
            Iterator<String> iterator = fileNames.iterator();
            while (iterator.hasNext()) {
                // 保存文件
                String fileName = iterator.next();
                byte[] bytes = readFile(attachmentMap.get(fileName));
                String filepath = attachmentSavePath + generatorFileName(fileName);
                saveFile(filepath, bytes);

                // 添加附件
                FileSystemResource attachmentResource = new FileSystemResource(new File(filepath));
                mimeMessageHelper.addAttachment(MimeUtility.encodeText(fileName), attachmentResource);
                tempFileList.add(filepath);
            }
        }

        return mimeMessage;
    }

    /**
     * 生成唯一文件名
     */
    private String generatorFileName(String fileName) {
        return UUID.randomUUID().toString() + fileName;
    }

    /**
     * 读取文件
     */
    private byte[] readFile(String filepath) {
        // 判断文件是否存在
        File file = new File(filepath);
        if (!file.exists()) {
            throw new RuntimeException("读取文件时文件不存在");
        }

        // 读取文件
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while (-1 != (length = fileInputStream.read(buffer, 0, bufferSize))) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("读取文件时出现异常");
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("读取文件时关闭输出流出现异常", e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("读取文件时关闭输出流出现异常", e);
                }
            }
        }
    }

    /**
     * 保存文件
     */
    private void saveFile(String filepath, byte[] bytes) {
        File file = new File(filepath);
        // 文件夹不存在创建文件夹
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 保存文件
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException("保存临时文件时出现异常", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("保存临时文件时关闭输出流出现异常", e);
                }
            }
        }
    }

    /**
     * 删除临时文件
     */
    private void removeFile() {
        if (tempFileList.size() != 0) {
            for (String filepath : tempFileList) {
                File file = new File(filepath);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 过滤邮件地址
     */
    private String[] filterAddresses(String[] addresses) {
        if (addresses == null || addresses.length <= 0) {
            return null;
        }

        List<String> addressList = new ArrayList<>();
        for (String ccAddress : addresses) {
            if (!StringUtils.isBlank(ccAddress)) {
                addressList.add(ccAddress);
            }
        }
        return addressList.toArray(new String[]{});
    }

}
