import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-manage-todo',
  imports: [MatChipsModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './manage-todo.html',
  styleUrl: './manage-todo.scss',
})
export class ManageTodo {}
