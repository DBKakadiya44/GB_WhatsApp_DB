package com.db.gbwhatsappdb;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "whats_scan";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public PrefManager(Context context) {
        this._context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public void setBoolean(String str, Boolean bool) {
        this.editor.putBoolean(str, bool.booleanValue());
        this.editor.commit();
    }

    public void setString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public void setCssString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public void setInt(String str, int i) {
        this.editor.putInt(str, i);
        this.editor.commit();
    }

    public boolean getBoolean(String str) {
        return this.pref.getBoolean(str, false);
    }

    public void remove(String str) {
        if (this.pref.contains(str)) {
            this.editor.remove(str);
            this.editor.commit();
        }
    }

    public String getString(String str) {
        return this.pref.contains(str) ? this.pref.getString(str, null) : "";
    }

    public String getCssString(String str, String str2) {
        return this.pref.getString(str, str2);
    }

    public void setLong(String str, long j) {
        this.editor.putLong(str, j).commit();
    }

    public long getLong(String str) {
        return this.pref.getLong(str, 0);
    }

    public int getInt(String str) {
        return this.pref.getInt(str, 0);
    }
}
