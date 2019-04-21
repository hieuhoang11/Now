package com.example.hieuhoang.now.Model.Home;


import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelHome {
   public List<String> getListImageBanner (){
       List<String> list = new ArrayList<>() ;
       String path = AppConstant.SERVER_NAME;
       List<HashMap<String, String>> attrs = new ArrayList<>();
       HashMap<String, String> hsFunction = new HashMap<>();
       hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_BANNER);

       attrs.add(hsFunction);
       DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
       downloadJSON.execute();
       try {
           String dataJSon = downloadJSON.get();
           JSONArray jsonArray = new JSONArray(dataJSon) ;
           int l = jsonArray.length() ;
           for (int i = 0 ;i<l;i++) {
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               list.add(jsonObject.getString(AppConstant.IMAGE)) ;
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (ExecutionException e) {
           e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return list ;
   }
}
