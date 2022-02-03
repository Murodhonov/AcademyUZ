package uz.umarxon.pdponline.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_edit_module.*
import kotlinx.android.synthetic.main.fragment_edit_module.view.*
import uz.umarxon.pdponline.DB.Database.AppDatabase
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.R
import uz.umarxon.pdponline.Utils.Data

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditModuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditModuleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_module, container, false)


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditModuleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        Data.isHome = false

        val course = arguments?.getSerializable("course") as Course
        val modul = arguments?.getSerializable("modul") as Modul

        root.name_edt.setText(modul.name)
        root.pos_edt.setText(modul.position)

        root.change.setOnClickListener {

            val name = name_edt.text.toString().trim()
            val pos = pos_edt.text.toString().trim()

            modul.name = name
            modul.position = pos

            if (name.isNotEmpty() && pos.isNotEmpty()) {
                AppDatabase.getInstance(root.context).moduleDao().updateModul(modul)
                Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else{
                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}