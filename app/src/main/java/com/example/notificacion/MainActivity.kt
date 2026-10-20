package com.example.notificacion

import Notificador
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.notificacion.ui.theme.NotificacionTheme
import com.example.notificacion.viewmodels.AguaViewModel
import com.example.notificacion.vistas.PrincipalScreen

class MainActivity : ComponentActivity() {
    private val viewModel: AguaViewModel by viewModels()
    private lateinit var notificador: Notificador

    private val pedirPermiso = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            enviarRecordatorio()
        } else {
            Toast.makeText(this, R.string.permiso_necesario, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificador = Notificador(this)
        notificador.crearCanal()

        enableEdgeToEdge()
        setContent {
            NotificacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PrincipalScreen(
                        viewModel = viewModel,
                        onBoton = { revisarPermisoYEnviar() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun tienePermiso(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true
        }
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun revisarPermisoYEnviar() {
        if (tienePermiso()) {
            enviarRecordatorio()
        } else {
            pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun enviarRecordatorio() {
        notificador.mostrar()
        viewModel.agregarRegistro()
    }
}