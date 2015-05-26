
package kr.co.cashqc;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static kr.co.cashqc.BaseActivity.setCartCount;
import static kr.co.cashqc.Utils.insertMenuLevel2;
import static kr.co.cashqc.BaseActivity.sIsTTSmode;
public class ShopMenuAdapter extends BaseExpandableListAdapter {

    public ShopMenuAdapter(Context context, ShopMenuData data,
            DialogInterface.OnDismissListener onDismissListener, ShopPageActivity activity) {
        super();

        mData = data;

        inflater = LayoutInflater.from(context);

        mOnDismissListener = onDismissListener;

        mActivity = activity;
    }

    private ShopPageActivity mActivity;

    private DialogInterface.OnDismissListener mOnDismissListener;

    private ShopMenuData mData;

    private LayoutInflater inflater = null;

    private ViewHolder h = null;

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            h = new ViewHolder();
            v = inflater.inflate(R.layout.list_menuexpand_row, null);
            h.tvChildName = (TextView)v.findViewById(R.id.tv_child);
            h.tvChildValue = (TextView)v.findViewById(R.id.tv_value);
            h.ivThumb = (ImageView)v.findViewById(R.id.iv_thumb);
            v.setTag(h);
        } else {
            h = (ViewHolder)v.getTag();
        }

        final MenuData item = (MenuData)getChild(groupPosition, childPosition);

        h.tvChildName.setText(item.getLabel());
        String price = "가격 정보 없음";
        if (!item.getPrice().isEmpty()) {
            price = String.format("%,d 원", Integer.parseInt(item.getPrice()));
        }
        // Log.e("price", "" + item.getPrice());
        h.tvChildValue.setText(price);

        String imgUrl = "http://cashq.co.kr/adm/upload/thumb/1424842254UWDWC.jpg";

        // ImageLoader.getInstance().displayImage(imgUrl, h.ivThumb);
        if (sIsTTSmode) {
            // if (false) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean hasLevel3 = !item.getChild().isEmpty();

                    if (hasLevel3) {

                        OrderMenuDialog dialog = new OrderMenuDialog(inflater.getContext(), mData,
                                groupPosition, childPosition, mOnDismissListener);
                        dialog.show();
                        dialog.setOnDismissListener(mOnDismissListener);

                    } else {
                        insertMenuLevel2(inflater.getContext(), mData, groupPosition, childPosition);
                        setCartCount(mActivity);
                    }

                    // Toast.makeText(inflater.getContext(),
                    // "group : " + groupPosition + "\nchild : " +
                    // childPosition,
                    // Toast.LENGTH_SHORT).show();
                }
            });
        }
        return v;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            h = new ViewHolder();
            v = inflater.inflate(R.layout.list_menu_row, parent, false);
            v.setBackgroundColor(Color.rgb(237, 237, 237));
            h.tvGroupName = (TextView)v.findViewById(R.id.tv_group);
            h.ivImage = (ImageView)v.findViewById(R.id.iv_image);
            h.ivIndicator = (ImageView)v.findViewById(R.id.iv_indicator);
            v.setTag(h);
        } else {
            h = (ViewHolder)v.getTag();
        }

        if (isExpanded) {
            // h.ivImage.setBackgroundColor(Color.GREEN);
            h.ivIndicator.setImageResource(R.drawable.btn_list_close);
        } else {
            // h.ivImage.setBackgroundColor(Color.WHITE);
            h.ivIndicator.setImageResource(R.drawable.btn_list_open);
        }

        h.tvGroupName.setText(((MenuData)getGroup(groupPosition)).getLabel());

        return v;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.getMenu().get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.getMenu().get(groupPosition).getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.getMenu().get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mData.getMenu().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder {
        private ImageView ivImage, ivThumb, ivIndicator;

        private TextView tvGroupName;

        private TextView tvChildName;

        private TextView tvChildValue;
    }

}
