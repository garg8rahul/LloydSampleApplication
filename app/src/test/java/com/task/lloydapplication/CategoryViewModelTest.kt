package com.task.lloydapplication

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.lloydapplication.repository.TweetRepository
import com.task.lloydapplication.viewmodels.CategoryViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import org.junit.Rule

@ExperimentalCoroutinesApi
class CategoryViewModelTest {

    // Add this rule to allow LiveData to work with coroutines
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoryViewModel
    private lateinit var tweetRepository: TweetRepository

    @Before
    fun setup() {
        tweetRepository = mock(TweetRepository::class.java)
        viewModel = CategoryViewModel(tweetRepository)
    }

    @Test
    fun testCategoriesFlow() = runBlockingTest {
        // Define a list of categories for testing
        val categoriesList = listOf("Category1", "Category2", "Category3")

        // Create a Flow of categories using the 'flow' builder
        val categoriesFlow: MutableStateFlow<List<String>> = MutableStateFlow(categoriesList)

        // Mock the 'categories' property of the repository to return the categoriesFlow
        `when`(tweetRepository.categories).thenReturn((categoriesFlow))

        // Create a collector to collect the values emitted by the categories Flow
        val collector = mutableListOf<List<String>>()
        viewModel.categories.collect {
            collector.add(it)
        }

        // Verify that the ViewModel's categories property emits the expected categories
        assertEquals(1, collector.size)
        assertEquals(categoriesList, collector[0])
    }

    @Test
    suspend fun testInitialization() {
        // Verify that when the ViewModel is initialized, the 'getCategories' function is called
        verify(tweetRepository).getCategories()
    }
}
