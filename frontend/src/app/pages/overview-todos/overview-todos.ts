import { AfterViewInit, Component, inject, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSort, Sort, MatSortModule } from '@angular/material/sort';
import { TodoStore } from '../../service/todo.store';
import { Todo } from '../../service/todo.types';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
export interface PeriodicElement {
  complete: boolean;
  name: string;
  deadline: string;
  edit: boolean;
}

@Component({
  selector: 'app-overview-todos',
  imports: [
    MatButtonModule,
    MatTableModule,
    MatCheckboxModule,
    MatIconModule,
    MatSortModule,
    RouterLink,
  ],
  templateUrl: './overview-todos.html',
  styleUrl: './overview-todos.scss',
})
export class OverviewTodos implements AfterViewInit, OnInit {
  private _liveAnnouncer = inject(LiveAnnouncer);

  displayedColumns: string[] = ['complete', 'name', 'deadline', 'edit'];
  dataSource = new MatTableDataSource<Partial<Todo>>();

  constructor(public store: TodoStore) {
    store.todo$.pipe(takeUntilDestroyed()).subscribe((rows) => {
      this.dataSource.data = rows ?? [];
    });
  }

  @ViewChild(MatSort)
  sort: MatSort | undefined;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.store.load();
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`${sortState.direction}ending gesorteerd`);
    } else {
      this._liveAnnouncer.announce('Gesorteerd gewist');
    }
  }
}
