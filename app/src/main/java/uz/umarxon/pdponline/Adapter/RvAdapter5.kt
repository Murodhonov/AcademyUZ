package uz.umarxon.pdponline.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Modul
import uz.umarxon.pdponline.databinding.HorizontalRvItemBinding
import uz.umarxon.pdponline.databinding.SettingRvListBinding

class RvAdapter5(private val list: List<Modul>, var myClick: MyClick) :
    RecyclerView.Adapter<RvAdapter5.Vh>() {
    inner class Vh(var itemRv: HorizontalRvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(modul: Modul, position: Int) {

            itemRv.horizontalRvText.setOnClickListener {
                myClick.click(modul,position)
            }

            itemRv.horizontalRvText.text = modul.name

        }
    }

    interface MyClick{
        fun click(modul: Modul,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HorizontalRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}