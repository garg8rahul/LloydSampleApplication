package com.task.lloydapplication

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.junit.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.task.lloydapplication.models.TweetListItem
import com.task.lloydapplication.repository.TweetRepository
import com.task.lloydapplication.viewmodels.TweetsViewModel
import org.junit.Rule

@ExperimentalCoroutinesApi
class TweetsViewModelTest {

    // Add this rule to allow LiveData to work with coroutines
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TweetsViewModel
    private lateinit var tweetRepository: TweetRepository
    private val savedStateHandle: SavedStateHandle = mock(SavedStateHandle::class.java)

    @Before
    fun setup() {
        tweetRepository = mock(TweetRepository::class.java)
        viewModel = TweetsViewModel(tweetRepository, savedStateHandle)
    }

    @Test
    fun testTweetsFlow() = runBlockingTest {
        // Define a list of TweetListItems for testing
        val tweetList = listOf(
            TweetListItem("Tweet1", "Author1"),
            TweetListItem("Tweet2", "Author2")
        )

        // Create a MutableStateFlow for testing
        val tweetsFlow: MutableStateFlow<List<TweetListItem>> = MutableStateFlow(tweetList)

        // Mock the 'tweets' property of the repository to return the tweetsFlow
        `when`(tweetRepository.tweets).thenReturn(tweetsFlow)

        // Verify that the ViewModel's tweets property emits the expected values
        val emittedTweets = viewModel.tweets.value
        assertEquals(tweetList, emittedTweets)
    }

    @Test
    suspend fun testInitialization() {
        val category = "motivation"

        // Mock the savedStateHandle to return the category value
        `when`(savedStateHandle.get<String>("category")).thenReturn(category)

        // Verify that when the ViewModel is initialized, the 'getTweets' function is called with the expected category
        verify(tweetRepository).getTweets(category)
    }
}
