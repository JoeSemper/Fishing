package com.joesemper.fishing.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.joesemper.fishing.R
import com.joesemper.fishing.model.entity.user.User
import com.joesemper.fishing.utils.Logger
import com.joesemper.fishing.view.fragments.dialogFragments.LogoutListener
import com.joesemper.fishing.view.fragments.dialogFragments.UserBottomSheetDialogFragment
import com.joesemper.fishing.viewmodel.main.MainViewModel
import com.joesemper.fishing.viewmodel.main.MainViewState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity(), AndroidScopeComponent, LogoutListener {

    override val scope : Scope by activityScope()
    private val viewModel: MainViewModel by viewModel()

    private val logger: Logger by inject()

    private var currentUser: User? = null

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNav()
        subscribeOnViewModel()
    }

    private fun subscribeOnViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.subscribe().collect { viewState ->
                when (viewState) {
                    is MainViewState.Success -> { onSuccess(viewState.user) }
                    is MainViewState.Error -> { onError(viewState.error) }
                    MainViewState.Loading -> { }
                }
            }
        }
    }

    private fun onSuccess(user: User?) {
        if (user != null) {
            currentUser = user
        } else {
            startSplashActivity()
        }
    }

    private fun onError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        logger.log(error.message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_logout -> { startBottomSheetDialogFragment() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLogout() {
        viewModel.logOut()
    }

    private fun initBottomNav() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment? ?: return
        val navController = host.navController

        bottomNav.setupWithNavController(navController)
    }

    private fun startBottomSheetDialogFragment(): Boolean {
        if (currentUser != null) {
            val dialog = UserBottomSheetDialogFragment.newInstance(currentUser!!)
            dialog.show(supportFragmentManager, "TAG")
        }
        return true
    }

    private fun startSplashActivity() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

}
