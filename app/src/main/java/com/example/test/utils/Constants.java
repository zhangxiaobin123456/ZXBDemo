package com.example.test.utils;

import android.os.Environment;

/**
 * Description:
 */

public class Constants {
    public static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/zxb/";
    public static final int MSGCLIENT = 0;
    public static final int MSGSERVICE = 1;
    public static final int BOOKARRIVED = 100;
    public static final int ICOMPUTECODE = 5;
    public static final int IENCRYPTIONCODE = 6;
}
