package uz.umarxon.pdponline.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter4
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
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false)


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        Data.isHome = true

        val list = AppDatabase.getInstance(root.context).courseDao().getAllCourse()
        val list2 = AppDatabase.getInstance(root.context).moduleDao().getAllModul()

        root.rv.adapter = RvAdapter4(list,list2,
            AppDatabase.getInstance(root.context),object : RvAdapter4.MyClick{
                override fun change(modul: Modul) {
                    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
                }

                override fun delete(modul: Modul) {
                    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
                }

                override fun click(modul: Modul,course:Course) {
                    findNavController().navigate(R.id.courseListFragment, bundleOf("modul" to modul,"course" to course))
                }

                override fun click2(modul: Modul,course:Course) {
                    findNavController().navigate(R.id.moduleListFragment, bundleOf("modul" to modul,"course" to course))
                }
            })

        root.setting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }
    }
}