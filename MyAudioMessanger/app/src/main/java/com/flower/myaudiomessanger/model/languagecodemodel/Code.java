
package com.flower.myaudiomessanger.model.languagecodemodel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code implements Parcelable
{

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("voice")
    @Expose
    private String voice;
    @SerializedName("engine")
    @Expose
    private String engine;
    public final static Creator<Code> CREATOR = new Creator<Code>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Code createFromParcel(android.os.Parcel in) {
            return new Code(in);
        }

        public Code[] newArray(int size) {
            return (new Code[size]);
        }

    }
    ;

    protected Code(android.os.Parcel in) {
        this.language = ((String) in.readValue((String.class.getClassLoader())));
        this.languageCode = ((String) in.readValue((String.class.getClassLoader())));
        this.voice = ((String) in.readValue((String.class.getClassLoader())));
        this.engine = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Code() {
    }

    public Code(String language, String languageCode, String voice, String engine) {
        this.language = language;
        this.languageCode = languageCode;
        this.voice = voice;
        this.engine = engine;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(language);
        dest.writeValue(languageCode);
        dest.writeValue(voice);
        dest.writeValue(engine);
    }

    public int describeContents() {
        return  0;
    }

}
