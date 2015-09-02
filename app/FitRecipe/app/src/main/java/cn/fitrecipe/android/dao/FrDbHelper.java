package cn.fitrecipe.android.dao;

import android.content.Context;

import com.tencent.tauth.IRequestListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.BasketItem;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Plan;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.PlanItem2Recipe;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by wk on 2015/8/15.
 */
public class FrDbHelper {

    private static FrDbHelper instance;
    private static Context context;

    private FrDbHelper(Context context) {
        this.context = context;
    }

    public static FrDbHelper getInstance(Context context) {
        synchronized (FrDbHelper.class) {
            if(instance == null)
                instance = new FrDbHelper(context);
        }
        return instance;
    }


    /**
     * add Recipe
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        RecipeDao recipeDao = new RecipeDao(context);
        IngredientDao ingredientDao = new IngredientDao(context);
        ComponentDao componentDao = new ComponentDao(context);
        NutritionDao nutritionDao = new NutritionDao(context);

        recipeDao.add(recipe);
        List<Component> component_set = recipe.getComponent_set();
        if(component_set != null) {
            for (int i = 0; i < component_set.size(); i++) {
                Component component = component_set.get(i);
                component.setRecipe(recipe);

                Ingredient ingredient = component.getIngredient();
                ingredientDao.add(ingredient);

                componentDao.addComponentBelongToRecipe(component);
            }
        }

        List<Nutrition> nutrition_set = recipe.getNutrition_set();
        if(nutrition_set != null) {
            for (int i = 0; i < nutrition_set.size(); i++) {
                Nutrition nutrition = nutrition_set.get(i);
                nutrition.setRecipe(recipe);
                nutritionDao.add(nutrition);
            }
        }
    }
    /**
     * get All recipe
     */
    public List<Recipe> getAllRecipe() {
        RecipeDao recipeDao = new RecipeDao(context);
        List<Recipe> recipes = recipeDao.getAll();
        if(recipes != null) {
            for(int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                recipe.setNutrition_set(new NutritionDao(context).getNutritions(recipe.getId()));
                recipe.setComponent_set(new ComponentDao(context).getComponents(recipe.getId()));
            }
        }
        return  recipes;
    }

    /**
     *get Recipe by Id
     * @param id
     * @return Recipe
     */
    public Recipe getRecipe(int id) {
        RecipeDao recipeDao = new RecipeDao(context);
        Recipe recipe = recipeDao.get(id);
        if(recipe != null) {
            recipe.setNutrition_set(new NutritionDao(context).getNutritions(recipe.getId()));
            recipe.setComponent_set(new ComponentDao(context).getComponents(recipe.getId()));
        }
        return  recipe;
    }

    /**
     * get Report belongs to Author
     * @param author
     * @return Report
     */
    public Report getReport(Author author) {
        ReportDao dao = new ReportDao(context);
        return dao.getReport(author);
    }

    /**
     * add Report
     * @param report
     */
    public void addReport(Report report) {
        ReportDao dao = new ReportDao(context);
        dao.add(report);
    }

    /**
     * save author
     * @param author
     */
    public void saveAuthor(Author author) {
        new AuthorDao(context).save(author);
    }

    /**
     * Author logout
     * @param author
     */
    public void authorLogout(Author author) {
        new AuthorDao(context).logout(author);
    }

    public Author getLoginAuthor() {
        return new AuthorDao(context).getAuthor();
    }

    /**
     * add Recipe to basket
     * @param recipe
     */
    public void addToBasket(Recipe recipe) {
        RecipeDao dao = new RecipeDao(context);
        Recipe old = getRecipe(recipe.getId());
        recipe.setInBasket(true);
        if(recipe.getId() != -1) {
            recipe.setWeightInBasket(old.getWeightInBasket() + recipe.getIncreWeight());
            dao.add(recipe);
        }else {
            if(old != null) {
                List<Component> components = old.getComponent_set();
                int len = components.size();
                for (int i = 0; i < recipe.getComponent_set().size(); i++) {
                    Component com1 = recipe.getComponent_set().get(i);
                    boolean flag = false;
                    for (int j = 0; j < len; j++) {
                        Component com2 = components.get(j);
                        if (com1.getIngredient().getName().equals(com2.getIngredient().getName())) {
                            com2.setMAmount(com2.getMAmount() + com1.getMAmount());
                            flag = true;
                        }
                    }
                    if (flag)
                        components.add(com1);
                }
                addRecipe(old);
            }else {
                recipe.setTitle("其他");
                addRecipe(recipe);
            }
        }
    }

