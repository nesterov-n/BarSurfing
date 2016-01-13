package barsurfing.nnesterov.ru.barsurfing.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import barsurfing.nnesterov.ru.barsurfing.R
import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : FragmentActivity(), OnMapReadyCallback, MainView {

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.setMyLocationEnabled(true);
    }

    override fun showPlaces(places: List<Place>) {
    }

    override fun showLoading() {
    }

    override fun showError(ex: Throwable?) {
    }

    override fun clear() {
    }

    override fun showCurrentLocation(latitude: Double, longitude: Double) {
        Toast.makeText(this, "" + latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
    }
}
