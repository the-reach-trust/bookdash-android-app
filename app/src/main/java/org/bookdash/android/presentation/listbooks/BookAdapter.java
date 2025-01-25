package org.bookdash.android.presentation.listbooks;

import static org.bookdash.android.presentation.utils.StringUtils.convertGsUrlToHttp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.bookdash.android.R;
import org.bookdash.android.config.GlideApp;
import org.bookdash.android.domain.model.firebase.FireBookDetails;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import timber.log.Timber;

/**
 * @author Rebecca Franks
 * @since 2015/07/16 2:06 PM
 */
public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private final Context context;
    private List<FireBookDetails> bookDetails;
    private View.OnClickListener onClickListener;


    public BookAdapter(List<FireBookDetails> bookDetails, Context context, View.OnClickListener onClickListener) {
        this.bookDetails = bookDetails;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new BookViewHolder(v);
    }


    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        FireBookDetails bookDetail = bookDetails.get(position);
        holder.bookTitle.setText(bookDetail.getBookTitle());

        String bookCoverImage = bookDetail.getBookCoverPageUrl();
        if (bookCoverImage != null) {
            Timber.tag("BookAdapter").d("Book url:%s", bookCoverImage);
            String firebaseUrl = convertGsUrlToHttp(bookCoverImage);

            if (firebaseUrl != null) {
                GlideApp.with(context)
                        .load(firebaseUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.bookdash_placeholder)
                        .error(R.drawable.bookdash_placeholder)
                        .into(holder.bookCover);
            } else {
                // Handle error: possibly use a fallback image if conversion fails
                holder.bookCover.setImageResource(R.drawable.bookdash_placeholder);
            }
        } else {
            // Handle case where URL is null
            holder.bookCover.setImageResource(R.drawable.bookdash_placeholder);
        }

        holder.bookDetail = bookDetail;
        holder.downloadedIcon.setVisibility(bookDetail.isDownloadedAlready() ? View.VISIBLE : View.INVISIBLE);
        holder.cardContainer.setTag(holder);
        holder.cardContainer.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return bookDetails.size();
    }
}