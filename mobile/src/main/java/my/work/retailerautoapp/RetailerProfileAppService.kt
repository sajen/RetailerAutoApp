package my.work.retailerautoapp

import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.apps.auto.sdk.CarActivityService

class RetailerProfileAppService : CarActivityService() {
    override fun getCarActivity(): Class<out CarActivity> = RetailerProfile::class.java
}