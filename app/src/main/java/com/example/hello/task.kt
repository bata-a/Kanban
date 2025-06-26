package com.example.hello

import android.os.Parcel
import android.os.Parcelable
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    var status: TaskStatus
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: UUID.randomUUID().toString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",

        parcel.readString()?.let { statusName ->
            try {
                TaskStatus.valueOf(statusName)
            } catch (e: IllegalArgumentException) {
                TaskStatus.TO_DO
            }
        } ?: TaskStatus.TO_DO
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(status.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}

enum class TaskStatus {
    TO_DO,
    IN_PROGRESS,
    DONE
}