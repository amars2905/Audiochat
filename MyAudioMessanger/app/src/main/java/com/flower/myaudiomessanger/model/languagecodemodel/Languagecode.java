
package com.flower.myaudiomessanger.model.languagecodemodel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Languagecode implements Parcelable
{

    @SerializedName("AWS_language_code")
    @Expose
    private String aWSLanguageCode;
    @SerializedName("code")
    @Expose
    private List<Code> code = null;
    public final static Creator<Languagecode> CREATOR = new Creator<Languagecode>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Languagecode createFromParcel(android.os.Parcel in) {
            return new Languagecode(in);
        }

        public Languagecode[] newArray(int size) {
            return (new Languagecode[size]);
        }

    }
    ;

    protected Languagecode(android.os.Parcel in) {
        this.aWSLanguageCode = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.code, (Code.class.getClassLoader()));
    }

    public Languagecode() {
    }

    public String getAWSLanguageCode() {
        return aWSLanguageCode;
    }

    public void setAWSLanguageCode(String aWSLanguageCode) {
        this.aWSLanguageCode = aWSLanguageCode;
    }

    public List<Code> getCode() {
        return code;
    }

    public void setCode(List<Code> code) {
        this.code = code;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(aWSLanguageCode);
        dest.writeList(code);
    }

    public int describeContents() {
        return  0;
    }

}
