package com.example.blu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.Exception

class BluetoothLostReceiver : BroadcastReceiver() {
    var main: MainActivity? = null

    fun setMainActivity(main: MainActivity?) {
        this.main = main
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if(BluetoothDevice.ACTION_ACL_DISCONNECTED == intent!!.action)
        {
            Log.d("mylog", "connection failed")
           main!!.connection()
        }


        Log.d("mylog", intent.action.toString())
    }
}