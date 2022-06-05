package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class OrderViewModel : ViewModel() {

    //Added Properties
    //add backing fields
    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price

    //update the properties above, depending on the user's choice:
    fun setQuantity(numberCupCakes: Int) {
        _quantity.value = numberCupCakes
    }

    fun setFlavor(desiredFlavour: String) {
        _flavor.value = desiredFlavour
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    //check if the flavour is set or not
    fun hasNoFlavourSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

}