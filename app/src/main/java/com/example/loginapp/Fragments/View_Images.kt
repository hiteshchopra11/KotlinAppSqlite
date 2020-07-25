package com.example.loginapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.API.APIClient
import com.example.loginapp.API.APIInterface
import com.example.loginapp.Adapter.DataAdapter
import com.example.loginapp.Model.Images
import com.example.loginapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class View_Images : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: DataAdapter
    private lateinit var mainLayout: LinearLayout
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var service: APIInterface
    private lateinit var finalMainLayout: LinearLayout
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos_api, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = adapter
        progressBar.visibility = View.VISIBLE
        makeAPICAll()
    }

    private fun makeAPICAll() {
        service.getUserDetails()?.enqueue(object : Callback<MutableList<Images?>?> {
            override fun onResponse(call: Call<MutableList<Images?>?>?, response: Response<MutableList<Images?>?>?) {
                progressBar.visibility = View.INVISIBLE
                Log.e("Response Code", response?.code().toString() + "")
                if (response?.isSuccessful!! && response.code() == 200) {
                    val results = response.body()
                    if (results?.size == 0) {
                        val mainLayout = view?.findViewById<LinearLayout?>(R.id.searchLinearLayout)
                        mainLayout?.removeAllViews()
                        // inflate (create) another copy of our custom layout
                        val inflater = layoutInflater
                        val myLayout = inflater.inflate(R.layout.no_results, mainLayout, false)
                        mainLayout!!.addView(myLayout)
                    }
                    adapter.addAll(results)
                } else {
                    // error case
                    when (response.code()) {
                        404 -> Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(context, "Server broken", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Images?>?>?, throwable: Throwable?) {
                finalMainLayout.removeAllViews()
                // inflate (create) another copy of our custom layout
                val inflater = layoutInflater
                val myLayout = inflater.inflate(R.layout.network_failure, finalMainLayout, false)
                finalMainLayout.addView(myLayout)
                if (throwable != null) {
                    Toast.makeText(context, "Following error occurred-: " + throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initViews() {
        service = APIClient.getClient()?.create(APIInterface::class.java)!!
        mRecyclerView = view?.findViewById(R.id.recycleViewer)!!
        progressBar = view?.findViewById<ProgressBar?>(R.id.main_progress)!!
        adapter = DataAdapter(view!!.context)
        mainLayout = view?.findViewById<LinearLayout?>(R.id.searchLinearLayout)!!
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        finalMainLayout = mainLayout
    }
}