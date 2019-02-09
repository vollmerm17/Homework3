package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "notes",
        foreignKeys = [ForeignKey(
                entity = Student::class,
                parentColumns=["studentId"],
                childColumns =["idStudent"],
                onDelete = ForeignKey.CASCADE
                )]
)

class Note(
        val title: String?,
        val content: String?,
        @ColumnInfo(name = "idStudent", index = true) var idStudent: Long){
        @PrimaryKey (autoGenerate = true)
        var id : Long = 0

}
