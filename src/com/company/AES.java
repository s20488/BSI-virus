package com.company;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static com.company.Main.readFile;
import static com.company.Main.writeFile;

/**
 * AES- algorithm encryption and decryption
 *
 * AES is a block cipher.
 * The key size can be 128/192/256 bits.
 *
 * Encrypts data in blocks of 128 bits each. That means it takes 128 bits as input and outputs 128 bits of encrypted
 * cipher text as output. AES relies on substitution-permutation network principle which means it is performed using
 * a series of linked operations which involves replacing and shuffling of the input data.
 *
 * @Authors Anastasiia Ponkratova and Julia Migiel
 */

class AES {
    private static final String ALG = "AES";
    private static byte[] keyValue;

    /**
     * @param key a seed to randomize encrypte
     */

    public static byte[] AES(String key) {
        keyValue = (key).getBytes();
        return keyValue;
    }

    /**
     * data object was encrypted here
     * @param Data data to encrypt
     * @return return encrypted data
     *
     */

    public static String encrypt(String Data,byte[] keyValue) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key key = generateKey(keyValue);
        Cipher c = Cipher.getInstance(ALG);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        byte[] encodedBytes = Base64.getEncoder().encode(encVal);
        String encryptedValue = new String(encodedBytes);
        return encryptedValue;
    }

    /**
     * encryptedData object was decrypted here
     * @param encryptedData encrypted data to decrypte
     * @return return decrypted data
     *
     */

    public static String decrypt(String encryptedData,byte[] keyValue) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Key key = generateKey(keyValue);
        Cipher c = Cipher.getInstance(ALG);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decorderValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decorderValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey(byte[] keyValue) {
        Key key = new SecretKeySpec(keyValue, ALG);
        return key;
    }

    /**
     * The main object used to initialize and declare the variables
     */

    public static void encryptDecryptASE() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String key;

        File test = new File("src/com/company/Test");

        do {
            System.out.println("Please enter your secret key (size = 16):");
            key = scanner.next();

            if (key.length() != 16) {
                System.out.println("Invalid key size. Try again.\n");
            }
        } while (key.length() != 16);

        byte[] aes = AES(key);

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
                    writeFile(encrypt(readFile(fileEntry), aes), fileEntry);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (choice == 2) {
            for(final File fileEntry: test.listFiles()) {
                try {
                    writeFile(decrypt(readFile(fileEntry), aes), fileEntry);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Do you want to continue? Enter 1 or any other number to exit.");
        if (scanner.nextInt() == 1) {
            encryptDecryptASE();
        }
    }
}
