package com.example.rickmorty.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickmorty.R
import com.example.rickmorty.data.model.NightMode
import com.example.rickmorty.databinding.FragmentModeBinding
import com.example.rickmorty.presentation.extensions.applyInsetsWithAppBar
import com.example.rickmorty.presentation.manager.SharedPrefersManager
import org.koin.android.ext.android.inject

class ModeFragment : Fragment(){

    private var _binding: FragmentModeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager: SharedPrefersManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentModeBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            toolbar
                .setupWithNavController(findNavController())

            appBar.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right, top = insets.top)
                insets
            }

            darkModeSwitch.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right)
                insets
            }

            when(prefsManager.nightMode){
                NightMode.LIGHT -> darkModeSwitch.isChecked = false
                NightMode.DARK -> darkModeSwitch.isChecked = true
            }

            darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    prefsManager.nightMode = NightMode.DARK
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    prefsManager.nightMode = NightMode.LIGHT
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}