package com.example.classviewer;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

public class ClassAdapter  extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    List<Class> classes;
    Context context;


    public ClassAdapter(List<Class> classes, Context context) {
        this.classes = classes;
        this.context = context;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_class,parent,false);
        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ClassViewHolder holder, int position) {
        if(classes != null){
            Class currentClass = classes.get(position);
            holder.bindTo(currentClass);
        }
    }

    @Override
    public int getItemCount() {
        if (classes != null){
            return classes.size();
        }
        return 0;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder{

        private TextView className;
        private TextView time;
        private TextView lecturer;
        private TextView location;


        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className=  itemView.findViewById(R.id.class_name);
            lecturer = itemView.findViewById(R.id.class_lecturer);
            location = itemView.findViewById(R.id.class_location);
            time = itemView.findViewById(R.id.class_time);
        }

        public void bindTo(Class currentClass) {
            //set all the text views
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            className.setText(currentClass.getTitle());
            lecturer.setText(currentClass.getLecturer());
            location.setText(currentClass.getLocation());
            if (currentClass.isAllDay()){
                time.setText("All day");
            }else{



                itemView.setLayoutParams(params);
                time.setText(currentClass.getHourMinute());
            }

        }
    }
}
