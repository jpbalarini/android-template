package com.fsstudio.template.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.fsstudio.template.base.ModularPreferenceFragment;
import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;


public class RxPreferenceFragment extends ModularPreferenceFragment {
  final Subject<LifeCycleEvent, LifeCycleEvent> events = PublishSubject.create();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    events.onNext(LifeCycleEvent.CREATE);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    events.onNext(LifeCycleEvent.VIEW_CREATED);
  }

  @Override public void onResume() {
    super.onResume();
    events.onNext(LifeCycleEvent.RESUME);
  }

  @Override public void onPause() {
    events.onNext(LifeCycleEvent.PAUSE);
    super.onPause();
  }

  @Override public void onDestroyView() {
    events.onNext(LifeCycleEvent.DESTROY_VIEW);
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    events.onNext(LifeCycleEvent.DESTROY);
    super.onDestroy();
  }

  protected @NotNull Observable<LifeCycleEvent> pauses() {
    return events.filter(ev -> ev == LifeCycleEvent.PAUSE);
  }

  protected @NotNull Observable<LifeCycleEvent> destroyViews() {
    return events.filter(ev -> ev == LifeCycleEvent.DESTROY_VIEW);
  }

  protected RxActivity activity() {
    return (RxActivity) getActivity();
  }
}
