package edu.msoe.rutherfordc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CarDetailFragment : Fragment() {

    private val carViewModel: CarViewModel by activityViewModels()
    private var car: Car? = null
    private lateinit var purchaseDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = arguments?.getParcelable("car")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("car_update", viewLifecycleOwner) { _, bundle ->
            val updatedCar: Car? = bundle.getParcelable("car")
            updatedCar?.let {
                car = it
            }
        }

        // Find Views
        val makeTextView = view.findViewById<TextView>(R.id.makeTextView)
        val colorTextView = view.findViewById<TextView>(R.id.colorTextView)
        val yearTextView = view.findViewById<TextView>(R.id.yearTextView)
        purchaseDateTextView = view.findViewById(R.id.purchaseDateTextView)
        val editButton = view.findViewById<Button>(R.id.editPurchaseDateButton)

        // Display Car Details
        car?.let {
            makeTextView.text = "Manufacturer: " + it.make
            colorTextView.text = "Color: " + it.color
            yearTextView.text = "Year: " + it.modelYear.toString()

            // Display formatted purchase date
            purchaseDateTextView.text = "Purchase Date: " + formatDate(it.purchaseDate)
        }

        // edit Button Click
        editButton.setOnClickListener {
            car?.let { selectedCar ->
                val bundle = Bundle().apply {
                    putParcelable("car", selectedCar)
                }
                findNavController().navigate(R.id.action_carDetailFragment_to_carEditFragment, bundle)
            }
        }
    }

    private fun formatDate(date: Date?): String {
        return date?.let {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            sdf.format(it)
        } ?: "Not Set"
    }
}
