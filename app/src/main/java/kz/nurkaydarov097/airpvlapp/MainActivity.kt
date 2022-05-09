package kz.nurkaydarov097.airpvlapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.nurkaydarov097.airpvl.viewModels.JsoupDataViewModel
import kz.nurkaydarov097.airpvlapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy{ ViewModelProvider(this).get(JsoupDataViewModel::class.java)}
    //private val popullantViewModel by lazy{ ViewModelProvider(this).get(PopullantViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
            ?:return
        val navController: NavController = host.navController
        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener{
                _, destination, _ ->
        }

        viewModel.init(this)
        //popullantViewModel.init(this)

        viewModel.fetchData().observe(this, {
            Log.d("FetchData", "data - ${it} \n\n")
        })

    }

    private fun setupBottomNavMenu(navController: NavController){
        val bottomNav =findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav?.setupWithNavController(navController)
    }
}