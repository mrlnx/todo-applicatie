import { inject, Injectable } from '@angular/core';
import { TodoService } from './todo.service';
import { BehaviorSubject, catchError, finalize, map, tap } from 'rxjs';
import { CreateTodo, Todo, UpdateTodo } from './todo.types';

type State = {
  entities: Record<Todo['id'], Todo>;
  ids: string[];
  loading: boolean;
  error: string | null;
};

const initial: State = {
  entities: {},
  ids: [],
  loading: false,
  error: null,
};

@Injectable({ providedIn: 'root' })
export class TodoStore {
  private service = inject(TodoService);
  private state = new BehaviorSubject<State>(initial);
  readonly state$ = this.state.asObservable();

  readonly todo$ = this.state$.pipe(map((s) => s.ids.map((id) => s.entities[id])));
  readonly count$ = this.state$.pipe(
    map((s) => Object.values(s.entities).filter((todo) => !todo.completed).length)
  );
  readonly loading$ = this.state$.pipe(map((s) => s.loading));
  readonly error$ = this.state$.pipe(map((s) => s.error));

  todoById$(id: Todo['id']) {
    return this.state$.pipe(map((s) => s.entities[id]));
  }

  // helpers
  private set(s: State) {
    this.state.next(s);
  }

  private patch(p: Partial<State>) {
    this.set({ ...this.state.value, ...p });
  }

  private normalize(list: Todo[]): State {
    const entities: Record<Todo['id'], Todo> = {};
    const ids: Todo['id'][] = [];
    for (const todo of list) {
      entities[todo.id] = todo;
      ids.push(todo.id);
    }
    return { entities, ids, loading: false, error: null };
  }

  private upsert(items: Todo[]) {
    const state = this.state.value;
    const entities = { ...state.entities };
    const ids = new Set(state.ids);
    for (const p of items) {
      entities[p.id] = p;
      ids.add(p.id);
    }
    this.set({ ...state, entities, ids: Array.from(ids) });
  }

  private removeIds(idsToRemove: Todo['id'][]) {
    const state = this.state.value;
    const entities = { ...state.entities };
    for (const id of idsToRemove) delete entities[id];
    this.set({ ...state, entities, ids: state.ids.filter((id) => !idsToRemove.includes(id)) });
  }

  load() {
    this.patch({ loading: true, error: null });

    this.service
      .list()
      .pipe(
        tap((list) => this.set(this.normalize(list))),
        catchError((err) => {
          this.patch({ error: 'Failed' });
          throw err;
        }),
        finalize(() => this.patch({ loading: false }))
      )
      .subscribe();
  }

  loadById(id: Todo['id']) {
    this.patch({ loading: true, error: null });
    if (this.state.value.entities[id]) return;
    this.service.get(id).pipe(
      tap((t) => this.upsert([t])),
      catchError((err) => {
        this.patch({ error: 'Failed' });
        throw err;
      }),
      finalize(() => this.patch({ loading: false }))
    );
  }

  create(todo: CreateTodo) {
    this.patch({ loading: true, error: null });

    return this.service.create(todo).pipe(
      tap((t) => this.upsert([t])),
      catchError((err) => {
        this.patch({ error: 'Failed' });
        throw err;
      }),
      finalize(() => this.patch({ loading: false }))
    );
  }

  update(id: Todo['id'], todo: UpdateTodo) {
    this.patch({ loading: true, error: null });

    return this.service.update(id, todo).pipe(
      tap((t) => this.upsert([t])),
      catchError((err) => {
        this.patch({ error: 'Failed!' });
        throw err;
      }),
      finalize(() => this.patch({ loading: false }))
    );
  }

  remove(id: Todo['id']) {
    this.patch({ loading: true, error: null });

    return this.service.remove(id).pipe(
      tap(() => this.removeIds([id])),
      catchError((err) => {
        this.patch({ error: 'Failed!' });
        throw err;
      }),
      finalize(() => this.patch({ loading: false }))
    );
  }
}
