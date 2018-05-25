package com.example.lyq.software.ui.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lyq on 2018/3/12.
 */

public class Images implements Serializable{

    private String releaseId;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;

    public Images(){
        super();
    }

    public Images(String releaseId, String image1, String image2, String image3, String image4, String image5,
                  String image6, String image7, String image8, String image9){
        super();
        this.releaseId = releaseId;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.image9 = image9;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getReleaseId(){
        return releaseId;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage1(){
        return image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage2(){
        return image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage3(){
        return image3;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage4(){
        return image4;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage5(){
        return image5;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage6(){
        return image6;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage7(){
        return image7;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage8(){
        return image8;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    public String getImage9(){
        return image9;
    }

    public static Images setImages(JSONObject json){
        try {
            return new Images(
                    json.getString("releaseId"),
                    json.getString("image1"),
                    json.getString("image2"),
                    json.getString("image3"),
                    json.getString("image4"),
                    json.getString("image5"),
                    json.getString("image6"),
                    json.getString("image7"),
                    json.getString("image8"),
                    json.getString("image9")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
