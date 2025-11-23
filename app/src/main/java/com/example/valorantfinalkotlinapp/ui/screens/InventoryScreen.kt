package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(mainViewModel: MainViewModel) {
    val ownedSkins by mainViewModel.ownedSkins.collectAsState()
    val allSkins by mainViewModel.allSkins.collectAsState()

    var selectedWeapon by remember { mutableStateOf("Toutes") }
    var selectedTheme by remember { mutableStateOf("Toutes") }

    val weapons = listOf("Toutes") + allSkins.mapNotNull { it.weaponDisplayName }.distinct().sorted()
    val themes = listOf("Toutes") + allSkins.mapNotNull { it.themeDisplayName }.distinct().sorted()

    val filteredSkins = ownedSkins.filter { skin ->
        (selectedWeapon == "Toutes" || skin.weaponDisplayName == selectedWeapon) &&
        (selectedTheme == "Toutes" || skin.themeDisplayName == selectedTheme)
    }

    var weaponMenuExpanded by remember { mutableStateOf(false) }
    var themeMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inventaire") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Filtre par arme
                ExposedDropdownMenuBox(
                    expanded = weaponMenuExpanded,
                    onExpandedChange = { weaponMenuExpanded = !weaponMenuExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    TextField(
                        value = selectedWeapon,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Arme") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = weaponMenuExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = weaponMenuExpanded,
                        onDismissRequest = { weaponMenuExpanded = false }
                    ) {
                        weapons.forEach { weapon ->
                            DropdownMenuItem(
                                text = { Text(weapon) },
                                onClick = {
                                    selectedWeapon = weapon
                                    weaponMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Filtre par collection
                ExposedDropdownMenuBox(
                    expanded = themeMenuExpanded,
                    onExpandedChange = { themeMenuExpanded = !themeMenuExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    TextField(
                        value = selectedTheme,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Collection") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = themeMenuExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = themeMenuExpanded,
                        onDismissRequest = { themeMenuExpanded = false }
                    ) {
                        themes.forEach { theme ->
                            DropdownMenuItem(
                                text = { Text(theme) },
                                onClick = {
                                    selectedTheme = theme
                                    themeMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredSkins.isEmpty()) {
                Text(
                    text = "Aucun skin ne correspond à vos filtres.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    contentPadding = PaddingValues(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredSkins) { skin ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AsyncImage(
                                model = skin.displayIcon,
                                contentDescription = skin.displayName,
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = skin.displayName,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}