package com.example.danbidemo1;

import android.os.Handler;
import android.os.Message;

public class IntroThread extends Thread {

    private Handler handler;

    public IntroThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();

        Message message = new Message();

        try{
            Thread.sleep(2000);
            message.what = 1;
            handler.sendEmptyMessage(message.what);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
