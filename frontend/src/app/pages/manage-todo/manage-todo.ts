/**
 * https://stackoverflow.com/questions/76264067/takeuntildestroyed-can-only-be-used-within-an-injection-context
 */
import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TodoStore } from '../../service/todo.store';
import { Todo } from '../../service/todo.types';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Toaster } from '../../components/toaster/toaster';

@Component({
  selector: 'app-manage-todo',
  imports: [MatChipsModule, MatSlideToggleModule, MatButtonModule, RouterLink],
  templateUrl: './manage-todo.html',
  styleUrl: './manage-todo.scss',
})
export class ManageTodo implements OnInit {
  private destroyRef = inject(DestroyRef);
  public todo: Todo | undefined;
  private _toaster = inject(MatSnackBar);

  constructor(private route: ActivatedRoute, private store: TodoStore, private router: Router) {}

  deleteTodo(id: Todo['id']): void {
    this.store.remove(id).subscribe();
    this.router.navigate(['/overview']);
  }

  toggleComplete(id: Todo['id']) {
    const todo = { ...this.todo };
    todo.completed = !todo.completed;

    this.openToaster(todo.completed ? 'Todo voltooid! 🎉' : 'Todo voltooid ongedaan gemaakt!');

    this.store.update(id, todo).subscribe();
  }

  openToaster(message: string) {
    this._toaster.openFromComponent(Toaster, {
      data: { message },
      duration: 7000,
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.store.loadById(id as Todo['id']);
    this.store
      .todoById$(id as Todo['id'])
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((todo) => {
        this.todo = todo;
      });
  }
}
