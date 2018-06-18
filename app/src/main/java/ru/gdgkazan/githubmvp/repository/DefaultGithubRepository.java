package ru.gdgkazan.githubmvp.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.RxUtils;
import ru.gdgkazan.githubmvp.api.ApiFactory;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.CommitResponse;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.utils.AuthorizationUtils;
import rx.Observable;

public class DefaultGithubRepository implements GithubRepository {

    @NonNull
    @Override
    public Observable<List<CommitResponse>> commits(@NonNull String user, @NonNull String repo) {
        return ApiFactory.getGithubService()
                .commits(user, repo)
                .flatMap(commitResponses -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(CommitResponse.class);
                        realm.insert(commitResponses);
                    });
                    return Observable.just(commitResponses);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<CommitResponse> commits = realm.where(CommitResponse.class).findAll();
                    return Observable.just(realm.copyFromRealm(commits));
                })
                .compose(RxUtils.async());
    }
        @NonNull
        @Override
        public Observable<List<Repository>> repositories() {
            return ApiFactory.getGithubService()
                    .repositories()
                    .flatMap(repositories -> {
                        Realm.getDefaultInstance().executeTransaction(realm -> {
                            realm.delete(Repository.class);
                            realm.insert(repositories);
                        });
                        return Observable.just(repositories);
                    })
                    .onErrorResumeNext(throwable -> {
                        Realm realm = Realm.getDefaultInstance();
                        RealmResults<Repository> repositories = realm.where(Repository.class).findAll();
                        return Observable.just(realm.copyFromRealm(repositories));
                    })
                    .compose(RxUtils.async());
        }

        @NonNull
        public Observable<Authorization> auth(@NonNull String login, @NonNull String password) {
            String authorizationString = AuthorizationUtils.createAuthorizationString(login, password);
            return ApiFactory.getGithubService()
                    .authorize(authorizationString, AuthorizationUtils.createAuthorizationParam())
                    .flatMap(authorization -> {
                        KeyValueStorage storage = RepositoryProvider.provideKeyValueStorage();
                        storage.saveToken(authorization.getToken());
                        storage.saveUserName(login);
                        ApiFactory.recreate();
                        return Observable.just(authorization);
                    })
                    .doOnError(throwable -> RepositoryProvider.provideKeyValueStorage().clear())
                    .compose(RxUtils.async());
        }
    }
