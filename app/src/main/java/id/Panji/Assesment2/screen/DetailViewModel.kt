package id.Panji.Assesment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import id.Panji.Assesment2.database.NominalDao
import id.Panji.Assesment2.model.Nominal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: NominalDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(nama: Int, kategori: String) {
        val nominal = Nominal(
            tanggal = formatter.format(Date()),
            nominal = nama,
            kategori = kategori,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(nominal)
        }
    }
    suspend fun getMahasiswa(id: Long): Nominal? {
        return dao.getNominalById(id)
    }
    fun update(id: Long, nama: Int, kategori: String) {
        val nominal = Nominal(
            id = id,
            tanggal = formatter.format(Date()),
            nominal = nama,
            kategori = kategori
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(nominal)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
