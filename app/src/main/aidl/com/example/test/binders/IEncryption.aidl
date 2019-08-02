// IEncryption.aidl
package com.example.test.binders;

// Declare any non-default types here with import statements

interface IEncryption {

    String encrypt(String content);
    String decrypt(String password);
}
