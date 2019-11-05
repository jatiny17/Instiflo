package com.rohg007.android.instiflo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Event;

import java.util.ArrayList;
import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private ArrayList<Event> eventsList;
    private View.OnClickListener onItemClickListener;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    public EventsAdapter(ArrayList<Event> eventsList){
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell,parent,false);
        return new EventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        if(eventsList!=null && position<eventsList.size()){
            Event event = eventsList.get(position);
            holder.eventTitleTitleView.setText(event.getEventTitle());
            holder.eventDateTitleView.setText(event.getEventDate());
            holder.eventTimeTitleView.setText(event.getEventTime());
            holder.eventTitleContentView.setText(event.getEventTitle());
            holder.eventDateContentView.setText(event.getEventDate());
            holder.eventTimeContentView.setText(event.getEventTime());
            holder.eventLocationContentView.setText(event.getEventLocation());
            holder.eventDescriptionContentView.setText(event.getEventDescription());
        }
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onItemClickListener=itemClickListener;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder{

        TextView eventTitleTitleView;
        TextView eventDateTitleView;
        TextView eventTimeTitleView;

        TextView eventTitleContentView;
        TextView eventDateContentView;
        TextView eventTimeContentView;
        TextView eventLocationContentView;
        TextView eventDescriptionContentView;

        RelativeLayout relativeLayout;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTitleView = itemView.findViewById(R.id.event_title_title);
            eventDateTitleView = itemView.findViewById(R.id.event_title_date);
            eventTimeTitleView = itemView.findViewById(R.id.event_title_time);
            eventTitleContentView = itemView.findViewById(R.id.event_content_title);
            eventDateContentView = itemView.findViewById(R.id.event_content_date);
            eventTimeContentView = itemView.findViewById(R.id.event_content_time);
            eventLocationContentView = itemView.findViewById(R.id.event_content_location);
            eventDescriptionContentView = itemView.findViewById(R.id.event_content_description);
            relativeLayout=itemView.findViewById(R.id.detail_relative);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
