package com.example.test.binders;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.test.utils.Constants;

/**
 * Description:
 */

public class IBinderPoolImpl extends IBinderPool.Stub{
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder mIBinder = null;
        switch (binderCode){
            case Constants.ICOMPUTECODE:
                mIBinder = new IComputeImpl();
                break;
            case Constants.IENCRYPTIONCODE:
                mIBinder = new IEncryptionImpl();
                break;
            default:
                break;
        }
        return mIBinder;
    }
}
