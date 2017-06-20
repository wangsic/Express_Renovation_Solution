package com.jzwy.zkx.common.encrypt;

/**
 * MD5安全加密
 */
public class MD5SecurityEncoder extends MessageDigestSecurityEncoder {

    public MD5SecurityEncoder(){
        super("MD5");
    }

}
