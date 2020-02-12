package jp.onlineconsultant.listsample

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var content: String?,
    val from_user_id: Int?,
    val to_user_id: Int?,
    var limit: String?,
    var purpose_id: Int?,
    var created: String,
    var updated: String
) {

    override fun toString(): String {

        val task_name:String
        if(content != null){
            task_name = content!!
        }else{
            task_name = "無題"
        }
        return task_name
    }
}