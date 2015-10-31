package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.ThemeActivity;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.RequestErrorHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCardAdapter extends RecyclerView.Adapter<ThemeCardAdapter.ThemeCardViewHolder>{

    private List<Theme> themeCardsList;
    private List<Integer> themeCardsListID;
    private Context context;
    private boolean isCollect;

    public ThemeCardAdapter(Context context, List<Theme> recipeCardsList) {
        this.context = context;
        this.themeCardsList = recipeCardsList;
        this.themeCardsListID = null;
        this.isCollect = false;
    }

    public ThemeCardAdapter(Context context, List<Theme> recipeCardsList, List<Integer> themeCardsListID, boolean isCollect) {
        this.context = context;
        this.themeCardsList = recipeCardsList;
        this.themeCardsListID = themeCardsListID;
        this.isCollect = isCollect;
    }

    @Override
    public ThemeCardAdapter.ThemeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_index_theme_item, viewGroup, false);
        return new ThemeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThemeCardAdapter.ThemeCardViewHolder contactViewHolder, final int i) {
        Theme tc = themeCardsList.get(i);
        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ThemeActivity.class);
                intent.putExtra("theme", themeCardsList.get(i));
                context.startActivity(intent);
            }
        });
        if (isCollect){
            contactViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Common.infoDialog(context, "取消收藏", "确认取消" + themeCardsList.get(i).getTitle() + "的收藏?")
                            .setCancelText("取消")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(final SweetAlertDialog sweetAlertDialog) {
                                    String url = FrServerConfig.getDeleteCollectionUrl("theme", themeCardsListID.get(i));
                                    PostRequest request = new PostRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject res) {
                                            themeCardsList.remove(i);
                                            themeCardsListID.remove(i);
                                            notifyDataSetChanged();
                                            sweetAlertDialog.dismiss();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            RequestErrorHelper.toast(context, volleyError);
                                        }
                                    });
                                    FrRequest.getInstance().request(request);
                                }
                            }).show();
                    return false;
                }
            });
        }
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.theme_background, tc.getThumbnail());
    }

    @Override
    public int getItemCount() {
        if(themeCardsList == null)
            return 0;
        else
            return themeCardsList.size();
    }

    public static class ThemeCardViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout theme_background;
        public View itemView;

        public ThemeCardViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            theme_background = (LinearLayout) itemView.findViewById(R.id.theme_image);
        }
    }
}
