package com.example.michael.mapssetup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraChangeListener, GoogleMap.CancelableCallback {

    private GoogleMap map;
    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnCameraChangeListener(this);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "Latitude: " + latLng.latitude + " \nLongitude: " + latLng.longitude, Toast.LENGTH_SHORT).show();

        //CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(latLng).zoom(8).bearing(180).tilt(45);

        CameraPosition cameraPosition = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        //map.moveCamera(update);
        map.animateCamera(update, 6000, this);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 8);
        map.animateCamera(update);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        txtMsg.setText(cameraPosition.toString());
    }

    @Override
    public void onFinish() {
        Toast.makeText(this, "Animation finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Animation canceled", Toast.LENGTH_SHORT).show();
        
    }
}
