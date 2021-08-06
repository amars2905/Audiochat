package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Users type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class Users implements Model {
  public static final QueryField ID = field("Users", "id");
  public static final QueryField NAME = field("Users", "name");
  public static final QueryField EMAIL = field("Users", "email");
  public static final QueryField PASSWORD = field("Users", "password");
  public static final QueryField LANGUAGE = field("Users", "language");
  public static final QueryField LANGUAGE_CODE = field("Users", "language_code");
  public static final QueryField VOICE = field("Users", "voice");
  public static final QueryField ENGINE = field("Users", "engine");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String") String password;
  private final @ModelField(targetType="String", isRequired = true) String language;
  private final @ModelField(targetType="String") String language_code;
  private final @ModelField(targetType="String") String voice;
  private final @ModelField(targetType="String") String engine;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getPassword() {
      return password;
  }
  
  public String getLanguage() {
      return language;
  }
  
  public String getLanguageCode() {
      return language_code;
  }
  
  public String getVoice() {
      return voice;
  }
  
  public String getEngine() {
      return engine;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Users(String id, String name, String email, String password, String language, String language_code, String voice, String engine) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.language = language;
    this.language_code = language_code;
    this.voice = voice;
    this.engine = engine;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Users users = (Users) obj;
      return ObjectsCompat.equals(getId(), users.getId()) &&
              ObjectsCompat.equals(getName(), users.getName()) &&
              ObjectsCompat.equals(getEmail(), users.getEmail()) &&
              ObjectsCompat.equals(getPassword(), users.getPassword()) &&
              ObjectsCompat.equals(getLanguage(), users.getLanguage()) &&
              ObjectsCompat.equals(getLanguageCode(), users.getLanguageCode()) &&
              ObjectsCompat.equals(getVoice(), users.getVoice()) &&
              ObjectsCompat.equals(getEngine(), users.getEngine()) &&
              ObjectsCompat.equals(getCreatedAt(), users.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), users.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getEmail())
      .append(getPassword())
      .append(getLanguage())
      .append(getLanguageCode())
      .append(getVoice())
      .append(getEngine())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Users {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("password=" + String.valueOf(getPassword()) + ", ")
      .append("language=" + String.valueOf(getLanguage()) + ", ")
      .append("language_code=" + String.valueOf(getLanguageCode()) + ", ")
      .append("voice=" + String.valueOf(getVoice()) + ", ")
      .append("engine=" + String.valueOf(getEngine()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Users justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Users(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      email,
      password,
      language,
      language_code,
      voice,
      engine);
  }
  public interface NameStep {
    EmailStep name(String name);
  }
  

  public interface EmailStep {
    LanguageStep email(String email);
  }
  

  public interface LanguageStep {
    BuildStep language(String language);
  }
  

  public interface BuildStep {
    Users build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep password(String password);
    BuildStep languageCode(String languageCode);
    BuildStep voice(String voice);
    BuildStep engine(String engine);
  }
  

  public static class Builder implements NameStep, EmailStep, LanguageStep, BuildStep {
    private String id;
    private String name;
    private String email;
    private String language;
    private String password;
    private String language_code;
    private String voice;
    private String engine;
    @Override
     public Users build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Users(
          id,
          name,
          email,
          password,
          language,
          language_code,
          voice,
          engine);
    }
    
    @Override
     public EmailStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public LanguageStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep language(String language) {
        Objects.requireNonNull(language);
        this.language = language;
        return this;
    }
    
    @Override
     public BuildStep password(String password) {
        this.password = password;
        return this;
    }
    
    @Override
     public BuildStep languageCode(String languageCode) {
        this.language_code = languageCode;
        return this;
    }
    
    @Override
     public BuildStep voice(String voice) {
        this.voice = voice;
        return this;
    }
    
    @Override
     public BuildStep engine(String engine) {
        this.engine = engine;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String email, String password, String language, String languageCode, String voice, String engine) {
      super.id(id);
      super.name(name)
        .email(email)
        .language(language)
        .password(password)
        .languageCode(languageCode)
        .voice(voice)
        .engine(engine);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder language(String language) {
      return (CopyOfBuilder) super.language(language);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
    
    @Override
     public CopyOfBuilder languageCode(String languageCode) {
      return (CopyOfBuilder) super.languageCode(languageCode);
    }
    
    @Override
     public CopyOfBuilder voice(String voice) {
      return (CopyOfBuilder) super.voice(voice);
    }
    
    @Override
     public CopyOfBuilder engine(String engine) {
      return (CopyOfBuilder) super.engine(engine);
    }
  }
  
}
