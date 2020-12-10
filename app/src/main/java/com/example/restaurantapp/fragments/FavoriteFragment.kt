package com.example.restaurantapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.FavoritesListAdapter
import com.example.restaurantapp.databinding.FragmentFavoriteBinding
import com.example.restaurantapp.viewmodel.FavoritesViewModel
import com.example.restaurantapp.viewmodel.RestaurantViewModel
import com.example.restaurantapp.viewmodel.UserViewModel


class FavoriteFragment : Fragment(), FavoritesListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private val restaurantViewModel: RestaurantViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)


        recyclerView = binding.favoritesList
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        val adapter = FavoritesListAdapter(listOf(), this, binding.root.context, restaurantViewModel, userViewModel)
        recyclerView.adapter = adapter

        favoritesViewModel.favorites.observe(viewLifecycleOwner) { it ->
            if(it.isEmpty()) {
                binding.noResult.visibility = View.VISIBLE
            }
            else {
                binding.noResult.visibility = View.GONE
                val usersFavorite = it.filter { it.userId == userViewModel.currentUser.value?.uid }
                adapter.setData(usersFavorite)
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment)
    }

}