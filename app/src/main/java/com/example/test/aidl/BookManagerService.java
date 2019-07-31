package com.example.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 */

public class BookManagerService extends Service{
    private static final String TAG = "BookManagerService";

    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            }
//            Log.e(TAG, "registerListener:   mListenerList大小"+mListenerList.size());
            mListenerList.register(listener);
            int size = mListenerList.beginBroadcast();
            Log.e(TAG, "registerListener:   mListenerList大小"+size);
            mListenerList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//            }else{
//                Log.e(TAG, "找不到该listener  -----");
//            }
            mListenerList.unregister(listener);
            int size = mListenerList.beginBroadcast();
            Log.e(TAG, "unregisterListener:   mListenerList大小:"+size);
            mListenerList.finishBroadcast();

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1,"西游记"));
        mBookList.add(new Book(2,"红楼梦"));
        mBookList.add(new Book(3,"水浒传"));
        mBookList.add(new Book(4,"三国演义"));

        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int i = checkCallingOrSelfPermission("Permission.BookManagerService");
        Log.e(TAG, "onBind: i "+i);
        if (i == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);

//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListener iOnNewBookArrivedListener = mListenerList.get(i);
//            iOnNewBookArrivedListener.onNewBookArrived(book);
//        }
        int size = mListenerList.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            listener.onNewBookArrived(book);
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            //为真时一直执行
            while (!mAtomicBoolean.get()){

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size()+1;
                Book book = new Book(bookId,"火影忍者第"+bookId+"话");
                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mAtomicBoolean.set(true);
        super.onDestroy();
    }
}
