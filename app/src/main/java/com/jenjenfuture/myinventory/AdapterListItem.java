package com.jenjenfuture.myinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListItem extends RecyclerView.Adapter<AdapterListItem.ViewHolder> {

    private  MainActivity mainActivity;
    DBProducts dbProducts;
    private List <Product> productList = new ArrayList<>() ;
    private Context context;

    public  AdapterListItem (MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.context = mainActivity.getApplicationContext();
        dbProducts = new DBProducts(context);
        productList.addAll(dbProducts.getAllProducts());
    }
    @NonNull
    @Override
    public AdapterListItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItem.ViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.productName.setText(product.name);
        holder.productQuantity.setText(String.valueOf(product.quantity));

        if (product.desc.length()>100){
            String str = product.desc.substring(0,100) + "...";
            holder.productDesc.setText(str);
        }
        else{
            holder.productDesc.setText(product.desc);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productDesc;
        TextView productQuantity;
        Context context;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();
            productName = itemView.findViewById(R.id.itemName);
            productDesc = itemView.findViewById(R.id.itemDesc);
            productQuantity = itemView.findViewById(R.id.itemQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,EditItemActivity.class);
                    intent.putExtra("id",productList.get(getAdapterPosition()).id);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("desc",productList.get(getAdapterPosition()).desc);
                    intent.putExtra("quan",productList.get(getAdapterPosition()).quantity);
                    context.startActivity(intent);
                }
            });
        }
    }
}
