package barsurfing.nnesterov.ru.barsurfing.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import barsurfing.nnesterov.ru.barsurfing.R
import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : FragmentActivity(), OnMapReadyCallback, MainView {
    private val defaultZoomLevel = 15.0f
    private var map: GoogleMap? = null


    private var presenter : MainActivityPresenter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        presenter = MainActivityPresenterImpl(this)
        presenter?.onCreate(this, savedInstanceState)
    }

    override fun onPause() {
        presenter?.onPause()
    }

    override fun onStop() {
        presenter?.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.isMyLocationEnabled = true;
        presenter?.onMapFullReady()
    }

    override fun showPlaces(places: List<Place>) {
        val boundsBuilder = LatLngBounds.Builder();

        places.forEach {
            val position = LatLng(it.latitude, it.longitude)
            val markerOptions = MarkerOptions()
                    .position(position)
                    .title(it.name)
            val marker = map?.addMarker(markerOptions)
            boundsBuilder.include(position)
        }
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 20)
        map?.animateCamera(cameraUpdate)
    }

    override fun showLoading() {
    }

    override fun showError(ex: Throwable?) {
        Toast.makeText(this, ex?.toString() ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }

    override fun clear() {
    }

    override fun showCurrentLocation(latitude: Double, longitude: Double) {

    }
}
