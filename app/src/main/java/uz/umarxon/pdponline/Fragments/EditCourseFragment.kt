package uz.umarxon.pdponline.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_edit_course.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
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
 * Use the [EditCourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCourseFragment : Fragment() {
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
    var image:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_course, container, false)



        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditCourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditCourseFragment().apply {
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

        root.name_course_edit.text = course.name

        root.name_course.setText(course.name)

        if(image.isNotEmpty()){
            root.images.setImageURI(Uri.parse(image))
        }else{
            root.images.setImageURI(Uri.parse(course.image))
        }

        root.images.setOnClickListener {
            getImageContent.launch("image/*")
        }

        root.edit_course.setOnClickListener {
            val name = root.name_course.text.toString()

            if (name.isNotEmpty() && image.isNotEmpty()) {

                course.name = name
                course.image = image

                AppDatabase.getInstance(root.context).courseDao().updateCourse(course)
                Toast.makeText(context, "Changed", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            uri ?: return@registerForActivityResult

            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = File(activity?.filesDir, "${simpleDateFormat}rasm.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)

            inputStream?.close()
            fileOutputStream.close()

            image = file.absolutePath
            root.images.setImageURI(Uri.parse(image))

        }
}