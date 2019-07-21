package com.murach.winr;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.WineViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    public WineAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }
    public class WineViewHolder extends RecyclerView.ViewHolder {
        public TextView redWineTextView;

        public WineViewHolder(@NonNull View itemView) {
            super(itemView);

            redWineTextView = itemView.findViewById(R.id.red_wine_recycler_view_text_view);
        }
    }
    @NonNull
    @Override
    public WineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.wine_item, parent, false);
        return new WineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WineViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String date = mCursor.getString(mCursor.getColumnIndex(WineContract.WineEntry.COLUMN_DATE));
        String day = mCursor.getString(mCursor.getColumnIndex(WineContract.WineEntry.COLUMN_DAY));
        String time = mCursor.getString(mCursor.getColumnIndex(WineContract.WineEntry.COLUMN_TIME));
        String height = mCursor.getString(mCursor.getColumnIndex(WineContract.WineEntry.COLUMN_PREDFT));
        String out = date + " " + day + " " + time + " " + height + "ft";

        holder.redWineTextView.setText(out);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
