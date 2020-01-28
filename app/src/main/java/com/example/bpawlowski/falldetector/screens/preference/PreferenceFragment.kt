package com.example.bpawlowski.falldetector.screens.preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bpawlowski.domain.model.Sensitivity
import com.example.bpawlowski.falldetector.databinding.FragmentPreferenceBinding
import com.example.bpawlowski.falldetector.screens.preference.compose.PreferenceItem
import com.example.bpawlowski.falldetector.screens.preference.compose.PreferenceOption
import com.example.bpawlowski.falldetector.screens.preference.compose.PreferenceScreen
import com.example.bpawlowski.falldetector.screens.preference.model.SensitivityPreference
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreferenceFragment : Fragment() {

    private val viewModel: PreferenceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPreferenceBinding.inflate(inflater)

        binding.composeView.setContent {
            PreferenceScreen(
                preferenceFlow = viewModel.preferenceFlow,
                onBooleanPreferenceChanged = viewModel.updateBooleanValue,
                onListPreferenceChanged = viewModel.updateStringValue
            )
        }
        return binding.root
    }
}
