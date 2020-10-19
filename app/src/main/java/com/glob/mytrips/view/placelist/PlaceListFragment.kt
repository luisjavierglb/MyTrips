package com.glob.mytrips.view.placelist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.glob.mytrips.R
import com.glob.mytrips.adapters.PlaceAdapter
import com.glob.mytrips.adapters.PlaceListener
import com.glob.mytrips.adapters.StateAdapter
import com.glob.mytrips.domain.dtos.CountryDto
import com.glob.mytrips.domain.dtos.PlaceDto
import com.glob.mytrips.domain.dtos.StateDto
import com.glob.mytrips.domain.dtos.base.PlaceReference
import com.glob.mytrips.view.placelist.contacts.PlaceListContracts
import kotlinx.android.synthetic.main.fragment_place_list.*

class PlaceListFragment : Fragment(), PlaceListener,
    PlaceListContracts.ViewCountries {

    private lateinit var parentListener: OnItemListChanged
    private var fragmentType: Int = 0
    private lateinit var myAdapter: PlaceAdapter
    private val presenter: PlaceListContracts.Presenter by lazy {
        PlaceListPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = PlaceAdapter(listener = this)
        rvMyPlaces.apply {
            adapter = myAdapter
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun changeAdapter(place: PlaceReference) {
        rvMyPlaces.adapter = when (place) {
            is CountryDto -> StateAdapter(listOf(place as StateDto), this)
            is StateDto -> PlaceAdapter(listOf(place as PlaceDto), this)
            else -> null
        }
    }

    fun setupInfo(places: List<PlaceDto>) {
        myAdapter.updateMyPlaces(places)
    }

    override fun setCountries(item: List<CountryDto>) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemListChanged) {
            parentListener = context
        } else {
            throw ClassCastException(
                "$context must implements PlaceListFragment.OnItemListChanged"
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaceListFragment()

        const val MOVE_TO_DETAILS = 4
    }

    interface OnItemListChanged {
        fun onListChanged(moveTo: Int, onItem: Int)
    }

    override fun onItemClicked(openDetail: Boolean, onItem: Int) {
        parentListener.onListChanged(MOVE_TO_DETAILS, onItem)
    }
}