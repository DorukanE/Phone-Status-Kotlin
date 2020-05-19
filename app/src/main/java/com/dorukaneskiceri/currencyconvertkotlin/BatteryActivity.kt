package com.dorukaneskiceri.currencyconvertkotlin

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_battery.*

class BatteryActivity : AppCompatActivity() {

    private lateinit var batteryManager: BatteryManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            applicationContext.registerReceiver(null,ifilter)
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

        if(isCharging){
            if(usbCharge){
                statusText.text = "Cihaz USB ile şarj oluyor."
            }else if(acCharge){
                statusText.text = "Cihaz prize takılı ve şarj oluyor."
            }else{
                statusText.text = "Şarj olma durumunu alırken bir hata oluştu."
            }
        }
        else{
            statusText.text = "Cihaz şarj olmuyor."
        }
    }
}
