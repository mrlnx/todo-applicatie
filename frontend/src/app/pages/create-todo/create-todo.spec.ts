import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTodo } from './create-todo';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

describe('CreateTodo', () => {
  let component: CreateTodo;
  let fixture: ComponentFixture<CreateTodo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTodo],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateTodo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
