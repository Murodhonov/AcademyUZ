package uz.umarxon.pdponline.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_lessond_add.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter3
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
 * Use the [LessondAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LessondAddFragment : Fragment() {
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
        root = inflater.inflate(R.layout.fragment_lessond_add, container, false)


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LessondAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LessondAddFragment().apply {
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

        val lessonList = AppDatabase.getInstance(root.context).lessonDao().getAllLesson()

        val sortedLesson = ArrayList<Lesson>()
        val sortedLesson2 = ArrayList<Lesson>()

        for (i in lessonList) {
            if (i.module_id == modul.id) {
                sortedLesson.add(i)
            }
        }

        val posList = ArrayList<Int>()

        for (i in sortedLesson) {
            posList.add(i.position!!.toString().trim().toDouble().toInt())
        }

        posList.sort()

        for (i in posList.indices) {
            sortedLesson2.add(sortedLesson[getLessonAtPos(posList[i], sortedLesson)])//bu yerda dars o'rnini to'g'irlash uchun malumotlar birma-bir yangi list ga olinmoqda
        }

        root.add_lesson.setOnClickListener {
            val name = root.name.text.toString()
            val description = root.description.text.toString()
            val pos = root.course_pos.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty() && pos.isNotEmpty()) {

                var isHave = false
                for (i in sortedLesson2){
                    if (pos == i.position){
                        isHave = true
                    }
                }

                if (isHave){

                    Toast.makeText(context, "$pos o'rin oldin band qilingan", Toast.LENGTH_SHORT).show()

                }else{
                    val lesson = Lesson()

                    lesson.name = name
                    lesson.description = description
                    lesson.position = pos
                    lesson.module_id = modul.id

                    AppDatabase.getInstance(root.context).lessonDao().addLesson(lesson)
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    onResume()
                }

            } else {
                Toast.makeText(context, "Fields empty", Toast.LENGTH_SHORT).show()
            }
        }
        root.rv.adapter = RvAdapter3(sortedLesson2, object : RvAdapter3.MyClick {
            override fun change(lesson: Lesson) {
                findNavController().navigate(R.id.editLessonFragment, bundleOf("lesson" to lesson))
            }

            override fun delete(lesson: Lesson) {
                val bottomSheetDialog = BottomSheetDialog(root.context,R.style.SheetDialog)

                val itemView = LayoutInflater.from(root.context).inflate(R.layout.delete_dialog,null,false)

                bottomSheetDialog.setContentView(itemView)

                itemView.no_btn.setOnClickListener {
                    Toast.makeText(context, "No clicked", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                }

                itemView.yes_btn.setOnClickListener {
                    AppDatabase.getInstance(root.context).lessonDao().deleteLesson(lesson)
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                    onResume()
                }

                bottomSheetDialog.show()
            }

            override fun click(lesson: Lesson, position: Int) {
                findNavController().navigate(R.id.lessonFragment, bundleOf("modul" to lesson,"pos" to position))
            }
        }, course.image!!)
    }

    private fun getLessonAtPos(i: Int, s: ArrayList<Lesson>): Int {
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