package com.example.myfirstapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel() : ViewModel() {
    private val _repository: CounterRepository = CounterRepository()
    private val _count = mutableIntStateOf(_repository.getCounter().count)

    //Expose state immutable
    val count: MutableState<Int> = _count

    //handlers
    fun increment() {
        _repository.incrementCounter()
        _count.intValue = _repository.getCounter().count
    }

    fun decrement() {
        _repository.decrementCounter()
        _count.intValue = _repository.getCounter().count
    }
}