package my.work.retailerautoapp

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.widget.TextView
import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.apps.auto.sdk.MenuAdapter
import com.google.android.apps.auto.sdk.MenuItem

class RetailerProfile : CarActivity() {
    lateinit var retailerNameTextView:TextView
    lateinit var addressTextView:TextView

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.retailer_profile)

        retailerNameTextView =  findViewById(R.id.retailerName) as TextView
        addressTextView =  findViewById(R.id.address) as TextView
        prepateMenu()
    }

    override fun onResume() {
        val retailerName = getSharedPreferences("retailer",0).getString("retailerName","")
        val address = getSharedPreferences("retailer",0).getString("address","")
        retailerNameTextView.text = retailerName
        addressTextView.text = address
    }
    private fun prepateMenu(){
        carUiController.menuController.showMenuButton()
        carUiController.statusBarController.setTitle("Retailer Profile")
        val am = MenuItem.Builder()
                .setTitle("Home")
                .setType(MenuItem.Type.ITEM)
                .build()
        val menus = arrayOf(am)
        val menuAdapter = object :MenuAdapter(){
            override fun getMenuItem(p0: Int): MenuItem = menus[p0]

            override fun getMenuItemCount(): Int  = menus.size
            override fun onMenuItemClicked(p0: Int) {
                when(p0){
                    0 -> navigateToHomeScreen()
                }
            }
        }
        carUiController.menuController.setRootMenuAdapter(menuAdapter)
    }
    fun navigateToHomeScreen(){
        val intent = Intent(this,RetailerAutoAppService::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        startCarActivity(intent)
    }
}

