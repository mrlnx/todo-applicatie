import { AfterViewInit, Component, inject, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSort, Sort, MatSortModule } from '@angular/material/sort';

export interface PeriodicElement {
  complete: boolean;
  name: string;
  deadline: string;
  edit: boolean;
}

const date = new Date().toLocaleDateString('nl-NL').replace(/\//g, '-');

const ELEMENT_DATA: PeriodicElement[] = [
  { complete: true, name: 'Write unit tests', deadline: date, edit: true },
  { complete: true, name: 'Write e2e tests', deadline: date, edit: true },
  { complete: true, name: 'Make user stories for dev team', deadline: date, edit: true },
  { complete: true, name: 'Make user storeis for infra team', deadline: date, edit: true },
];

@Component({
  selector: 'app-overview-todos',
  imports: [MatButtonModule, MatTableModule, MatCheckboxModule, MatIconModule, MatSortModule],
  templateUrl: './overview-todos.html',
  styleUrl: './overview-todos.scss',
})
export class OverviewTodos implements AfterViewInit {
  private _liveAnnouncer = inject(LiveAnnouncer);

  displayedColumns: string[] = ['complete', 'name', 'deadline', 'edit'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  @ViewChild(MatSort)
  sort: MatSort | undefined;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }
}
