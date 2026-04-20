package com.medai.app.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.medai.app.R;
import com.medai.app.data.model.UiState;
import com.medai.app.databinding.ActivityChatBinding;
import com.medai.app.ui.login.LoginActivity;
import dagger.hilt.android.AndroidEntryPoint;
import io.noties.markwon.Markwon;
import java.util.Locale;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u0012\u0010\u001d\u001a\u00020\u001b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u001bH\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\b\u0010\"\u001a\u00020\u001bH\u0002J\b\u0010#\u001a\u00020\u001bH\u0002J\b\u0010$\u001a\u00020\u001bH\u0002J\b\u0010%\u001a\u00020\u001bH\u0002J\b\u0010&\u001a\u00020\u001bH\u0002J\b\u0010\'\u001a\u00020\u001bH\u0002J\b\u0010(\u001a\u00020\u001bH\u0002J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0005H\u0002J\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0005H\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006,"}, d2 = {"Lcom/medai/app/ui/chat/ChatActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "audioPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "binding", "Lcom/medai/app/databinding/ActivityChatBinding;", "chatAdapter", "Lcom/medai/app/ui/chat/ChatAdapter;", "convAdapter", "Lcom/medai/app/ui/chat/ConversationAdapter;", "imagePicker", "isListening", "", "markwon", "Lio/noties/markwon/Markwon;", "speechRecognizer", "Landroid/speech/SpeechRecognizer;", "viewModel", "Lcom/medai/app/ui/chat/ChatViewModel;", "getViewModel", "()Lcom/medai/app/ui/chat/ChatViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "applyTheme", "", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "sendMessage", "setupDrawer", "setupInput", "setupRecyclerViews", "setupSuggestionChips", "setupVoice", "showApiKeyDialog", "startListening", "stopListening", "updateLanguageButtons", "lang", "updateSuggestionChips", "app_debug"})
public final class ChatActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.medai.app.databinding.ActivityChatBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.medai.app.ui.chat.ChatAdapter chatAdapter;
    private com.medai.app.ui.chat.ConversationAdapter convAdapter;
    private io.noties.markwon.Markwon markwon;
    @org.jetbrains.annotations.Nullable()
    private android.speech.SpeechRecognizer speechRecognizer;
    private boolean isListening = false;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> imagePicker = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> audioPermission = null;
    
    public ChatActivity() {
        super();
    }
    
    private final com.medai.app.ui.chat.ChatViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerViews() {
    }
    
    private final void setupDrawer() {
    }
    
    private final void setupInput() {
    }
    
    private final void sendMessage() {
    }
    
    private final void setupSuggestionChips() {
    }
    
    private final void setupVoice() {
    }
    
    private final void startListening() {
    }
    
    private final void stopListening() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void applyTheme() {
    }
    
    private final void updateLanguageButtons(java.lang.String lang) {
    }
    
    private final void updateSuggestionChips(java.lang.String lang) {
    }
    
    private final void showApiKeyDialog() {
    }
}