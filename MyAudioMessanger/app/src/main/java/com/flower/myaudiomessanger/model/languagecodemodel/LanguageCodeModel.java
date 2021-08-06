
package com.flower.myaudiomessanger.model.languagecodemodel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageCodeModel implements Parcelable
{

    @SerializedName("languagecode")
    @Expose
    private Languagecode languagecode;
    public final static Creator<LanguageCodeModel> CREATOR = new Creator<LanguageCodeModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LanguageCodeModel createFromParcel(android.os.Parcel in) {
            return new LanguageCodeModel(in);
        }

        public LanguageCodeModel[] newArray(int size) {
            return (new LanguageCodeModel[size]);
        }

    }
    ;

    protected LanguageCodeModel(android.os.Parcel in) {
        this.languagecode = ((Languagecode) in.readValue((Languagecode.class.getClassLoader())));
    }

    public LanguageCodeModel() {
    }

    public Languagecode getLanguagecode() {
        return languagecode;
    }

    public void setLanguagecode(Languagecode languagecode) {
        this.languagecode = languagecode;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(languagecode);
    }

    public int describeContents() {
        return  0;
    }

}
