package id.feinn.feinnnearby.ui.onboarding.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.components.PagerIndicator
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme
import id.feinn.feinnnearby.ui.theme.background
import id.feinn.feinnnearby.ui.theme.backgroundElevated
import id.feinn.feinnnearby.ui.theme.error
import id.feinn.feinnnearby.ui.theme.glassBorder
import id.feinn.feinnnearby.ui.theme.onPrimary
import id.feinn.feinnnearby.ui.theme.onSurfaceVariant
import id.feinn.feinnnearby.ui.theme.primary400
import id.feinn.feinnnearby.ui.theme.primary600

@Composable
fun JoinMeshNetworkPage(
    modifier: Modifier = Modifier,
    onBluetoothActivateClick: () -> Unit = {},
    onConnectClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.join_mesh_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.join_mesh_subtitle),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = onSurfaceVariant,
                lineHeight = 22.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        ConnectivityCard(
            iconRes = R.drawable.ic_bluetooth,
            title = stringResource(R.string.join_mesh_ble_title),
            description = stringResource(R.string.join_mesh_ble_description),
            statusText = stringResource(R.string.join_mesh_status_off),
            isStatusOn = false,
            buttonContent = {
                Button(
                    onClick = onBluetoothActivateClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, glassBorder)
                ) {
                    Text(
                        text = stringResource(R.string.join_mesh_ble_activate),
                        color = primary400,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ConnectivityCard(
            iconRes = R.drawable.ic_wifi,
            title = stringResource(R.string.join_mesh_wifi_title),
            description = stringResource(R.string.join_mesh_wifi_description),
            statusText = stringResource(R.string.join_mesh_status_on),
            isStatusOn = true,
            buttonContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF132328))
                        .border(1.dp, Color(0xFF1B4332).copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shield_check),
                            contentDescription = null,
                            tint = Color(0xFF4ADE80),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.join_mesh_wifi_authorized),
                            color = Color(0xFF4ADE80),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onConnectClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary400,
                contentColor = onPrimary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = stringResource(R.string.join_mesh_button_connect),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.join_mesh_footer),
            style = MaterialTheme.typography.labelSmall.copy(
                color = onSurfaceVariant.copy(alpha = 0.5f),
                letterSpacing = 0.5.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        PagerIndicator(
            currentPage = 1,
            pageCount = 3
        )
    }
}

@Composable
fun ConnectivityCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    title: String,
    description: String,
    statusText: String,
    isStatusOn: Boolean,
    buttonContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundElevated)
            .padding(24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = title,
                        tint = primary400,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (isStatusOn) primary600 else error)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (isStatusOn) primary600 else error,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = onSurfaceVariant,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            buttonContent()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF0A0E1A)
private fun PreviewJoinMeshNetworkPage() {
    FeinnNearbyTheme {
        JoinMeshNetworkPage()
    }
}
