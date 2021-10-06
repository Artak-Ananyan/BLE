package com.example.blu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import java.io.IOException

class ServerClass(bluetoothAdapter: BluetoothAdapter) : Thread() {
    private var serverSocket: BluetoothServerSocket? = null

    init {
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                MainActivity.APP_NAME,
                MainActivity.MY_UUID
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}