package com.todo.kotlin.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.kotlin.R
import com.todo.kotlin.ui.adapter.TodoAdapter
import com.todo.kotlin.ui.factory.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TodoAdapter()
        }
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java).apply {
            todoList.observe(this@MainActivity, Observer {
                (recycler.adapter as TodoAdapter).setData(it)
            })
            responseStatus.observe(this@MainActivity, Observer {
                when(it){
                    MainViewModel.Status.COMPLETED-> onSuccess()
                    MainViewModel.Status.ERROR-> onError()
                    MainViewModel.Status.LOADING -> onLoad()
                }
            })
        }.let {
            loadData(it)
            swipeRefresh.setOnRefreshListener {
                loadData(it)
            }
            retry.setOnClickListener {_->
                loadData(it)
            }
        }
    }

    private fun loadData(viewModel: MainViewModel) {
        viewModel.fetchTodoList()
    }

    private fun onLoad() {
        swipeRefresh.isRefreshing = true
        retry.visibility = View.GONE
        errorTV.visibility = View.GONE
    }

    private fun onSuccess(){
        swipeRefresh.isRefreshing = false
    }

    private fun onError(){
        swipeRefresh.isRefreshing = false
        Toast.makeText(this@MainActivity, getString(R.string.error_msg), Toast.LENGTH_LONG).show()
        if((recycler.adapter as TodoAdapter).itemCount == 0){
            retry.visibility = View.VISIBLE
            errorTV.visibility = View.VISIBLE
        }
    }
}
