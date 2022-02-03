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
import kotlinx.android.synthetic.main.fragment_course_list.view.rv
import kotlinx.android.synthetic.main.fragment_module_list.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter5
import uz.umarxon.pdponline.Adapter.RvAdapter8
import uz.umarxon.pdponline.DB.Database.AppDatabase
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Lesson
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.R
import uz.umarxon.pdponline.Utils.Data

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ModuleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleListFragment : Fragment() {
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
        root = inflater.inflate(R.layout.fragment_module_list, container, false)


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModuleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModuleListFragment().apply {
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

        root.name_course8.text = course.name

        val appDatabase = AppDatabase.getInstance(root.context)

        val lessonList = appDatabase.lessonDao().getAllLesson()

        val sortedLesson = ArrayList<Lesson>()
        val sortedLesson2 = ArrayList<Lesson>()

        for (i in lessonList){
            if (i.module_id == modul.id){
                sortedLesson.add(i)
            }
        }

        val posList = ArrayList<Int>()

        for (i in sortedLesson) {
            posList.add(i.position!!.toString().trim().toDouble().toInt())
        }

        posList.sort()

        for (i in posList.indices) {
            sortedLesson2.add(sortedLesson[getModuleAtPos(posList[i], sortedLesson)])
        }

        root.rv.adapter = RvAdapter8(sortedLesson2,object : RvAdapter8.MyClick{
            override fun click(modul: Lesson, position: Int) {
                findNavController().navigate(R.id.lessonFragment, bundleOf("modul" to modul,"pos" to position))
            }
        })

    }
    private fun getModuleAtPos(i: Int, s: ArrayList<Lesson>): Int {
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