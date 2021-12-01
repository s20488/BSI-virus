package com.company;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import static com.company.Main.readFile;
import static com.company.Main.writeFile;

/**
 * BLOWFISH Algorithm used for cryptography
 *
 * Blowfish is a symmetric-key which provide a good encryption rate in software however the aes receives more attention.
 * Blowfish is a general-purpose algorithm that is intended as an alternative to DES.
 * The algorithm is hereby placed in the public domain, and can be freely used by anyone.
 *
 * @author Anastasiia Ponkratova and Julia Migiel
 * Source: https://github.com/espenfjo/lovebot/blob/master/main/Blowfish.java
 */

@SuppressWarnings("DuplicatedCode")
public class Blowfish {

    /**
     * Object which is Encrypting the entered text after choosing encryption.
     *
     * @param word it is the in main initialized String which you want to encrypt
     * @param key the secret key value which you have to enter when decrypting the ciphered test
     * @return returns the encrypted text to be printed out in the encryptDecrypt function
     */

    public static String encrypt(String word, String key) {
        try {
            byte[] keyData = (key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] text = cipher.doFinal(word.getBytes());
            return new String(Base64.getEncoder().encode(text));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Object which is Decrypting the entered text after choosing decryption.
     *
     * @param word it is the in main initialized String which you want to encrypt
     * @param key the secret key which was used to encrypt the text should be the same here
     * @return returns the decrypted text
     */

    public static String decrypt(String word, String key) {
        try {
            byte[] keyData = (key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish/CFB/NoPadding");
            Cipher cipher = Cipher.getInstance("Blowfish/CFB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] text = cipher.doFinal(java.util.Base64.getDecoder().decode(word));
            return new String(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The main object used to initialize and declare the variables
     */

    public static void encryptDecryptBlowfish() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String key;

        File test = new File("src/com/company/Test");

        do {
            System.out.println("Please enter your secret key (size = 8):");
            key = scanner.next();

            if (key.length() != 8) {
                System.out.println("Invalid key size. Try again.\n");
            }
        } while (key.length() != 8);

        do {
            System.out.println("Do you want to Encrypt (1) or Decrypt (2)?");
            choice = scanner.nextInt();

            if (choice != 1 && choice != 2) {
                System.out.println("You chose a wrong number. Try again.\n");
            }
        } while (choice != 1 && choice != 2);

        if (choice == 1) {
            for (final File fileEntry : test.listFiles()) {
                try {
                    writeFile(encrypt(readFile(fileEntry), key), fileEntry);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (choice == 2) {
            for(final File fileEntry: test.listFiles()) {
                try {
                    writeFile(decrypt(readFile(fileEntry), key), fileEntry);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Do you want to continue? Enter 1 or any other number to exit.");
        if (scanner.nextInt() == 1) {
            encryptDecryptBlowfish();
        }
    }
}