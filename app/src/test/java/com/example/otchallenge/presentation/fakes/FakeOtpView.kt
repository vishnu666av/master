package com.example.otchallenge.presentation.fakes

import com.example.otchallenge.presentation.OtpContract

class FakeOtpView: OtpContract.View {

    val listOfOperations = mutableListOf<OtpContract.Model>()

    override fun setModel(model: OtpContract.Model) {
        listOfOperations.add(model)
    }
}