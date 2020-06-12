package my.work.retailerautoapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.apps.auto.sdk.CarActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RetailerAutoActivity extends CarActivity {

    ArrayList<Retailer> retailerList = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.retailer_list);

        setValue();

//        getCarUiController().getStatusBarController().setTitle("Trade Coverage");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.retailer_list_view);

        OutletListAdapter kpiListAdapter = new OutletListAdapter();
        recyclerView.setAdapter(kpiListAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setValue(){
        try {
            JSONArray jsonArray = new JSONArray(Constants.outletList);

            for (int i = 0;i < jsonArray.length();i++){
                JSONObject obj = new JSONObject(jsonArray.get(i).toString());

                Retailer retailer = new Retailer();

                if (obj.has("RetailerName") && obj.get("RetailerName")!=null)
                    retailer.setRetailerName(obj.get("RetailerName").toString());

                if (obj.has("Address") && obj.get("Address")!=null)
                    retailer.setRetailerAddress(obj.get("Address").toString());

                if (obj.has("latitude") && obj.get("latitude")!=null)
                    retailer.setLatitude((double)obj.get("latitude"));

                if (obj.has("longitude") && obj.get("longitude")!=null)
                    retailer.setLongitude((double)obj.get("longitude"));

                retailerList.add(retailer);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.MyViewHolder> {


        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView retailerNameTv,retailerAddressTv;

            private ImageView direction_img;

            public MyViewHolder(View view) {
                super(view);

                retailerNameTv = view.findViewById(R.id.tv_kpi_name);
                retailerAddressTv = view.findViewById(R.id.tv_value);

                direction_img = view.findViewById(R.id.tv_txt_kpi_percent);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outlet_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.retailerNameTv.setText(retailerList.get(position).getRetailerName());
            holder.retailerAddressTv.setText(retailerList.get(position).getRetailerAddress());

            holder.direction_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        LatLng latLng = new LatLng(retailerList.get(position).getLatitude(),retailerList.get(position).getLongitude());
                        navigateToGoogleMap(latLng);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return retailerList.size();
        }
    }

    private void navigateToGoogleMap(LatLng latLng){
        /*String uri = "http://maps.google.com/maps?saddr="
                + latLng.latitude + "," + latLng.longitude
                + "(" + "Current " + ")&daddr=" + latLng.latitude + ","
                + latLng.longitude + " (Retailer)";*/

        String uri = "http://maps.google.com/maps?daddr=" + latLng.latitude + ","
                + latLng.longitude + " (Retailer)";

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
