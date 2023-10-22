package com.task.lloydapplication.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.lloydapplication.models.TweetListItem
import com.task.lloydapplication.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val tweetRepository: TweetRepository,
    private val savedStateHandle: SavedStateHandle) :ViewModel() {

    val tweets : StateFlow<List<TweetListItem>>
        get() = tweetRepository.tweets

    init {
        viewModelScope.launch {
            var category = savedStateHandle.get<String>("category") ?: "motivation"
            tweetRepository.getTweets(category)
        }
    }
}