package com.medai.app.data.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import com.google.gson.Gson;
import com.medai.app.data.api.GroqApiService;
import com.medai.app.data.model.*;
import com.medai.app.utils.Constants;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import java.io.ByteArrayOutputStream;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/medai/app/data/repository/StreamChoice;", "", "delta", "Lcom/medai/app/data/repository/StreamDelta;", "(Lcom/medai/app/data/repository/StreamDelta;)V", "getDelta", "()Lcom/medai/app/data/repository/StreamDelta;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class StreamChoice {
    @org.jetbrains.annotations.Nullable()
    private final com.medai.app.data.repository.StreamDelta delta = null;
    
    public StreamChoice(@org.jetbrains.annotations.Nullable()
    com.medai.app.data.repository.StreamDelta delta) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.medai.app.data.repository.StreamDelta getDelta() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.medai.app.data.repository.StreamDelta component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.medai.app.data.repository.StreamChoice copy(@org.jetbrains.annotations.Nullable()
    com.medai.app.data.repository.StreamDelta delta) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}