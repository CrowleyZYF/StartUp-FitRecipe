package cn.fitrecipe.android.function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.fitrecipe.android.entity.Nutrition;

/**
 * Created by wk on 2015/10/9.
 */
public class JsonParseHelper {

    public static ArrayList<Nutrition>  getNutritionSetFromJson(JSONObject json) throws JSONException {
        ArrayList<Nutrition> nutrition_set = new ArrayList<>();
        String[] names = new String[]{"Water", "Protein", "Total lipid (fat)", "Carbohydrate, by difference", "Fiber, total dietary", "Sodium, Na", "Vitamin C, total ascorbic acid", "Vitamin D", "Fatty acids", "Cholesterol"};
        for (int i = 0; i < names.length; i++) {
            JSONObject jnutrition = json.getJSONObject(names[i]);
            Nutrition nutrition = new Nutrition();
            if(jnutrition.has("name"))
                nutrition.setName(jnutrition.getString("name"));
            if(jnutrition.has("amount"))
                nutrition.setAmount(jnutrition.getDouble("amount"));
            if(jnutrition.has("unit"))
                nutrition.setUnit(jnutrition.getString("unit"));
            nutrition_set.add(nutrition);
        }
        return nutrition_set;
    }
}
