package com.example.hieuhoang.now.Model.Store.Info;



import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelInfoStore {

    public int countBranch(String idBrand)  {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.COUNT_BRANCH);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_BRAND, idBrand);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson) ;
            return jsonObject.getInt(AppConstant.QUANTITY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 1 ;
    }
}
