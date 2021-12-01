package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Decryption and Encryption Algorithms for BSI classes PJATK Gdańsk
 *
 * @author Anastasiia Ponkratova and Julia Migiel
 * @version IntelliJ IDEA 2021.3 Build #IU-213.5744.223, built on November 27, 2021
*/

public class Main {
    public static void main(String[] args) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

        System.out.println("Welcome to the Encryption and Decryption Program\n");

        System.out.println("""
                Choose which algorithm you want to use:
                1. DES (Data Encryption Standard);
                2. AES (Advanced Encryption Standard);
                3. Blowfish algorithm.
                """
        );

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                DES.encryptDecryptDES();
                break;
            case 2:
                AES.encryptDecryptASE();
                break;
            case 3:
                Blowfish.encryptDecryptBlowfish();
                break;
        }
    }

    /**
     * This method is getting the string from a file
     *
     * @param file file where from where string is read
     * @return returns a string
     */

    static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * This method is writing the string to a file
     *
     * @param file file from where string is read
     * @param str string value in a file
     */

    static void writeFile(String str, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(str);
        printWriter.close();
    }
}