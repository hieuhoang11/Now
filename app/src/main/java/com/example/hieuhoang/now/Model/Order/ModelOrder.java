package com.example.hieuhoang.now.Model.Order;


import android.util.Log;

import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ModelOrder {
    private final String TAG = "kiemtra";

    public Order addNewOrder(String idAccount, String idStore) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.ADD_ORDER);

        HashMap<String, String> hsIDAccount = new HashMap<>();
        hsIDAccount.put(AppConstant.ID_ACCOUNT, idAccount);

        HashMap<String, String> hsIDStore = new HashMap<>();
        hsIDStore.put(AppConstant.ID_STORE, idStore);

        HashMap<String, String> hsIDStatus = new HashMap<>();
        hsIDStatus.put(AppConstant.ORDER_STATUS, String.valueOf(AppConstant.DRAFT_ORDER_STATUS));

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, generateOrderId(idStore, idAccount));

        attrs.add(hsFunction);
        attrs.add(hsIDAccount);
        attrs.add(hsIDStore);
        attrs.add(hsIDStatus);
        attrs.add(hsIDOrder);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            Log.i("kiemtra", "addNewOrder: " + dataJSON);
            JSONObject jsonObject = new JSONObject(dataJSON);
            String idOrder = jsonObject.getString(AppConstant.ID_ORDER);
            Order order = new Order();
            order.setIdCustomer(idAccount);
            order.setIdOrder(idOrder);
            order.setIdStore(idStore);
            return order;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addDetailOrder(String idOrder, String idProduct, int quantity, String note) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.ADD_DETAIL_ORDER);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        HashMap<String, String> hsQuantity = new HashMap<>();
        hsQuantity.put(AppConstant.QUANTITY, String.valueOf(quantity));

        HashMap<String, String> hsNote = new HashMap<>();
        hsNote.put(AppConstant.NOTE, note);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);
        attrs.add(hsQuantity);
        attrs.add(hsNote);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDetailOrder(String idOrder, String idProduct) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.DELETE_DETAIL_ORDER);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderDetail> getListDraftOrderDetail(String idOrder) {
        return getDetailOrder(idOrder, AppConstant.GET_LIST_DRAFT_ORDER_DETAIL, null);
    }

    public List<OrderDetail> getListOrderDetail(String idOrder) {
        return getDetailOrder(idOrder, AppConstant.GET_LIST_ORDER_DETAIL, String.valueOf(AppConstant.SUBMIT_ORDER_STATUS));
    }

    private List<OrderDetail> getDetailOrder(String idOrder, String function, String orderStatus) {
        List<OrderDetail> list = new ArrayList<>();

        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, function);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);

        if (orderStatus != null) {
            HashMap<String, String> hsStatus = new HashMap<>();
            hsStatus.put(AppConstant.ORDER_STATUS, orderStatus);
            attrs.add(hsStatus);
        }

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {

            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getListOrderDetail: " + dataJson);
            JSONArray jsonArray = new JSONArray(dataJson);
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String idProduct = jsonObject.getString(AppConstant.ID_PRODUCT);
                String productName = jsonObject.getString(AppConstant.PRODUCT_NAME);
                String note = jsonObject.getString(AppConstant.NOTE);
                float price = (float) jsonObject.getDouble(AppConstant.PRODUCT_PRICE);
                float disCount = (float) jsonObject.getDouble(AppConstant.PRODUCT_DISCOUNT);
                int quantity = jsonObject.getInt(AppConstant.QUANTITY);

                OrderDetail detail = new OrderDetail();

                detail.setIdOrder(idOrder);
                detail.setDisCount(disCount);
                detail.setIdProduct(idProduct);
                detail.setNote(note);
                detail.setProductPrice(price);
                detail.setProductName(productName);
                detail.setQuantity(quantity);

                list.add(detail);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderById(String idOrder) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_ORDER_BY_ID);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsSubmitOrder = new HashMap<>();
        hsSubmitOrder.put(AppConstant.SUBMIT_ORDER, String.valueOf(AppConstant.SUBMIT_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsSubmitOrder);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getOrder: " + dataJson);
            JSONObject jsonObject = new JSONObject(dataJson);
            String idStore = jsonObject.getString(AppConstant.ID_STORE);
            int quantity = jsonObject.getInt(AppConstant.QUANTITY);
            float totalMoney = (float) jsonObject.getDouble(AppConstant.TOTAL_MONEY);
            String idCustomer = jsonObject.getString(AppConstant.ID_ACCOUNT);
            String address = jsonObject.getString(AppConstant.ADDRESS);
            String note = jsonObject.getString(AppConstant.NOTE);
            Order order = new Order();
            order.setIdOrder(idOrder);
            order.setQuantityProduct(quantity);
            order.setTotalMoney(totalMoney);
            order.setIdStore(idStore);
            order.setIdCustomer(idCustomer);
            order.setCustomerAddress(address);
            order.setNote(note);
            return order;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getDraftOrderById(String idOrder) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_DRAFT_ORDER_BY_ID);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getDraftOrderInformation: " + dataJson);
            JSONObject jsonObject = new JSONObject(dataJson);
            String idStore = jsonObject.getString(AppConstant.ID_STORE);
            int quantity = jsonObject.getInt(AppConstant.QUANTITY);
            float totalMoney = (float) jsonObject.getDouble(AppConstant.TOTAL_MONEY);
            String idCustomer = jsonObject.getString(AppConstant.ID_ACCOUNT);
            Order order = new Order();
            order.setIdOrder(idOrder);
            order.setQuantityProduct(quantity);
            order.setTotalMoney(totalMoney);
            order.setIdStore(idStore);
            order.setIdCustomer(idCustomer);
            return order;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getDraftOrder(String idStore, String idCustomer) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_DRAFT_ORDER);

        HashMap<String, String> hsIdStore = new HashMap<>();
        hsIdStore.put(AppConstant.ID_STORE, idStore);

        HashMap<String, String> hsIdCus = new HashMap<>();
        hsIdCus.put(AppConstant.ID_ACCOUNT, idCustomer);

        HashMap<String, String> hsIdStatus = new HashMap<>();
        hsIdStatus.put(AppConstant.ORDER_STATUS, String.valueOf(AppConstant.DRAFT_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIdStore);
        attrs.add(hsIdCus);
        attrs.add(hsIdStatus);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getDraftOrder: " + dataJson);
            JSONObject jsonObject = new JSONObject(dataJson);

            String idOrder = jsonObject.getString(AppConstant.ID_ORDER);
            int quantity = jsonObject.getInt(AppConstant.QUANTITY);
            float totalMoney = (float) jsonObject.getDouble(AppConstant.TOTAL_MONEY);

            Order order = new Order();
            order.setIdOrder(idOrder);
            order.setQuantityProduct(quantity);
            order.setTotalMoney(totalMoney);

            return order;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteDraftOrder(String idOrder) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.DELETE_DRAFT_ORDER);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllDraftOrder(String idCustomer) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.DELETE_ALL_DRAFT_ORDER);

        HashMap<String, String> hsIDCustomer = new HashMap<>();
        hsIDCustomer.put(AppConstant.ID_ACCOUNT, idCustomer);

        attrs.add(hsFunction);
        attrs.add(hsIDCustomer);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateQuantityProductInOrderDetail(String idOrder, String idProduct, int quantity) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.UPDATE_QUANTITY_PRODUCT_ORDER_DETAIL);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        HashMap<String, String> hsQuantity = new HashMap<>();
        hsQuantity.put(AppConstant.QUANTITY, String.valueOf(quantity));

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);
        attrs.add(hsQuantity);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNoteInOrderDetail(String idOrder, String idProduct, String note) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.UPDATE_NOTE_ORDER_DETAIL);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        HashMap<String, String> hsNote = new HashMap<>();
        hsNote.put(AppConstant.NOTE, note);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);
        attrs.add(hsNote);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getQuantityProductInDraftOrder(String idOrder, String idProduct) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_QUANTITY_PRODUCT_IN_DRAFT_ORDER);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson);
            return jsonObject.getInt(AppConstant.QUANTITY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> getListDraftOrder(String idCustomer) {
        List<Order> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_LIST_DRAFT_ORDER);

        HashMap<String, String> hsIDCustomer = new HashMap<>();
        hsIDCustomer.put(AppConstant.ID_ACCOUNT, idCustomer);

        HashMap<String, String> hsOrderStatus = new HashMap<>();
        hsOrderStatus.put(AppConstant.ORDER_STATUS, String.valueOf(AppConstant.DRAFT_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIDCustomer);
        attrs.add(hsOrderStatus);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getListDraftOrderByIdCustomer: " + dataJson);
            list = parseListOrder(dataJson) ;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getListOnGoingOrder(String idCustomer) {
        List<Order> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_LIST_ON_GOING_ORDER);

        HashMap<String, String> hsIDCustomer = new HashMap<>();
        hsIDCustomer.put(AppConstant.ID_ACCOUNT, idCustomer);

        HashMap<String, String> hsDraftStatus = new HashMap<>();
        hsDraftStatus.put(AppConstant.DRAFT_ORDER, String.valueOf(AppConstant.DRAFT_ORDER_STATUS));

        HashMap<String, String> hsCompleteStatus = new HashMap<>();
        hsCompleteStatus.put(AppConstant.COMPLETE_ORDER, String.valueOf(AppConstant.COMPLETE_ORDER_STATUS));

        HashMap<String, String> hsCancelStatus = new HashMap<>();
        hsCancelStatus.put(AppConstant.CANCEL_ORDER, String.valueOf(AppConstant.CANCEL_ORDER_STATUS));

        HashMap<String, String> hsSubmitStatus = new HashMap<>();
        hsSubmitStatus.put(AppConstant.SUBMIT_ORDER, String.valueOf(AppConstant.SUBMIT_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIDCustomer);
        attrs.add(hsDraftStatus);
        attrs.add(hsCompleteStatus);
        attrs.add(hsCancelStatus);
        attrs.add(hsSubmitStatus);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getListOnGoingOrder: " + dataJson);
            list = parseListOrder(dataJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getListHistoryOrder(String idCustomer , String startDate , String finishDate) {
        List<Order> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_LIST_HISTORY_ORDER);

        HashMap<String, String> hsIDCustomer = new HashMap<>();
        hsIDCustomer.put(AppConstant.ID_ACCOUNT, idCustomer);

        HashMap<String, String> hsCompleteStatus = new HashMap<>();
        hsCompleteStatus.put(AppConstant.COMPLETE_ORDER, String.valueOf(AppConstant.COMPLETE_ORDER_STATUS));

        HashMap<String, String> hsSubmitStatus = new HashMap<>();
        hsSubmitStatus.put(AppConstant.SUBMIT_ORDER, String.valueOf(AppConstant.SUBMIT_ORDER_STATUS));

        HashMap<String, String> hsStartDay = new HashMap<>();
        hsStartDay.put(AppConstant.START_DATE, startDate);

        HashMap<String, String> hsFinishDay = new HashMap<>();
        hsFinishDay.put(AppConstant.FINISH_DATE, finishDate);


        attrs.add(hsFunction);
        attrs.add(hsIDCustomer);
        attrs.add(hsCompleteStatus);
        attrs.add(hsSubmitStatus);
        attrs.add(hsStartDay);
        attrs.add(hsFinishDay);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            list = parseListOrder(dataJson) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<Order> parseListOrder (String dataJson) throws JSONException {
        List<Order> list = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(dataJson);
        int l = jsonArray.length();
        for (int i = 0; i < l; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String idOrder = jsonObject.getString(AppConstant.ID_ORDER);
            String idStore = jsonObject.getString(AppConstant.ID_STORE);
            String storeName = jsonObject.getString(AppConstant.STORE_NAME);
            String address = jsonObject.getString(AppConstant.STORE_ADDRESS);
            String image = jsonObject.getString(AppConstant.IMAGE);
            int quantity = jsonObject.getInt(AppConstant.QUANTITY);
            float total = (float) jsonObject.getDouble(AppConstant.TOTAL_MONEY);

            Order order = new Order();
            order.setIdOrder(idOrder);
            order.setIdStore(idStore);
            order.setStoreName(storeName);
            order.setStoreImage(image);
            order.setStoreAddress(address);
            order.setQuantityProduct(quantity);
            order.setTotalMoney(total);
            try{
                String time = jsonObject.getString(AppConstant.TIME) ;
                order.setTime(time);
            }catch (Exception e) {
                e.printStackTrace();
            }

            list.add(order);
        }
        return list ;
    }


    public boolean submitOrder(String idOrder, String address, String note) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_SUBMIT_ORDER);

        HashMap<String, String> hsIdOrder = new HashMap<>();
        hsIdOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsAddress = new HashMap<>();
        hsAddress.put(AppConstant.ADDRESS, address);

        HashMap<String, String> hsNote = new HashMap<>();
        hsNote.put(AppConstant.NOTE, note);

        HashMap<String, String> hsStatus = new HashMap<>();
        hsStatus.put(AppConstant.ORDER_STATUS, String.valueOf(AppConstant.SUBMIT_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIdOrder);
        attrs.add(hsAddress);
        attrs.add(hsNote);
        attrs.add(hsStatus);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, String> getOrderStatus(String idOrder) {
        Map<Integer, String> map = new HashMap<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_ORDER_STATUS);

        HashMap<String, String> hsIdOrder = new HashMap<>();
        hsIdOrder.put(AppConstant.ID_ORDER, idOrder);

        attrs.add(hsFunction);
        attrs.add(hsIdOrder);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getOrderStatus: " + dataJson);
            JSONArray jsonArray = new JSONArray(dataJson);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(AppConstant.ID_ORDER_STATUS);
                String time = jsonObject.getString(AppConstant.TIME);
                map.put(id, time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public boolean cancelOrder(String idOrder) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_CANCEL_ORDER);

        HashMap<String, String> hsIdOrder = new HashMap<>();
        hsIdOrder.put(AppConstant.ID_ORDER, idOrder);

        HashMap<String, String> hsCancel = new HashMap<>();
        hsCancel.put(AppConstant.CANCEL_ORDER, String.valueOf(AppConstant.CANCEL_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsIdOrder);
        attrs.add(hsCancel);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String generateOrderId(String idStore, String idCustomer) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        //Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        Calendar now = Calendar.getInstance();
        buffer.append(idStore).append(idCustomer).append(format.format(now.getTime()));
        return buffer.toString();
    }
}
