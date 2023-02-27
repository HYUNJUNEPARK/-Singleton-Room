package com.module.appdatabase.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006J\u001e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/module/appdatabase/ui/AppViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_App_memoList", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/module/appdatabase/data/AppMemo;", "appMemoList", "Landroidx/lifecycle/LiveData;", "getAppMemoList", "()Landroidx/lifecycle/LiveData;", "ioScope", "Lkotlinx/coroutines/CoroutineScope;", "mainScope", "deleteData", "", "context", "Landroid/content/Context;", "appMemo", "getDbData", "insertData", "updateData", "newAppMemo", "idx", "", "appDatabase_debug"})
public final class AppViewModel extends androidx.lifecycle.ViewModel {
    private final kotlinx.coroutines.CoroutineScope ioScope = null;
    private final kotlinx.coroutines.CoroutineScope mainScope = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.module.appdatabase.data.AppMemo>> _App_memoList = null;
    
    public AppViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.module.appdatabase.data.AppMemo>> getAppMemoList() {
        return null;
    }
    
    public final void getDbData(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void insertData(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.module.appdatabase.data.AppMemo appMemo) {
    }
    
    public final void updateData(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.module.appdatabase.data.AppMemo newAppMemo, int idx) {
    }
    
    public final void deleteData(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.module.appdatabase.data.AppMemo appMemo) {
    }
}