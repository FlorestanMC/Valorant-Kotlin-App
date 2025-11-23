package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.R
import com.example.valorantfinalkotlinapp.models.Skin
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxOpeningScreen(mainViewModel: MainViewModel) {
    val allSkins by mainViewModel.allSkins.collectAsState()
    val contentTiers by mainViewModel.contentTiers.collectAsState()
    var revealedSkin by remember { mutableStateOf<Skin?>(null) }
    var spinningSkin by remember { mutableStateOf<Skin?>(null) }
    var isOpening by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val glowElevation by animateDpAsState(
        targetValue = if (showConfetti) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 400, delayMillis = 200)
    )

    fun openBox() {
        scope.launch {
            val unownedSkins = allSkins.filter { !it.isOwned }
            if (unownedSkins.isNotEmpty()) {
                isOpening = true
                revealedSkin = null // Reset previous skin
                showConfetti = false

                // Slot machine effect
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < 2000) { // Spin for 2 seconds
                    spinningSkin = unownedSkins.random()
                    delay(100) // How fast the images cycle
                }

                val randomSkin = unownedSkins.random()
                mainViewModel.updateSkin(randomSkin.copy(isOwned = true))
                revealedSkin = randomSkin
                showConfetti = true
                isOpening = false
                spinningSkin = null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ouvrir une boîte") })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // The content for the top part, wrapped in an if/else
                if (isOpening) {
                    Box(modifier = Modifier.height(200.dp), contentAlignment = Alignment.Center) {
                        spinningSkin?.let { skin ->
                            AsyncImage(
                                model = skin.displayIcon,
                                contentDescription = "Spinning Skin",
                                modifier = Modifier.size(128.dp)
                            )
                        }
                    }
                } else if (revealedSkin != null) {
                    AnimatedVisibility(
                        modifier = Modifier.height(200.dp),
                        visible = revealedSkin != null,
                        enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            revealedSkin?.let { skin ->
                                val tier = contentTiers.find { it.uuid == skin.contentTierUuid }
                                val borderColor = tier?.highlightColor?.let { Color("#$it".toColorInt()) } ?: MaterialTheme.colorScheme.primary

                                AsyncImage(
                                    model = skin.displayIcon,
                                    contentDescription = skin.displayName,
                                    modifier = Modifier
                                        .size(128.dp)
                                        .shadow(
                                            elevation = glowElevation,
                                            shape = CircleShape,
                                            clip = false,
                                            ambientColor = borderColor,
                                            spotColor = borderColor
                                        )
                                        .border(2.dp, borderColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = skin.displayName,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.height(200.dp), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.valorant_logo),
                            contentDescription = "Boîte Cadeau",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { openBox() },
                    enabled = !isOpening && allSkins.any { !it.isOwned }
                ) {
                    Text("Ouvrir une caisse")
                }
            }

            if (showConfetti) {
                KonfettiView(
                    modifier = Modifier.fillMaxSize(),
                    parties = listOf(
                        Party(
                            speed = 0f, maxSpeed = 30f, damping = 0.9f, spread = 360,
                            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
                        )
                    )
                )
            }
        }
    }
}