package com.example.hieuhoang.now.ConnectInternet;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.android.gms.common.util.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadJSON extends AsyncTask<String, Void, String> {
    private String path;
    private List<HashMap<String, String>> attrs;
    private boolean method = true;

    public DownloadJSON(String path) {
        this.path = path;
        method = true;
    }

    public DownloadJSON(String path, List<HashMap<String, String>> attrs) {
        this.path = path;
        this.attrs = attrs;
        method = false ;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(!method)
                return methodPost(connection);
            else
                return methodGet(connection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
}

    private String methodGet(HttpURLConnection httpURLConnection) {
        String re = "";
        try {
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = "";
            StringBuffer data = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            re = data.toString();

            reader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

    private String methodPost(HttpURLConnection httpURLConnection) {
        String data = "";
        String key = "";
        String value = "";
        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();
            int count = attrs.size();
            for (int i = 0; i < count; i++) {
                for (Map.Entry<String, String> values : attrs.get(i).entrySet()) {
                    key = values.getKey();
                    value = values.getValue();
                }
                builder.appendQueryParameter(key, value);
            }
            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);

            writer.write(query);
            writer.flush();
            writer.close();
            outputStreamWriter.close();
            outputStream.close();

            data = methodGet(httpURLConnection);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
