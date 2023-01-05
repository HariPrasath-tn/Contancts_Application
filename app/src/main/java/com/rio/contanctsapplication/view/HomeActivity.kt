package com.rio.contanctsapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.ActivityHomeBinding
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_home_fragment.ContactsFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.ContactInsertionFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.dailer_fragment.DialerFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.home_fragment.HomeFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.settings_fragment.SettingsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private var stackCount:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        // installing hte splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // initializing the binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        // setting the home fragment to the fragment container
        replaceFragment(HomeFragment())

        Toast.makeText(this, applicationInfo.dataDir, Toast.LENGTH_SHORT).show()
        binding.homeOptionMenu.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.homeOptionMenu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.optionItemAdd -> {
                        // initializing contact insertion fragment
                        setFragment(ContactInsertionFragment())
                    }
                    R.id.optionItemExport -> {

                    }
                    R.id.optionITemImportContact -> {

                    }
                    R.id.optionItemSettings -> {
                        // initializing settings fragment
                        setFragment(SettingsFragment())
                    }
                }
                false
            }
            popupMenu.inflate(R.menu.home_option)
            popupMenu.show()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomNavMenuProfile -> {
                    // initializing home fragment
                    setViewForStandard()
                    replaceFragment(HomeFragment())
                }
                R.id.bottomNavMenuDialer -> {
                    // initializing dialer fragment
                    setViewForStandard()
                    replaceFragment(DialerFragment())
                }
                R.id.bottomNavMenuContacts -> {
                    // initializing contact fragment
                    replaceFragment(ContactsFragment())
                }
            }
            true
        }
    }


    /**
     * [setFragment] methods the adds the given fragment into the fragment container and the backstack
     * @param fragment of type Fragment representing the instance of a fragment
     */
    fun setFragment(fragment:Fragment){
        // since there must be no disturbance in inner fragments disabling the top and bottom
        // components in the Activity
        setViewForSpecialFragment()
        // enabling the searchView if the fragment is Contact Fragment
        if(fragment is ContactsFragment){
            setViewForContactsView()
        }
        // initializing the transaction using the support manager
        val transaction = supportFragmentManager.beginTransaction()
        // adding the fragment to the fragment container
        transaction.replace(binding.fragmentContainer.id, fragment)
        // adding the given fragment to the backstack
        transaction.addToBackStack("fragment${++stackCount}")
        // committing the changes made to the supportManager
        transaction.commit()
    }

    /**
     * [replaceFragment] method replaces the fragment in the fragment container with the given
     * fragment
     * @param fragment of type Fragment representing the instance of a fragment
     */
    private fun replaceFragment(fragment:Fragment){
        // enabling the searchView if the fragment is Contact Fragment
        if(fragment is ContactsFragment){
            setViewForContactsView()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, fragment)
        transaction.commit()
    }

    /*
     * overriding the onBackPressed call back for some requirements provided in the
     * setViewForStandard method and to close whe there are no fragments in the fragment container
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val presentStackPosition = supportFragmentManager.backStackEntryCount
        if(supportFragmentManager.fragments[presentStackPosition] is ContactsFragment){
            setViewForStandard()
            setViewForContactsView()
        }else if(presentStackPosition == 0){
            setViewForStandard()
        }
    }

    /**
     * [setViewForStandard] method applies changes such as setting the visibilities to searchView(as
     * invisible) and homeOptionMenu(as visible)
     */
    private fun setViewForStandard(){
        binding.apply {
            contactSearchView.visibility = View.INVISIBLE
            activityTopBar.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    /**
     * [setViewForSpecialFragment] method applies changes such as, setting the visibilities to both
     * searchView and homeOptionMenu(as invisible)
     */
    // setting this settings is required inorder to prevent duplicate fragment instance
    // for example creating the instance of the add contacts fragment  inside the add contacts
    // fragment is unnecessary and also result in duplicate instance
    private fun setViewForSpecialFragment(){
        binding.apply {
            bottomNavigationView.visibility = View.GONE
            activityTopBar.visibility = View.GONE
        }
    }

    /**
     * [setViewForContactsView] method set the visibility to both searchView and homeOptionMenu as visible
     */
    // in contacts list fragment both search view and the options are needed since it need all the
    // functionalities
    private fun setViewForContactsView(){
        binding.apply{
            homeOptionMenu.visibility = View.VISIBLE
            activityTopBar.visibility = View.VISIBLE
            contactSearchView.visibility = View.VISIBLE
        }
    }
}