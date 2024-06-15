package id.Panji.Assesment2.navigation

import id.Panji.Assesment2.screen.KEY_ID_NOMINAL


sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")

    data object FormBaru : Screen("detailScreen")

    data object About: Screen("aboutScreen")

    data object Bukti: Screen("buktiScreen")

    data object FormUbah: Screen("detailScreen/{$KEY_ID_NOMINAL}") {
        fun withId(id: Long) = "detailScreen/$id"
        }

}