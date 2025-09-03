import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TodoStore } from '../../service/todo.store';
import { AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule, AsyncPipe, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header implements OnInit {
  constructor(public store: TodoStore) {}

  ngOnInit(): void {
    this.store.load();
  }
}
