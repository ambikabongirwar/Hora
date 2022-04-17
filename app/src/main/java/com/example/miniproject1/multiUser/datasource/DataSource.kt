package com.example.miniproject1.multiUser.datasource

import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.Group

class DataSource {
    fun loadAffirmations(): List<Group> {
        return listOf<Group>(
            Group(R.string.affirmation1),
            Group(R.string.affirmation2),
            Group(R.string.affirmation3),
            Group(R.string.affirmation4),
            Group(R.string.affirmation5),
            Group(R.string.affirmation6),
            Group(R.string.affirmation7),
            Group(R.string.affirmation8),
            Group(R.string.affirmation9),
            Group(R.string.affirmation10)
        )
    }
}