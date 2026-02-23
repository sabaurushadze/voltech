package com.tbc.core_testing.test


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class CoroutineTestRule {

    protected val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)
}