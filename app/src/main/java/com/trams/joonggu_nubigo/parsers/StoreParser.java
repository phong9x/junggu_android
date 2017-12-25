package com.trams.joonggu_nubigo.parsers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Facility;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dbmanager.DbFacilityUpdate;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.objects.AreaStoreDetailPage2Obj;
import com.trams.joonggu_nubigo.objects.ImageObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dang on 18/11/2015.
 */
public class StoreParser {

    public static final String FIELD_PATTERN = " ";

    public static String getPresentImageUrl(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String preUrl = WebServiceConfig.BASE_IMAGE_URL + jsonArray.getString(0);
                return preUrl;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static int getGrade(String json) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            int grade = Integer.parseInt(jsonObject.getString("grade"));
//            return grade;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static float getGra(String json) {
        float result = 0;
        try {
            JSONObject jsonData = new JSONObject(json);
            float totalUserRate = (float) jsonData.getDouble("total_rate");
            float totalGrade = (float) jsonData.getDouble("grade");
            if (totalUserRate != 0) {
                result = totalGrade / totalUserRate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getTotalRate(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int totalRate = Integer.parseInt(jsonObject.getString("total_rate"));
            return totalRate;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFieldList(String json) {
        String result = "";
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fieldName = jsonObject.getString("field_list_name");
                result = result + fieldName + FIELD_PATTERN;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Integer> getAccessibilityList(String json) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(Integer.parseInt(jsonArray.getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setAccessibilityList(String accessJsonArray, CheckBox[] checkBoxes) {
        try {
            // convert json array to int[] arr
            JSONArray jsonArray = new JSONArray(accessJsonArray);
            int[] accessArr = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                accessArr[i] = Integer.parseInt(jsonArray.getString(i)) - 1;
            }


            // set all accessibility to normal
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(false);
            }

            // set some supporting accessibility to active
            for (int i = 0; i < accessArr.length; i++) {
                int supIdx = accessArr[i];
                checkBoxes[supIdx].setChecked(true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getTags(String tags) {
        ArrayList<String> result = new ArrayList<>();
        if (tags != null && !tags.equals("null")) {
            String[] tagArr = tags.split(",", -1);
            result = new ArrayList<String>(Arrays.asList(tagArr));
        }
        return result;
    }

    public static ArrayList<ImageObj> getMainImageUrls(String json) {
        ArrayList<ImageObj> listObjs = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                ImageObj obj = new ImageObj();
                obj.setUrlImage(WebServiceConfig.BASE_IMAGE_URL + jsonArray.getString(i));
                listObjs.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listObjs;
    }

    public static ArrayList<AreaStoreDetailPage2Obj> getDataExtendDetail2(String json) {
        ArrayList<AreaStoreDetailPage2Obj> areaStoreDetailPage2Objs = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // location name
                String name = jsonObject.getString("name_of_relevant");
                // list url
                ArrayList<ImageObj> imageObjs = new ArrayList<>();
                JSONArray jsonArray1 = jsonObject.getJSONArray("photos");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    ImageObj imageObj = new ImageObj();
                    imageObj.setUrlImage(WebServiceConfig.BASE_IMAGE_URL + jsonArray1.getString(j));
                    imageObjs.add(imageObj);
                }

                AreaStoreDetailPage2Obj obj = new AreaStoreDetailPage2Obj();
                obj.setName(name);
                obj.setImageObjs(imageObjs);

                areaStoreDetailPage2Objs.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return areaStoreDetailPage2Objs;
    }

    public static void setFieldIcon(Store target, ImageView fieldView) {
        String mainType = StoreParser.getFieldList(target.getFieldList()).split(StoreParser.FIELD_PATTERN, -1)[0];

        if (mainType.equals("먹거리")) {
            fieldView.setImageResource(R.drawable.field_food);
        } else if (mainType.equals("관광지")) {
            fieldView.setImageResource(R.drawable.field_sight);
        } else if (mainType.equals("숙박")) {
            fieldView.setImageResource(R.drawable.field_accommodation);
        } else if (mainType.equals("쇼핑")) {
            fieldView.setImageResource(R.drawable.field_shopping);
        } else if (mainType.equals("생활")) {
            fieldView.setImageResource(R.drawable.field_living);
        }
    }

    public static ArrayList<Integer> getListFieldId(Store store) {
        ArrayList<Integer> result = new ArrayList<>();

        String fieldName = store.getFieldList();

        try {
            JSONArray jsonArray = new JSONArray(fieldName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int fieldId = jsonObject.getInt("field_list_id");

                if (!result.contains(fieldId))
                    result.add(fieldId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getListFieldName(Store store) {
        ArrayList<String> result = new ArrayList<>();

        String fieldList = store.getFieldList();

        try {
            JSONArray jsonArray = new JSONArray(fieldList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fieldName = jsonObject.getString("field_list_name");
                if (!result.contains(fieldName))
                    result.add(fieldName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getAddress1(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String jsonArr = jsonObject.getString("address");
            JSONArray jsonArray = new JSONArray(jsonArr);
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            String address1 = jsonObject1.getString("address1");
            return address1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getAddress2(String json, String catName) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String jsonArr = jsonObject.getString("address");
            JSONArray jsonArray = new JSONArray(jsonArr);
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            String address2 = catName + " " + jsonObject1.getString("address2");
            return address2;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFacilityList(Context context, String json) {
        String result = "";
        try {
            JSONArray jsonArray = new JSONArray(json);
            DbFacilityUpdate dbFacilityUpdate = new DbFacilityUpdate(context);
            for (int i = 0; i < jsonArray.length(); i++) {
                long id = jsonArray.getLong(i);
                Facility facility = dbFacilityUpdate.getFacility((Activity) context, id);
                if (facility != null)
                    result = result + facility.getName() + " ";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}

