package uz.umarxon.pdponline.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Modul:Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    var name:String? = null
    var position:String? = null
    var courseId:Int? = null
    override fun toString(): String {
        return "Modul(id=$id, name=$name, position=$position, courseId=$courseId)"
    }
}