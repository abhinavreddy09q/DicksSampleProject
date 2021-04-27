package com.example.dicks.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicks.data.SeatGeekResponse
import com.example.dicks.repo.SeatGeekRepository
import com.example.dicks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SeatGeekViewModel @Inject constructor(private val repository: SeatGeekRepository) : ViewModel() {

    private val disposable = CompositeDisposable()

    val seatGeekResp = MutableLiveData<Resource<SeatGeekResponse>>()

    fun getSeatGeek(searchQuery: String? = null) {
        disposable.add(repository.getListOfSeatGeeks(searchQuery)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { seatGeekResp.value = Resource.loading() }
            .subscribe({ result -> seatGeekResp.value = Resource.success(result) }
            ) { error -> seatGeekResp.value = Resource.error(error.message) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}