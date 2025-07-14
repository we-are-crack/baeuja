package com.eello.baeuja.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily

private val NavigationBarHeight = 107.dp
private val NavigationBarItemHeight = 83.dp

@Composable
fun OneButtonBottomBar(
    buttonText: String = "Verify",
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(NavigationBarHeight)
            .shadow(16.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), clip = false)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(NavigationBarItemHeight)
                .padding(bottom = 24.dp)
        ) {
            Button(
                enabled = enabled,
                onClick = onClick,
                modifier = Modifier
                    .size(370.dp, 50.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(8.dp),
                colors = if (enabled) {
                    ButtonDefaults.buttonColors(containerColor = Color(0xFF9388e8))
                } else {
                    ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)) // disabled color
                }
            ) {
                Text(
                    text = buttonText,
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOneButtonBottomBar() {
    BaujaTheme {
        OneButtonBottomBar(enabled = false, onClick = {})
    }
}