package com.banvien.fc.auth.security;

import com.banvien.fc.common.util.DesEncryptionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {

    private static MyPasswordEncoder instance = new MyPasswordEncoder();

    public static MyPasswordEncoder getInstance() {
        return instance;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return DesEncryptionUtils.getInstance().encrypt(String.valueOf(charSequence));
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }
}
