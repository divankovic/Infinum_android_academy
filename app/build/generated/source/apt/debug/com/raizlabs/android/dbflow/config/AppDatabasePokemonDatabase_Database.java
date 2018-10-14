package com.raizlabs.android.dbflow.config;

import infinum.pokemonapp.database.AppDatabase;
import infinum.pokemonapp.database.PokemonItem_Table;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class AppDatabasePokemonDatabase_Database extends DatabaseDefinition {
  public AppDatabasePokemonDatabase_Database(DatabaseHolder holder) {
    addModelAdapter(new PokemonItem_Table(this), holder);
  }

  @Override
  public final Class<?> getAssociatedDatabaseClassFile() {
    return AppDatabase.class;
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean isInMemory() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "PokemonDatabase";
  }
}
