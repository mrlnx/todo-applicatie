export interface Todo {
  id: string;
  name: string;
  description?: string;
  tags?: string[];
  createdAt?: string;
  deadline?: string;
  completed?: boolean;
}

export type CreateTodo = Omit<Todo, 'id'>;
export type UpdateTodo = Partial<Omit<Todo, 'id'>>;
