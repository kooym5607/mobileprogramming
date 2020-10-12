package edu.mobile.homework1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;


import edu.mobile.homework1.Adapter.ProductListAdapter;
import edu.mobile.homework1.R;

public class ProductFragment extends ListFragment {
    private ListView product_ListView;
    private ProductListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        listAdapter = new ProductListAdapter(this.getContext());
        product_ListView = (ListView)v.findViewById(android.R.id.list);
        product_ListView.setAdapter(listAdapter);

        listAdapter.addItem(R.drawable.pencil,"pencil",200);
        listAdapter.addItem(R.drawable.eraser,"eraser",500);
        listAdapter.addItem(R.drawable.pen,"pen",1000);

        listAdapter.notifyDataSetChanged();


        return v;
    }

}
