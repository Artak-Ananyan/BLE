package com.example.blu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.IntentFilter
import android.telephony.TelephonyManager
import java.lang.Exception


class MainActivity : AppCompatActivity() {



    lateinit var listen : Button
    lateinit var send : Button
    lateinit var connectDevices: Button
    lateinit var listView : ListView
    lateinit var msg_box : TextView
    lateinit var status : TextView
    lateinit var writeMsg : EditText
    lateinit var handler: MyHendler


    lateinit var bluetoothAdapter : BluetoothAdapter
    var btArray = ArrayList<BluetoothDevice>()
    var mydevice : BluetoothDevice? = null


    var REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val STATE_LISTENING = 1
        val STATE_CONNECTING = 2
        val STATE_CONNECTED = 3
        val STATE_CONNECTION_FAILED = 4
        val STATE_MESSAGE_RECEIVED = 5
        var statuss = MutableLiveData<String>("")
        var msgbox = MutableLiveData<String>("")
        var sendReceive: SendReceive? = null
        const val APP_NAME = "BTChat"
        val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewByIdes()

        var bluetoothLostReceiver = BluetoothLostReceiver()
        bluetoothLostReceiver.setMainActivity(this)
        val filter = IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED")
        registerReceiver(bluetoothLostReceiver, filter)

        handler = MyHendler(this)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val serverClass = ServerClass(bluetoothAdapter)
        serverClass.start()

        if (!bluetoothAdapter.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        implementListeners()

    }


    private fun implementListeners() {

        connection()
        msgbox.observe(this, androidx.lifecycle.Observer {
            msg_box.setText(it)
        })
        statuss.observe(this, androidx.lifecycle.Observer {
            status.setText(it)
        })


        connectDevices.setOnClickListener {
            status.text = "Connecting"
        }

        send.setOnClickListener {
            val string = writeMsg.text.toString()
            sendReceive!!.write(string.toByteArray())
        }
    }

    fun connection(){
        val bt = bluetoothAdapter.bondedDevices
        if (bt.size > 0) {
            for (device in bt) {
                if (device.name.equals("ESP32")) {
                    mydevice = device
                    ClientClass(mydevice!!, handler).start()

                }
            }
        }
    }

    private fun findViewByIdes() {
        listen = findViewById<View>(R.id.listen) as Button
        send = findViewById<View>(R.id.send) as Button
        listView = findViewById<View>(R.id.listview) as ListView
        msg_box = findViewById<View>(R.id.msg) as TextView
        status = findViewById<View>(R.id.status) as TextView
        writeMsg = findViewById<View>(R.id.writemsg) as EditText
        connectDevices = findViewById<View>(R.id.connectDevices) as Button
    }

}