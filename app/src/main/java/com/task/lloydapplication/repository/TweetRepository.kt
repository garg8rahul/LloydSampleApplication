package com.task.lloydapplication.repository

import com.task.lloydapplication.api.TweetsAPI
import com.task.lloydapplication.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsAPI: TweetsAPI) {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories : StateFlow<List<String>>
    get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets : StateFlow<List<TweetListItem>>
        get() = _tweets


    suspend fun getCategories(){
        val resposne = tweetsAPI.getCategories()
        if(resposne.isSuccessful && resposne.body() != null){
            _categories.emit(resposne.body()!!)
        }
    }

    suspend fun getTweets(category: String){
        val resposne = tweetsAPI.getTweets("tweets[?(@.category==\"$category\")]")
        if(resposne.isSuccessful && resposne.body() != null){
            _tweets.emit(resposne.body()!!)
        }
    }
}