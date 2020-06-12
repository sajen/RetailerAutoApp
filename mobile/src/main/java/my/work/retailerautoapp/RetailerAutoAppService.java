package my.work.retailerautoapp;


import com.google.android.apps.auto.sdk.CarActivity;
import com.google.android.apps.auto.sdk.CarActivityService;

public class RetailerAutoAppService extends CarActivityService {

    @Override
    public Class<? extends CarActivity> getCarActivity() {
        return  RetailerAutoActivity.class;
    }

}
