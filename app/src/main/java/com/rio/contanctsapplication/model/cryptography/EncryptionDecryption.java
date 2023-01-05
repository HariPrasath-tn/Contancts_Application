package com.rio.contanctsapplication.model.cryptography;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="#">EncryptionDecryption</a> class provides the cryptography function such as
 * <a href="link ignored">encryptText</a>(returns encrypted cipher text), <a href="link ignored">decryptCipher</a>(returns
 * decrypted plain text).
 */
public class EncryptionDecryption {
    // shuffled list of the ascii values
    private static List<Integer> asciiShuffled = Arrays.asList(249, 33, 91, 81, 237, 233, 121, 76, 211, 184, 45, 242, 226, 199, 172, 168, 54, 235, 71, 253, 88, 217, 50, 219, 236, 163, 57, 77, 92, 42, 250, 59, 191, 190, 245, 186, 181, 234, 171, 49, 120, 166, 179, 43, 109, 215, 103, 197, 69, 180, 173, 218, 205, 244, 230, 224, 210, 62, 98, 74, 248, 40, 66, 68, 94, 90, 79, 64, 126, 162, 251, 170, 73, 80, 222, 89, 70, 203, 254, 107, 174, 44, 36, 196, 183, 185, 46, 192, 216, 119, 41, 96, 102, 243, 47, 52, 51, 193, 113, 93, 204, 178, 111, 117, 247, 85, 56, 202, 252, 169, 201, 239, 55, 209, 116, 238, 112, 208, 223, 194, 34, 60, 39, 200, 10, 87, 188, 115, 32, 105, 48, 195, 95, 165, 124, 246, 167, 61, 221, 187, 175, 255, 106, 86, 37, 75, 220, 35, 229, 38, 123, 118, 83, 232, 97, 125, 84, 114, 99, 228, 182, 78, 207, 227, 64, 110, 122, 198, 82, 240, 108, 189, 65, 231, 104, 58, 72, 212, 176, 100, 225, 161, 206, 177, 63, 67, 101, 214, 53, 241, 213, 164);


    /**
     * <a href="encryptText">encryptedText</a> method provides standard encryption for the given text
     * @param key
     * @param plainText
     * @return cipherText
     */
    public String encryptText(int key, String plainText){
        /*
         * shuffled order of list of elements of ascii values are stored in the list [asciiShuffled]
         * methodology:
         *      iterating through the characters of the plainString:
         *          * finding the ascii value for the character since the ascii values only stored
         *          in the list
         *          * finding the position of ascii in the list [asciiShuffled] and storing it in
         *          the variable [characterIndexInList]
         *          * adding the key to the value stored in the characterIndexInList, then taking
         *          modulo of 190(size of the list) to the resultant of the sum inorder to act it
         *          as a circular list (explained in next point) to achieve the caesar cipher encryption
         *          {En(x) = (x+n)mod(190) } where n=key, x=position of the character in the list
         *
         *          * let the list of alphabet be [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]
         *          Given:
         *              ==> message "bye"
         *              ==> key=5 and character=b, index of b is 1 and adding the value of the key
         *              to it will give 6. character in the index 4 is 'g' appending it to the cipherString
         *
         *              ==> similarly suppose character=y and key=5, index of 'y' is 24 and adding
         *              the key will give 29 which is out of the range of the list. in this case we
         *              need to take the modulo by 26 which will return 3, character at the index 3
         *              is 'd'. finally appending it to the cipher text.
         *              ==> similarly for 'e' -> ''. now the encrypted cipherText = "gdj"
         */

        // String used to store the ciphered string provided after the encryption
        String cipherString = "";
        /*
         * ==> characterIndexInList is used to store the index of the current character's ascii
         * value in the list [asciiShuffled]
         * ==> newIndex is used as an container to store the new position of the character after adding
         * the key to the index of the current character in the List [asciiShuffled]
         */
        int characterIndexInList, newIndex;
        // iterating through all the characters of the given plainText by converting it into
        // char array
        for(char plainTextCharacter : plainText.toCharArray()){
            // finding the index of the current character in the list [asciiShuffled]
            characterIndexInList = asciiShuffled.indexOf((int)plainTextCharacter);
            /* adding the key to the characterIndexInList and taking modulo of 190 to it
             * inorder to prevent the overflow of the index
             * for example list size = 190
             *     if the index of the character == 187, and key = 5 adding them together gives us
             *          192 which is greater than the list which will result in exception to prevent
             *          and apply the caesar cipher we need to take the modulo
             *          here taking modulo will provide 2 which is the newIndex  */
            newIndex = (characterIndexInList + key) % 190;
            /* character for the ascii value present in the position newIndex in the list [asciiShuffled]
             * is appended to the string cipherString */
            cipherString += (char)((int)asciiShuffled.get(newIndex));
        }
        return cipherString;
    }


