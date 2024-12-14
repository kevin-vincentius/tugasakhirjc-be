package com.bcaf.tugasakhir.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;
import org.mindrot.jbcrypt.BCrypt;
import org.bouncycastle.crypto.engines.AESLightEngine;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDate;


public class Crypto {
//    6cc5500a-738e-417b-8a74-ce431da1e99d
    private static final String key = "kevin123";

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

//    private static boolean performEncrypt(String cryptoText) {
//        return performEncrypt(key, cryptoText);
//    }

    public static void main(String[] args) throws Exception {
//        Admin
//        username: 20240351
//        password: admin123

        Long userId = 20240353L;
        String password = "staff123";
        String strToEncrypt = userId + password;
        System.out.println("Encryption Result : " + BCrypt.hashpw(strToEncrypt, BCrypt.gensalt(11)));

//        boolean matches = BcryptImpl.verifyHash("20240351admin123", "$2a$11$vHPrQ3aEmi1whEe.74jBveZaLtBQEsfJkyNVWSVmIy45pyBe08lMu");
//        if (matches) {
//            System.out.println("Password is correct!");
//        } else {
//            System.out.println("Invalid password!");
//        }

//        SecretKey secretKey = generateKey();
//        System.out.println("Encryption Result : "+performEncrypt(strToEncrypt));
//        LocalDate date = LocalDate.parse("2024-11-10");
//        System.out.println(date);
    }

}
