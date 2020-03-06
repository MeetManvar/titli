package com.example.rickshaw;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager  locationManager;
    LocationListener locationListener;
    LatLng userLatLong;

    private GoogleMap Map;
    private SupportMapFragment mapFragment;
    private SearchView searchView;
    private SearchView searchView1;
    private static final int LOCATION_REQUEST = 500;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    // Button getDirection;
    private Polyline currentPolyline;
    private LatLng location1,location2;
    double Distance;
    TextView distance;
    // private double SphericalUtil;
    MarkerOptions marker;

    /*  Map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

          @Override
          public void onMapLongClick(LatLng arg0) {
              if (marker != null) {
                  marker.remove();
              }
              marker = MAP.addMarker(new MarkerOptions()
                      .position(
                              new LatLng(arg0.latitude,
                                      arg0.longitude))
                      .draggable(true).visible(true));
          }
      });*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        searchView = findViewById(R.id.sv_location);
        searchView1 = findViewById(R.id.sv_location1);
        distance = findViewById(R.id.distance);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location!=null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    location1 = new LatLng(address.getLatitude(),address.getLongitude());
                    //place1=new MarkerOptions().position(location1).title(location);
                    Map.addMarker(new MarkerOptions().position(location1).title(location));
                    Map.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView1.getQuery().toString();
                List<Address> addressList = null;

                if(location!=null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    location2 = new LatLng(address.getLatitude(),address.getLongitude());
                    //  place2=new MarkerOptions().position(location2).title(location);

                    Map.addMarker(new MarkerOptions().position(location2).title(location));
                    Map.animateCamera(CameraUpdateFactory.newLatLngZoom(location2,10));
                    Distance = SphericalUtil.computeDistanceBetween(location1,location2);
                    Distance = Distance/1000;
                    String str = new Integer((int) Distance).toString();
                    distance.setText(str);
                    //Toast.makeText(MapsActivity.this,Distance/1000+"KM",Toast.LENGTH_LONG).show();
                    //distance = SphericalUtil.computeDistanceBetween(location1,location2);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Map = googleMap;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
        Map.setMyLocationEnabled(true);

       locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLatLong = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.clear();
                Map.addMarker(new MarkerOptions().position(userLatLong).title("Your Lcation"));
                Map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 18), 5000, null);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Log.e("Distance","distance in km="+Distance/1000);


        // Add a marker in Sydney and move the camera
       // LatLng latLng  = new LatLng(22.599749, 72.820465);
        //Map.addMarker(new MarkerOptions().position(userLatLong).title("Marker in Charusat_University"));
        //Map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 18), 5000, null);
    }
    public void onRequestPermissionResults(int requestCode, @NonNull String[] permission,@NonNull int[] grantResults){
        switch (requestCode){
            case LOCATION_REQUEST:
                if(grantResults.length>0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED)
                {
                    Map.setMyLocationEnabled(true);
                }
                break;
        }
    }

}
