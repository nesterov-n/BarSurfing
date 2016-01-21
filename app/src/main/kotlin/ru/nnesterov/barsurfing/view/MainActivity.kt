/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.view

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import ru.nnesterov.barsurfing.R
import ru.nnesterov.barsurfing.domain.places.Place
import ru.nnesterov.barsurfing.domain.places.Route
import ru.nnesterov.barsurfing.domain.places.RoutedPlaces
import java.util.*

class MainActivity : FragmentActivity(), OnMapReadyCallback, MainView {
    private val routeLIneWidth = 20.0f
    private val hideAnimationDuration = 200L
    private var map: GoogleMap? = null
    private val markerBoundPadding = 100;
    private val visitedHue = 88.0f;
    private val notVisitedHue = 4.0f;
    private lateinit var progressBar: ProgressBar
    private lateinit var aboutOverlay: View
    private lateinit var errorOverlay: TextView
    private lateinit var routeInfo: TextView
    private var presenter: MainActivityPresenter? = null;
    private val markerMap = HashMap<String, Place>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        progressBar = findViewById(R.id.progress_bar) as ProgressBar
        aboutOverlay = findViewById(R.id.overlay_about) as View
        errorOverlay = findViewById(R.id.overlay_error) as TextView
        routeInfo = findViewById(R.id.route_info_view) as TextView

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
        map?.uiSettings?.isMapToolbarEnabled = false
        map?.setInfoWindowAdapter(InfoWindowAdapter())
        map?.setOnInfoWindowClickListener(InfowWindowClickListener())
    }

    override fun showPlaces(places: RoutedPlaces) {
        progressBar.visibility = View.INVISIBLE
        clear();
        addMarkers(places.places)
        addPolyline(places.route)
        showRouteInfo(places)
    }

    override fun showAboutOverlay() {
        aboutOverlay.visibility = View.VISIBLE
    }

    override fun hideAbout() {
        startHideAnimation(aboutOverlay)
    }

    override fun hideError() {
        startHideAnimation(errorOverlay)
    }

    private fun addMarkers(places: List<Place>) {
        markerMap.clear();
        val boundsBuilder = LatLngBounds.Builder()
        places.forEach {
            val position = LatLng(it.latitude, it.longitude)
            val markerColor = if (it.visited) {
                visitedHue
            } else {
                notVisitedHue
            }

            val markerOptions = MarkerOptions()
                    .position(position)
                    .title(it.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
            val markerId = map?.addMarker(markerOptions)?.id
            markerMap.put(markerId ?: "", it)
            boundsBuilder.include(position)
        }
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), markerBoundPadding)
        map?.animateCamera(cameraUpdate)
    }

    private fun showRouteInfo(routedPlaces: RoutedPlaces) {
        val barCount = resources.getQuantityString(R.plurals.bar_count, routedPlaces.places.size, routedPlaces.places.size)
        val distance = routedPlaces.route.distanceMeters / 1000.0

        val info = getString(R.string.route_info, barCount, distance)
        routeInfo.visibility = View.VISIBLE
        routeInfo.text = info
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun startHideAnimation(view: View) {
        view.animate()
                .alpha(0.0f)
                .withEndAction { view.visibility = View.INVISIBLE }
                .setDuration(hideAnimationDuration)
                .start()
    }

    private fun addPolyline(route: Route) {
        val polyline = PolylineOptions()
                .addAll(route.points)
                .color(R.color.colorPrimaryDark)
                .width(routeLIneWidth)
        map?.addPolyline(polyline)
    }

    override fun showLoading() {
        routeInfo.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun showError(errorText: String) {
        progressBar.visibility = View.INVISIBLE
        routeInfo.visibility = View.INVISIBLE
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this)
                    .setMessage(R.string.exit_confirmation)
                    .setPositiveButton(android.R.string.ok, { dialog, which -> this@MainActivity.finish() })
                    .setNegativeButton(android.R.string.cancel, { dialog, which -> dialog?.dismiss() })
                    .show()
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class InfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        override fun getInfoContents(marker: Marker?): View? {
            if (marker == null) {
                return null
            }
            val place = markerMap[marker.id] ?: return null
            val infoView = LayoutInflater.from(this@MainActivity).inflate(R.layout.info_window, null);
            val titleView = infoView.findViewById(R.id.info_window_title) as TextView
            val visitedView = infoView.findViewById(R.id.info_window_visited_button) as CheckBox
            val addressView = infoView.findViewById(R.id.info_window_address) as TextView

            titleView.text = place.name
            visitedView.isChecked = place.visited
            addressView.text = place.address

            return infoView
        }

        override fun getInfoWindow(marker: Marker?): View? = null
    }

    private inner class InfowWindowClickListener : GoogleMap.OnInfoWindowClickListener {
        override fun onInfoWindowClick(marker: Marker?) {
            if (marker == null) {
                return
            }
            marker.hideInfoWindow()
            val place = markerMap[marker.id] ?: return
            presenter?.onPlaceVisitedChanged(place.id)
        }
    }
}
