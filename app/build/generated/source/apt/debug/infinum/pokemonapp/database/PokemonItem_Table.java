package infinum.pokemonapp.database;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Class;
import java.lang.Double;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class PokemonItem_Table extends ModelAdapter<PokemonItem> {
  /**
   * Primary Key AutoIncrement */
  public static final Property<Long> id = new Property<Long>(PokemonItem.class, "id");

  public static final Property<String> name = new Property<String>(PokemonItem.class, "name");

  public static final Property<String> imageUri = new Property<String>(PokemonItem.class, "imageUri");

  public static final Property<String> description = new Property<String>(PokemonItem.class, "description");

  public static final Property<Double> height = new Property<Double>(PokemonItem.class, "height");

  public static final Property<Double> weight = new Property<Double>(PokemonItem.class, "weight");

  public static final Property<String> category = new Property<String>(PokemonItem.class, "category");

  public static final Property<String> abilities = new Property<String>(PokemonItem.class, "abilities");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name,imageUri,description,height,weight,category,abilities};

  public PokemonItem_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<PokemonItem> getModelClass() {
    return PokemonItem.class;
  }

  @Override
  public final String getTableName() {
    return "`PokemonItem`";
  }

  @Override
  public final PokemonItem newInstance() {
    return new PokemonItem();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`name`":  {
        return name;
      }
      case "`imageUri`":  {
        return imageUri;
      }
      case "`description`":  {
        return description;
      }
      case "`height`":  {
        return height;
      }
      case "`weight`":  {
        return weight;
      }
      case "`category`":  {
        return category;
      }
      case "`abilities`":  {
        return abilities;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void updateAutoIncrement(PokemonItem model, Number id) {
    model.setId(id.longValue());
  }

  @Override
  public final Number getAutoIncrementingId(PokemonItem model) {
    return model.getId();
  }

  @Override
  public final String getAutoIncrementingColumnName() {
    return "id";
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, PokemonItem model) {
    values.put("`name`", model.getName());
    values.put("`imageUri`", model.getImageUri());
    values.put("`description`", model.getDescription());
    values.put("`height`", model.getHeight());
    values.put("`weight`", model.getWeight());
    values.put("`category`", model.getCategory());
    values.put("`abilities`", model.getAbilities());
  }

  @Override
  public final void bindToContentValues(ContentValues values, PokemonItem model) {
    values.put("`id`", model.getId());
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, PokemonItem model,
      int start) {
    statement.bindStringOrNull(1 + start, model.getName());
    statement.bindStringOrNull(2 + start, model.getImageUri());
    statement.bindStringOrNull(3 + start, model.getDescription());
    statement.bindDouble(4 + start, model.getHeight());
    statement.bindDouble(5 + start, model.getWeight());
    statement.bindStringOrNull(6 + start, model.getCategory());
    statement.bindStringOrNull(7 + start, model.getAbilities());
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, PokemonItem model) {
    int start = 0;
    statement.bindLong(1 + start, model.getId());
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, PokemonItem model) {
    statement.bindLong(1, model.getId());
    statement.bindStringOrNull(2, model.getName());
    statement.bindStringOrNull(3, model.getImageUri());
    statement.bindStringOrNull(4, model.getDescription());
    statement.bindDouble(5, model.getHeight());
    statement.bindDouble(6, model.getWeight());
    statement.bindStringOrNull(7, model.getCategory());
    statement.bindStringOrNull(8, model.getAbilities());
    statement.bindLong(9, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, PokemonItem model) {
    statement.bindLong(1, model.getId());
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `PokemonItem`(`name`,`imageUri`,`description`,`height`,`weight`,`category`,`abilities`) VALUES (?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `PokemonItem`(`id`,`name`,`imageUri`,`description`,`height`,`weight`,`category`,`abilities`) VALUES (?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `PokemonItem` SET `id`=?,`name`=?,`imageUri`=?,`description`=?,`height`=?,`weight`=?,`category`=?,`abilities`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `PokemonItem` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `PokemonItem`(`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `imageUri` TEXT, `description` TEXT, `height` REAL, `weight` REAL, `category` TEXT, `abilities` TEXT)";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, PokemonItem model) {
    model.setId(cursor.getLongOrDefault("id"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setImageUri(cursor.getStringOrDefault("imageUri"));
    model.setDescription(cursor.getStringOrDefault("description"));
    model.setHeight(cursor.getDoubleOrDefault("height"));
    model.setWeight(cursor.getDoubleOrDefault("weight"));
    model.setCategory(cursor.getStringOrDefault("category"));
    model.setAbilities(cursor.getStringOrDefault("abilities"));
  }

  @Override
  public final boolean exists(PokemonItem model, DatabaseWrapper wrapper) {
    return model.getId() > 0
    && SQLite.selectCountOf()
    .from(PokemonItem.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(PokemonItem model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}