    /**
     * <a href="decryptCipher">encryptedText</a> method provides standard encryption for the given text
     * @param key of type Int representing the key used for decryption
     * @param cipherText of type String representing the string to be decrypted
     * @return plainText
     */
    public String decryptCipher(int key, String cipherText){
        /*
         * shuffled order of list of elements of ascii are stored in the list [asciiShuffled]
         * methodology:
         *      iterating through the characters of the CipherText:
         *          * finding the ascii value for the character.
         *          * finding the position of ascii in the list [asciiShuffled] and storing it in
         *          the variable [characterIndexInList]
         *          * subtracting the key from the value stored in the characterIndexInList and adding
         *          it with 190. as the negative value indicates the position of the character in reverse
         *          order.
         *          * for example character at -1 = 'z' when we see in reverse order.
         *          similarly (190-1) = 189 which is == 'z'.
         *          * formula for caesar cipher decryption is {Pn(x) = (190+x-n)mod(190) } where n=key, x=position of the character in the list
         *
         *          * let's consider the previous case in the encryption cipherText = "gdj"
         *          and list of alphabet be [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]
         *          Given:
         *              ==> cipherText = "gdj"
         *              ==> length of list = 26
         *              ==> key=5 and character=g, position of 'g' x = 6 :: Pn(6) = (26+6-5)mod 26
         *              191 mod 190 = 1 which is 'b'
         *
         *              ==> similarly d ==> (26+3-5)mod 26 = 24 ==> 'y'
         *              ==> j ==> (26+9-5)mod 26 = 4 ==> 'e'
         *          plainText = "bye".                                                            */

        // String used to store the ciphered string provided after the encryption
        String plainString = "";
        /*
         * ==> characterIndexInList is used to store the index of the current character's ascii
         * value in the list [asciiShuffled]
         * ==> newIndex is used as an container to store the new position of the character after adding
         * the key to the index of the current character in the List [asciiShuffled]
         */
        int newIndex, characterIndexInList;
        // iterating through all the characters of the given plainText by converting it into
        // char array
        for(char plainTextCharacter : cipherText.toCharArray()){
            // finding the index of the current character in the list [asciiShuffled]
            characterIndexInList = asciiShuffled.indexOf((int)plainTextCharacter);
            newIndex = (190 + characterIndexInList - key) % 190;
            /* character for the ascii value present in the position newIndex in the list [asciiShuffled]
             * is appended to the string plainString */
            plainString += (char)((int)asciiShuffled.get(newIndex));
        }
        return plainString;
    }


    /**
     * <a href="link ignored">generateEncryptionDecryptionKeyFrom</a> method used to generate the
     * key for user data encryption.
     *
     * @param keyString of type String representing the key string used to generate the key for
     *                  encryption and decryption
     * @return key: derived key
     */
    public int generateEncryptionDecryptionKeyFrom(String keyString){
        // variable that stores the key
        int key=0, len=keyString.length();
        long temp=0;
        //iterating through the characters of the string
        for(int characterPosition=0; characterPosition<len; characterPosition++){
            // generating the hash for the string
            temp += (long)((int)keyString.charAt(characterPosition))*Math.pow(31, len-1-characterPosition);
        }
        // taking the modulo of 190 to the hash generated since the length of the list is 190
        key = (int) (temp%190);
        return key;
    }
}
