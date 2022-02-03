package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.AddModuleRvItemBinding
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter2(private val list: List<Modul>, var myClick: MyClick,var image:String) :
    RecyclerView.Adapter<RvAdapter2.Vh>() {
    inner class Vh(var itemRv: AddModuleRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(modul: Modul, position: Int) {

            itemRv.text2.text = modul.name

            itemRv.image2.setImageURI(Uri.parse(image))

            itemRv.coursePos.text = modul.position

            itemRv.card1.setOnClickListener {
                myClick.click(modul,position)
            }

            itemRv.change.setOnClickListener {
                myClick.change(modul)
            }

            itemRv.delete.setOnClickListener {
                myClick.delete(modul)
            }

        }
    }

    interface MyClick{
        fun change(modul: Modul)
        fun delete(modul: Modul)
        fun click(modul: Modul,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(AddModuleRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}