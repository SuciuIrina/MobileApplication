package ro.ubbcluj.android.libraryapplication.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ro.ubbcluj.android.libraryapplication.model.User;

/**
 * Created by dell on 12/20/2017.
 */

public class UserRepository {
    private List<User> repo;
    private final AppDatabase appDatabase;
    private Executor executor= Executors.newSingleThreadExecutor();

    public UserRepository(final AppDatabase appDatabase) {
        this.repo=new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.userDao().getAll());
            }
        });

        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(final User user){
        repo.add(user);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().insertAll(user);
            }
        });
    }

    public  void update(final User user){
        User newUser=null;
        for(User u:repo){
            if(u.getId()==user.getId()){
                newUser=user;
                break;
            }
        }
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());

        final User userUpdated=newUser;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().update(userUpdated);
            }
        });
    }

    public List<User> getRepo() {
        return repo;
    }

    public User getUserById(final int id) {
        for (User b : repo) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public User getUserByIndex(Integer index){
        return repo.get(index);
    }
}
