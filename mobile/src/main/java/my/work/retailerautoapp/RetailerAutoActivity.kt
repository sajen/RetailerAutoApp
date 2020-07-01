package my.work.retailerautoapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.gms.maps.model.LatLng
import my.work.retailerautoapp.Constants.Companion.outletList
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class RetailerAutoActivity : CarActivity() {

    var retailerList = ArrayList<Retailer>()

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.retailer_list)

        setValue()

//        getCarUiController().getStatusBarController().setTitle("Trade Coverage");

        val recyclerView = findViewById(R.id.retailer_list_view) as RecyclerView

        val kpiListAdapter = OutletListAdapter()
        recyclerView.adapter = kpiListAdapter
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = mLayoutManager
    }
    private fun setValue() {
        try {
            val jsonArray = JSONArray(outletList)
            for (i in 0 until jsonArray.length()) {
                val obj = JSONObject(jsonArray[i].toString())
                val retailer = Retailer()
                obj["RetailerName"].let {
                    if (obj.has("RetailerName")) retailer.setRetailerName(obj["RetailerName"].toString())
                }
                obj["Address"].let {
                    if (obj.has("Address")) retailer.setRetailerAddress(obj["Address"].toString())
                }
                obj["latitude"].let {
                    if (obj.has("latitude")) retailer.setLatitude(obj["latitude"] as Double)
                }
                obj["longitude"].let {
                    if (obj.has("longitude")) retailer.setLongitude(obj["longitude"] as Double)
                }
                retailerList.add(retailer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class OutletListAdapter : RecyclerView.Adapter<OutletListAdapter.MyViewHolder>() {
        inner class MyViewHolder(view: View) : ViewHolder(view) {
            val retailerNameTv: TextView
            val retailerAddressTv: TextView
            val direction_img: ImageView

            init {
                retailerNameTv = view.findViewById(R.id.tv_kpi_name)
                retailerAddressTv = view.findViewById(R.id.tv_value)
                direction_img = view.findViewById(R.id.tv_txt_kpi_percent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.outlet_item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.retailerNameTv.text = retailerList.get(position).getRetailerName()
            holder.retailerAddressTv.text = retailerList.get(position).getRetailerAddress()
            holder.direction_img.setOnClickListener {
                try {
                    val latLng = LatLng(retailerList.get(position).getLatitude(), retailerList.get(position).getLongitude())
                    navigateToGoogleMap(latLng)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
            holder.itemView.setOnClickListener {
                navigateToProfile(retailerList[position])
            }
        }

        override fun getItemCount(): Int {
            return retailerList.size
        }
    }

    private fun navigateToGoogleMap(latLng: LatLng) {

        val uri = "google.navigation:q=" + latLng.latitude + "," + latLng.longitude
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setPackage("com.google.android.apps.maps")
            startCarActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            try {
                val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(unrestrictedIntent)
            } catch (innerEx: ActivityNotFoundException) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun navigateToProfile(retailer:Retailer){
        val intent = Intent(this,RetailerProfileAppService::class.java)
        getSharedPreferences("retailer",0)
                .edit()
                .putString("retailerName",retailer.getRetailerName())
                .putString("address",retailer.getRetailerAddress())
                .apply()
        /*val bundle = Bundle()
        bundle.putString("retailerName",retailer.getRetailerName())
        bundle.putString("address",retailer.getRetailerAddress())
        intent.putExtras(bundle)*/
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        startCarActivity(intent)
    }
}