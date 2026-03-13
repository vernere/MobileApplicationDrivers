package com.example.bluetoothbeacon

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.os.Handler




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BluetoothScannerScreen()
                }
            }
        }
    }
}

data class BleDevice(
    val name: String,
    val address: String,
    val rssi: Int,
    val isConnectable: Boolean
)

@Composable
fun BluetoothScannerScreen() {
    val context = LocalContext.current
    var devices by remember { mutableStateOf<List<BleDevice>>(emptyList()) }
    var isScanning by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("") }

    val requiredPermission =  if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startBleScan(context, onDeviceFound = { device ->
                devices = (devices + device).distinctBy { it.address }
            }, onScanComplete = {
                isScanning = false
                statusMessage = "Scan complete. Found ${devices.size} devices(s)."
            }).also { isScanning = true }
        } else {
            statusMessage = "Permissions denied. Cannot scan."
        }
    }

    fun onStartScan() {
        val allGranted = requiredPermission.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allGranted) {
            devices = emptyList()
            statusMessage = "Scanning..."
            isScanning = true
            startBleScan(context, onDeviceFound = { device ->
                devices = (devices + device).distinctBy { it.address }
            }, onScanComplete = {
                isScanning = false
                statusMessage = "Scan complete. Found ${devices.size} devices(s)"
            })
        } else {
            permissionLauncher.launch(requiredPermission)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onStartScan() },
            enabled = !isScanning,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                if (isScanning) "Scanning..." else "Start Scanning",
                color = Color.White
            )
        }

        if (statusMessage.isNotEmpty()) {
            Text(
                text = statusMessage,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(devices) { device ->
                DeviceListItem(device)
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun DeviceListItem(device: BleDevice) {
    val textColor = if(device.isConnectable) Color.Black else Color.Gray
    val displayName = device.name.ifEmpty { "Unknown" }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment =  Alignment.CenterVertically
    ) {
        Text(
            text = "${device.address} $displayName ${device.rssi}dBm",
            color =textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
fun startBleScan(
    context: Context,
    onDeviceFound: (BleDevice) -> Unit,
    onScanComplete: () -> Unit
) {
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    val bleScanner = bluetoothAdapter?.bluetoothLeScanner ?: run {
        onScanComplete()
        return
    }

    val scanCallback = object : ScanCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val name = try {
                result.device.name ?: ""
            } catch (e: SecurityException) { "" }

            val device = BleDevice(
                name = name,
                address = result.device.address,
                rssi = result.rssi,
                isConnectable = result.isConnectable
            )
            onDeviceFound(device)
        }
    }

    try {
        bleScanner.startScan(scanCallback)
    } catch (e: SecurityException) {
        onScanComplete()
        return
    }

    Handler(Looper.getMainLooper()).postDelayed({
        try {
            bleScanner.stopScan(scanCallback)
        } catch (e: SecurityException) { /* ignore */ }
        onScanComplete()
    }, 3000)
}
