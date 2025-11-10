package com.bussiness.inventory.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    public String hashEmailWithMD5(String email){
        if(email==null || email.isEmpty()){
            throw new IllegalArgumentException("email cannot be Empty");
        }
        return DigestUtils.md5Hex(email.toLowerCase().trim());
    }

    public boolean verifyEmail(String email, String hash){
        return hashEmailWithMD5(email).equals(hash);
    }
    
}