    public void removeFromBasket(Recipe recipe) {
        if(recipe.getId() == -1) {
            Recipe old = getRecipe(-1);
            List<Component> components = old.getComponent_set();
            ComponentDao dao = new ComponentDao(context);
            for(int i = 0; i < components.size(); i++) {
                Component com1 = components.get(i);
                for(int j = 0; j < recipe.getComponent_set().size(); j++) {
                    Component com2 = recipe.getComponent_set().get(j);
                    if(com1.getIngredient().getName().equals(com2.getIngredient().getName())) {
                        com1.setMAmount(com1.getMAmount() - com2.getMAmount());
                        if(com1.getMAmount() == 0) {
                            dao.remove(com1.getId());
                        }
                    }
                }
            }
        }else {
            RecipeDao dao = new RecipeDao(context);
            Recipe old = dao.get(recipe.getId());
            recipe.setWeightInBasket(old.getWeightInBasket() - recipe.getIncreWeight());
            if(recipe.getWeightInBasket() == 0)
                recipe.setInBasket(false);
            dao.add(recipe);
        }
    }

    /**
     * save Basket status
     * @param basket
     */
    public void saveBasket(List<Recipe> basket) {
        if(basket != null) {
            for(int i = 0; i < basket.size(); i++) {
                Recipe recipe = basket.get(i);
                recipe.setInBasket(true);
                addRecipe(recipe);
            }
        }
    }

    /**
     * get content of Basket
     * @return a list of Basket
     */
    public List<Recipe> getBasket() {
        List<Recipe> basket = new ArrayList<>();
        RecipeDao dao = new RecipeDao(context);
        List<Recipe> recipes = dao.getInBasket();
        for(int i = 0; i < recipes.size(); i++) {
            Recipe recipe = getRecipe(recipes.get(i).getId());
            if(!((recipe.getComponent_set().size() == 0) && recipe.getId() == -1))
                basket.add(recipe);
        }
        return basket;
    }

    /**
     * clear the basket
     * @param basket
     */
    public void clearBasket(List<Recipe> basket) {
        if(basket != null) {
            for(int i = 0; i < basket.size(); i++) {
                Recipe recipe = basket.get(i);
                for(int j = 0; j < recipe.getComponent_set().size(); j++)
                    recipe.getComponent_set().get(j).setStatus(0);
                recipe.setInBasket(false);
                recipe.setWeightInBasket(0);
                addRecipe(recipe);
            }
        }
    }

    /**
     * add plan item, breakfast, lunch, add_meal, supper
     * @param item
     */
    public PlanItem addPlanItem(PlanItem item) {
        PlanItemDao dao = new PlanItemDao(context);
        int id = dao.add(item);
        item.setId(id);
        List<Object> data = item.getData();
        if(data != null) {
            PlanItem2RecipeDao dao1 = new PlanItem2RecipeDao(context);
            ComponentDao dao2 = new ComponentDao(context);
            for(int i = 0; i < data.size(); i++) {
                Object obj = data.get(i);
                if(obj instanceof Recipe) {
                    Recipe recipe = (Recipe) obj;
                    PlanItem2Recipe planItem2Recipe = new PlanItem2Recipe();
                    planItem2Recipe.setPlanItem(item);
                    planItem2Recipe.setRecipe(recipe);
                    planItem2Recipe.setItemIndex(i);
                    planItem2Recipe.setRecipeWeight(recipe.getIncreWeight());
                    dao1.add(planItem2Recipe);
                }else if(obj instanceof Component) {
                    Component component = (Component) obj;
                    component.setPlanItem(item);
                    component.setItemIndex(i);
                    dao2.addComponentBelongToPlanItem(component);
                }
            }
        }
        return item;
    }

