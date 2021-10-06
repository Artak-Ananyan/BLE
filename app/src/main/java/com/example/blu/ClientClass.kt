package com.example.blu

import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*

class ClientClass(device: BluetoothDevice, var handler: MyHendler) : Thread() {

    private var socket: BluetoothSocket? = null


    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    init {
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun run() {
        try {
            socket!!.connect()
            val message = Message.obtain()
            message.what = MainActivity.STATE_CONNECTED
            handler.sendMessage(message)
            MainActivity.sendReceive = SendReceive(socket!!, handler)
            MainActivity.sendReceive!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
            val message = Message.obtain()
            Log.d("mylog", message.toString())
            message.what = MainActivity.STATE_CONNECTION_FAILED
            handler.sendMessage(message)
        }
    }



}