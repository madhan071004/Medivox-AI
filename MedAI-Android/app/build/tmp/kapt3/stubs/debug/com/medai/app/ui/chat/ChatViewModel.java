package com.medai.app.ui.chat;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.medai.app.data.model.ChatMessage;
import com.medai.app.data.model.Conversation;
import com.medai.app.data.model.UiState;
import com.medai.app.data.repository.ChatRepository;
import com.medai.app.utils.PrefsManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\u0019H\u0002J\u0006\u00102\u001a\u00020/J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020/J\u0006\u00106\u001a\u00020/J\u000e\u00107\u001a\u00020/2\u0006\u00108\u001a\u00020\tJ\u000e\u00109\u001a\u00020/2\u0006\u0010:\u001a\u00020\tJ\u0010\u0010;\u001a\u00020/2\b\u0010<\u001a\u0004\u0018\u00010\u0010J\u0006\u0010=\u001a\u000204J\u0006\u0010>\u001a\u000204J\u000e\u0010?\u001a\u0002042\u0006\u0010@\u001a\u00020\tJ\u000e\u0010A\u001a\u0002042\u0006\u0010B\u001a\u00020\tJ \u0010C\u001a\u00020/2\u0006\u00100\u001a\u00020\t2\u0006\u0010D\u001a\u00020\t2\u0006\u0010E\u001a\u00020\tH\u0002R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\t\u0018\u00010\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0019\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010&\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\t\u0018\u00010\u00120\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0017R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0017R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0017R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0017\u00a8\u0006F"}, d2 = {"Lcom/medai/app/ui/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/medai/app/data/repository/ChatRepository;", "prefs", "Lcom/medai/app/utils/PrefsManager;", "(Lcom/medai/app/data/repository/ChatRepository;Lcom/medai/app/utils/PrefsManager;)V", "_activeConvId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_conversations", "", "Lcom/medai/app/data/model/Conversation;", "_isLoading", "", "_pendingImageUri", "Landroid/net/Uri;", "_sendState", "Lcom/medai/app/data/model/UiState;", "_streamingText", "activeConvId", "Lkotlinx/coroutines/flow/StateFlow;", "getActiveConvId", "()Lkotlinx/coroutines/flow/StateFlow;", "activeMessages", "Lcom/medai/app/data/model/ChatMessage;", "getActiveMessages", "apiKey", "getApiKey", "conversations", "getConversations", "darkMode", "getDarkMode", "isLoading", "language", "getLanguage", "pendingImageUri", "getPendingImageUri", "sendState", "getSendState", "streamingText", "getStreamingText", "userEmail", "getUserEmail", "userName", "getUserName", "appendMessage", "", "convId", "msg", "clearPendingImage", "logout", "Lkotlinx/coroutines/Job;", "newConversation", "resetSendState", "selectConversation", "id", "sendMessage", "userText", "setPendingImage", "uri", "toggleDarkMode", "toggleLanguage", "updateApiKey", "key", "updateLanguage", "lang", "updateMessage", "msgId", "content", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.medai.app.data.repository.ChatRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.medai.app.utils.PrefsManager prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> apiKey = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> darkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> language = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userEmail = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.medai.app.data.model.Conversation>> _conversations = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.medai.app.data.model.Conversation>> conversations = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _activeConvId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> activeConvId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.medai.app.data.model.ChatMessage>> activeMessages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.medai.app.data.model.UiState<java.lang.String>> _sendState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.medai.app.data.model.UiState<java.lang.String>> sendState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _streamingText = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> streamingText = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<android.net.Uri> _pendingImageUri = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<android.net.Uri> pendingImageUri = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.medai.app.data.repository.ChatRepository repo, @org.jetbrains.annotations.NotNull()
    com.medai.app.utils.PrefsManager prefs) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getApiKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.medai.app.data.model.Conversation>> getConversations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getActiveConvId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.medai.app.data.model.ChatMessage>> getActiveMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.medai.app.data.model.UiState<java.lang.String>> getSendState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStreamingText() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<android.net.Uri> getPendingImageUri() {
        return null;
    }
    
    public final void setPendingImage(@org.jetbrains.annotations.Nullable()
    android.net.Uri uri) {
    }
    
    public final void clearPendingImage() {
    }
    
    public final void newConversation() {
    }
    
    public final void selectConversation(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String userText) {
    }
    
    private final void appendMessage(java.lang.String convId, com.medai.app.data.model.ChatMessage msg) {
    }
    
    private final void updateMessage(java.lang.String convId, java.lang.String msgId, java.lang.String content) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job toggleDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job toggleLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job updateLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job updateApiKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job logout() {
        return null;
    }
    
    public final void resetSendState() {
    }
}