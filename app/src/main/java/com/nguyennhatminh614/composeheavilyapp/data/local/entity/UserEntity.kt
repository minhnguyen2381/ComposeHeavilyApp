package com.nguyennhatminh614.composeheavilyapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "user")
data class UserEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "time")
    var time: String?,
    @ColumnInfo(name = "type")
    var type: Int?, // type template or resume
    @ColumnInfo(name = "pin")
    var pin: Boolean?,
    @ColumnInfo(name = "template")
    var template: Int?,
    @ColumnInfo(name = "thumb")
    var pathThumb: String?,
    @ColumnInfo(name = "id_cover_letter")
    var idCoverLetter: Int?,
    @ColumnInfo(name = "id_resume")
    var idResume: Int?,
    @Ignore
    var name: String = " "
) : Parcelable {

    companion object {
        fun toUser(jsonData: String): UserEntity? {
            return Gson().fromJson(jsonData, UserEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }


    override fun toString(): String {
        return "UserEntity id:$id  --  $title -- $time -- $type -- $pin"
    }
}
