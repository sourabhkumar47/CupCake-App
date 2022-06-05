package com.example.cupcake.model

import android.provider.CalendarContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    val dateOptions = getPickupOptions()

    //Added Properties
    //add backing fields
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

//    private val _price = MutableLiveData<Double>()
//    val price: LiveData<Double> = _price

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    //update the properties above, depending on the user's choice:
    fun setQuantity(numberCupCakes: Int) {
        _quantity.value = numberCupCakes
        updatePrice()
    }

    fun setFlavor(desiredFlavour: String) {
        _flavor.value = desiredFlavour
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    //check if the flavour is set or not
    fun hasNoFlavourSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    //create and return the list of pickup dates.
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        //E -> Day name e.g Tue...
        val formatter = SimpleDateFormat("E MM d", Locale.getDefault())
        //Get calender instance
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    //to reset the MutableLiveData Properties
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    init {
        resetOrder()
    }

    //calculate price
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE

        //check if the user selected the same day pickup
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

}