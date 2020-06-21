package my.work.retailerautoapp

import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.apps.auto.sdk.CarActivityService

class RetailerAutoAppService : CarActivityService() {
    override fun getCarActivity(): Class<out CarActivity> = RetailerAutoActivity::class.java
}