package com.rio.contanctsapplication.model.testing

import com.rio.contanctsapplication.model.cryptography.EncryptionDecryption

fun main(){
//    EncryptionDecryption().apply{
//        val cipherText = encryptText(5, "hai Hello how are you ")
//        val plainText = decryptCipher(5, cipherText)
//        println("cipher Text = $cipherText\nPlain Text = $plainText")
//    }

    EncryptionDecryption().apply {
        generateEncryptionDecryptionKeyFrom("a3")
    }
}