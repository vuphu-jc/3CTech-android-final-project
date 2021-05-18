package com.yoshitoke.weatheringwithyou.mvp.view

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.yoshitoke.weatheringwithyou.R
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City
import kotlinx.android.synthetic.main.activity_maps.*


class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    companion object {
        var marker: Marker? = null
        var mGoogleMap: GoogleMap? = null
        var localCity: City? = null
        const val CITY_DATA = "CITY_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        confirm_button.setOnClickListener { confirmLocation() }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val daNang = LatLng(16.0678, 108.2208)
        mGoogleMap = googleMap
        marker = googleMap.addMarker(
                MarkerOptions()
                        .position(daNang)
                        .title("Marker in Da Nang")
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(daNang, 10F))
        mGoogleMap?.setOnMapLongClickListener(this)

    }

    override fun onMapLongClick(p0: LatLng) {
        marker?.remove()
        marker = mGoogleMap?.addMarker(
                MarkerOptions()
                        .position(p0)
                        .title("Choosed Place")
                        .draggable(true)
        )

        val localCityName = reverseGeocoding(p0)
        localCity = City(localCityName, p0.longitude, p0.latitude)
    }

    private fun reverseGeocoding(position: LatLng) : String {
        val geoCoder = Geocoder(this)
        val matches: MutableList<Address> = geoCoder.getFromLocation(position.latitude, position.longitude, 1)
        val bestMatch: Address? = if (matches.isEmpty()) null else matches[0]

        return if(bestMatch?.locality == null) {
            bestMatch?.adminArea.toString()
        } else {
            bestMatch.locality + ", " + bestMatch.adminArea
        }
    }

    private fun confirmLocation() {
        val data = Intent()
        data.putExtra(CITY_DATA, Gson().toJson(localCity))

        setResult(RESULT_OK, data)
        finish()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

}
