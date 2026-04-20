package com.medai.app.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.medai.app.R
import com.medai.app.data.model.UiState
import com.medai.app.databinding.ActivityChatBinding
import com.medai.app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var convAdapter: ConversationAdapter
    private lateinit var markwon: Markwon

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    // Image picker
    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setPendingImage(it)
            binding.ivImagePreview.visibility = View.VISIBLE
            Glide.with(this).load(it).centerCrop().into(binding.ivImagePreview)
            binding.btnRemoveImage.visibility = View.VISIBLE
        }
    }

    // Audio permission
    private val audioPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startListening() else Toast.makeText(this, "Microphone permission needed for voice input", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        markwon = Markwon.create(this)

        setupRecyclerViews()
        setupDrawer()
        setupInput()
        setupVoice()
        observeViewModel()
        applyTheme()
    }

    // ── RecyclerViews ──────────────────────────────────────
    private fun setupRecyclerViews() {
        chatAdapter = ChatAdapter(markwon)
        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity).also { it.stackFromEnd = true }
        }

        convAdapter = ConversationAdapter { conv ->
            viewModel.selectConversation(conv.id)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.rvConversations.apply {
            adapter = convAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }

    // ── Drawer / Sidebar ───────────────────────────────────
    private fun setupDrawer() {
        binding.btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            else
                binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.btnNewChat.setOnClickListener {
            viewModel.newConversation()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Dark / Light toggle switch
        binding.switchDarkMode.setOnCheckedChangeListener { _, _ ->
            viewModel.toggleDarkMode()
        }

        // Language toggle
        binding.btnLanguageEn.setOnClickListener {
            viewModel.updateLanguage("en")
            updateLanguageButtons("en")
        }
        binding.btnLanguageTa.setOnClickListener {
            viewModel.updateLanguage("ta")
            updateLanguageButtons("ta")
        }

        // Change API key
        binding.btnChangeKey.setOnClickListener { showApiKeyDialog() }

        // Logout
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                startActivity(Intent(this@ChatActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    // ── Input bar ──────────────────────────────────────────
    private fun setupInput() {
        // Send on keyboard action
        binding.etMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) { sendMessage(); true } else false
        }

        binding.btnSend.setOnClickListener { sendMessage() }

        binding.btnAttach.setOnClickListener { imagePicker.launch("image/*") }

        binding.btnRemoveImage.setOnClickListener {
            viewModel.clearPendingImage()
            binding.ivImagePreview.visibility = View.GONE
            binding.btnRemoveImage.visibility = View.GONE
        }

        // Suggestion chips
        setupSuggestionChips()
    }

    private fun sendMessage() {
        val text = binding.etMessage.text.toString().trim()
        binding.etMessage.setText("")
        viewModel.sendMessage(text)
    }

    private fun setupSuggestionChips() {
        val chips = listOf(
            binding.chip1, binding.chip2, binding.chip3, binding.chip4
        )
        chips.forEach { chip ->
            chip.setOnClickListener {
                binding.etMessage.setText(chip.text)
                sendMessage()
            }
        }
    }

    // ── Voice ──────────────────────────────────────────────
    private fun setupVoice() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = matches?.firstOrNull() ?: return
                val current = binding.etMessage.text.toString()
                binding.etMessage.setText(if (current.isBlank()) text else "$current $text")
                binding.etMessage.setSelection(binding.etMessage.text?.length ?: 0)
                stopListening()
            }
            override fun onError(error: Int) { stopListening() }
            override fun onReadyForSpeech(p: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(b: ByteArray) {}
            override fun onEndOfSpeech() { stopListening() }
            override fun onPartialResults(b: Bundle) {}
            override fun onEvent(t: Int, b: Bundle) {}
        })

        binding.btnVoice.setOnClickListener {
            if (isListening) stopListening()
            else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) startListening()
                else audioPermission.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    private fun startListening() {
        isListening = true
        binding.btnVoice.setImageResource(R.drawable.ic_stop)
        binding.btnVoice.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red_500)

        val lang = viewModel.language.value
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, if (lang == "ta") "ta-IN" else "en-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer?.startListening(intent)
    }

    private fun stopListening() {
        isListening = false
        speechRecognizer?.stopListening()
        binding.btnVoice.setImageResource(R.drawable.ic_mic)
        binding.btnVoice.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.surface_variant)
    }

    // ── Observers ──────────────────────────────────────────
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.activeMessages.collectLatest { messages ->
                chatAdapter.submitList(messages.toList()) {
                    if (messages.isNotEmpty())
                        binding.rvMessages.smoothScrollToPosition(messages.size - 1)
                }
                // Show/hide empty state
                binding.layoutEmpty.visibility =
                    if (messages.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.conversations.collectLatest { convs ->
                convAdapter.submitList(convs)
            }
        }

        lifecycleScope.launch {
            viewModel.activeConvId.collectLatest { id ->
                convAdapter.setActiveId(id)
                binding.tvToolbarTitle.text =
                    if (id == null) "MedAI — Health Assistant"
                    else viewModel.conversations.value.find { it.id == id }?.title
                        ?: "MedAI Chat"
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { loading ->
                binding.btnSend.isEnabled = !loading
                binding.btnSend.alpha = if (loading) 0.5f else 1f
            }
        }

        lifecycleScope.launch {
            viewModel.darkMode.collectLatest { dark ->
                AppCompatDelegate.setDefaultNightMode(
                    if (dark) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
                binding.switchDarkMode.isChecked = dark
            }
        }

        lifecycleScope.launch {
            viewModel.language.collectLatest { lang ->
                updateLanguageButtons(lang)
                updateSuggestionChips(lang)
                binding.etMessage.hint = if (lang == "ta") "ஒரு செய்தி அனுப்பவும்…" else "Send a message"
            }
        }

        lifecycleScope.launch {
            viewModel.userName.collectLatest { name ->
                binding.tvUserName.text = name
                val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
                binding.tvUserInitials.text = initials.ifBlank { "U" }
            }
        }

        lifecycleScope.launch {
            viewModel.userEmail.collectLatest { email ->
                binding.tvUserEmail.text = email
            }
        }

        lifecycleScope.launch {
            viewModel.sendState.collectLatest { state ->
                if (state is UiState.Error && state.message == "NO_KEY") {
                    showApiKeyDialog()
                    viewModel.resetSendState()
                }
            }
        }
    }

    // ── Theme ──────────────────────────────────────────────
    private fun applyTheme() {
        lifecycleScope.launch {
            val dark = viewModel.darkMode.value
            AppCompatDelegate.setDefaultNightMode(
                if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    // ── Language helpers ───────────────────────
    private fun updateLanguageButtons(lang: String) {
        if (lang == "ta") {
            binding.btnLanguageTa.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_500))
            binding.btnLanguageTa.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnLanguageEn.setBackgroundColor(ContextCompat.getColor(this, R.color.surface_variant))
            binding.btnLanguageEn.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        } else {
            binding.btnLanguageEn.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_500))
            binding.btnLanguageEn.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnLanguageTa.setBackgroundColor(ContextCompat.getColor(this, R.color.surface_variant))
            binding.btnLanguageTa.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        }
    }

    private fun updateSuggestionChips(lang: String) {
        if (lang == "ta") {
            binding.chip1.text = "எனது தலைவலிக்கு என்ன காரணம்?"
            binding.chip2.text = "ஆரோக்கியமான உணவுகள்"
            binding.chip3.text = "நல்ல தூக்கத்திற்கான குறிப்புகள்"
            binding.chip4.text = "இந்த படத்தைப் பற்றி சொல்லுங்கள்"
        } else {
            binding.chip1.text = "What causes my headache?"
            binding.chip2.text = "Healthy food options"
            binding.chip3.text = "Tips for better sleep"
            binding.chip4.text = "Tell me about this image"
        }
    }

    private fun showApiKeyDialog() {
        val input = android.widget.EditText(this).apply {
            hint = "Enter your Groq API Key"
            setText(viewModel.apiKey.value)
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("API Key Configuration")
            .setMessage("Please enter your Groq API key to use the chat.")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val key = input.text.toString().trim()
                if (key.isNotEmpty()) {
                    viewModel.updateApiKey(key)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
