package com.wapss.digo360.authentication;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptLib {

    public static String encrypt(String text, String manager) throws Exception {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(manager.toCharArray(), iv, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));

        byte[] encryptedText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        byte[] combined = new byte[iv.length + encryptedText.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedText, 0, combined, iv.length, encryptedText.length);

        return Base64.encodeToString(combined, Base64.DEFAULT);
    }

    public static String decrypt(String text, String manager) throws Exception {
        byte[] combined = Base64.decode(text, Base64.DEFAULT);
        byte[] iv = new byte[16];
        byte[] encryptedText = new byte[combined.length - 16];

        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encryptedText, 0, encryptedText.length);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(manager.toCharArray(), iv, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

        byte[] decryptedText = cipher.doFinal(encryptedText);
        return new String(decryptedText, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Hello, World!";
        String manager = "yourManagerPassword";

        String encrypted = encrypt(plaintext, manager);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = decrypt(encrypted, manager);
        System.out.println("Decrypted: " + decrypted);
    }
}
