package com.alfa.alfastoryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfa.alfastoryapp.api.ApiConfig
import com.alfa.alfastoryapp.databinding.ActivityMainBinding
import com.alfa.alfastoryapp.datastore.UserPreference
import com.alfa.alfastoryapp.detail.DetailActivity
import com.alfa.alfastoryapp.login.LoginActivity
import com.alfa.alfastoryapp.main.ListStory
import com.alfa.alfastoryapp.main.MainViewModel
import com.alfa.alfastoryapp.repository.Repository
import com.alfa.alfastoryapp.story.StoryActivity
import com.alfa.alfastoryapp.story.StoryAdapter
import com.alfa.alfastoryapp.utils.Result
import com.alfa.alfastoryapp.utils.UserViewModelFactory

class MainActivity : AppCompatActivity(), StoryAdapter.OnItemClickCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference
    private lateinit var viewModel: MainViewModel
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)
        storyAdapter = StoryAdapter(this, this)
        val repository = Repository(ApiConfig.getApiService())
        viewModel = ViewModelProvider(this, UserViewModelFactory(repository))[MainViewModel::class.java]

        fetchData(userPreference.token)

        binding.swRefresh.setOnRefreshListener {
            binding.swRefresh.isRefreshing = true
            fetchData(userPreference.token)
        }

        binding.btnTry.setOnClickListener{
            showLoading(true)
            fetchData(userPreference.token)
        }

        binding.fbAddStory.setOnClickListener{
            val addStory = Intent(this, StoryActivity::class.java)
            startActivity(addStory)
        }
    }

    private fun fetchData(authorization: String){
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter
        }
        viewModel.apply {
            showLoading(true)
            fetchListStory(authorization)
            responseListStory.observe(this@MainActivity){
                when(it){
                    is Result.Success ->{
                        if (it.data?.listStory != null){
                            storyAdapter.setData(it.data.listStory)
                            binding.btnTry.visibility = View.GONE
                        }else{
                            binding.btnTry.visibility = View.GONE
                            binding.rvStory.visibility = View.GONE
                        }
                        binding.tvError.visibility = View.GONE
                        showLoading(false)
                        binding.swRefresh.isRefreshing = false
                    }
                    is Result.Loading ->{
                        showLoading(true)
                        binding.swRefresh.isRefreshing = true
                    }
                    is Result.Error ->{
                        showLoading(false)
                        binding.rvStory.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.btnTry.visibility = View.VISIBLE
                        binding.swRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_change_language ->{
                val changeLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(changeLanguage)
                return true
            }
            R.id.menu_logout ->{
                val logout = AlertDialog.Builder(this)
                logout.setTitle(resources.getString(R.string.logout))
                logout.setMessage(getString(R.string.logout_confirm))
                logout.setPositiveButton(getString(R.string.logout_accept)){ _,_ ->
                    userPreference.clear()
                    val logoutConfirm = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(logoutConfirm)
                    this@MainActivity.finish()
                    setMessage(this, getString(R.string.logout_status_accept))
                }
                logout.setNegativeButton(getString(R.string.logout_denied)){ _,_ ->
                    setMessage(this, getString(R.string.logout_status_denied))
                }
                logout.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(listStory: ListStory, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, listStory)
        startActivity(intent, optionsCompat.toBundle())
    }


    private fun showLoading(b: Boolean) {
        if (b) {
            binding.pbMain.visibility = View.VISIBLE
        } else {
            binding.pbMain.visibility = View.GONE
        }
    }

    private fun setMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


}