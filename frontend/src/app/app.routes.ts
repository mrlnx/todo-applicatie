import { Routes } from '@angular/router';
import { CreateTodo } from './pages/create-todo/create-todo';
import { OverviewTodos } from './pages/overview-todos/overview-todos';
import { ManageTodo } from './pages/manage-todo/manage-todo';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'create',
    pathMatch: 'full',
  },
  {
    path: 'overview',
    component: OverviewTodos,
  },
  {
    path: 'create',
    component: CreateTodo,
  },
  {
    path: 'overview/:id',
    component: ManageTodo,
  },
];
