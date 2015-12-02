package com.felipecsl.elifut.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felipecsl.elifut.R;
import com.felipecsl.elifut.models.Club;
import com.felipecsl.elifut.models.ClubStats;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

import static com.felipecsl.elifut.util.CollectionUtils.sort;
import static com.google.common.base.Preconditions.checkNotNull;

public final class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder> implements
    StickyRecyclerHeadersAdapter<ClubsAdapter.ViewHolder> {
  private final List<Club> clubs;
  private final Club selectedClub;

  public ClubsAdapter(List<Club> clubs, Club selectedClub) {
    this.selectedClub = checkNotNull(selectedClub);
    this.clubs = checkNotNull(sort(clubs, (c1, c2) ->
        c2.nonNullStats().points() - c1.nonNullStats().points()));
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(parent);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(position, clubs.get(position));
  }

  @Override public long getHeaderId(int position) {
    return 0;
  }

  @Override public long getItemId(int position) {
    return clubs.get(position).hashCode();
  }

  @Override public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
    return new HeaderViewHolder(parent);
  }

  @Override public void onBindHeaderViewHolder(ViewHolder holder, int position) {
    ((HeaderViewHolder) holder).bind();
  }

  @Override public int getItemCount() {
    return clubs.size();
  }

  public void setItems(List<Club> newItems) {
    clubs.clear();
    clubs.addAll(sort(newItems, (c1, c2) -> c2.nonNullStats().points() - c1.nonNullStats().points()));
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.layout) ViewGroup layout;
    @Bind(R.id.txt_position) TextView position;
    @Bind(R.id.txt_club_name) TextView clubName;
    @Bind(R.id.txt_points) TextView points;
    @Bind(R.id.txt_wins) TextView wins;
    @Bind(R.id.txt_draws) TextView draws;
    @Bind(R.id.txt_losses) TextView losses;
    @Bind(R.id.txt_goals_difference) TextView goalsDifference;
    @BindColor(android.R.color.transparent) int transparent;
    @BindColor(R.color.club_table_header_bg) int headerBg;
    @BindColor(R.color.club_table_header_text_color) int headerTextColor;

    ViewHolder(ViewGroup parent) {
      super(LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item, parent, false));
      ButterKnife.bind(this, itemView);
    }

    void bind(int pos, Club club) {
      ClubStats stats = club.nonNullStats();
      int typeface = selectedClub.equals(club) ? Typeface.BOLD : Typeface.NORMAL;
      position.setText(String.valueOf(pos + 1));
      points.setTypeface(null, typeface);
      points.setText(String.valueOf(stats.points()));
      clubName.setText(club.abbrev_name());
      clubName.setTypeface(null, typeface);
      wins.setTypeface(null, typeface);
      wins.setText(String.valueOf(stats.wins()));
      draws.setTypeface(null, typeface);
      draws.setText(String.valueOf(stats.draws()));
      losses.setTypeface(null, typeface);
      losses.setText(String.valueOf(stats.losses()));
      goalsDifference.setTypeface(null, typeface);
      goalsDifference.setText(String.valueOf(stats.goals()));
    }
  }

  private class HeaderViewHolder extends ViewHolder {
    HeaderViewHolder(ViewGroup parent) {
      super(parent);
    }

    void bind() {
      layout.setBackgroundColor(headerBg);
      clubName.setText(R.string.team);
      clubName.setTextColor(headerTextColor);
      clubName.setBackgroundColor(transparent);
      points.setText("P");
      points.setTextColor(headerTextColor);
      points.setBackgroundColor(transparent);
      wins.setText("W");
      wins.setTextColor(headerTextColor);
      wins.setBackgroundColor(transparent);
      draws.setText("D");
      draws.setTextColor(headerTextColor);
      draws.setBackgroundColor(transparent);
      losses.setText("L");
      losses.setTextColor(headerTextColor);
      losses.setBackgroundColor(transparent);
      goalsDifference.setText("G");
      goalsDifference.setTextColor(headerTextColor);
      goalsDifference.setBackgroundColor(transparent);
    }
  }
}
