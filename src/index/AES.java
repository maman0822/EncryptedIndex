/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AES {
    private static SecretKey KEY;
    private static IvParameterSpec IV;
    
    public AES(String keyPath) throws IOException{
        readKey(keyPath);
    }
    
    public AES(int keySize) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        KEY = generateKey(keySize, "AES");
        IV = generateIV();
    }
    
    public void test() {
        String plaintext = "Hello There";
        try {
            //System.out.println("==Java==");
            //System.out.println("plain:   " + plaintext);

            byte[] cipher = encryptString(plaintext);
            
            System.out.print("cipher:  ");
            for (int i=0; i<cipher.length; i++)
                System.out.print(new Integer(cipher[i])+":");
            System.out.println("");

            System.out.println("decrypt: " + decryptString(cipher));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } 
    }
    
    public Cipher getCipher(){
        try {
            return Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void encryptFile(String path) throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(path);
        FileOutputStream outStream = new FileOutputStream(new File(path).getName() + "encrypted");
        byte[] buffer = new byte[1048576];
        int readBytes = 0;
        try {
            while((readBytes = stream.read(buffer, 0, buffer.length)) > 0)
            {
                if(readBytes == buffer.length){
                    byte[] encrypted = encrypt(buffer);
                    outStream.write(encrypted, 0, encrypted.length);
                }
                else if(readBytes < buffer.length){
                    byte[] lastBytes = new byte[readBytes];
                    System.arraycopy(buffer, 0, lastBytes, 0, readBytes);
                    byte[] encrypted = encrypt(lastBytes);
                    outStream.write(encrypted, 0, encrypted.length);
                }
            }
            outStream.flush();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        } finally {
            stream.close();
            outStream.close();
        }
    }
    
    public void decryptFile(String path) throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(path);
        FileOutputStream outStream = new FileOutputStream(new File(path).getName().replace("encrypted", ""));
        byte[] buffer = new byte[1048576];
        int readBytes = 0;
        try {
            while((readBytes = stream.read(buffer, 0, buffer.length)) > 0)
            {
                if(readBytes == buffer.length){
                    byte[] decrypted = decrypt(buffer);
                    outStream.write(decrypted, 0, decrypted.length);
                }
                else if(readBytes < buffer.length){
                    byte[] lastBytes = new byte[readBytes];
                    System.arraycopy(buffer, 0, lastBytes, 0, readBytes);
                    byte[] decrypted = decrypt(lastBytes);
                    outStream.write(decrypted, 0, decrypted.length);
                }
            }
            outStream.flush();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        } finally {
            stream.close();
            outStream.close();
        }
    }
    
    public byte[] hashBytes(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return hash;
    }
    
    public byte[] encryptString(String plain){
        try {
            byte[] cipher = encrypt(plain.getBytes("UTF-8"));
            return cipher;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return new byte[1];
    }
    
    public String decryptString(byte[] cipher){
        try {
            byte[] decrypted = decrypt(cipher);
            String decipher = new String(decrypted);
            return decipher;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
        
    private SecretKey generateKey(int size, String Algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(Algorithm);
        keyGen.init(size);
        return keyGen.generateKey();
    }
    
    private IvParameterSpec generateIV()
    {
        byte[] b = new byte[16];
        new Random().nextBytes(b);
        return new IvParameterSpec(b);    
    }
    
    public void saveKey(SecretKey key, IvParameterSpec IV, String path) throws IOException{
        FileOutputStream stream = new FileOutputStream(path);
        try {
            stream.write(key.getEncoded());
            stream.write(IV.getIV());
        } finally {
            stream.close();
        }
    }
    
    public void saveKey(String path) throws IOException{
        FileOutputStream stream = new FileOutputStream(path);
        try {
            stream.write(KEY.getEncoded());
            stream.write(IV.getIV());
        } finally {
            stream.close();
        }
    }
        
    public void readKey(String path) throws FileNotFoundException, IOException{
        FileInputStream stream = new FileInputStream(path);
        File f = new File(path);
        int keySize = (int)f.length()- 16;
        try {
            byte[] key = new byte[keySize];
            byte[] iv = new byte[16];
            stream.read(key, 0, keySize);
            stream.read(iv, 0, 16);
            KEY = new SecretKeySpec(key, 0, keySize, "AES");
            IV = new IvParameterSpec(iv);
        } finally {
            stream.close();
        }
    }
    
    private static byte[] encrypt(byte[] plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        cipher.init(Cipher.ENCRYPT_MODE, KEY, IV);
        return cipher.doFinal(plainText);
    }

    private byte[] decrypt(byte[] cipherText) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        cipher.init(Cipher.DECRYPT_MODE, KEY , IV);
        return cipher.doFinal(cipherText);
    }
}
