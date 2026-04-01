package id.feinn.feinnnearby.ui.onboarding.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.components.FeatureIconPlaceholder
import id.feinn.feinnnearby.ui.components.PagerIndicator
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme
import id.feinn.feinnnearby.ui.theme.glassBorder
import id.feinn.feinnnearby.ui.theme.glassSurface
import id.feinn.feinnnearby.ui.theme.primary400
import id.feinn.feinnnearby.ui.theme.primary600
import id.feinn.feinnnearby.ui.theme.primary700
import id.feinn.feinnnearby.ui.theme.tertiary300

@Composable
fun HowItWorkPage(
    onGetStartedClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            painter = painterResource(id = R.drawable.img_illustration_onboarding),
            contentDescription = stringResource(R.string.onboarding_illustration_image_description),
            contentScale = ContentScale.FillWidth
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = glassSurface
            ),
            border = BorderStroke(1.dp, glassBorder),
            shape = RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(stringResource(R.string.onboarding_title_part1) + "\n")
                        }
                        withStyle(style = SpanStyle(color = primary400)) {
                            append(stringResource(R.string.onboarding_title_part2))
                        }
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                Text(
                    text = stringResource(R.string.onboarding_description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Feature List
                FeatureItem(
                    icon = {
                        FeatureIconPlaceholder(
                            color = primary700,
                            id = R.drawable.ic_offline,
                            contentDescription = stringResource(R.string.onboarding_feature_mesh)
                        )
                    },
                    text = stringResource(R.string.onboarding_feature_mesh)
                )
                Spacer(modifier = Modifier.height(12.dp))
                FeatureItem(
                    icon = {
                        FeatureIconPlaceholder(
                            color = tertiary300,
                            id = R.drawable.ic_encrypted,
                            contentDescription = stringResource(R.string.onboarding_feature_encrypted)
                        )
                    },
                    text = stringResource(R.string.onboarding_feature_encrypted)
                )
                Spacer(modifier = Modifier.height(12.dp))
                FeatureItem(
                    icon = {
                        FeatureIconPlaceholder(
                            color = primary600,
                            id = R.drawable.ic_decentralized,
                            contentDescription = stringResource(R.string.onboarding_feature_no_servers)
                        )
                    },
                    text = stringResource(R.string.onboarding_feature_no_servers)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Get Started Button
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary400,
                        contentColor = Color.Black
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.onboarding_button_get_started),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.onboarding_footer_text),
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

            }

        }

        Spacer(modifier = Modifier.height(48.dp))

        PagerIndicator(
            currentPage = 0,
            pageCount = 3
        )

        Spacer(
            modifier = Modifier
                .height(48.dp)
        )

    }
}

@Composable
fun FeatureItem(icon: @Composable () -> Unit, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewHowItWork() {
    FeinnNearbyTheme {
        HowItWorkPage()
    }
}
