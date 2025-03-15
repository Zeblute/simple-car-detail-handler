package edu.msoe.rutherfordc

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*

class CarEditFragment : Fragment() {

    private var car: Car? = null
    private lateinit var purchaseDateEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = arguments?.getParcelable("car")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        purchaseDateEditText = view.findViewById(R.id.purchaseDateEditText)

        // populate the edit field with the current purchase date
        car?.let {
            purchaseDateEditText.setText(formatDate(it.purchaseDate))
        }

        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            car?.let { selectedCar ->
                selectedCar.purchaseDate = purchaseDateEditText.text.toString().takeIf { it.isNotEmpty() }?.let { dateString ->
                    try {
                        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                        sdf.parse(dateString)
                    } catch (e: Exception) {
                        null
                    }
                }

                val resultBundle = Bundle().apply {
                    putParcelable("car", selectedCar)
                }
                parentFragmentManager.setFragmentResult("car_update", resultBundle)
                findNavController().navigateUp()
            }
        }
    }

    private fun formatDate(date: Date?): String {
        return date?.let {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            sdf.format(it)
        } ?: ""
    }
}

