package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karan.materialtest.Activity.SubActivity;
import com.karan.materialtest.R;

import java.util.Collections;
import java.util.List;

import Models.DataStore;

/**
 * Created by Admin on 10/12/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    List<DataStore> mdDataStores = Collections.emptyList();
    MyViewHolder myViewHolder;

    public CustomAdapter(Context cxt, List<DataStore> mdDataStores) {
        inflater = LayoutInflater.from(cxt);
        this.mdDataStores = mdDataStores;
        this.context = cxt;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mvView = inflater.inflate(R.layout.custom_rows, parent, false);
        Log.d("ViewHolder", "OnCreateView");
        myViewHolder = new MyViewHolder(mvView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //Getting reference of each object from array list and setting up the data in every view holder.
        DataStore current = mdDataStores.get(position);
        Log.d("karan", "bindViewHolder called");
        myViewHolder.title.setText(current.title);
        myViewHolder.image_icon.setImageResource(current.icon_id);
        //First method to handle the onClicks in Recycler view
        /*holder.image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You clicked on item at"+position,Toast.LENGTH_LONG).show();
            }
        });*/
    }
    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }
    @Override
    public int getItemCount() {
        return mdDataStores.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image_icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Creating listener of the item and opening SubActivity on the click of it
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, SubActivity.class));
                    if(clickListener!=null)
                    {
                       clickListener.itemClicked(v,getPosition());
                    }
                }
            });
            title = (TextView) itemView.findViewById(R.id.disp_text_list);
            image_icon = (ImageView) itemView.findViewById(R.id.image_icon_drawer);
            image_icon.setOnClickListener(this);
        }

        //Method to delete each item from the recycler view when the item is clicked.
        public void delete(int position) {
            mdDataStores.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Item clicked at position" + getPosition(), Toast.LENGTH_LONG).show();
            //Method to call when the item is removed¿
            delete(getPosition());
        }

    }
    public interface ClickListener{
        public  void itemClicked(View v,int pos);

    }
}
