package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Lesson
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.AddLessonRvItemBinding
import uz.umarxon.pdponline.databinding.AddModuleRvItemBinding
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter3(private val list: List<Lesson>, var myClick: MyClick, var image:String) :
    RecyclerView.Adapter<RvAdapter3.Vh>() {
    inner class Vh(var itemRv: AddLessonRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(lesson: Lesson, position: Int) {

            itemRv.name.text = lesson.name

            itemRv.image3.setImageURI(Uri.parse(image))

            val text = if (lesson.description!!.length > 15){
                lesson.description!!.subSequence(0,15).toString()+"..."
            }else{
                lesson.description
            }

            itemRv.theme.text = text

            itemRv.change.setOnClickListener {
                myClick.change(lesson)
            }

            itemRv.delete.setOnClickListener {
                myClick.delete(lesson)
            }

            itemRv.card.setOnClickListener {
                myClick.click(lesson,position)
            }
        }
    }

    interface MyClick{
        fun change(lesson: Lesson)
        fun delete(lesson: Lesson)
        fun click(lesson: Lesson,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(AddLessonRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}