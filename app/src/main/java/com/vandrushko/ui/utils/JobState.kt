package com.vandrushko.ui.utils

import com.vandrushko.data.model.UserData

open class JobState {
    object Empty : JobState()
    data class Success(val userData: UserData) : JobState()
    object Loading : JobState()
    data class Error(val error: Int) : JobState()
}