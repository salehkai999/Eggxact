package com.se491.eggxact.ui.landingpage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import static androidx.recyclerview.widget.ItemTouchHelper.*;

import com.se491.eggxact.R;

public class SwipeDeleteCallback extends ItemTouchHelper.SimpleCallback{

    private final FavAdapter favAdapter;
    private final Drawable swipeLeftIcon;
    private static final String TAG = "SwipeDeleteCallback";
    private ColorDrawable background;

    public SwipeDeleteCallback( FavAdapter favAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.favAdapter = favAdapter;
        swipeLeftIcon = ContextCompat.getDrawable(favAdapter.getContext(), R.drawable.outline_delete_sweep_black_20);
        background = new ColorDrawable(Color.WHITE);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT);
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull  RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Log.d(TAG, "onSwiped: "+position);
        favAdapter.deleteFave(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;


        if (dX < 0) {

            int iconMargin = (itemView.getHeight() - swipeLeftIcon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - swipeLeftIcon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + swipeLeftIcon.getIntrinsicHeight();

            background = new ColorDrawable(Color.WHITE);

            int iconLeft = itemView.getRight() - iconMargin - swipeLeftIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            swipeLeftIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
            background.draw(c);
            swipeLeftIcon.draw(c);
        } else {
            background.setBounds(0, 0, 0, 0);
        }
    }
}
