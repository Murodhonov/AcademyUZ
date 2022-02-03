package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.CourseListRvItemBinding
import uz.umarxon.pdponline.databinding.HorizontalRvItemBinding
import uz.umarxon.pdponline.databinding.ModuleRvItemBinding
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter7(private val list: List<Modul>, var myClick: MyClick,var t:String) :
    RecyclerView.Adapter<RvAdapter7.Vh>() {
    inner class Vh(var itemRv: CourseListRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(modul: Modul, position: Int) {

            itemRv.card.setOnClickListener {
                myClick.click(modul,position)
            }

            itemRv.countLesson.text = modul.position

            itemRv.courseItemName.text = modul.name

            itemRv.courseTypeName.text = t

        }
    }

    interface MyClick{
        fun click(modul: Modul,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CourseListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}