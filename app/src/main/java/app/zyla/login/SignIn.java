package app.zyla.login;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by trist_000 on 08/12/2016.
 */


public class SignIn extends AsyncTask<String, Void, String> {
    private String email;
    private String password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        InputStream is = null;
        String result = "";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        System.err.println("##" + email + "##" + password + "##");

        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://app-zyla.esy.es/getuser.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            System.err.println(result);
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try
        {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); ++i)
            {
                JSONObject json_data = jsonArray.getJSONObject(i);
                res += json_data.getString("birthdate") + "\n"
                        + json_data.getInt("gender");
            }
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return res;
    }
}
