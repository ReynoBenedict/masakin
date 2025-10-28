package com.example.masakin.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.viewmodel.OnboardingUiState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun OnboardingScreen(
    ui: OnboardingUiState,
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    // simpan page sebelumnya untuk arah animasi (kanan/kiri)
    var lastPage by remember { mutableIntStateOf(ui.currentPage) }
    val forward = ui.currentPage >= lastPage
    LaunchedEffect(ui.currentPage) { lastPage = ui.currentPage }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_onboarding_red),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(485.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))

            // ======== ANIMASI KONTEN HALAMAN ========
            AnimatedContent(
                targetState = ui.currentPage,
                transitionSpec = {
                    val slideIn = if (forward)
                        slideInHorizontally(animationSpec = tween(350)) { full -> full }
                    else
                        slideInHorizontally(animationSpec = tween(350)) { full -> -full }

                    val slideOut = if (forward)
                        slideOutHorizontally(animationSpec = tween(350)) { full -> -full / 2 }
                    else
                        slideOutHorizontally(animationSpec = tween(350)) { full -> full / 2 }

                    (slideIn + fadeIn(tween(250))) togetherWith (slideOut + fadeOut(tween(250)))
                },
                label = "onboarding_page",
                contentKey = { it }, // kunci oleh index page
            ) { pageIndex ->
                val page = ui.pages[pageIndex]

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = page.image),
                        contentDescription = "Onboarding Illustration",
                        modifier = Modifier.height(300.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(100.dp))

                    Text(
                        text = page.title,
                        color = Color(0xFFDB2A2A),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = page.description,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            Spacer(Modifier.height(210.dp))

            // ======== INDIKATOR DENGAN ANIMASI ========
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(ui.totalPages) { i ->
                    val targetW = if (i == ui.currentPage) 18.dp else 8.dp
                    val widthAnim by animateDpAsState(targetW, animationSpec = tween(250), label = "dotW")
                    val targetColor = if (i == ui.currentPage) Color(0xFFDB2A2A) else Color.LightGray
                    val colorAnim by animateColorAsState(targetColor, animationSpec = tween(250), label = "dotC")

                    Box(
                        modifier = Modifier
                            .width(widthAnim)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(colorAnim)
                    )
                    if (i != ui.totalPages - 1) Spacer(Modifier.width(6.dp))
                }
            }

            Spacer(Modifier.height(60.dp))

            // ======== TOMBOL ========
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onSkip) {
                    Text("Skip", color = Color(0xFFDB2A2A), fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB2A2A)),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .width(90.dp)
                        .height(48.dp)
                ) {
                    // label berubah halus
                    Crossfade(targetState = ui.isLastPage, label = "next_label") { last ->
                        Text(
                            text = if (last) "Start" else "Next",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}