import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTodo, Todo, UpdateTodo } from './todo.types';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class TodoService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/todos`;

  list(): Observable<Todo[]> {
    return this.http.get<Todo[]>(this.baseUrl);
  }
  get(id: Todo['id']): Observable<Todo> {
    return this.http.get<Todo>(`${this.baseUrl}/${id}`);
  }
  create(todo: CreateTodo): Observable<Todo> {
    return this.http.post<Todo>(this.baseUrl, todo);
  }
  update(id: Todo['id'], todo: UpdateTodo): Observable<Todo> {
    return this.http.put<Todo>(`${this.baseUrl}/${id}`, todo);
  }
  remove(id: Todo['id']): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
