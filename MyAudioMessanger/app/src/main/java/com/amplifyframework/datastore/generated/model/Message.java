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

/** This is an auto generated class representing the Message type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Messages")
public final class Message implements Model {
  public static final QueryField ID = field("Message", "id");
  public static final QueryField MESSAGE = field("Message", "message");
  public static final QueryField TIME = field("Message", "time");
  public static final QueryField SENDER = field("Message", "sender");
  public static final QueryField RECEIVER = field("Message", "receiver");
  public static final QueryField AUDIO_URL = field("Message", "audioUrl");
  public static final QueryField USER_URL = field("Message", "userUrl");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String message;
  private final @ModelField(targetType="String") String time;
  private final @ModelField(targetType="String") String sender;
  private final @ModelField(targetType="String") String receiver;
  private final @ModelField(targetType="String") String audioUrl;
  private final @ModelField(targetType="String") String userUrl;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getMessage() {
      return message;
  }
  
  public String getTime() {
      return time;
  }
  
  public String getSender() {
      return sender;
  }
  
  public String getReceiver() {
      return receiver;
  }
  
  public String getAudioUrl() {
      return audioUrl;
  }
  
  public String getUserUrl() {
      return userUrl;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Message(String id, String message, String time, String sender, String receiver, String audioUrl, String userUrl) {
    this.id = id;
    this.message = message;
    this.time = time;
    this.sender = sender;
    this.receiver = receiver;
    this.audioUrl = audioUrl;
    this.userUrl = userUrl;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Message message = (Message) obj;
      return ObjectsCompat.equals(getId(), message.getId()) &&
              ObjectsCompat.equals(getMessage(), message.getMessage()) &&
              ObjectsCompat.equals(getTime(), message.getTime()) &&
              ObjectsCompat.equals(getSender(), message.getSender()) &&
              ObjectsCompat.equals(getReceiver(), message.getReceiver()) &&
              ObjectsCompat.equals(getAudioUrl(), message.getAudioUrl()) &&
              ObjectsCompat.equals(getUserUrl(), message.getUserUrl()) &&
              ObjectsCompat.equals(getCreatedAt(), message.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), message.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMessage())
      .append(getTime())
      .append(getSender())
      .append(getReceiver())
      .append(getAudioUrl())
      .append(getUserUrl())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Message {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("message=" + String.valueOf(getMessage()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
      .append("sender=" + String.valueOf(getSender()) + ", ")
      .append("receiver=" + String.valueOf(getReceiver()) + ", ")
      .append("audioUrl=" + String.valueOf(getAudioUrl()) + ", ")
      .append("userUrl=" + String.valueOf(getUserUrl()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Message justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Message(
      id,
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
      message,
      time,
      sender,
      receiver,
      audioUrl,
      userUrl);
  }
  public interface BuildStep {
    Message build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep message(String message);
    BuildStep time(String time);
    BuildStep sender(String sender);
    BuildStep receiver(String receiver);
    BuildStep audioUrl(String audioUrl);
    BuildStep userUrl(String userUrl);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String message;
    private String time;
    private String sender;
    private String receiver;
    private String audioUrl;
    private String userUrl;
    @Override
     public Message build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Message(
          id,
          message,
          time,
          sender,
          receiver,
          audioUrl,
          userUrl);
    }
    
    @Override
     public BuildStep message(String message) {
        this.message = message;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        this.time = time;
        return this;
    }
    
    @Override
     public BuildStep sender(String sender) {
        this.sender = sender;
        return this;
    }
    
    @Override
     public BuildStep receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }
    
    @Override
     public BuildStep audioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }
    
    @Override
     public BuildStep userUrl(String userUrl) {
        this.userUrl = userUrl;
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
    private CopyOfBuilder(String id, String message, String time, String sender, String receiver, String audioUrl, String userUrl) {
      super.id(id);
      super.message(message)
        .time(time)
        .sender(sender)
        .receiver(receiver)
        .audioUrl(audioUrl)
        .userUrl(userUrl);
    }
    
    @Override
     public CopyOfBuilder message(String message) {
      return (CopyOfBuilder) super.message(message);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
    
    @Override
     public CopyOfBuilder sender(String sender) {
      return (CopyOfBuilder) super.sender(sender);
    }
    
    @Override
     public CopyOfBuilder receiver(String receiver) {
      return (CopyOfBuilder) super.receiver(receiver);
    }
    
    @Override
     public CopyOfBuilder audioUrl(String audioUrl) {
      return (CopyOfBuilder) super.audioUrl(audioUrl);
    }
    
    @Override
     public CopyOfBuilder userUrl(String userUrl) {
      return (CopyOfBuilder) super.userUrl(userUrl);
    }
  }
  
}