    /**
     *get a plan item by id
     * @param id
     * @return PlanItem
     */
    public PlanItem getPlanItem(int id) {
        PlanItemDao dao = new PlanItemDao(context);
        PlanItem item = dao.get(id);
        if(item == null)    return null;
        PlanItem2RecipeDao dao1 = new PlanItem2RecipeDao(context);
        ComponentDao dao2 = new ComponentDao(context);
        List<PlanItem2Recipe> matches = dao1.get(id);
        int size = 0;
        List<Component> components = dao2.getComponentsInPlanItem(id);
        if(matches != null) {
            size += matches.size();
        }
        if(components != null) {
            size += components.size();
        }
        TreeMap<Integer, Object> map = new TreeMap<Integer, Object>();
        if(matches != null) {
            for(int i = 0; i < matches.size(); i++) {
                PlanItem2Recipe match = matches.get(i);
                Recipe recipe = getRecipe(matches.get(i).getRecipe().getId());
                recipe.setIncreWeight(match.getRecipeWeight());
                map.put(match.getItemIndex(), recipe);
            }
        }
        if(components != null) {
            for(int i = 0; i < components.size(); i++) {
                map.put(components.get(i).getItemIndex(), components.get(i));
            }
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            item.addContent(map.get(iterator.next()));
        }
        return item;
    }

    /**
     * get plan one day by id
     * @param strDate
     * @return DayPan
     */
    public DayPlan getDayPlan(String  strDate) {
        DayPlanDao dao = new DayPlanDao(context);
        DayPlan dayPlan = dao.get(strDate);
        if(dayPlan == null) return dayPlan;
        PlanItemDao dao1 = new PlanItemDao(context);
        List<PlanItem> items = dao1.getPlanItemsInDayPlan(dayPlan.getId());
        if(items != null) {
            for(int i = 0; i < items.size(); i++) {
                PlanItem item = items.get(i);
                item = getPlanItem(item.getId());
                item.setDayPlan(dayPlan);
                items.set(i, item);
            }
        }
        dayPlan.setPlanItems(items);
        return dayPlan;
    }

    /**
     * add and update dayplan
     * @param dayPlan
     * @return  DayPlan
     */
    public DayPlan addDayPlan(DayPlan dayPlan) {
        DayPlanDao dao = new DayPlanDao(context);
        int id = dao.add(dayPlan);
        dayPlan.setId(id);
        List<PlanItem> items = dayPlan.getPlanItems();
        if(items == null) {
            items = new ArrayList<>();
            for(PlanItem.ItemType type : PlanItem.ItemType.values()) {
                if (type == PlanItem.ItemType.ALL)  continue;
                PlanItem item = new PlanItem();
                item.setImageCover("drawable://" + R.drawable.plan_04);
                item.setItemType(type);
                item.setDayPlan(dayPlan);
                item = addPlanItem(item);
                items.add(item);
            }
            dayPlan.setPlanItems(items);
        }else {
            for (int i = 0; i < items.size(); i++) {
                PlanItem item = items.get(i);
                item.setDayPlan(dayPlan);
                addPlanItem(item);
            }
        }
        return dayPlan;
    }

    /**
     *
     * @param item
     */
    public void deletePlanItem(PlanItem item, int index) {
        Object obj = item.getData().get(index);
        if(obj instanceof Recipe) {
            PlanItem2RecipeDao dao = new PlanItem2RecipeDao(context);
            PlanItem2Recipe match = new PlanItem2Recipe();
            match.setRecipe((Recipe)(obj));
            match.setPlanItem(item);
            dao.delete(match);
        }else if(obj instanceof Component) {
            Component component = (Component) obj;
            ComponentDao dao = new ComponentDao(context);
            dao.remove(component.getId());
        }
    }


    /**
     *
     * @param item
     */
    public void punch(PlanItem item) {
        PlanItemDao dao = new PlanItemDao(context);
        dao.add(item);
        DayPlanDao dao1 = new DayPlanDao(context);
        DayPlan dayPlan = item.getDayPlan();
        dao1.add(dayPlan);
    }

}
