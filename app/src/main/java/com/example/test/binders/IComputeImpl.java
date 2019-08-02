package com.example.test.binders;

import android.os.RemoteException;

/**
 * Description:
 */

public class IComputeImpl extends ICompute.Stub{
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
