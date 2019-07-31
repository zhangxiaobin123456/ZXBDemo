package com.example.test.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.test.R;
import com.example.test.utils.Constants;

import java.util.List;

public class AidlActivity extends AppCompatActivity {

    private static final String TAG = "AidlActivity";
    private IBookManager mIBookManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                mIBookManager = iBookManager;
                iBookManager.addBook(new Book(5,"三打白骨精"));

                List<Book> bookList = iBookManager.getBookList();

                for (int i = 0; i < bookList.size(); i++) {
                    Book book = bookList.get(i);
                    Log.e(TAG, "Book: bookName ————"+book.getBookName() +"|||  bookId ————" +book.getBookId());
                }
                //注册
                iBookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.BOOKARRIVED:
                   Book book = (Book) msg.obj;
                    Log.e(TAG, "Book: bookName ————"+book.getBookName() +"|||  bookId ————" +book.getBookId());
                    break;
                    default:
                        super.handleMessage(msg);

                        break;
            }
        }
    };

    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(Constants.BOOKARRIVED,book).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mIBookManager != null && mIBookManager.asBinder().isBinderAlive()) {
            try {
                //取消注册
                mIBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
