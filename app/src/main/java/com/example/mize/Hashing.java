package com.example.mize;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing  {
    public String  hash(String  str)
    {
        String inputString = str;

        //You can replace "SHA-256" with other algorithms such as "MD5" or "SHA-512"
        // Create an instance of the SHA-256 algorithm
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Convert the input string to bytes
        byte[] encodedHash = digest.digest(inputString.getBytes(StandardCharsets.UTF_8));

        // Convert the hashed bytes to a hexadecimal representation
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }

        // Print the hashed string
//        System.out.println();
        return hexString.toString();
    }
}
