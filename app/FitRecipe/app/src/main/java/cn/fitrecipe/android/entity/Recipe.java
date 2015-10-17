package cn.fitrecipe.android.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
public class Recipe implements Serializable{
    
    private int id;
    //ingnore when create database
    private ArrayList<Label> meat_labels;
    private ArrayList<Label> time_labels;
    private ArrayList<Label> effect_labels;
    private ArrayList<Label> other_labels;
    private ArrayList<Procedure>  procedure_set;
    private ArrayList<Comment> comment_set;
    private ArrayList<Nutrition> nutrition_set;
    private Collection<Component> component_set;
    private String macro_element_ratio;
    private int total_amount;
    private double protein_ratio;
    private double fat_ratio;
    private Author author;
    private String created_time;
    private String updated_time;
    private String img;
    private String thumbnail;
    private String recommend_img;
    private String recommend_thumbnail;
    private String title;
    private String introduce;
    private String tips;
    private int duration;
    private double calories;
    private int collection_count;
    private boolean has_collected;
    private String tags;


    public static Recipe fromJson(String json) throws JSONException {
        JSONObject data = new JSONObject(json);
        Recipe recipe = new Recipe();
        Gson gson = new Gson();
        if(data.has("id"))
            recipe.setId(data.getInt("id"));

        //parse meat labels
        if(data.has("meat_labels")) {
            JSONArray jlabels = data.getJSONArray("meat_labels");
            ArrayList<Label> meat_labels = gson.fromJson(jlabels.toString(), new TypeToken<ArrayList<Label>>() {
            }.getType());
            recipe.setMeat_labels(meat_labels);
        }

        //parse time labels
        if(data.has("time_labels")) {
            JSONArray jlabels = data.getJSONArray("time_labels");
            ArrayList<Label> time_labels = gson.fromJson(jlabels.toString(), new TypeToken<ArrayList<Label>>(){}.getType());
            recipe.setTime_labels(time_labels);
        }

        //parse effect labels
        if(data.has("effect_labels")) {
            JSONArray jlabels = data.getJSONArray("effect_labels");
            ArrayList<Label> effect_labels = gson.fromJson(jlabels.toString(), new TypeToken<ArrayList<Label>>(){}.getType());
            recipe.setEffect_labels(effect_labels);
        }

        //parse other labels
        if(data.has("other_labels")) {
            JSONArray jlabels = data.getJSONArray("other_labels");
            ArrayList<Label> other_labels = gson.fromJson(jlabels.toString(), new TypeToken<ArrayList<Label>>(){}.getType());
            recipe.setOther_labels(other_labels);
        }

        //parse procedure set
        if(data.has("procedure_set")) {
            JSONArray jprocedure = data.getJSONArray("procedure_set");
            ArrayList<Procedure> procedure_set = gson.fromJson(jprocedure.toString(), new TypeToken<ArrayList<Procedure>>(){}.getType());
            recipe.setProcedure_set(procedure_set);
        }

        //parse component set
        if(data.has("component_set")) {
            JSONArray jcomponent = data.getJSONArray("component_set");
            ArrayList<Component> component_set = gson.fromJson(jcomponent.toString(), new TypeToken<ArrayList<Component>>(){}.getType());
            recipe.setComponent_set(component_set);
        }

        //parse nutrition_set
        if(data.has("nutrition_set")) {
            JSONObject jnutrition_set = data.getJSONObject("nutrition_set");
            ArrayList<Nutrition> nutrition_set = new ArrayList<>();
            String[] names = new String[]{"Water", "Protein", "Total lipid (fat)", "Carbohydrate, by difference", "Fiber, total dietary", "Sodium, Na", "Vitamin C, total ascorbic acid", "Vitamin D", "Fatty acids", "Cholesterol"};
            for (int i = 0; i < names.length; i++) {
                JSONObject jnutrition = jnutrition_set.getJSONObject(names[i]);
                Nutrition nutrition = new Nutrition();
                if(jnutrition.has("name"))
                    nutrition.setName(jnutrition.getString("name"));
                if(jnutrition.has("amount"))
                    nutrition.setAmount(jnutrition.getDouble("amount"));
                if(jnutrition.has("unit"))
                    nutrition.setUnit(jnutrition.getString("unit"));
                nutrition_set.add(nutrition);
            }
            recipe.setNutrition_set(nutrition_set);
        }

        if(data.has("macro_element_ratio"))
            recipe.setMacro_element_ratio(data.getString("macro_element_ratio"));

        if(data.has("total_amount")) {
            String total_amount = data.getString("total_amount");
            recipe.setTotal_amount(Integer.parseInt(total_amount.substring(0, total_amount.length() - 1)));
        }

        if(data.has("protein_ratio")) {
            String str = data.getString("protein_ratio");
            recipe.setProtein_ratio(Double.parseDouble(str.substring(0, str.length() - 1)));
        }

        if(data.has("fat_ratio")) {
            String str = data.getString("fat_ratio");
            recipe.setFat_ratio(Double.parseDouble(str.substring(0, str.length() - 1)));
        }

        //parse Author
        if(data.has("author") && !data.get("author").toString().equals("null")) {
            JSONObject jauthor = data.getJSONObject("author");
            if(jauthor != null) {
                Author author = gson.fromJson(jauthor.toString(), Author.class);
                recipe.setAuthor(author);
            }
        }

        if(data.has("created_time"))
            recipe.setCreated_time(data.getString("created_time"));

        if(data.has("updated_time"))
            recipe.setUpdated_time(data.getString("updated_time"));

        if(data.has("img"))
            recipe.setImg(data.getString("img"));

        if(data.has("thumbnail"))
            recipe.setThumbnail(data.getString("thumbnail"));

        if(data.has("recommend_img"))
            recipe.setRecommend_img(data.getString("recommend_img"));

        if(data.has("recommend_thumbnail"))
            recipe.setRecommend_thumbnail(data.getString("recommend_thumbnail"));

        if(data.has("title"))
            recipe.setTitle(data.getString("title"));

        if(data.has("introduce"))
            recipe.setIntroduce(data.getString("introduce"));

        if(data.has("tips"))
            recipe.setTips(data.getString("tips"));

        if(data.has("duration"))
            recipe.setDuration(data.getInt("duration"));

        if(data.has("calories"))
            recipe.setCalories(data.getDouble("calories"));

        if(data.has("collection_count"))
            recipe.setCollection_count(data.getInt("collection_count"));

        if(data.has("has_collected"))
            recipe.setHas_collected(data.getBoolean("has_collected"));

        if(data.has("comment_set")) {
            String jsona = data.getJSONArray("comment_set").toString();
            ArrayList<Comment>  comments = new Gson().fromJson(jsona, new TypeToken<ArrayList<Comment>>() {
            }.getType());
            recipe.setComment_set(comments);
        }
        //parse complete
        return recipe;
    }

