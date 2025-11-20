package com.example.masakin.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.example.masakin.viewmodel.OnboardingViewModel
import com.example.masakin.viewmodel.OnboardingEffect
import com.example.masakin.ui.screens.OnboardingScreen


@Composable
fun OnboardingRoute(
    vm: OnboardingViewModel = viewModel(),
    onFinish: () -> Unit
) {
    val ui by vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        vm.effect.collect { eff ->
            if (eff is OnboardingEffect.NavigateToAuth) onFinish()
        }
    }
    OnboardingScreen(
        ui = ui,
        onSkip = { scope.launch { vm.skip() } },
        onNext = {
            if (ui.isLastPage) {
                scope.launch { vm.finish() }
            } else {
                vm.next()
            }
        }
    )
}
