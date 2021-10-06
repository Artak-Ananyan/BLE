package com.example.blu

import android.os.Handler
import android.os.Message
import android.util.Log
import android.text.Editable




class MyHendler (activity: MainActivity) :
    Handler()  {

    private var activity = activity

    override fun handleMessage(msg: Message) {

        when (msg.what) {
            MainActivity.STATE_LISTENING -> MainActivity.statuss.postValue("Listening")
            MainActivity.STATE_CONNECTING ->  MainActivity.statuss.postValue("Connecting")
            MainActivity.STATE_CONNECTED ->  MainActivity.statuss.postValue("Connected")
            MainActivity.STATE_CONNECTION_FAILED -> {
                MainActivity.statuss.postValue("Connection Failed")
                this.activity.connection()
            }
            MainActivity.STATE_MESSAGE_RECEIVED -> {
                val readBuff = msg.obj as ByteArray
                val tempMsg = String(readBuff, 0, msg.arg1)
                Log.d("mylogzzzz", tempMsg)
                MainActivity.msgbox.postValue(tempMsg)
            }
        }
    }

}