package dev.daneeskripter.cernyrobin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment())
        val navigationMenu = findViewById<BottomNavigationView>(R.id.BottomNavigationView)
        navigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeBtn -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.infoBtn -> {
                    replaceFragment(InfoFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentMgr = supportFragmentManager
        val fragmentTransaction = fragmentMgr.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}