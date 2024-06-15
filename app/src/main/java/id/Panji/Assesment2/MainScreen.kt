package id.Panji.Assesment2

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3061.mobpro1.R
import id.Panji.Assesment2.database.NominalDb
import id.Panji.Assesment2.model.Nominal
import id.Panji.Assesment2.navigation.Screen
import id.Panji.Assesment2.screen.MainViewModel
import id.Panji.Assesment2.ui.theme.Mobpro1Theme
import id.Panji.Assesment2.util.SettingsDataStore
import id.Panji.Assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
    var isAscending by remember { mutableStateOf(true) }

    val db = NominalDb.getInstance(LocalContext.current)
    val factory = remember { ViewModelFactory(db.dao) }
    val viewModel: MainViewModel = viewModel(factory = factory)


    val dataState by viewModel.sortedData(isAscending).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {

                    IconButton(onClick = {
                        isAscending = !isAscending
                    }) {
                        Icon(
                            painter = painterResource(
                                if (isAscending) R.drawable.baseline_arrow_upward_24
                                else R.drawable.baseline_arrow_downward_24
                            ),
                            contentDescription = stringResource(
                                if (isAscending) R.string.ASC
                                else R.string.DESC
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {navController.navigate(Screen.About.route)}) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = stringResource(
                            R.string.tentang_aplikasi
                        ),
                            tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.FormBaru.route)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.tambah_data),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Bukti.route)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.receipt),
                        contentDescription = stringResource(R.string.bukti_screen),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) { padding ->
        ScreenContent(showList, dataState, navController, Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(
    showList: Boolean,
    data: List<Nominal>,
    navController: NavHostController,
    modifier: Modifier
) {
    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.data_kosong))
            Image(
                painter = painterResource(id = R.mipmap.nothing),
                contentDescription = null,
                modifier = Modifier.padding(top = 16.dp))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items (data) {
                    ListItem(nominal = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    Divider()
                }
            }
        } else{
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 84.dp)
            ){
                items(data) {
                    GridItem(nominal = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                }
            }
        }
    }
}

private fun shareData (
    context: Context,
    nominal: String,
    kegunaan: String,
    tanggal: String
) {
    val message = context.getString(
        R.string.bagikan_template,
        nominal,
        kegunaan,
        tanggal
    )
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}
@Composable
fun ListItem(nominal: Nominal, onClick: () -> Unit) {
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = nominal.nominal.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(text = nominal.kategori)
        Text(text = nominal.tanggal)
    }
    Button(
        onClick = {
            shareData(
                context = context,
                nominal = nominal.nominal.toString(),
                kegunaan = nominal.kategori,
                tanggal = nominal.tanggal
            )
        },
        modifier = Modifier.padding(top = 8.dp),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Text(text = stringResource(R.string.bagikan))
    }
}

@Composable
fun GridItem(nominal: Nominal, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = nominal.nominal.toString(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(text = nominal.kategori)
            Text(text = nominal.tanggal)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Mobpro1Theme {
        MainScreen(rememberNavController())
    }
}