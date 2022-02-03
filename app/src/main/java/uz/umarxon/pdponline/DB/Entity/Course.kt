package uz.umarxon.pdponline.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Course:Serializable {

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    var name:String? = null
    var image:String? = null
    override fun toString(): String {
        return "Course(id=$id, name=$name, image=$image)"
    }

}