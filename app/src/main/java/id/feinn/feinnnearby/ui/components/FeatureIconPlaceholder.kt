package id.feinn.feinnnearby.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FeatureIconPlaceholder(
    color: Color,
    @DrawableRes id: Int,
    contentDescription: String?
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(17.dp),
            painter = painterResource(id),
            contentDescription = contentDescription,
        )
    }
}
