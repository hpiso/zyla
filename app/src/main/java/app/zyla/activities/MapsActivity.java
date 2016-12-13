package app.zyla.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.zyla.R;
import app.zyla.models.Shop;
import app.zyla.sync.HttpHandler;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Shop> listShops = new ArrayList<Shop>();
    private double myLat;
    private double myLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        myLat = location.getLatitude();
        myLng = location.getLongitude();

        new GetShops(myLat, myLng).execute();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!listShops.isEmpty()) {
            for (Shop shop : listShops) {
                LatLng place = new LatLng(shop.getLat(), shop.getLng());
                mMap.addMarker(new MarkerOptions().position(place).title(shop.getName()));
            }

            LatLng place = new LatLng(this.myLat, this.myLng);
            mMap.addMarker(new MarkerOptions().position(place).title("My position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));

            Toast.makeText(getApplicationContext(), listShops.size() + " places found " ,Toast.LENGTH_LONG)
                    .show();
        }

    }

    /**
     * Async to get json by making HTTP call
     */
    private class GetShops extends AsyncTask<Void, Void, Void> {

        private double lat;
        private double lng;

        public GetShops(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.webServiceGetCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=2000&types=grocery_or_supermarket&key=AIzaSyCHycpnYlZXzkbq3cLncZ2IRfI_022fT-I");

            if (jsonStr != null) {
                try {
                    JSONObject res  = new JSONObject(jsonStr);
                    JSONArray shops = res.getJSONArray("results");

                    for (int i = 0; i < shops.length(); ++i) {
                        JSONObject s = shops.getJSONObject(i);
                        JSONObject geo = s.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");

                        double lat = loc.getDouble("lat");
                        double lng = loc.getDouble("lng");
                        String shopName = s.getString("name");

                        Shop shop = new Shop(shopName, lat, lng);
                        listShops.add(shop);
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            onMapReady(mMap);
        }

    }

}
