package com.example.hello

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedTaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    fun addTask(task: Task) {
        val currentList = _tasks.value ?: mutableListOf()
        currentList.add(task)
        _tasks.value = currentList
    }

    fun updateTask(updatedTask: Task) {
        val currentList = _tasks.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            currentList[index] = updatedTask
            _tasks.value = currentList
        }
    }


    fun deleteTask(taskId: String) {
        val currentList = _tasks.value ?: mutableListOf()
        val updatedList = currentList.filter { it.id != taskId }.toMutableList()
        _tasks.value = updatedList
    }
}