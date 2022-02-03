package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Lesson
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.CourseListRvItemBinding
import uz.umarxon.pdponline.databinding.HorizontalRvItemBinding
import uz.umarxon.pdponline.databinding.ModuleRvItemBinding
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter8(private val list: List<Lesson>, var myClick: MyClick) :
    RecyclerView.Adapter<RvAdapter8.Vh>() {
    inner class Vh(var itemRv: ModuleRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(modul: Lesson, position: Int) {

            itemRv.card.setOnClickListener {
                myClick.click(modul,position)
            }

            itemRv.countLesson.text = modul.position


        }
    }

    interface MyClick{
        fun click(modul: Lesson,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ModuleRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}