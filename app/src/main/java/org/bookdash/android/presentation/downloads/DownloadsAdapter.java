package org.bookdash.android.presentation.downloads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.bookdash.android.R;
import org.bookdash.android.config.GlideApp;
import org.bookdash.android.domain.model.firebase.FireBookDetails;
import org.bookdash.android.presentation.utils.StringUtils;

import java.util.List;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsViewHolder> {

    private final View.OnClickListener deleteClickListener;
    private final View.OnClickListener bookClickListener;
    private List<FireBookDetails> bookList;
    private Context context;

    public DownloadsAdapter(List<FireBookDetails> bookList, Context context, View.OnClickListener deleteClickListener, View.OnClickListener bookClickListener) {
        this.bookList = bookList;
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        this.bookClickListener = bookClickListener;
    }

    @Override
    public DownloadsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_download, parent, false);

        return new DownloadsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DownloadsViewHolder holder, int position) {
        FireBookDetails book = bookList.get(position);
        holder.downloadTitleTextView.setText(book.getBookTitle());
        String firebaseBookCoverUrl = StringUtils.convertGsUrlToHttp(book.getBookCoverPageUrl());
        GlideApp.with(context).load(firebaseBookCoverUrl).into(holder.downloadImageTextView);
        holder.downloadActionButtonView.setOnClickListener(deleteClickListener);
        holder.book = book;
        holder.downloadActionButtonView.setTag(holder);
        holder.itemView.setOnClickListener(bookClickListener);
        holder.itemView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        if (bookList == null) {
            return 0;
        }
        return bookList.size();
    }

    public void setBooks(List<FireBookDetails> books) {
        this.bookList = books;
    }
}
