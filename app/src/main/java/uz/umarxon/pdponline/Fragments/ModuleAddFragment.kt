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
import kotlinx.android.synthetic.main.fragment_module_add.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter2
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
 * Use the [ModuleAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleAddFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        root = inflater.inflate(R.layout.fragment_module_add, container, false)



        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModuleAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModuleAddFragment().apply {
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

        root.course_name9.text = course.name

        val modulList = AppDatabase.getInstance(root.context).moduleDao().getAllModul()

        val sortedModul = ArrayList<Modul>()
        val sortedModul2 = ArrayList<Modul>()

        for (i in modulList) {
            if (i.courseId == course.id) {
                sortedModul.add(i)
            }
        }

        val posList = ArrayList<Int>()

        for (i in sortedModul) {
            posList.add(i.position!!.toString().trim().toDouble().toInt())
        }

        posList.sort()

        for (i in posList.indices) {
            sortedModul2.add(sortedModul[getModuleAtPos(posList[i], sortedModul)])
        }

        root.btn_add.setOnClickListener {
            val name = root.name.text.toString()
            val position = root.course_position.text.toString()

            if (name.isNotEmpty() && position.isNotEmpty()) {

                var isHave = false

                for (i in sortedModul) {
                    if (i.position == position) {
                        Toast.makeText(context,
                            "$position o'rni oldin band qilingan",
                            Toast.LENGTH_SHORT).show()
                        isHave = true
                        break
                    }
                }

                if (!isHave) {
                    val modul = Modul()

                    modul.name = name
                    modul.position = position
                    modul.courseId = course.id

                    AppDatabase.getInstance(root.context).moduleDao().addModul(modul)
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    onResume()
                }

            } else {
                Toast.makeText(context, "Fields empty", Toast.LENGTH_SHORT).show()
            }
        }

        root.rv.adapter = RvAdapter2(sortedModul2, object : RvAdapter2.MyClick {
            override fun change(modul: Modul) {
                findNavController().navigate(R.id.editModuleFragment, bundleOf("modul" to modul, "course" to course))
            }

            override fun delete(modul: Modul) {

                val bottomSheetDialog = BottomSheetDialog(root.context,R.style.SheetDialog)

                val itemView = LayoutInflater.from(root.context).inflate(R.layout.delete_dialog,null,false)

                bottomSheetDialog.setContentView(itemView)

                itemView.no_btn.setOnClickListener {
                    Toast.makeText(context, "No clicked", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                }

                itemView.yes_btn.setOnClickListener {
                    AppDatabase.getInstance(root.context).moduleDao().deleteModul(modul)
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                    onResume()
                }

                bottomSheetDialog.show()
            }

            override fun click(modul: Modul, position: Int) {
                findNavController().navigate(R.id.lessondAddFragment, bundleOf("modul" to modul, "course" to course))
            }
        }, course.image!!)
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