package com.example.blu

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class SendReceive(socket: BluetoothSocket, handler: MyHendler) : Thread() {

    private var bluetoothSocket: BluetoothSocket? = socket
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private var handler: MyHendler? = handler

    init {
        var tempIn: InputStream? = null
        var tempOut: OutputStream? = null
        try {
            tempIn = bluetoothSocket!!.inputStream
            tempOut = bluetoothSocket!!.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
        inputStream = tempIn
        outputStream = tempOut
    }


    override fun run() {
        val buffer = ByteArray(1024)
        var bytes: Int
        while (true) {
            try {
                bytes = inputStream!!.read(buffer)
                handler!!.obtainMessage(MainActivity.STATE_MESSAGE_RECEIVED, bytes, -1, buffer)
                    .sendToTarget()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun write(bytes: ByteArray?) {
        try {
            outputStream!!.write(bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}