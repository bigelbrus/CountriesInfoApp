package com.example.countriesinfoapp.utilities;

import io.realm.RealmConfiguration;

public class RealmUtils {
    private static final int SCHEMA_V_PREV = 1;
    private static final int SCHEMA_V_NOW = 2;


    public static int getSchemaVNow() {
        return SCHEMA_V_NOW;
    }


    public static RealmConfiguration getDefaultConfig() {
        return new RealmConfiguration.Builder()
                .schemaVersion(SCHEMA_V_NOW)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
