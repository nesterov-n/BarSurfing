package ru.nnesterov.barsurfing.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import ru.nnesterov.barsurfing.R
import ru.nnesterov.barsurfing.domain.places.Place
import ru.nnesterov.barsurfing.domain.places.Route
import ru.nnesterov.barsurfing.domain.places.RoutedPlaces

class MainActivity : FragmentActivity(), OnMapReadyCallback, MainView {

    private var map: GoogleMap? = null
    private val markerBoundPadding = 100;
    private lateinit var progressBar: ProgressBar
    private lateinit var aboutOverlay: View
    private lateinit var errorOverlay: TextView
    private var presenter: MainActivityPresenter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        progressBar = findViewById(R.id.progress_bar) as ProgressBar
        aboutOverlay = findViewById(R.id.overlay_about) as View
        errorOverlay = findViewById(R.id.overlay_error) as TextView

        presenter = MainActivityPresenterImpl(this)
        presenter?.onCreate(this, savedInstanceState)

        aboutOverlay.setOnClickListener {
            presenter?.onAboutOverlayClicked()
        }

        errorOverlay.setOnClickListener {
            presenter?.onErrorOverlayClicked()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter?.onSaveInstanceState(outState!!)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.isMyLocationEnabled = true
        map?.setOnMapLoadedCallback {
            presenter?.onMapFullReady()
        }
    }

    override fun showPlaces(places: RoutedPlaces) {
        progressBar.visibility = View.INVISIBLE
        addMarkers(places.places)
        addPolyline(places.route)
    }

    override fun showAboutOverlay() {
        aboutOverlay.visibility = View.VISIBLE
    }

    override fun hideAbout() {
        aboutOverlay.visibility = View.INVISIBLE
    }

    override fun hideError() {
        errorOverlay.visibility = View.INVISIBLE
    }

    private fun addMarkers(places: List<Place>) {
        val boundsBuilder = LatLngBounds.Builder()
        places.forEach {
            val position = LatLng(it.latitude, it.longitude)
            val markerOptions = MarkerOptions()
                    .position(position)
                    .title(it.name)
            map?.addMarker(markerOptions)
            boundsBuilder.include(position)
        }
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), markerBoundPadding)
        map?.animateCamera(cameraUpdate)
    }

    private fun addPolyline(route: Route) {
        val polyline = PolylineOptions()
                .addAll(route.points)
                .color(R.color.colorPrimaryDark)
                .width(20.0f)
        map?.addPolyline(polyline)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showError(errorText: String) {
        progressBar.visibility = View.INVISIBLE
        aboutOverlay.visibility = View.INVISIBLE
        errorOverlay.visibility = View.VISIBLE
        errorOverlay.text = errorText
    }

    override fun clear() {
        map?.clear()
    }

    override fun showCurrentLocation(latitude: Double, longitude: Double) {
        // do nothing
    }
}
