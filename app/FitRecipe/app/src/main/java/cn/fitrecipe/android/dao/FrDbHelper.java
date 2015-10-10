package cn.fitrecipe.android.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Basket;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.PlanInUse;
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
     * @return Report
     */
    public Report getReport() {
        ReportDao dao = new ReportDao(context);
        return dao.getReport();
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
        new ReportDao(context).clear();
    }

    public Author getLoginAuthor() {
        return new AuthorDao(context).getAuthor();
    }


    /**
     * set plan is using
     * @param plan
     */
    public void setPlanUsed(SeriesPlan plan) {
        PlanInUseDao dao = new PlanInUseDao(context);
        PlanInUse planInUse = new PlanInUse();
        planInUse.setDays(plan.getTotal_days());
        planInUse.setName(plan.getTitle());
        planInUse.setStartDate(Common.getDate());
        planInUse.setDateplans(plan.getDatePlans());
        dao.setPlanInUse(planInUse);

        for (int i = 0; i < 7; i++) {
            DatePlan datePlan = planInUse.getDateplans().get(i % plan.getTotal_days());
            datePlan.setDate(Common.getSomeDay(Common.getDate(), i));
            datePlan.setPlan_name(plan.getTitle());
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
     * get dateplans from start to end
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
                if(Common.CompareDate(start, Common.getDate()) < 0)
                    start = Common.getDate();
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

    /**
     *  generate a custom dateplan
     * @return list of dateplanitem
     */
    public ArrayList<DatePlanItem> generateDatePlan() {
        ArrayList<DatePlanItem> items = new ArrayList<>();
        DatePlanItem item1 = new DatePlanItem();
        item1.setCalories_need(1000);
        item1.setCarbohydrate_need(1000);
        item1.setFat_need(1000);
        item1.setProtein_need(1000);
        item1.setDefaultImageCover("drawable://" + R.drawable.breakfast);
        item1.setTime("08:30am");
        item1.setType("breakfast");


        DatePlanItem item2 = new DatePlanItem();
        item2.setCalories_need(1000);
        item2.setCarbohydrate_need(1000);
        item2.setFat_need(1000);
        item2.setProtein_need(1000);
        item2.setDefaultImageCover("drawable://" + R.drawable.add_meal_01);
        item2.setTime("10:30am");
        item2.setType("add_meal_01");


        DatePlanItem item3 = new DatePlanItem();
        item3.setCalories_need(1000);
        item3.setCarbohydrate_need(1000);
        item3.setFat_need(1000);
        item3.setProtein_need(1000);
        item3.setDefaultImageCover("drawable://" + R.drawable.lunch);
        item3.setTime("12:30am");
        item3.setType("lunch");

        DatePlanItem item4 = new DatePlanItem();
        item4.setCalories_need(1000);
        item4.setCarbohydrate_need(1000);
        item4.setFat_need(1000);
        item4.setProtein_need(1000);
        item4.setDefaultImageCover("drawable://" + R.drawable.add_meal_02);
        item4.setTime("15:30am");
        item4.setType("add_meal_02");

        DatePlanItem item5 = new DatePlanItem();
        item5.setCalories_need(1000);
        item5.setCarbohydrate_need(1000);
        item5.setFat_need(1000);
        item5.setProtein_need(1000);
        item5.setDefaultImageCover("drawable://" + R.drawable.dinner);
        item5.setTime("18:30am");
        item5.setType("supper");


        DatePlanItem item6 = new DatePlanItem();
        item6.setCalories_need(1000);
        item6.setCarbohydrate_need(1000);
        item6.setFat_need(1000);
        item6.setProtein_need(1000);
        item6.setDefaultImageCover("drawable://" + R.drawable.add_meal_03);
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

    /**
     * save date plan when create or update
     * @param datePlan
     */
    public void addDatePlan(DatePlan datePlan) {
        DatePlanDao dao = new DatePlanDao(context);
        dao.addDatePlan(datePlan);
    }

    /**
     * add list of plancomponent to basket
     * @param components
     */
    public void addToBasket(List<PlanComponent> components) {
        BasketDao dao = new BasketDao(context);
        Basket basket = dao.getBasket();
        if(basket == null)  basket = new Basket();
        List<PlanComponent> componentList = basket.getContent();
        if(componentList == null)
            basket.setContent(components);
        else {
            if(components != null) {
                for (int i = 0; i < components.size(); i++) {
                    boolean flag = true;
                    for(int j = 0; j < componentList.size(); j++)
                        if(components.get(i).getName().equals(componentList.get(j).getName())) {
                            componentList.get(j).setAmount(componentList.get(j).getAmount() + components.get(i).getAmount());
                            flag = false;
                            break;
                        }
                    if(flag)
                        componentList.add(components.get(i));
                }
            }
            basket.setContent(componentList);
        }
        dao.add(basket);
    }

    /**
     * add a plan component to basket
     * @param component
     */
    public void addToBasket(PlanComponent component) {
        List<PlanComponent> components = new ArrayList<>();
        components.add(component);
        addToBasket(components);
    }

    /**
     * remove components from basket
     * @param components
     */
    public void removeFromBasket(List<PlanComponent> components) {
        BasketDao dao = new BasketDao(context);
        Basket basket = dao.getBasket();
        List<PlanComponent> componentList = basket.getContent();
        if(componentList == null)
            basket.setContent(components);
        else {
            if(components != null) {
                for (int i = 0; i < components.size(); i++) {
                    for(int j = 0; j < componentList.size(); j++)
                        if(components.get(i).getName().equals(componentList.get(j).getName())) {
                            componentList.get(j).setAmount(componentList.get(j).getAmount() - components.get(i).getAmount());
                            if(componentList.get(j).getAmount() == 0)
                                componentList.remove(j);
                            break;
                        }
                }
            }
            basket.setContent(componentList);
        }
        dao.add(basket);
    }
    /**
     * clear basket
     */
    public void clearBasket() {
        BasketDao dao = new BasketDao(context);
        dao.add(new Basket());

        DatePlanDao dao2 = new DatePlanDao(context);
        List<DatePlan> list = dao2.getInBasket();
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                DatePlan datePlan = list.get(i);
                List<DatePlanItem> items = datePlan.getItems();
                for(int j = 0; j < items.size(); j++) {
                    items.get(j).setIsInBasket(false);
                }
                datePlan.setItems(items);
                datePlan.setInBasket(false);
                dao2.addDatePlan(datePlan);
            }
        }
    }

    /**
     * save the whole basket
     * @param basket
     */
    public void saveBasket(Basket basket) {
        BasketDao dao = new BasketDao(context);
        dao.add(basket);
    }

    /**
     * get content of basketN
     * @return Basket
     */
    public Basket getBasket() {
        BasketDao dao = new BasketDao(context);
        return dao.getBasket();
    }

    public List<DatePlan> getPunchDatePlans() {
        DatePlanDao datePlanDao = new DatePlanDao(context);
        List<DatePlan> datePlans = datePlanDao.getPunchDatePlans();
        for(int i = 0; i < datePlans.size(); i++) {
            List<DatePlanItem> items = datePlans.get(i).getItems();
            for(int j = 0; j < items.size(); ) {
                if(!items.get(j).isPunch())
                    items.remove(j);
                else
                    j++;
            }
            datePlans.get(i).setItems(items);
        }
        return datePlans;
    }
}
