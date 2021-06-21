package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.network.NetApiUtil2
import com.example.myapplication.util.ThreadUtil
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val MSG_RECEIVE_CODE = 1 //收到短信的验证码
    private var smsContentObserver: SMSContentObserver? = null

    //回调接口
    @SuppressLint("HandlerLeak")
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what === MSG_RECEIVE_CODE) {
                Log.e("======", "======" + msg.obj.toString())
                activity_main_msg_content_tv.setText(msg.obj.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //activity_main_msg_content_tv.set
        smsContentObserver = SMSContentObserver(
            this@MainActivity, handler
        )
        //ContentObserver注册
        contentResolver.registerContentObserver(
            Uri.parse("content://sms/"), true, smsContentObserver!!
        )

        PermissionX.init(this)
            .permissions(Manifest.permission.READ_SMS)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
//                        call()
                } else {
                    Toast.makeText(this, "您拒绝了读取短信权限", Toast.LENGTH_SHORT).show()
                }
            }
        activity_main_msg_click_tv.setOnClickListener {
            ThreadUtil.executeMore(Runnable {
                NetApiUtil2.PostWaitOrder(this@MainActivity,"1111","1111",1.00)
            })
        }
    }

    /**
     * 取消注册
     */
    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(smsContentObserver!!)
    }
}