package uz.umarxon.pdponline.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Lesson :Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    var name:String? = null
    var description:String? = null
    var position:String? = null
    var module_id:Int? = null

    constructor()

    override fun toString(): String {
        return "Lesson(id=$id, name=$name, description=$description, position=$position, module_id=$module_id)"
    }
}