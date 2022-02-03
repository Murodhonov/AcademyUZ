package uz.umarxon.pdponline.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Database.AppDatabase
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Lesson
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.HorizontalRvItemBinding
import uz.umarxon.pdponline.databinding.MainRvItemBinding

class RvAdapter4(private val list: List<Course>,private val list2: List<Modul>,var appDatabase: AppDatabase,var myClick: RvAdapter4.MyClick) :
    RecyclerView.Adapter<RvAdapter4.Vh>() {
    inner class Vh(var itemRv: MainRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(course: Course,p:Int) {

            itemRv.nameCourse.text = course.name

            val sortedList = ArrayList<Modul>()
    
            for (i in list2){
                if (list[p].id == i.courseId){
                    sortedList.add(i)
                }
            }

            itemRv.innerRv.adapter = RvAdapter5(sortedList,object : RvAdapter5.MyClick{

                override fun click(modul: Modul, position: Int) {
                    myClick.click2(modul,course)
                }
            })

            itemRv.arrow.setOnClickListener {
                myClick.click(list2[p],course)
            }
            itemRv.viewCourse.setOnClickListener {
                myClick.click(list2[p],course)
            }

        }
    }

    interface MyClick{
        fun change(modul: Modul)
        fun delete(modul: Modul)
        fun click(modul: Modul,course:Course)
        fun click2(modul: Modul,course:Course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(MainRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size
}