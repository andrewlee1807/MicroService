package com.banvien.fc.sms.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;


public class Main {
	
	    public static void main(String... argv) throws Exception {
	    	String path = Main.class.getResource("/").getPath();
//	        //First generate a public/private key pair
//	        KeyPair pair = generateKeyPair();
//	        KeyPair pair = getKeyPairFromKeyStore();	        
	   
	    	PrivateKey privateKey = PEM.readPrivateKey(RsaFileUtils.readFileWithBase64(path, "private.key"));
	    	PublicKey publicKey = PEM.readPublicKey(RsaFileUtils.readFileWithBase64(path, "public.key"));
	    	
	        //Our secret message
	        String message = "the answer to life the universe and everything";

	        //Encrypt the message
	        String cipherText = RSA.encrypt(message, publicKey);
	        System.out.println(cipherText);
	        //Now decrypt it
	        String decipheredMessage = RSA.decrypt(cipherText, privateKey);
	        System.out.println(decipheredMessage);
//	        //Let's sign our message
	        String signature = RSA.sign("foobar",privateKey);
	        System.out.println("Signature : " + signature);
//	        //Let's check the signature
	        boolean isCorrect = RSA.verify("foobar",signature, publicKey);
	        System.out.println("Signature correct: " + isCorrect);
	    }

}
