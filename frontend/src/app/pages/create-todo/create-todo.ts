import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DateAdapter, provideNativeDateAdapter } from '@angular/material/core';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TodoStore } from '../../service/todo.store';
import { Todo } from '../../service/todo.types';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-create-todo',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatChipsModule,
    MatIconModule,
  ],
  templateUrl: './create-todo.html',
  styleUrl: './create-todo.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateTodo {
  readonly tags = signal([] as string[]);
  private formBuilder = inject(FormBuilder);

  constructor(
    private store: TodoStore,
    private router: Router,
    private dateAdapter: DateAdapter<Date>
  ) {
    this.dateAdapter.setLocale('nl-NL');
  }

  announcer = inject(LiveAnnouncer);

  form = this.formBuilder.group({
    name: ['', [Validators.required, Validators.minLength(2)]],
    description: [''],
    tags: [''],
    deadline: [''],
  });

  onSubmit() {
    const tags = this.tags();
    const todo = { ...this.form.value } as Todo;
    const deadline = todo.deadline as string;
    let formattedDate = '';

    if (deadline.length > 0) {
      formattedDate = formatDate(todo.deadline as string, 'yyyy-MM-dd', 'en');
    }

    todo.tags = tags;

    this.store.create(todo).subscribe();
    this.router.navigate(['/overview']);
  }

  removeTag(tag: string) {
    this.tags.update((tags) => {
      const index = tags.indexOf(tag);
      if (index < 0) {
        return tags;
      }

      tags.splice(index, 1);
      this.announcer.announce(`Verwijder ${tag}`);
      return [...tags];
    });
  }

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.tags.update((tags) => [...tags, value]);
      this.announcer.announce(`Voeg ${value} toe`);
    }

    event.chipInput!.clear();
  }
}
