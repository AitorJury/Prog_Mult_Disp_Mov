package com.example.flashcardsproject.ui.windows

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.flashcardsproject.data.Flashcard
import com.example.flashcardsproject.data.FlashcardRepository

@Composable
fun AddFlashcardWindow(navController: NavHostController, albumId: Int) {
    val context = LocalContext.current

    // Lista temporal de tarjetas (Borrador)
    val tempCards = remember { mutableStateListOf<Flashcard>() }

    // Estados para la tarjeta que se está escribiendo ahora
    var frontText by remember { mutableStateOf("") }
    var frontUri by remember { mutableStateOf<Uri?>(null) }
    var isFrontImage by remember { mutableStateOf(false) }

    var backText by remember { mutableStateOf("") }
    var backUri by remember { mutableStateOf<Uri?>(null) }
    var isBackImage by remember { mutableStateOf(false) }

    val frontLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            frontUri = it
        }
    }

    val backLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            backUri = it
        }
    }

    val canAddToQueue = (if (isFrontImage) frontUri != null else frontText.isNotBlank()) &&
            (if (isBackImage) backUri != null else backText.isNotBlank())

    Surface(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text("Añadir Varias Tarjetas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {

                ContentSelector(
                    title = "Anverso (Cara A)",
                    isImage = isFrontImage,
                    textValue = frontText,
                    imageUri = frontUri,
                    onModeChange = { isFrontImage = it },
                    onTextChange = { frontText = it },
                    onPickImage = { frontLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

                ContentSelector(
                    title = "Reverso (Cara B)",
                    isImage = isBackImage,
                    textValue = backText,
                    imageUri = backUri,
                    onModeChange = { isBackImage = it },
                    onTextChange = { backText = it },
                    onPickImage = { backLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val newCard = Flashcard(
                            id = 0,
                            frontContent = if (isFrontImage) frontUri.toString() else frontText,
                            isFrontImage = isFrontImage,
                            backContent = if (isBackImage) backUri.toString() else backText,
                            isBackImage = isBackImage
                        )
                        tempCards.add(newCard)
                        // Limpiar para la siguiente
                        frontText = ""; frontUri = null; backText = ""; backUri = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canAddToQueue,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Añadir a la lista temporal")
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (tempCards.isNotEmpty()) {
                    Text("Tarjetas listas para guardar (${tempCards.size}):", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        itemsIndexed(tempCards) { index, card ->
                            PreviewCardItem(card = card, onDelete = { tempCards.removeAt(index) })
                        }
                    }
                }
            }

            // BOTÓN FINAL USANDO LA NUEVA FUNCIÓN MASIVA
            Button(
                onClick = {
                    FlashcardRepository.addMultipleFlashcardsToAlbum(albumId, tempCards, context)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).height(56.dp),
                enabled = tempCards.isNotEmpty(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Guardar todas (${tempCards.size})", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun PreviewCardItem(card: Flashcard, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if (card.isFrontImage) {
                AsyncImage(model = card.frontContent, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            } else {
                Text(card.frontContent, maxLines = 2, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(4.dp))
            }
        }
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(24.dp).align(Alignment.TopEnd).background(Color.Red.copy(alpha = 0.7f), RoundedCornerShape(bottomStart = 8.dp))
        ) {
            Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
fun ContentSelector(
    title: String,
    isImage: Boolean,
    textValue: String,
    imageUri: Uri?,
    onModeChange: (Boolean) -> Unit,
    onTextChange: (String) -> Unit,
    onPickImage: () -> Unit
) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

        Row(modifier = Modifier.padding(vertical = 12.dp)) {
            FilterChip(
                selected = !isImage,
                onClick = { onModeChange(false) },
                label = { Text("Texto") },
                leadingIcon = { Icon(Icons.Default.Edit, null, modifier = Modifier.size(18.dp)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = isImage,
                onClick = { onModeChange(true) },
                label = { Text("Galería") },
                leadingIcon = { Icon(Icons.Default.Image, null, modifier = Modifier.size(18.dp)) }
            )
        }

        if (isImage) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                    .clickable { onPickImage() },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(model = imageUri, contentDescription = null, modifier = Modifier.fillMaxSize())
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Image, null, tint = MaterialTheme.colorScheme.primary)
                        Text("Toca para abrir la galería", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        } else {
            OutlinedTextField(
                value = textValue,
                onValueChange = onTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Escribe el contenido...") },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}