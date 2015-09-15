package cn.fitrecipe.android.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanInUse;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.PlanItem2Recipe;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;

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



    public List<Ingredient> getAllIngredient() {
        IngredientDao dao = new IngredientDao(context);
        return dao.getAll();
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
//                recipe.setNutrition_set(new NutritionDao(context).getNutritions(recipe.getId()));
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
//            recipe.setNutrition_set(new NutritionDao(context).getNutritions(recipe.getId()));
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
     * add Recipe to basket in planitem
     * @param recipe
     */
    private void addToBasket(Recipe recipe) {
        RecipeDao dao = new RecipeDao(context);
        Recipe old = getRecipe(recipe.getId());
        recipe.setInPlanItemBasket(true);
        if(recipe.getId() != -1) {
            recipe.setWeightInPlanBasket(old.getWeightInPlanBasket() + recipe.getIncreWeight());
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
                            com2.setAmount(com2.getAmount() + com1.getAmount());
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

    /**
     * add PlanItem to basket
     * @param item
     */
    public void addPlanItemToBasket(PlanItem item) {
        item.setInBasket(true);
        addPlanItem(item);
        List<Component> components = new ArrayList<Component>();
        for(int i = 0; i < item.getData().size(); i++) {
            Object obj = item.getData().get(i);
            if(obj instanceof Recipe) {
                addToBasket((Recipe) obj);
            }else if(obj instanceof Component) {
                Component component = (Component) obj;
                component.setPlanItem(null);
                components.add(component);
            }
        }
        if(components.size() > 0) {
            Recipe recipe = new Recipe();
            recipe.setId(-1);
            recipe.setComponent_set(components);
            addToBasket(recipe);
        }
    }


    private void removeFromBasket(Recipe recipe) {
        if(recipe.getId() == -1) {
            Recipe old = getRecipe(-1);
            List<Component> components = old.getComponent_set();
            ComponentDao dao = new ComponentDao(context);
            for(int i = 0; i < components.size(); i++) {
                Component com1 = components.get(i);
                for(int j = 0; j < recipe.getComponent_set().size(); j++) {
                    Component com2 = recipe.getComponent_set().get(j);
                    if(com1.getIngredient().getName().equals(com2.getIngredient().getName())) {
                        com1.setAmount(com1.getAmount() - com2.getAmount());
                        if(com1.getAmount() == 0) {
                            dao.remove(com1.getId());
                        }
                    }
                }
            }
        }else {
            RecipeDao dao = new RecipeDao(context);
            Recipe old = dao.get(recipe.getId());
            recipe.setWeightInPlanBasket(old.getWeightInPlanBasket() - recipe.getIncreWeight());
            if(recipe.getWeightInPlanBasket() == 0)
                recipe.setInPlanItemBasket(false);
            dao.add(recipe);
        }
    }

    /**
     * remove plan item from basket
     * @param item
     */
    public void removePlanItemToBasket(PlanItem item) {
        item.setInBasket(false);
        addPlanItem(item);
        List<Component> components = new ArrayList<Component>();
        for(int i = 0; i < item.getData().size(); i++) {
            Object obj = item.getData().get(i);
            if(obj instanceof Recipe) {
                removeFromBasket((Recipe) obj);
            }else if(obj instanceof Component) {
                Component component = (Component) obj;
                component.setPlanItem(null);
                components.add(component);
            }
        }
        if(components.size() > 0) {
            Recipe recipe = new Recipe();
            recipe.setId(-1);
            recipe.setComponent_set(components);
            removeFromBasket(recipe);
        }
    }


    /**
     * add recipe to basket
     * @param recipe
     */
    public void addRecipeToBasket(Recipe recipe) {
        recipe.setInBasket(true);
        addRecipe(recipe);
    }

    /**
     * remove recipe from basket
     * @param recipe
     */
    public void removeRecipeFromBasket(Recipe recipe) {
        recipe.setInBasket(false);
        recipe.setWeightInRecipeBasket(0);
        addRecipe(recipe);
    }


    /**
     * save Basket status
     * @param basket
     */
    public void saveBasket(List<Recipe> basket) {
        RecipeDao dao = new RecipeDao(context);
        ComponentDao dao1 = new ComponentDao(context);
        if(basket != null) {
            for(int i = 0; i < basket.size(); i++) {
                Recipe recipe = basket.get(i);
                recipe.setInBasket(true);
                dao.add(recipe);
                List<Component> components = recipe.getComponent_set();
                for(int j = 0; j < components.size(); j++) {
                    dao1.updateComponentStatus(components.get(j));
                }
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
            if(!((recipe.getComponent_set().size() == 0) && recipe.getId() == -1)) {
                if(recipe.getId() != -1) {
                    List<Component> components = recipe.getComponent_set();
                    for (int j = 0; j < components.size(); j++)
                        components.get(j).setAmount(components.get(j).getAmount() * (recipe.getWeightInPlanBasket() + recipe.getWeightInRecipeBasket()) / recipe.getTotal_amount());
                }
                basket.add(recipe);
            }
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
                recipe.setWeightInPlanBasket(0);
                recipe.setWeightInRecipeBasket(0);
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
                    addRecipe(recipe);
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
            double total = 0.0;
            for (int i = 0; i < items.size(); i++) {
                PlanItem item = items.get(i);
                total += item.gettCalories();
                item.setDayPlan(dayPlan);
                addPlanItem(item);
            }
            dayPlan.setCalories(total);
            dao.add(dayPlan);
        }
        return dayPlan;
    }

    /**
     * delete index recipe or component in the plan item
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
     *  punch plan item
     * @param item
     */
    public void punch(PlanItem item) {
        PlanItemDao dao = new PlanItemDao(context);
        dao.add(item);
        DayPlanDao dao1 = new DayPlanDao(context);
        DayPlan dayPlan = item.getDayPlan();
        dao1.add(dayPlan);
    }


    /**
     * set plan is using
     * @param plan
     */
    public void setPlanUsed(SeriesPlan plan) {
        PlanInUseDao dao = new PlanInUseDao(context);
        PlanInUse planInUse = new PlanInUse();
        planInUse.setDays(plan.getDays());
        planInUse.setName(plan.getName());
        planInUse.setStartDate(Common.getDate());
        planInUse.setDateplans(plan.getDatePlans());
        dao.setPlanInUse(planInUse);

        for (int i = 0; i < 7; i++) {
            DatePlan datePlan = planInUse.getDateplans().get(i % plan.getDays());
            datePlan.setDate(Common.getSomeDay(Common.getDate(), i));
            datePlan.setPlan_name(plan.getName());
            addDatePlan(datePlan);
        }
    }

    /**
     * set plan unused
     * @param plan
     */
    public void setPlanUnUsed(SeriesPlan plan) {
        PlanInUseDao dao = new PlanInUseDao(context);
        dao.setPlanNotInUse();
        for (int i = 0; i < 7; i++) {
            DatePlan datePlan = new DatePlan();
            datePlan.setPlan_name("自定义计划");
            datePlan.setDate(Common.getSomeDay(Common.getDate(), i));
            datePlan.setItems(generateDatePlan());
            addDatePlan(datePlan);
        }
    }


    /**
     * get plan in use
     * @return PlanInUse
     */
    public PlanInUse getSeriesPlanNowUsed() {
        PlanInUseDao dao = new PlanInUseDao(context);
        return dao.getPlanInUse();
    }

    /**
     *
     * @param start
     * @param end
     * @return Map<String, DatePlan>
     */
    public Map<String, DatePlan> getDatePlan(String start, String end) {
        Map<String, DatePlan> data = new HashMap<>();
        DatePlanDao dao = new DatePlanDao(context);
        PlanInUseDao dao1 = new PlanInUseDao(context);
        List<DatePlan> datePlans = dao.getById(start, end);
        PlanInUse planInUse = dao1.getPlanInUse();
        if(datePlans.size() == 0) {
            if(planInUse == null) {
                while (Common.CompareDate(start, end) <= 0) {
                    DatePlan datePlan = new DatePlan();
                    datePlan.setPlan_name("自定义计划");
                    datePlan.setDate(start);
                    datePlan.setItems(generateDatePlan());
                    data.put(datePlan.getDate(), datePlan);
                    start = Common.getSomeDay(start, 1);
                }
            }else {
                List<DatePlan> datePlans1 = planInUse.getDateplans();
                int index = Common.getDiff(start, planInUse.getStartDate());
                if(index < 0)
                    start = planInUse.getStartDate();
                while (Common.CompareDate(start, end) <= 0) {
                    DatePlan datePlan = new DatePlan();
                    datePlan.setPlan_name(planInUse.getName());
                    datePlan.setDate(start);
                    datePlan.setItems(datePlans1.get(index % planInUse.getDays()).getItems());
                    data.put(datePlan.getDate(), datePlan);
                    start = Common.getSomeDay(start, 1);
                }
            }
        }else {
            for(int i = 0; i < datePlans.size(); i++) {
                data.put(datePlans.get(i).getDate(), datePlans.get(i));
            }
            Collections.sort(datePlans);
            start = Common.getSomeDay(datePlans.get(datePlans.size() - 1).getDate(), 1);
            if(planInUse == null) {
//                if(!datePlans.get(datePlans.size() - 1).getPlan_name().equals("自定义计划"))
//                    start =
                while (Common.CompareDate(start, end) <= 0) {
                    DatePlan datePlan = new DatePlan();
                    datePlan.setPlan_name("自定义计划");
                    datePlan.setDate(start);
                    datePlan.setItems(generateDatePlan());
                    data.put(datePlan.getDate(), datePlan);
                    start = Common.getSomeDay(start, 1);
                }
            }else {
                List<DatePlan> datePlans1 = planInUse.getDateplans();
                int index = Common.getDiff(start, planInUse.getStartDate());
                if(index < 0)
                    start = planInUse.getStartDate();
                while (Common.CompareDate(start, end) <= 0) {
                    DatePlan datePlan = new DatePlan();
                    datePlan.setPlan_name(planInUse.getName());
                    datePlan.setDate(start);
                    datePlan.setItems(datePlans1.get(index % planInUse.getDays()).getItems());
                    data.put(datePlan.getDate(), datePlan);
                    start = Common.getSomeDay(start, 1);
                }
            }
        }
        return data;
    }

    public ArrayList<DatePlanItem> generateDatePlan() {
        ArrayList<DatePlanItem> items = new ArrayList<>();
        DatePlanItem item1 = new DatePlanItem();
        item1.setCalories_need(1000);
        item1.setCarbohydrate_need(1000);
        item1.setFat_need(1000);
        item1.setProtein_need(1000);
        item1.setImageCover("drawable://" + R.drawable.breakfast);
        item1.setTime("08:30am");
        item1.setType("breakfast");


        DatePlanItem item2 = new DatePlanItem();
        item2.setCalories_need(1000);
        item2.setCarbohydrate_need(1000);
        item2.setFat_need(1000);
        item2.setProtein_need(1000);
        item2.setImageCover("drawable://" + R.drawable.add_meal_01);
        item2.setTime("10:30am");
        item2.setType("add_meal_01");


        DatePlanItem item3 = new DatePlanItem();
        item3.setCalories_need(1000);
        item3.setCarbohydrate_need(1000);
        item3.setFat_need(1000);
        item3.setProtein_need(1000);
        item3.setImageCover("drawable://" + R.drawable.lunch);
        item3.setTime("12:30am");
        item3.setType("lunch");

        DatePlanItem item4 = new DatePlanItem();
        item4.setCalories_need(1000);
        item4.setCarbohydrate_need(1000);
        item4.setFat_need(1000);
        item4.setProtein_need(1000);
        item4.setImageCover("drawable://" + R.drawable.add_meal_02);
        item4.setTime("15:30am");
        item4.setType("add_meal_02");

        DatePlanItem item5 = new DatePlanItem();
        item5.setCalories_need(1000);
        item5.setCarbohydrate_need(1000);
        item5.setFat_need(1000);
        item5.setProtein_need(1000);
        item5.setImageCover("drawable://" + R.drawable.dinner);
        item5.setTime("18:30am");
        item5.setType("supper");


        DatePlanItem item6 = new DatePlanItem();
        item6.setCalories_need(1000);
        item6.setCarbohydrate_need(1000);
        item6.setFat_need(1000);
        item6.setProtein_need(1000);
        item6.setImageCover("drawable://" + R.drawable.add_meal_03);
        item6.setTime("22:30am");
        item6.setType("add_meal_03");

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        return items;
    }

    public void addDatePlan(DatePlan datePlan) {
        DatePlanDao dao = new DatePlanDao(context);
        dao.addDatePlan(datePlan);
    }
}
