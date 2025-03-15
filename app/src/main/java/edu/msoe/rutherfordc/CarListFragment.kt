package edu.msoe.rutherfordc;


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.rutherfordc.Car
import edu.msoe.rutherfordc.CarViewModel
import edu.msoe.rutherfordc.R
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


class CarListFragment : Fragment() {

    private val carViewModel: CarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        carViewModel.getCars().observe(viewLifecycleOwner) { cars ->
            recyclerView.adapter = CarAdapter(cars) { car ->
                val bundle = Bundle().apply {
                    putParcelable("car", car) // pass the Car object
                }
                findNavController().navigate(R.id.action_carListFragment_to_carDetailFragment, bundle)
            }
        }

        return view
    }
}

class CarAdapter(private val cars: List<Car>, private val onClick: (Car) -> Unit) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.textView.text = "${car.modelYear} ${car.make}"

        holder.itemView.setOnClickListener {
            //pass car when viewing
            onClick(car)
        }
    }

    override fun getItemCount() = cars.size
}

