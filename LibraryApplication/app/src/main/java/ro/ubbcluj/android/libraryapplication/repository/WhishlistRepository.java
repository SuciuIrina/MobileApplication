package ro.ubbcluj.android.libraryapplication.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;

/**
 * Created by dell on 12/20/2017.
 */

public class WhishlistRepository {
    private List<Whishlist> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public WhishlistRepository(final AppDatabase appDatabase) {
        this.repo=new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.whishlistDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(final Whishlist whishlist) {
        repo.add(whishlist);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.whishlistDao().insertAll(whishlist);
            }
        });
    }

    public void update(final Whishlist whishlist) {
        Whishlist newWhishlist=null;
        for (Whishlist w : repo) {
            if(w.getUserId()==whishlist.getUserId() && w.getBookId()==whishlist.getBookId()){
                newWhishlist=whishlist;
                break;
            }
        }

        newWhishlist.setDate(whishlist.getDate());

        final Whishlist whishlistUpdated = newWhishlist;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.whishlistDao().update(whishlistUpdated);
            }
        });
    }

    public void delete(final Whishlist whishlist){
        for(Whishlist w:repo){
            if(w.getBookId()==whishlist.getBookId() && w.getUserId()==whishlist.getUserId()){
                repo.remove(w);
                final Whishlist whishlistDelete = w;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.whishlistDao().delete(whishlistDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Whishlist> getWhishlistByBookId(int bookId){
        List<Whishlist> list=new ArrayList<>();
        for(Whishlist w:repo){
            if(w.getBookId()==bookId){
                list.add(w);
            }
        }

        return list;
    }

    public List<Whishlist> getRepo() {
        return repo;
    }
}
