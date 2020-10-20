package com.glob.mytrips.view.placelist

import com.glob.mytrips.models.CountryModel
import com.glob.mytrips.view.placelist.contacts.PlaceListContracts

// TODO: 13/10/2020 Create a General View for any Place
class PlaceListPresenter(val view: PlaceListContracts.ViewCountries): PlaceListContracts.Presenter {
    private lateinit var places: List<CountryModel>

//    override fun unwrapList(list: List<PlaceReference>) {
//        //TODO("Not yet implemented")
//    }

    override fun setPlaces(generalList: List<CountryModel>) {
       this.places = generalList
    }


}