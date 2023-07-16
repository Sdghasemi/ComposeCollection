package com.hirno.compose.collection.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hirno.compose.collection.MainApplication
import com.hirno.compose.collection.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var viewDataBinding: FragmentMainBinding

    private lateinit var listAdapter: MainAdapter

    /**
     * A dynamic BroadcastReceiver to listen for network connectivity changes
     */
    private val networkConnectivityChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.onNetworkConnectivityChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = MainViewModelFactory((requireContext().applicationContext as MainApplication).collectionsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.loadCollections()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            networkConnectivityChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION) // Only deprecated for manifest-registered broadcasts
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(networkConnectivityChangeReceiver)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = MainAdapter(viewModel)
            viewDataBinding.list.apply {
                adapter = listAdapter
            }
        } else {
            Log.w(MainFragment::class.java.simpleName, "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}