    public String getRecipe_function() {
        return effect_labels.get(0).getName();
    }

    public String getRecipe_function_backup() {
        if(effect_labels.size() > 1) {
            return effect_labels.get(1).getName();
        }
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setMeat_labels(ArrayList<Label> meat_labels) {
        this.meat_labels = meat_labels;
    }


    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public double getProtein_ratio() {
        return protein_ratio;
    }

    public void setProtein_ratio(double protein_ratio) {
        this.protein_ratio = protein_ratio;
    }

    public double getFat_ratio() {
        return fat_ratio;
    }

    public void setFat_ratio(double fat_ratio) {
        this.fat_ratio = fat_ratio;
    }

    public List<Component> getComponent_set() {
        return (List<Component>)component_set;
    }

    public void setTime_labels(ArrayList<Label> time_labels) {
        this.time_labels = time_labels;
    }


    public void setEffect_labels(ArrayList<Label> effect_labels) {
        this.effect_labels = effect_labels;
    }

    public ArrayList<Label> getOther_labels() {
        return other_labels;
    }

    public void setOther_labels(ArrayList<Label> other_labels) {
        this.other_labels = other_labels;
    }


    public ArrayList<Procedure> getProcedure_set() {
        return procedure_set;
    }

    public void setProcedure_set(ArrayList<Procedure> procedure_set) {
        this.procedure_set = procedure_set;
    }

    public String getMacro_element_ratio() {
        return macro_element_ratio;
    }

    public void setMacro_element_ratio(String macro_element_ratio) {
        this.macro_element_ratio = macro_element_ratio;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getImg() {
        return FrServerConfig.getImageCompressed(img);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbnail() {
        return FrServerConfig.getImageCompressed(thumbnail);
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRecommend_img() {
        return FrServerConfig.getImageCompressed(recommend_img);
    }

    public void setRecommend_img(String recommend_img) {
        this.recommend_img = recommend_img;
    }

    public String getRecommend_thumbnail() {
        return FrServerConfig.getImageCompressed(recommend_thumbnail);
    }

    public void setRecommend_thumbnail(String recommend_thumbnail) {
        this.recommend_thumbnail = recommend_thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }


    public int getCollection_count() {
        return collection_count;
    }

    public void setCollection_count(int collection_count) {
        this.collection_count = collection_count;
    }

    public ArrayList<Comment> getComment_set() {
        return comment_set;
    }

    public void setComment_set(ArrayList<Comment> comment_set) {
        this.comment_set = comment_set;
    }

    public String getTags() {
        StringBuilder tags = new StringBuilder();
        for(int i = 0; i < time_labels.size(); i++) {
            Label label = time_labels.get(i);
            tags.append(label.getName());
            tags.append("、");
        }

        for(int i = 0; i < meat_labels.size(); i++) {
            Label label = meat_labels.get(i);
            tags.append(label.getName());
            tags.append("、");
        }

        for(int i = 0; i < other_labels.size(); i++) {
            Label label = other_labels.get(i);
            tags.append(label.getName());
            tags.append("、");
        }
        if(tags.length() > 0)
            tags.deleteCharAt(tags.length() - 1);
        return tags.toString();
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public void setComponent_set(List<Component> component_set) {
        this.component_set = component_set;
    }

    public ArrayList<Nutrition> getNutrition_set() {
        return nutrition_set;
    }

    public void setNutrition_set(ArrayList<Nutrition> nutrition_set) {
        this.nutrition_set = nutrition_set;
    }

    public boolean isHas_collected() {
        return has_collected;
    }

    public void setHas_collected(boolean has_collected) {
        this.has_collected = has_collected;
    }
}
