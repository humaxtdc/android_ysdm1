package com.example.kjeom.ysdm_01;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {
    private final String[] mItemStrings = {"Display", "aaa"};

    public class MainMenuViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MainMenuViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            mTextView = (TextView)v.findViewById(R.id.mainMenuItemView);
        }

        public void setText(int position, String name) {
            mTextView.setText(String.format("%d. %s", position + 1, name));
        }
    }

    @Override
    public MainMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Context를 부모로 부터 받아와서
        Context context = parent.getContext();

        //받은 Context를 기반으로 LayoutInflater를 생성
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //생성된 LayoutInflater로 어떤 Layout을 가져와서 어떻게 View를 그릴지 결정
        View v = layoutInflater.inflate(R.layout.main_menu_item, parent, false);

        return new MainMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainMenuViewHolder vh, int position) {
        vh.setText(position, mItemStrings[position]);
    }

    @Override
    public int getItemCount() {
        return mItemStrings.length;
    }
}
