package id.feinn.feinnnearby.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.feinn.feinnnearby.ui.theme.primary400

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int = 3,
    currentPage: Int = 0,
    selectedColor: Color = primary400,
    unselectedColor: Color = Color.White.copy(alpha = 0.1f)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(if (isSelected) 24.dp else 6.dp)
                    .background(
                        color = if (isSelected) selectedColor else unselectedColor,
                        shape = RoundedCornerShape(100)
                    )
            )
        }
    }
}