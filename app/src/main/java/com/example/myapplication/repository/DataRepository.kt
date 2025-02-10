package com.example.myapplication.repository

import com.example.myapplication.R
import com.example.myapplication.model.ListItem

class DataRepository {
    fun getSampleImages(): List<Int> = listOf(R.drawable.india, R.drawable.avinya,R.drawable.microsoft,R.drawable.sachin)

    fun getListForImage(position: Int): List<ListItem> {
        return when (position) {
            0 -> listOf(ListItem("India",R.drawable.india),ListItem("Australia",R.drawable.australia),ListItem("West Indies",R.drawable.windies))
            1->   listOf(ListItem("Tata Avinya",R.drawable.avinya),ListItem("Mahindre be 6e",R.drawable.mahindra),ListItem("Tata Punch",R.drawable.punch))
            2-> listOf(ListItem("Microsoft",R.drawable.microsoft),ListItem("Google",R.drawable.google),ListItem("Facebook",R.drawable.facebook),ListItem("X",R.drawable.x))
            3->listOf(ListItem("Sachin",R.drawable.sachin),ListItem("Lara",R.drawable.lara),ListItem("Ricky Ponting",R.drawable.ricky),ListItem("Virat Kohli",R.drawable.virat))
            else -> emptyList()
        }
    }
}