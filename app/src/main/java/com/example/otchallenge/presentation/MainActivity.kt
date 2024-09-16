package com.example.otchallenge.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.otchallenge.R
import com.example.otchallenge.di.DaggerAppComponent
import com.example.otchallenge.di.PresentationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OtpContract.View {


	@Inject
	lateinit var presenter: OtpContract.Presenter

	lateinit var recyclerView: RecyclerView
	lateinit var results: View
	lateinit var otAdapter: OtAdapter
	lateinit var error: View
	lateinit var spinner: View

	override fun onCreate(savedInstanceState: Bundle?) {
		DaggerAppComponent.builder().presentationModule(PresentationModule(this)).build().inject(this)
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		otAdapter = OtAdapter(emptyList())
		recyclerView  = findViewById(R.id.recyecle_view)
		results = findViewById(R.id.results)
		error = findViewById(R.id.error)
		spinner = findViewById(R.id.indeterminateBar)


		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = otAdapter

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
	}


	override fun onStart() {
		super.onStart()
		presenter.onStart()
	}


	override fun setModel(model: OtpContract.Model) {
		spinner.isVisible = model.showSpinner
		results.isVisible = model.showResults
		error.isVisible = model.showError
		if(model is OtpContract.Model.Success){
			otAdapter.setData(model.results)
		}
	}

}
