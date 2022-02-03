package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter1(private val list: List<Course>,var myClick: MyClick) :
    RecyclerView.Adapter<RvAdapter1.Vh>() {
    inner class Vh(var itemRv: SettingRvListBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(course: Course, position: Int) {

            itemRv.card.setOnClickListener {
                myClick.click(course,position)
            }

            itemRv.name1.text = course.name

            itemRv.image1.setImageURI(Uri.parse(course.image))

            itemRv.edit.setOnClickListener{
                myClick.change(course)
            }

            itemRv.delete.setOnClickListener {
                myClick.delete(course)
            }

        }
    }

    interface MyClick{
        fun change(course: Course)
        fun delete(course: Course)
        fun click(course: Course,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(SettingRvListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}