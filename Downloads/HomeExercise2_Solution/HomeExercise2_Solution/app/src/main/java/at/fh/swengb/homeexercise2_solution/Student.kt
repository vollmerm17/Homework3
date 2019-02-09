package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.*

@Entity(tableName = "students")
class Student( val name: String, val password: String, val age: Int?) {
    @PrimaryKey (autoGenerate = true)
    var studentId : Long = 0

}
