package com.banvien.fc.sms.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RsaFileUtils {
	public static void writeFileWithBase64(String path, String filename, byte[] key) {
    	try {
			FileOutputStream fos = new FileOutputStream(path+"/"+filename);
			fos.write(Base64.encodeBase64String(key).getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static InputStream readFileWithBase64(String path, String filename) {
    	try {
    		InputStream is = new FileInputStream(path+"/"+filename);
			return is;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

    public static InputStream stringToInputStream(String source){
		try {
			InputStream is = IOUtils.toInputStream(source, UTF_8.toString());
			return is;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
