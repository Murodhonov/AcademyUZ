package uz.umarxon.pdponline.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_course_list.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter7
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
 * Use the [CourseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseListFragment : Fragment() {
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
        root = inflater.inflate(R.layout.fragment_course_list, container, false)

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CourseListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        Data.isHome = false

        val modul = arguments?.getSerializable("modul") as Modul
        val course = arguments?.getSerializable("course") as Course

        val moduleList = AppDatabase.getInstance(root.context).moduleDao().getAllModul()
        val sortedModule = ArrayList<Modul>()
        val sortedModule2 = ArrayList<Modul>()

        for (i in moduleList){
            if (i.courseId == modul.courseId){
                sortedModule.add(i)
            }
        }
        val posList = ArrayList<Int>()

        for (i in sortedModule) {
            posList.add(i.position!!.toString().trim().toDouble().toInt())
        }

        posList.sort()

        for (i in posList.indices) {
            sortedModule2.add(sortedModule[getModuleAtPos(posList[i], sortedModule)])
        }

        root.name_course5.text = course.name

        root.rv.adapter = RvAdapter7(sortedModule2,object : RvAdapter7.MyClick{
            override fun click(modul: Modul, position: Int) {
                findNavController().navigate(R.id.moduleListFragment, bundleOf("modul" to modul,"course" to course))
            }
        },course.name!!)

    }
    private fun getModuleAtPos(i: Int, s: ArrayList<Modul>): Int {
        var b = -1
        for (a in s.indices) {
            if (i.toString() == s[a].position) {
                b = a
                break
            }
        }
        return b
    }
}