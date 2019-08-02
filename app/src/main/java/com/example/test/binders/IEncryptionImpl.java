package com.example.test.binders;

import android.os.RemoteException;

/**
 * Description:
 */

public class IEncryptionImpl extends IEncryption.Stub{
    private static final char SECRETCODE = '^';
    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRETCODE;
        }
        return new String(chars);

    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
