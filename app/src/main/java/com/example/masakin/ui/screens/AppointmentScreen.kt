package com.example.masakin.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.masakin.R
import com.example.masakin.data.DummyData

/* --- WARNA --- */
private val MasakinRed = Color(0xFFD32F2F)
private val StarAmber = Color(0xFFFFC107)
private val IconBgRed = Color(0xFFFFEBEE)
private val TextGray = Color(0xFF757575)
private val SelectionBlue = Color(0xFFE3F2FD)
private val PrivacyGreenBg = Color(0xFFE8F5E9)
private val PrivacyGreenText = Color(0xFF4CAF50)

/* --- DATA MODEL --- */
data class PaymentMethod(
    val id: String,
    val name: String,
    val detail: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    consultantId: String = "1",
    onBack: () -> Unit = {},
    onNavigateToChat: (String) -> Unit = {} // Callback navigasi baru
) {
    val consultant = DummyData.consultants.find { it.id == consultantId } ?: DummyData.consultants.first()

    // State
    var showBottomSheet by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) } // State Pop Up Sukses
    val sheetState = rememberModalBottomSheetState()
    var selectedPaymentId by remember { mutableStateOf("visa") }

    val paymentMethods = remember {
        listOf(
            PaymentMethod("visa", "Kartu Kredit", "**** 6754", R.drawable.logo_visa),
            PaymentMethod("bca", "Bank BCA", "**2123424343", R.drawable.logo_bca),
            PaymentMethod("cash", "Tunai", "", R.drawable.ic_cash)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Appointment", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Button(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MasakinRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Beli", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Consultant Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 4.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                        Image(
                            painter = painterResource(id = consultant.favImageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(consultant.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(consultant.specialization, fontSize = 12.sp, color = TextGray)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("★", color = StarAmber, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(String.format("%.1f", consultant.rating), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatItem(Icons.Outlined.Groups, consultant.patients, "Patients")
                StatItem(Icons.Filled.ShowChart, consultant.yearsExperience, "Years Expert")
                StatItem(Icons.Filled.ThumbUp, consultant.likes, "Likes")
                StatItem(Icons.AutoMirrored.Filled.Chat, "${consultant.reviews}+", "Reviews")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("About me", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(consultant.description, fontSize = 13.sp, lineHeight = 20.sp, textAlign = TextAlign.Justify, color = Color.Black.copy(alpha = 0.7f))

            Spacer(modifier = Modifier.height(24.dp))
            Text("Doctor information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            InfoRow("Full name", consultant.fullName)
            InfoRow("Work in", consultant.workPlace)
            InfoRow("Available", consultant.availableTime)
            Spacer(modifier = Modifier.height(4.dp))
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = TextGray, fontSize = 14.sp)) { append("Price: ") }
                withStyle(style = SpanStyle(color = MasakinRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)) { append(consultant.price) }
            })
            Spacer(modifier = Modifier.height(100.dp))
        }

        // --- MODAL BOTTOM SHEET ---
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pilih Metode Pembayaran", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showBottomSheet = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = MasakinRed)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    paymentMethods.forEach { method ->
                        val isSelected = selectedPaymentId == method.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SelectionBlue else Color.Transparent)
                                .clickable { selectedPaymentId = method.id }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(shape = RoundedCornerShape(8.dp), color = Color.White, shadowElevation = 1.dp, modifier = Modifier.size(48.dp, 32.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(painter = painterResource(id = method.iconRes), contentDescription = null, modifier = Modifier.size(36.dp), contentScale = ContentScale.Fit)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(method.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                if (method.detail.isNotEmpty()) Text(method.detail, fontSize = 12.sp, color = TextGray)
                            }
                            if (isSelected) Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2196F3), modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Privacy
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(PrivacyGreenBg).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = PrivacyGreenText, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Kami akan melindungi data anda dan tidak akan kami jual data anda", fontSize = 11.sp, color = PrivacyGreenText, lineHeight = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // ACTION BUTTON - LANJUT
                    Button(
                        onClick = {
                            showBottomSheet = false
                            showSuccessDialog = true // Tampilkan dialog sukses
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MasakinRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Lanjut", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // --- PAYMENT SUCCESS DIALOG ---
        if (showSuccessDialog) {
            Dialog(onDismissRequest = {}) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            visible = true,
                            enter = scaleIn(animationSpec = tween(500)) + fadeIn()
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Success",
                                tint = Color(0xFF4CAF50), // Hijau Sukses
                                modifier = Modifier.size(100.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Pembayaran Berhasil!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Transaksi Anda telah berhasil diproses. Anda sekarang dapat berkonsultasi.",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = TextGray
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                showSuccessDialog = false
                                onNavigateToChat(consultantId) // Pindah ke Chat Screen
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MasakinRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Lanjut ke Chat", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp).clip(CircleShape).background(IconBgRed)) {
            Icon(icon, contentDescription = null, tint = MasakinRed, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MasakinRed)
        Text(label, fontSize = 10.sp, color = TextGray)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 4.dp)) {
        Text("$label : ", fontSize = 14.sp, color = TextGray, modifier = Modifier.width(80.dp))
        Text(value, fontSize = 14.sp, color = TextGray)
    }
}