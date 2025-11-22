package com.example.valorantfinalkotlinapp

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAppNavigationToGame() {
        // Start on the home screen and navigate to the game
        composeTestRule.onNodeWithText("Jeu").performClick()
        
        // Check for title on GameScreen
        composeTestRule.onNodeWithText("Memory Game").assertExists()
    }

    @Test
    fun testAppNavigationToStats() {
        // Start on the home screen and navigate to the game
        composeTestRule.onNodeWithText("Stats").performClick()

        // Check for title on GameScreen
        composeTestRule.onNodeWithText("Statistiques").assertExists()
    }

    @Test
    fun testMemoryGameInteraction() {
        // Navigation dans la fenêtre du jeu
        composeTestRule.onNodeWithText("Jeu").performClick()

        // Une fois que les cartes sont toutes affichées vérifie qu'elles sont bien là
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithTag("memory_board")
                .fetchSemanticsNode().children.size == 16
        }
        val cards = composeTestRule.onNodeWithTag("memory_board").onChildren()
        cards.assertCountEquals(16)

        // Retourne deux premières cartes
        cards[0].performClick()
        cards[1].performClick()

        // Test du bouton recommencer
        composeTestRule.onNodeWithText("Recommencer").performClick()

        // Après avoir cliqué sur recommencer test que les cartes reviennent bien
        composeTestRule.onNodeWithTag("memory_board").onChildren().assertCountEquals(16)
    }
}