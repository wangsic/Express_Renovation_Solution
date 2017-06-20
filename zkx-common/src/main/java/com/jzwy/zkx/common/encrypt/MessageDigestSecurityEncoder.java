package com.jzwy.zkx.common.encrypt;

import com.jzwy.zkx.common.codec.Utf8;
import com.jzwy.zkx.common.util.ByteUtils;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 抽象的报文摘要安全加密
 */
public abstract class MessageDigestSecurityEncoder implements SecurityEncoder {

    /**
     * 报文摘要算法 e.g. MD5
     */
    private String algorithm;

    /**
     * 加密的字符返回Base64文本
     */
    private boolean encodeHashAsBase64;

    /**
     * 加密次数
     */
    private int iterations = 1;

    public MessageDigestSecurityEncoder(String algorithm){
        this(algorithm, false);
    }

    public MessageDigestSecurityEncoder(String algorithm, boolean encodeHashAsBase64){
        this.algorithm = algorithm;
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    /**
     * 加密方法，该加密暂时不考虑加盐值，为了安全 盐值无需用户提供，每次随机生成；多重加密
     * @param rawInput
     * @return
     */
    @Override
    public String encrypt(String rawInput) throws SecurityEncoderException {

        byte[] rawInputBytes = null;
        MessageDigest messageDigest = null;

        try {
            rawInputBytes = rawInput.getBytes(CHARSET_UTF8);
            messageDigest = MessageDigest.getInstance(this.algorithm);
        }
        catch (UnsupportedEncodingException e){
            throw new SecurityEncoderException(e);
        }
        catch (NoSuchAlgorithmException e){
            throw new SecurityEncoderException(e);
        }

        messageDigest.update(rawInputBytes);

        byte[] digest = messageDigest.digest(rawInputBytes);

        for (int i = 1; i < this.iterations; i++) {
            digest = messageDigest.digest(digest);
        }

        if (this.encodeHashAsBase64) {
            return Utf8.decode(Base64Utils.encode(digest));
        }
        else {
            return ByteUtils.ToHexString(digest);
        }
    }

    @Override
    public boolean matches(String rawInput, String encryptedInput) {
        try {
            return Objects.equals(this.encrypt(rawInput), encryptedInput);
        }
        catch (SecurityEncoderException e){
            return false;
        }
    }

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
