/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Java program to calculate SHA hash value  
public class HashUtil {

    public static String hashSHA(String input) {
        try {
            return toHexString(getSHA(input));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        if (input != null) {
            // Static getInstance method is called with hashing SHA  
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called  
            // to calculate message digest of an input  
            // and return array of byte 
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    private static String toHexString(byte[] hash) {
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros 
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}
