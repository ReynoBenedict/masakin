package com.example.masakin.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.masakin.ui.theme.Red50

@Composable
fun SocialLoginButton(
    @DrawableRes icon: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    buttonSize: Dp = 70.dp,
    iconSize: Dp = 35.dp
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Red50),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White,
            contentColor = Red50
        ),
        modifier = Modifier.size(buttonSize)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize)
        )
    }
}
