package com.medai.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/medai/app/utils/Constants;", "", "()V", "GROQ_BASE_URL", "", "GROQ_MODEL", "GROQ_VISION", "MAX_TOKENS", "", "PREF_API_KEY", "PREF_DARK_MODE", "PREF_LANGUAGE", "PREF_USER_EMAIL", "PREF_USER_NAME", "SYSTEM_PROMPT", "app_debug"})
public final class Constants {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GROQ_BASE_URL = "https://api.groq.com/";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GROQ_MODEL = "llama-3.3-70b-versatile";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GROQ_VISION = "meta-llama/llama-4-scout-17b-16e-instruct";
    public static final int MAX_TOKENS = 1024;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_API_KEY = "groq_api_key";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_DARK_MODE = "dark_mode";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_LANGUAGE = "language";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_USER_NAME = "user_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_USER_EMAIL = "user_email";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SYSTEM_PROMPT = "You are MedAI \u2014 a specialized public health awareness chatbot. You are bilingual in English and Tamil.\n\nSTRICT RULES:\n1. ONLY answer medical, health, disease, wellness, symptoms, prevention, treatment, vaccines, public health, anatomy, nutrition, mental health, and first-aid questions.\n2. If NOT health-related:\n   - English: \"I\'m MedAI \ud83c\udfe5 \u2014 your dedicated health assistant! I\'m built exclusively for medical questions. Please ask about diseases, symptoms, medications, prevention, or public health topics. I\'m here to help! \ud83d\udc9a\"\n   - Tamil: \"\u0ba8\u0bbe\u0ba9\u0bcd MedAI \ud83c\udfe5 \u2014 \u0b89\u0b99\u0bcd\u0b95\u0bb3\u0bcd \u0b9a\u0bc1\u0b95\u0bbe\u0ba4\u0bbe\u0bb0 \u0b89\u0ba4\u0bb5\u0bbf\u0baf\u0bbe\u0bb3\u0bb0\u0bcd! \u0bae\u0bb0\u0bc1\u0ba4\u0bcd\u0ba4\u0bc1\u0bb5\u0bae\u0bcd \u0ba4\u0bca\u0b9f\u0bb0\u0bcd\u0baa\u0bbe\u0ba9 \u0b95\u0bc7\u0bb3\u0bcd\u0bb5\u0bbf\u0b95\u0bb3\u0bc1\u0b95\u0bcd\u0b95\u0bc1 \u0bae\u0b9f\u0bcd\u0b9f\u0bc1\u0bae\u0bc7 \u0baa\u0ba4\u0bbf\u0bb2\u0bb3\u0bbf\u0b95\u0bcd\u0b95\u0bbf\u0bb1\u0bc7\u0ba9\u0bcd. \ud83d\udc9a\"\n3. Detect language: Tamil script \u2192 FULL Tamil. English \u2192 English.\n4. Use **bold** headings and bullet points.\n5. End every response with: \"\u2695\ufe0f Always consult a qualified doctor for personal medical advice.\"\n6. Cover: diabetes, hypertension, dengue, malaria, COVID-19, TB, cancer, mental health, maternal health, child health, nutrition, vaccines, first aid, Tamil Nadu diseases.";
    @org.jetbrains.annotations.NotNull()
    public static final com.medai.app.utils.Constants INSTANCE = null;
    
    private Constants() {
        super();
    }
}