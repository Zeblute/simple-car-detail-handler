package edu.msoe.rutherfordc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Random

class CarViewModel : ViewModel() {

    private val _carList = MutableLiveData<List<Car>>()
    private val carList: LiveData<List<Car>> get() = _carList

    private val makes = listOf("Chevrolet", "Ford", "Dodge", "Honda", "Toyota", "Jeep", "Nissan", "BMW", "Mercedes")
    private val colors = listOf("Red", "Blue", "Black", "White", "Gray", "Silver", "Yellow")

    init {
        generateCars()
    }

    private fun generateCars() {
        val random = Random()
        val cars = List(20) {
            Car(
                id = it,
                color = colors[random.nextInt(colors.size)],
                make = makes[random.nextInt(makes.size)],
                modelYear = 1975 + random.nextInt(50)
            )
        }
        _carList.value = cars
    }

    fun getCars(): LiveData<List<Car>> {
        return carList
    }

    fun updateCar(updatedCar: Car) {
        _carList.value = _carList.value?.map { if (it.id == updatedCar.id) updatedCar else it }
    }

}
