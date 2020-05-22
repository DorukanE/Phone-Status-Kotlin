package com.dorukaneskiceri.currencyconvertkotlin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.*
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
//    private var latitude: Double = 38.5002
//    private var longitude: Double = 27.7084

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        //locationRequest()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        locationListener = object: LocationListener{
            override fun onLocationChanged(location: Location?) {

                if(location != null){
                    mMap.clear()

                    val userLocation = LatLng(location.latitude,location.longitude)
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Konumum"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }

            override fun onProviderEnabled(provider: String?) {

            }

            override fun onProviderDisabled(provider: String?) {

            }
        }

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)

        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1f,locationListener)
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if(lastKnownLocation != null){
                val lastLocationLatLng = LatLng(lastKnownLocation.latitude,lastKnownLocation.longitude)
                mMap.addMarker(MarkerOptions().position(lastLocationLatLng).title("Konumum"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocationLatLng,15f))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1f,locationListener)
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    

//        private fun locationRequest(){
//       val url = "http://yusufozgul.com:8282/login"
//        val jsonObject = JSONObject()
//
//        try{
//
//            jsonObject.put("username","mobil")
//            jsonObject.put("password","mobiluygulamagelistirme")
//
//            val queue = Volley.newRequestQueue(this@MapsActivity)
//            val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
//                Response.Listener {
//                        response ->
//                    val jsonObject = JSONObject(response["data"].toString())
//                    val location = jsonObject.getString("location")
//                    val locationObject = JSONObject(location)
//                    println(locationObject.getString("lat").toString())
//                    println(locationObject.getString("lon").toString())
//                    latitude = locationObject.getString("lat").toDouble()
//                    longitude = locationObject.getString("lon").toDouble()
//
//                }, Response.ErrorListener {
//                    Toast.makeText(this@MapsActivity,"Konum alma başarısız oldu.", Toast.LENGTH_LONG).show()
//                })
//            queue.add(request)
//
//        }catch(e: Exception){
//            e.printStackTrace()
//        }
//    }
}
