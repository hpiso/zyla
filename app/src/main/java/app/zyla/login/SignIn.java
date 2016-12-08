package app.zyla.login;
import android.os.AsyncTask;
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

        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://185.28.20.74/getuser.php");
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
                Log.i("log_tag", "Id user: " + json_data.getInt("id") + ", Name user: " + json_data.getString("firstname"));
                res += json_data.getString("firstname");
            }
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return res;
    }

    public URI setUriPort(URI uri, int port)
    {
        try {
            URI newUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), port, uri.getPath(), uri.getQuery(), uri.getFragment());
            return newUri;
        } catch (URISyntaxException e) {
            Log.e("URI port failed:", e.toString());
            return uri;
        }
    }
}
