package com.example.dicks.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abinav_dicks.R
import com.example.abinav_dicks.databinding.FragmentSeatGeekListBinding
import com.example.dicks.utils.Resource
import com.example.dicks.vm.SeatGeekViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeatGeekListFragment : Fragment(), SeatGeekAdapter.SeatGeekItemListener {

    private lateinit var binding: FragmentSeatGeekListBinding
    private val viewModel: SeatGeekViewModel by viewModels()
    private lateinit var adapter: SeatGeekAdapter
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeatGeekListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSeatGeek()
    }

    private fun setupObservers() {
        viewModel.seatGeekResp.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbList.isVisible = false
                    Log.d("Abhi", "Success ${it.data}")
                    val itemSeatGeekList = it.data?.events?.map { item ->
                        ItemSeatGeek(
                            imageUrl = item.performers.first { performers -> performers.image.isNotBlank() }.image,
                            title = item.title,
                            location = item.venue.display_location,
                            date = item.datetime_utc
                        )
                    } ?: emptyList()
                    adapter.setItems(itemSeatGeekList)
                }
                Resource.Status.ERROR -> {
                    Log.d("Abhi", "Failure: ${it.message}")
                    binding.pbList.isVisible = false
                }
                Resource.Status.LOADING -> {
                    binding.pbList.isVisible = true
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = SeatGeekAdapter(this)
        binding.apply {
            rvSeatList.layoutManager = LinearLayoutManager(requireContext())
            rvSeatList.adapter = adapter
        }
    }

    override fun onItemClicked(itemSeatGeek: ItemSeatGeek) {
        val directions = SeatGeekListFragmentDirections.goToDetails(itemSeatGeek)
        findNavController().navigate(directions)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem.actionView as SearchView
        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.i("onQueryTextChange", newText!!)
                    viewModel.getSeatGeek(newText)
                    //adapter.performSearch(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.i("onQueryTextSubmit", query!!)
                    viewModel.getSeatGeek(query)
                    //adapter.performSearch(query)
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                return false
            else -> {
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

}