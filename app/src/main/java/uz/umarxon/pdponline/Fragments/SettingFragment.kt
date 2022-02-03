package uz.umarxon.pdponline.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import uz.umarxon.pdponline.Adapter.RvAdapter1
import uz.umarxon.pdponline.DB.Database.AppDatabase
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.R
import uz.umarxon.pdponline.Utils.Data
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    var image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_setting, container, false)

        root.image_add.setOnClickListener {
            getImageContent.launch("image/*")
        }

        root.save.setOnClickListener {
            val name = root.name.text.toString()

            if (name.isNotEmpty() && image.isNotEmpty()) {
                val course = Course()

                course.name = name
                course.image = image

                root.name.text.clear()
                root.image_add.setImageResource(R.drawable.placeholder_image_1)
                image = ""

                AppDatabase.getInstance(root.context).courseDao().addCourse(course)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                onResume()
            } else {
                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            uri ?: return@registerForActivityResult
            root.image_add.setImageURI(uri)

            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = File(activity?.filesDir, "${simpleDateFormat}rasm.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)

            inputStream?.close()
            fileOutputStream.close()

            image = file.absolutePath
        }

    override fun onResume() {
        super.onResume()

        Data.isHome = false

        root.rv.adapter = RvAdapter1(AppDatabase.getInstance(root.context).courseDao().getAllCourse(),object : RvAdapter1.MyClick{
            override fun change(course: Course) {
                findNavController().navigate(R.id.editCourseFragment, bundleOf("course" to course))
            }

            override fun delete(course: Course) {
                val bottomSheetDialog = BottomSheetDialog(root.context,R.style.SheetDialog)

                val itemView = LayoutInflater.from(root.context).inflate(R.layout.delete_dialog,null,false)

                bottomSheetDialog.setContentView(itemView)

                itemView.no_btn.setOnClickListener {
                    Toast.makeText(context, "No clicked", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                }

                itemView.yes_btn.setOnClickListener {
                    AppDatabase.getInstance(root.context).courseDao().deleteCourse(course)
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.hide()
                    onResume()
                }

                bottomSheetDialog.show()

            }

            override fun click(course: Course, position: Int) {
                findNavController().navigate(R.id.moduleAddFragment, bundleOf("course" to course))
            }
        })
    }
}